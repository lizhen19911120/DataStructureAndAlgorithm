package algorithm;

import java.util.Arrays;

/**
 * 插入排序，时间界O(N2)
 * Created by lizhen on 2018/9/20.
 */
public class InsertionSort {

    /**
     *
     * @param array 待排序数组
     * @param m 数组中需要排序的开始元素下标
     * @param n 数组中需要排序的结束元素下标
     * @param <T> 元素类型
     */
    public static<T extends Comparable<? super T>> void insertionSort(T[] array,int m,int n) {

        for(int i=m+1;i<n+1;i++) {
            T get = array[i];
            int j = i-1;
            while(j>=m && array[j].compareTo(get)>0) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = get;
        }
    }

    public static void main(String[] args) {
        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        int size =example.length;
        insertionSort(example,0,size-1);
        System.out.println("插入排序结果： ");
        System.out.print(Arrays.toString(example));
    }

}
