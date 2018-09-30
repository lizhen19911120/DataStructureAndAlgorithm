package dataStructure;

/**
 * Created by lizhen on 2018/9/19.
 */
public class BinaryHeap<T extends Comparable<? super T>> {

    /**
     * 默认二叉堆的容量
     */
    private static final int DEFAULT_CAPACITY=10;
    /**
     * 当前二叉堆的元素个数
     */
    protected int currentSize;

    /**
     * 用于构造二叉堆的数组
     */
    protected T[] array;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(int capacity) {
        currentSize = 0;
        /**
         * 数组大小比二叉堆容量大1，array[0]为空，不放元素,在insert()等操作时存放待插入元素
         */
        array = (T[]) new Comparable[capacity + 1];
    }

    public BinaryHeap(T[] items) {
        currentSize = items.length;
        array = (T[]) new Comparable[(currentSize + 2) * 11 / 10];//没懂为什么样这样处理

        int i = 1;
        for (T item : items) {
            array[i++] = item;
        }
        buildHeap();
    }

    /**
     * 二叉堆插入元素方法，也是"上滤"概念的实现
     * @param t 待插入元素
     */
    protected void insert(T t) {
        if (currentSize == array.length - 1) {
            // 如果当前元素个数为数组长度-1，则扩容
            enlargeArray(array.length * 2 + 1);//没懂为什么样这样处理
        }
        int hole = ++currentSize;
        // array[0] = t初始化，最后如果循环到顶点，array[1]为t，循环结束
        for (array[0] = t; t.compareTo(array[hole / 2]) < 0; hole /= 2) {
            // 根节点的值赋值到子节点
            array[hole] = array[hole / 2];
        }
        // 根节点(或树叶节点)赋值为t
        array[hole] = t;
    }

    public T deleteMin() {
        if (isEmpty()) {
            // 这里如果堆为空，可以抛出异常。
            // throw new UnderflowException( );
        }
        T minItem = findMin();
        // 将最后一个节点赋值到根节点
        array[1] = array[currentSize--];
        // 从根节点执行下滤
        percolateDown(1);
        return minItem;
    }

    /**
     * "下滤"概念的实现
     *
     * @param hole 下滤开始节点下标
     */
    protected void percolateDown(int hole) {
        int child;
        T tmp = array[hole];

        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            // 判断有无右节点，child取左右子节点中更小的那个元素的数组下标
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                child++;
            }
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        // 找到合适的位置，将存放在array[1]中的原数组的末尾元素放入
        array[hole] = tmp;
    }

    /**
     * 将存在在数组中的元素调整为符合二叉堆堆序性质
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * 判断堆是否为空
     *
     * @return 为空返回true；否则返回false
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * 寻找堆内最小值。索引1处的元素最小。
     *
     * @return
     */
    public T findMin() {
        if (isEmpty()) {
            // 这里如果堆为空，可以抛出异常。
            // throw new UnderflowException( );
        }
        // 第1位的元素最小
        return array[1];
    }

    /**
     * 扩容，原数组array[0]为空，所以一并复制无所谓
     * @param newSize 新数组的大小
     */
    protected void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++) {
            array[i] = old[i];
        }
    }

    /**
     * 堆置空
     */
    public void makeEmpty() {
        currentSize = 0;
        array = (T[]) new Comparable[DEFAULT_CAPACITY + 1];
    }

    /**
     * 打印堆
     */
    public void print(){
        System.out.print("堆为:");
        for (int i = 1;array[i] != null;i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BinaryHeap binaryHeap = new BinaryHeap();
        Age a1 = new Age();a1.age=23;
        Age a2 = new Age();a2.age=98;
        Age a3 = new Age();a3.age=34;
        Age a4 = new Age();a4.age=63;
        Age a5 = new Age();a5.age=3;
        Age a6 = new Age();a6.age=0;
        Age a7 = new Age();a7.age=87;
        Age a8 = new Age();a8.age=45;
        Age[] nums = {a1, a2, a3, a4, a5, a6, a7, a8};
        for (Age num : nums) {
            binaryHeap.insert(num);
        }
        binaryHeap.print();
        System.out.println(binaryHeap.deleteMin());
        System.out.println(nums[0]);
        a1.age=1;
        binaryHeap.print();
        System.out.println(binaryHeap.deleteMin());

        binaryHeap.makeEmpty();
        System.out.println("堆是否为空:" + binaryHeap.isEmpty());

        binaryHeap.print();

    }


}

class Age implements Comparable<Age>{

    int age;

    @Override
    public int compareTo(Age o) {
        return this.age>o.age?1:this.age<o.age?-1:0;
    }

    @Override
    public String toString() {
        return "Age{" +
                "age=" + age +
                '}';
    }
}