package algorithm;

import java.util.*;

/**
 * 对非负值赋权有向图求最短路径
 * Created by lizhen on 2018/9/29.
 */
public class Dijkstra {


    /**
     * 图的顶点类
     * @param <T> 顶点存放对象的类型
     */
    public static class VerTex<T> implements Comparable<VerTex<T>>{
        /**
         * 给顶点的编号，用以区分不同的顶点
         */
        private int num;
        /**
         * 放入顶点的对象
         */
        private T object;
        /**
         * 存放某个顶点指向的顶点列表，Vi->{Vj，Vk,Vl...}
         */
        private List<VerTex<T>> adjacent = new ArrayList<>();

        /**
         * 标识顶点是否已知
         */
        private boolean known;

        /**
         * 记录顶点至起始顶点的加权距离
         */
        private int dist;

        /**
         * 记录顶点至其实顶点的上一个顶点
         */
        private VerTex<T> path;

        public VerTex(){
        }

        public VerTex(int num){
            this.num = num;
        }

        public VerTex(T object){
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public void setAdjacent(VerTex<T>... verTices){
            adjacent.addAll(Arrays.asList(verTices));
        }

        public List<VerTex<T>> getAdjacent(){
            return adjacent;
        }

        public int getDist() {
            return dist;
        }

        public int getNum() {
            return num;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }


        @Override
        public int compareTo(VerTex<T> o) {
            return this.dist>o.dist?1:this.dist<o.dist?-1:0;
        }

        @Override
        public String toString() {
            return "VerTex"+this.num;
        }
    }

    /**
     * 存放图的顶点集
     */
    private static Queue<VerTex> verQueue = new PriorityQueue<>();

    /**
     * 存放各条有向边的权重，num1->num2:权重
     */
    private static Map<String,Integer> costs = new HashMap<>();

    /**
     * 打印起始顶点到顶点v的最短路径
     * @param v 打印起始顶点到顶点v的最短路径
     * @param <T> 顶点存放对象的类型
     */
    public static <T> void printPath(VerTex<T> v){
        if(v.path != null){
            printPath(v.path);
            System.out.print(" to ");
        }
        System.out.print(v);

    }

    /**
     * 初始化Dijkstra算法
     * @param costs 各条有向边的权重map
     * @param verTices 存入的顶点数组
     * @param <T> 顶点存放对象的类型
     */
    public static <T> void init(Map<String,Integer> costs,VerTex<T>... verTices){
        for (VerTex<T> v:verTices) {
            v.dist = Integer.MAX_VALUE;
            v.known = false;
        }
        if(!verQueue.isEmpty())
            verQueue.clear();
        verQueue.addAll(Arrays.asList(verTices));

        costs.putAll(costs);
    }

    /**
     * Dijkstra算法
     * @param s 计算图最短路径的起始点
     * @param costs 各条有向边的权重map
     * @param verTices 存入的顶点数组
     * @param <T> 顶点存放对象的类型
     */
    public static <T> void dijkstra(VerTex<T> s,Map<String,Integer> costs,VerTex<T>... verTices){
        init(costs,verTices);
        s.dist=0;
        VerTex<T> v;
        while ((v=verQueue.poll())!=null){
            v.known=true;
            for (VerTex<T> w:v.adjacent) {
                if (!w.known){
                    int cvw = costs.get(v.num+"->"+w.num);
                    if (v.dist+cvw<w.dist){
                        w.dist=v.dist+cvw;
                        w.path=v;
                        /**
                         * 更新优先队列中w对象，重新构造优先队列（仅仅改变元素的话，并未进行优先队列重新构造）
                         */
                        verQueue.remove(w);
                        verQueue.offer(w);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        VerTex verTex1 = new VerTex(1),
               verTex2 = new VerTex(2),
               verTex3 = new VerTex(3),
               verTex4 = new VerTex(4),
               verTex5 = new VerTex(5),
               verTex6 = new VerTex(6),
               verTex7 = new VerTex(7);
        verTex1.setAdjacent(verTex2,verTex4);
        verTex2.setAdjacent(verTex4,verTex5);
        verTex3.setAdjacent(verTex1,verTex6);
        verTex4.setAdjacent(verTex3,verTex6,verTex7);
        verTex5.setAdjacent(verTex7);
        verTex7.setAdjacent(verTex6);

        Map<String,Integer> costs = new HashMap<>();
        costs.put("1->2",2);costs.put("1->4",1);
        costs.put("2->4",3);costs.put("2->5",10);
        costs.put("3->1",4);costs.put("3->6",5);
        costs.put("4->3",2);costs.put("4->6",8);costs.put("4->7",4);
        costs.put("5->7",6);
        costs.put("7->6",1);

        dijkstra(verTex1,costs,verTex1,verTex2,verTex3,verTex4,verTex5,verTex6,verTex7);

        printPath(verTex7);
        System.out.println();
        printPath(verTex6);
    }
}
