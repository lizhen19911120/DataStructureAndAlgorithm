package algorithm;

import java.util.*;

/**
 * 对图进行拓扑排序
 * Created by lizhen on 2018/9/29.
 */
public class TopSort {

    /**
     * 拓扑排序的顶点列表（邻接表表示）
     */
    private static List<VerTex> verList = new ArrayList<>();

    /**
     * 图的顶点类
     * @param <T> 顶点存放对象的类型
     */
    public static class VerTex<T>{
        /**
         * 给顶点的编号，用以区分不同的顶点
         */
        private int num;
        /**
         * 放入顶点的对象
         */
        private T object;
        /**
         * 顶点的入度
         */
        private int indegree = 0;
        /**
         * 拓扑排序后的顶点序号
         */
        private int topNum;
        /**
         * 存放某个顶点指向的顶点列表，Vi->{Vj，Vk,Vl...}
         */
        private List<VerTex<T>> adjacent = new ArrayList<>();

        public VerTex(){
        }

        public VerTex(int num){
            this.num = num;
        }

        public VerTex(T object){
            this.object = object;
        }

        public int getIndegree() {
            return indegree;
        }

        public int getTopNum() {
            return topNum;
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
    }

    /**
     * 初始化待排序的顶点列表
     * @param verTices 存入的顶点数组
     */
    public static <T> void init(VerTex<T>... verTices){
        initVerTices(verTices);
        initIndegreeOfVertex();
    }

    /**
     * 将顶点放入verList
     * @param verTices 存入的顶点数组
     */
    public static <T> void initVerTices(VerTex<T>... verTices){
        if(!verList.isEmpty())
            verList.clear();
        verList.addAll(Arrays.asList(verTices));
    }

    /**
     * 计算各个顶点的入度值
     */
    public static void initIndegreeOfVertex(){
        for (VerTex v:verList) {
            for (VerTex w:verList) {
                if (w.adjacent.contains(v))
                    v.indegree++;
            }
        }
    }

    /**
     * 拓扑排序
     * @param <T>
     * @throws CycleFoundException 图存在环异常
     */
    public static <T> void topSort(VerTex<T>... verTices) throws CycleFoundException{

        init(verTices);

        final int NUM_VERTICES = verList.size();

        Queue<VerTex<T>> queue = new LinkedList<>();
        int counter = 0;
        for (VerTex<T> v:verList) {
            if(v.indegree==0)
                queue.offer(v);
        }

        while (!queue.isEmpty()){
            VerTex<T> v = queue.poll();
            v.topNum = ++counter;

            for (VerTex<T> w:v.adjacent) {
                    if(--w.indegree==0)
                        queue.offer(w);
            }
        }

        if(counter != NUM_VERTICES)
            throw new CycleFoundException();

    }

    /**
     * 图存在环异常
     */
    private static class CycleFoundException extends RuntimeException{}

    public static void main(String[] args) {
        VerTex verTex1 = new VerTex(1),
               verTex2 = new VerTex(2),
               verTex3 = new VerTex(3),
               verTex4 = new VerTex(4),
               verTex5 = new VerTex(5),
               verTex6 = new VerTex(6),
               verTex7 = new VerTex(7);
        verTex1.setAdjacent(verTex2,verTex3,verTex4);
        verTex2.setAdjacent(verTex4,verTex5);
        verTex3.setAdjacent(verTex6);
        verTex4.setAdjacent(verTex3,verTex6,verTex7);
        verTex5.setAdjacent(verTex4,verTex7);
        verTex7.setAdjacent(verTex6);

        topSort(verTex1,verTex2,verTex3,verTex4,verTex5,verTex6,verTex7);

       verList.stream().forEach(verTex -> System.out.println(verTex.num + ":" + verTex.topNum));


    }
}
