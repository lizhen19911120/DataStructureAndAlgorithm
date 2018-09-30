package algorithm;

import java.util.Arrays;

/**
 * 归并排序，时间界O(NlogN)
 *
 * Created by lizhen on 2018/9/20.
 */
public class MergeSortRecursion {

    /**
     *
     * @param array 待排序数组
     * @param left 合并子数组的最左边元素数组下标1
     * @param mid 合并子数组的中间元素数组下标2
     * @param right 合并子数组的最右边元素数组下标3
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void merge(T[] array,int left,int mid,int right) {
        int len = right -left +1;
        Object[] tempArray = new Object[len];

        int index = 0;
        int i = left;
        int j =mid+1;

        while(i<=mid && j<=right) {
            tempArray[index++]=array[i].compareTo(array[j])<=0?array[i++]:array[j++];
        }

        while(i<=mid) {tempArray[index++] = array[i++];}

        while(j<=right) {tempArray[index++] = array[j++];}

        for(int k=0;k<len;k++) {
            array[left++]=(T)tempArray[k];
        }
    }

    /**
     * 归并排序，分治-合并
     *
     * @param all 待排序数组
     * @param left 进行排序的起始元素数组下标1
     * @param right 进行排序的末尾元素数组下标2
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void mergeSortRecursion(T[] all,int left,int right) {
        if(left == right) {return;}
        int mid = (left+right)/2;
        mergeSortRecursion(all,left,mid);
        mergeSortRecursion(all,mid+1,right);
        merge(all,left,mid,right);
    }


    public static void main(String[] args) {

        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        int size =example.length;
        mergeSortRecursion(example,0,size-1);
        System.out.println(Arrays.toString(example));
    }
}
