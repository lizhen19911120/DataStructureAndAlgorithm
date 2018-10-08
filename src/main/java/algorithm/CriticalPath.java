package algorithm;

import java.util.*;
import java.util.stream.Collectors;

import static algorithm.TopSort.getVerList;

/**
 * 关键路径分析（无圈赋权图）,前提需要做好事件节点图的设计。
 * Created by lizhen on 2018/10/8.
 */
public class CriticalPath{

    /**
     * 用于存放VerTex<T>顶点列表
     */
    private static List<VerTex> cp_verList = new ArrayList<>();

    /**
     * 按拓扑排序顺序放置的顶点列表（邻接表表示）
     */
    private static Queue<VerTex> verQueue = new PriorityQueue<>((o1, o2) -> o1.getTopNum()>o2.getTopNum()?1:o1.getTopNum()<o2.getTopNum()?-1:0);
    /**
     * 按拓扑排序倒序放置的顶点列表（邻接表表示）
     */
    private static Queue<VerTex> verQueue1 = new PriorityQueue<>((o1, o2) -> o1.getTopNum()>o2.getTopNum()?-1:o1.getTopNum()<o2.getTopNum()?1:0);

    /**
     * 存放各条有向边的权重，num1->num2:权重
     */
    private static Map<String,Integer> costsMap = new HashMap<>();

    /**
     * 基于拓扑排序的TopSort.VerTex<T>进行改写成求关键路径需要的顶点类
     * @param <T> 顶点存放元素类型
     */
    public static class VerTex<T> extends TopSort.VerTex<T>{
        /**
         * 节点最早完成时间
         */
        private int ec;
        /**
         * 节点最晚完成时间
         */
        private int lc;

        /**
         * 标识顶点是否为哑结点，这里事实上没有使用到这个属性
         */
        private boolean mute;

        /**
         * 存放指向某个顶点的顶点列表，{Vj，Vk,Vl...}->Vi
         */
        private List<VerTex<T>> previous = new ArrayList<>();

        public VerTex(){
        }

        public VerTex(int num){
            super(num);
        }

        public VerTex(int num,boolean mute){
            super(num);
            this.mute=mute;
        }

        public int getEc() {
            return ec;
        }

        public void setEc(int ec) {
            this.ec = ec;
        }

        public int getLc() {
            return lc;
        }

        public void setLc(int lc) {
            this.lc = lc;
        }

        public List<VerTex<T>> getPrevious() {
            return previous;
        }

        public void setPrevious(VerTex<T>... verTices) {
            previous.addAll(Arrays.asList(verTices));
        }

        public boolean isMute() {
            return mute;
        }
    }

    /**
     * 初始化关键路径算法
     * @param costs 各条有向边的权重map
     * @param verTices 存入的顶点数组
     * @param <T> 顶点存放对象的类型
     */
    public static <T> void init(Map<String,Integer> costs,VerTex<T>... verTices){
        if(!costsMap.isEmpty())
            costsMap.clear();
        costsMap.putAll(costs);
        TopSort.topSort(verTices);
        adjustVerList();
        Prioritize();
    }

    /**
     * 调整拓扑排序的节点列表为关键路径分析的节点列表
     */
    public static void adjustVerList(){
        List<TopSort.VerTex> verTexList0 = getVerList();
        if(!cp_verList.isEmpty())
            cp_verList.clear();
        cp_verList.addAll(verTexList0.stream().map(verTex -> (VerTex)verTex).collect(Collectors.toList()));
        cp_verList.stream().forEach(verTex -> {
            List<VerTex> adjacents = verTex.getAdjacent();
            adjacents.stream().forEach(v -> v.setPrevious(verTex));
        });
    }

    /**
     * 将顶点列表转为最小拓扑序号优先队列和最大拓扑序号优先队列
     */
    public static void Prioritize(){
        if(!verQueue.isEmpty())
            verQueue.clear();
        if(!verQueue1.isEmpty())
            verQueue1.clear();
        verQueue.addAll(cp_verList);
        verQueue1.addAll(cp_verList);
    }

    /**
     * 求某一数值序列的极值
     * @param list 待求极值序列
     * @param maxOrmin 标识求最大值还是最小值
     * @return 极值
     */
    private static Integer getExtremum(List<Integer> list,String... maxOrmin){
        IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(i -> i).summaryStatistics();
        if (maxOrmin.length>0 && "max".equals(maxOrmin[0]))
            return intSummaryStatistics.getMax();
        else
            return intSummaryStatistics.getMin();
    }

    /**
     * 处理顶点的最早完成时间EC
     */
    public static void EcCope(){
        VerTex verTex;
        while ((verTex = verQueue.poll()) !=null){
            if(verTex.getTopNum()==1){
                verTex.setEc(0);
            }else {
                List<VerTex> verTices = verTex.getPrevious();
                final VerTex tmp = verTex;
                List<Integer> ecs = verTices.stream().map(verTex1 -> verTex1.getEc()+costsMap.get(verTex1.getNum()+"->"+tmp.getNum())).collect(Collectors.toList());
                verTex.setEc(getExtremum(ecs,"max"));
            }
        }
    }

    /**
     * 处理顶点的最晚完成时间LC
     */
    public static void LcCope(){
        VerTex verTex;
        int size = cp_verList.size();
        while ((verTex = verQueue1.poll()) !=null){
            if(verTex.getTopNum()==size){
                verTex.setLc(verTex.getEc());
            }else {
                List<VerTex> verTices = verTex.getAdjacent();
                final VerTex tmp = verTex;
                List<Integer> lcs = verTices.stream().map(verTex1 -> verTex1.getLc()-costsMap.get(tmp.getNum()+"->"+verTex1.getNum())).collect(Collectors.toList());
                verTex.setLc(getExtremum(lcs));
            }
        }
    }

    /**
     * 求顶点V1到顶点V2的动作的松弛时间，即最大可以延迟多久进行
     * @param v1 事件1
     * @param v2 事件2
     * @param <T> 顶点存放对象类型
     * @return 最大松弛时间
     */
    public static <T> Integer slackCope(VerTex<T> v1,VerTex<T> v2){
        return v2.getLc()-v1.getEc()-costsMap.get(v1.getNum()+"->"+v2.getNum());
    }

    /**
     * 打印关键路径事件节点
     */
    public static void printCriticalPath(){
        List<VerTex> criticalPath = new ArrayList<>();
      cp_verList.stream().forEach(verTex -> {
          List<VerTex> verTices = verTex.getAdjacent();
          verTices.stream().filter(verTex1 -> (verTex1.getLc()-verTex.getEc()-costsMap.get(verTex.getNum()+"->"+verTex1.getNum()))==0)
                  .forEach(verTex1 -> criticalPath.add(verTex));
      });
        criticalPath.sort((o1, o2) -> o1.getTopNum()>o2.getTopNum()?1:o1.getTopNum()<o2.getTopNum()?-1:0);
        criticalPath.addAll(cp_verList.stream().filter(verTex -> verTex.getTopNum()==cp_verList.size()).collect(Collectors.toList()));
        System.out.println(Arrays.toString(criticalPath.toArray()));
    }

    public static List<VerTex> getCp_verList() {
        return cp_verList;
    }

    public static Queue<VerTex> getVerQueue() {
        return verQueue;
    }

    /**
     * 关键路径分析
     * @param costs 各条有向边的权重map
     * @param verTices 存入的顶点数组
     * @param <T> 顶点存放对象的类型
     */
    public static <T> void criticalPath(Map<String,Integer> costs,VerTex<T>... verTices){
        init(costs,verTices);
        EcCope();
        LcCope();
        printCriticalPath();
    }

    public static void main(String[] args) {

        VerTex verTex1 = new  VerTex(1,false),
                verTex2 = new VerTex(2,false),
                verTex3 = new VerTex(3,false),
                verTex4 = new VerTex(4,false),
                verTex5 = new VerTex(5,false),
                verTex6 = new VerTex(6,false),
                verTex7 = new VerTex(7,false),
                verTex8 = new VerTex(8,false),
                verTex9 = new VerTex(9,false),
                verTex10 = new VerTex(10,false),
                /*
                以下为哑结点
                 */
                verTex11 = new VerTex(11,true), //6'
                verTex12 = new VerTex(12,true), //7'
                verTex13 = new VerTex(13,true), //8'
                verTex14 = new VerTex(14,true); //10'
        verTex1.setAdjacent(verTex2,verTex3);verTex2.setAdjacent(verTex4,verTex11);
        verTex3.setAdjacent(verTex5,verTex11);verTex4.setAdjacent(verTex12);
        verTex5.setAdjacent(verTex9,verTex13);verTex6.setAdjacent(verTex12,verTex13);
        verTex7.setAdjacent(verTex14);verTex8.setAdjacent(verTex14);verTex9.setAdjacent(verTex14);
        verTex11.setAdjacent(verTex6);verTex12.setAdjacent(verTex7);verTex13.setAdjacent(verTex8);
        verTex14.setAdjacent(verTex10);
        Map<String,Integer> costs = new HashMap<>();
        /*
        到哑结点的赋权都是0，即所谓的哑边
         */
        costs.put("1->2",3);costs.put("1->3",2);
        costs.put("2->4",3);costs.put("2->11",0);
        costs.put("3->5",1);costs.put("3->11",0);
        costs.put("4->12",0);costs.put("5->9",4);costs.put("5->13",0);
        costs.put("6->12",0); costs.put("6->13",0);
        costs.put("7->14",0);costs.put("8->14",0);costs.put("9->14",0);
        costs.put("11->6",2);costs.put("12->7",3);costs.put("13->8",2);
        costs.put("14->10",1);

        criticalPath(costs,verTex1,verTex2,verTex3,verTex4,verTex5,verTex6,verTex7,verTex8,verTex9,verTex10,verTex11
                ,verTex12,verTex13,verTex14);
        System.out.println(verTex11.getEc());
        System.out.println(verTex11.getLc());
        System.out.println(slackCope(verTex11,verTex6));
    }
}
