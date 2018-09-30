package algorithm;

import java.util.*;

/**
 * 希尔排序，时间界Θ(N2)，如果使用{1,5,19,41,109，...}这样的精心构造的增量序列，时间界能达到O(N7/6)，基本达到线性
 * Created by lizhen on 2018/9/20.
 */
public class ShellSort {

    /**
     *
     * @param array 待排序数组
     * @param <T> 元素类型
     */
    public static<T extends Comparable<? super T>> void shellSort(T[] array){
        int j;
        for(int gap=array.length/2;gap>0;gap/=2){
            for (int i=gap;i<array.length;i++){
                T tmp = array[i];
                for (j=i;j>=gap && tmp.compareTo(array[j-gap])<0;j-=gap){
                    array[j]=array[j-gap];
                }
                array[j]=tmp;
            }
        }
    }

    /**
     * 最佳时间界的希尔排序，时间界O(n7/6)
     *
     * @param array 待排序数组
     * @param <T> 元素类型
     */
    public static<T extends Comparable<? super T>> void bestShellSort(T[] array){
        int length = array.length;
        int m=0,n=0;
        long x=0,y=0;
        Queue<Long> queue = new PriorityQueue<>(Collections.reverseOrder());

        while ((x=9*Pow.pow(4,m)-9*Pow.pow(2,m)+1)<length){
            queue.offer(x);
            m++;
        }
        while ((y=Pow.pow(4,n)-3*Pow.pow(2,n)+1)<length){
            if(y>0){
                queue.offer(y);
            }
            n++;
        }

        int j;
        for(int gap=queue.poll().intValue();gap>0;gap=queue.poll().intValue()){
            for (int i=gap;i<array.length;i++){
                T tmp = array[i];
                for (j=i;j>=gap && tmp.compareTo(array[j-gap])<0;j-=gap){
                    array[j]=array[j-gap];
                }
                array[j]=tmp;
            }
            if(queue.isEmpty())
                break;
        }
    }

    public static void main(String[] args) {
        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        shellSort(example); //最传统的希尔排序，使用h2=h1/2的增量序列
        bestShellSort(example); //使用目前最好的增量序列的希尔排序
        System.out.println("希尔排序结果： ");
        System.out.print(Arrays.toString(example));
    }

}
