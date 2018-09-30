package algorithm;

import java.util.Arrays;
import java.util.Scanner;

import static algorithm.InsertionSort.*;
/**
 * 快速排序，平均时间界O(NlogN),最坏时间界O(N2)
 *
 * Created by lizhen on 2018/9/20.
 */
public class QuickSort {

    /**
     * 使用插入排序或使用快速排序的数组元素阈值，默认为10
     */
    private static final int CUTOFF = 10;

    /**
     *
     * @param array 待排序数组，这里进行互换数组中的元素
     * @param i 进行互换的数组下标1
     * @param j 进行互换的数组下标2
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void swap(T[] array, int i,int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     *返回进行分割排序后，子数组的枢纽元下标，这里默认枢纽元选择子数组的最右边的元素（按书上应该选取中位元素作为枢纽元，能更提高效率）
     *
     * @param array 待排序数组
     * @param left 进行分割排序的子数组第一个元素下标
     * @param right 进行分割排序的子数组最后一个元素下标
     * @param <T> 元素类型
     * @return 分割排序后枢纽元所在的数组下标
     */
    private static<T extends Comparable<? super T>> int partition(T[] array, int left,int right) {
        T pivot = array[right];
        int tail = left-1;
        for(int i = left;i<right;i++) {
            if(array[i].compareTo(pivot)<=0) {
                swap(array, ++tail, i);
            }
        }
        swap(array, tail+1, right);

        return tail+1;
    }

    /**
     * 更好的分割排序策略，三数中值分割法
     * @param array 待排序数组
     * @param left 进行分割排序的子数组第一个元素下标
     * @param right 进行分割排序的子数组最后一个元素下标
     * @param <T> 元素类型
     * @return 分割排序后枢纽元所在的数组下标
     */
    private static<T extends Comparable<? super T>> int betterPartition(T[] array, int left,int right) {
        int center = (left+right)/2;
        if(array[center].compareTo(array[left])<0)
            swap(array,left,center);
        if(array[right].compareTo(array[left])<0)
            swap(array,left,right);
        if(array[right].compareTo(array[center])<0)
            swap(array,center,right);
        swap(array,center,right-1);

        T pivot = array[right-1];

        int i=left,j=right-1;
        for (;;){
            while (array[++i].compareTo(pivot)<0){}
            while (array[--j].compareTo(pivot)>0){}
            if(i<j)
                swap(array,i,j);
            else
                break;
        }
        swap(array,i,right-1);
        return i;
    }


    /**
     * 快速排序，这里做了优化，如果待排序数组元素较少则使用插入排序
     *
     * @param array 待排序数组
     * @param left 待排序数组起始排序的第一个元素下标
     * @param right 待排序数组起始排序的最后一个元素下标
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void quickSort(T[] array, int left,int right) {

        //当待排序数组个数在10个以内，使用插入排序
        if(left+CUTOFF<=right){
            int pivot_index = partition(array, left, right);
            quickSort(array, left, pivot_index-1);
            quickSort(array, pivot_index+1, right);
        }else {
            insertionSort(array,left,right);
        }

    }


    /**
     * 快速排序的变种，快速选择第k个小的元素，平均时间界O(N)，最坏时间界O(N2)
     *
     * @param array 待选择数组
     * @param left 待选择数组起始的第一个元素下标
     * @param right 待选择数组起始的最后一个元素下标
     * @param k 选取第k个小的元素
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void quickSelect(T[] array, int left,int right, int k) {

        if(left+CUTOFF<=right){
            int pivot_index = partition(array, left, right);

            if(k-1==pivot_index)
                return;
            else if(k-1<pivot_index)
                quickSelect(array,left,pivot_index-1,k);
            else if(k>pivot_index+1)
                quickSelect(array,pivot_index+1,right,k);
        }else {
            insertionSort(array,left,right);
        }
    }


    public static void main(String[] args) {
        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        int size =example.length;
        quickSort(example,0,size-1);
        System.out.println(Arrays.toString(example));


        Integer[] example1 = new Integer[]{6,5,3,1,8,7,2,4,10,9,12,11};
        Scanner scanner = new Scanner(System.in);
        int k =0;
        if(scanner.hasNextInt()){
            k = scanner.nextInt();
        }

        quickSelect(example1,0,example1.length-1,k);
        System.out.println(Arrays.toString(example1));
        System.out.printf("该数组第%d个小的元素是:%d",k,example1[k-1]);
    }
}
