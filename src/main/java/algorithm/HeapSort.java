package algorithm;

import dataStructure.BinaryHeap;

import java.util.Arrays;

/**
 * 堆排序，时间界O(NlogN)
 * Created by lizhen on 2018/9/20.
 */
public class HeapSort {

    /**
     *最大值在根的二叉堆，这里用不到
     *
     * @deprecated
     * @param <T> 二叉堆元素
     */
    @Deprecated
    private static class MaxBinayHeap<T extends Comparable<? super T>> extends BinaryHeap<T>{

        @Override
        protected void insert(T t) {
            if (currentSize == array.length - 1) {
                // 如果当前元素个数为数组长度-1，则扩容
                enlargeArray(array.length * 2 + 1);//没懂为什么样这样处理
            }
            int hole = ++currentSize;
            // array[0] = t初始化，最后如果循环到顶点，array[1]为t，循环结束
            for (array[0] = t; t.compareTo(array[hole / 2]) > 0; hole /= 2) {
                // 根节点的值赋值到子节点
                array[hole] = array[hole / 2];
            }
            // 根节点(或树叶节点)赋值为t
            array[hole] = t;
        }

        @Override
        protected void percolateDown(int hole) {
            super.percolateDown(hole);int child;
            T tmp = array[hole];

            for (; hole * 2 <= currentSize; hole = child) {
                child = hole * 2;
                if (child != currentSize && array[child + 1].compareTo(array[child]) > 0) {
                    child++;
                }
                if (array[child].compareTo(tmp) > 0) {
                    array[hole] = array[child];
                } else {
                    break;
                }
            }
            array[hole] = tmp;
        }
    }

    /**
     * 这里percDown()和swapReferences()构造了一个最大值在根的二叉堆，与原来二叉堆不同的是，二叉堆元素是从数组[0]位置开始的，而不是数组[1]位置
     *
     * @param i 数组进行二叉堆化后，要找出左儿子的元素数组下标
     * @return 左儿子的数组下标
     */
    private static int leftChild(int i){
        return 2*i+1;
    }

    /**
     * 将待排序数组进行二叉堆化
     *
     * @param a 待排序数组，这里进行二叉堆堆序化
     * @param i 进行下滤的开始数组下标
     * @param n 进行二叉堆化的逻辑容量，默认是和待排序数组一样大
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void percDown(T[] a,int i,int n){
        int child = 0;
        T tmp;
        for (tmp=a[i];leftChild(i)<n;i=child){
            child=leftChild(i);
            if(child != n-1 && a[child].compareTo(a[child+1])<0)
                child++;
            if (tmp.compareTo(a[child])<0)
                a[i]=a[child];
            else
                break;
        }
        a[i]=tmp;
    }

    /**
     *
     * @param a 待排序数组，这里进行互换数组中的元素
     * @param i 进行互换的数组下标1
     * @param j 进行互换的数组下标2
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void swapReferences(T[] a, int i, int j){

        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     * 二叉排序方法
     *
     * @param a 待排序数组
     * @param <T> 元素类型
     */
    public static<T extends Comparable<? super T>> void heapSort(T[] a){
        for (int i=a.length/2-1;i>=0;i--)
            percDown(a,i,a.length); /* 将待排序数组二叉堆化 */
        for (int i=a.length-1;i>0;i--){
            swapReferences(a,0,i); /* 将位于下标0的元素和末尾元素互换位置 */
            percDown(a,0,i); /* 重新调整二叉堆 */
        }
    }

    public static void main(String[] args) {
        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        heapSort(example);
        System.out.println("堆排序结果： ");
        System.out.print(Arrays.toString(example));

    }

}
