package dataStructure;

import java.util.Random;

/**
 * Created by lizhen on 2018/7/23.
 *
 * AVL树
 * @param <T>
 */
public class AvlTree<T extends Comparable<? super T>> {

    //定义AVL树根节点
    private AvlNode<T> root;

    //定义AVL树节点
    private static class AvlNode<T>{
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;
        AvlNode(T theElement){
            this(theElement, null, null);
        }
        AvlNode(T theElement, AvlNode<T> lt, AvlNode<T> rt){
            element = theElement;
            left = lt;
            right = rt;
            height = 0;
        }
    }

    //AVL树插入方法
    public void insert(T element){
        root = insert(element, root);
    }

    //AVL树判断包含方法
    public boolean contains(T x){
        return contains(x, root);
    }

    //AVL树删除方法
    public void remove(T element){
        root = remove(element, root);
    }

    //AVL树排序遍历
    public static <T extends Comparable<? super T>> void traverse(AvlTree<T> avlTree){
        traverse(avlTree.root);
    }

    /**
     * to calculate the height of the node
     * @param t the node to calculate the height
     * @return the height of the node
     */
    private int height(AvlNode<T> t){
        return t == null ? -1 : t.height;
    }

    //默认构造函数
    public AvlTree(){
        root = null;
    }
    //清空AVL树
    public void makeEmpty(){
        root = null;
    }
    //判断AVL树是否为空
    public boolean isEmpty(){
        return root == null;
    }

    /**
     *Internal method to insert into a subtree
     * @param x the item to insert
     * @param t the node that roots the subtree
     * @return the new root of the subtree
     */
    private AvlNode<T> insert(T x, AvlNode<T> t){
        if(t == null){
            return  new AvlNode<T>(x,null,null);
        }
        int compareResult = x.compareTo(t.element);
        if(compareResult<0)
            t.left = insert(x,t.left);
        else if(compareResult>0)
            t.right = insert(x,t.right);
        else
            ; //duplicate do nothing
        return balance(t);//每次插入后进行平衡
    }

    /**
     * Internal method to remove item from a subtree
     * @param x the item to remove
     * @param t the node that roots the subtree
     * @return the new root of the subtree
     */
    private AvlNode<T> remove(T x, AvlNode<T> t){
        if(t == null)
            return t;
        int compareResult =x.compareTo(t.element);
        if(compareResult<0)
            t.left = remove(x,t.left);
        else if (compareResult>0)
            t.right = remove(x,t.right);
        else if (t.left != null && t.right != null){ //如果compareResult=0且t节点有左右子树，则将t节点值设置为右子树的最小值并对右子树递归进行remove()操作
            t.element = findMin(t.right).element;
            t.right = remove(t.element,t.right);
        }
        else
            t = (t.left != null) ? t.left : t.right;//如果compareResult=0且t节点仅有左右子树中的一棵，则将t节点设置为左或右子树

        return balance(t);

    }

    /**
     * To find the minimum value of the subtree
     * @param t the node that roots the subtree
     * @return the node that holds the minimum value
     */
    private AvlNode<T> findMin(AvlNode<T> t){
        if(t == null)
            return null;
        if(t.left == null)
            return t;
        return findMin(t.left);
    }

    /**
     * To find the maximum value of the subtree
     * @param t the node that roots the subtree
     * @return
     */
    private AvlNode<T> findMax(AvlNode<T> t){
        if(t == null)
            return null;
        if(t.right == null)
            return t;
        return findMax(t.right);
    }


    /**
     * To varify whether the very element is included in the subtree
     * @param x the element to varify whether it is included in the subtree
     * @param t the node that roots the subtree
     * @return
     */
    private boolean contains(T x, AvlNode<T> t){
        //空树处理
        if(t == null)
            return false;
        //正常情况处理
        int compareResult = x.compareTo(t.element);
        if(compareResult < 0)
            return contains(x, t.left);
        else if(compareResult > 0)
            return contains(x, t.right);
        else
            return true;
    }

    // 配置的允许的AVL树的左右子树高度差，默认为1
    private static final int ALLOWED_IMBALANCE = 1;

    //假设AVL树要么是平衡的要么是只有一边是不平衡的
    /**
     *
     * @param t the root of the subtree to balance
     * @return the new root of the subtree
     */
    private AvlNode<T> balance(AvlNode<T> t){
        if(t == null){
            return t;
        }
        if(height(t.left)-height(t.right)>ALLOWED_IMBALANCE)
            if(height(t.left.left)>=height(t.left.right)) {
                t = rotateWithLeftChid(t);//左子树单旋转
            }else{
                t = dobleWithLeftChid(t);//左子树双旋转
            }
        else if(height(t.right)-height(t.left)>ALLOWED_IMBALANCE)
            if(height(t.right.right)>=height(t.right.left)) {
                t = rotateWithRightChid(t);//右子树单旋转
            }else{
                t = dobleWithRightChid(t);//右子树双旋转
            }

        t.height = Math.max(height(t.left),height(t.right)) + 1;//更新根节点的高度
        return t;
    }

    /**
     * This is a single rotation for case 1
     * @param k2 the root of the subtree to execute rotation
     * @return the new root of the subtree
     */
    private  AvlNode<T> rotateWithLeftChid(AvlNode<T> k2){
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left),height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left),height(k1.right)) + 1;
        return k1;
    }

    /**
     * This is a single rotation for case 4
     * @param k1 the root of the subtree to execute rotation
     * @return the new root of the subtree
     */
    private  AvlNode<T> rotateWithRightChid(AvlNode<T> k1){
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left),height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left),height(k2.right)) + 1;
        return k2;
    }

    /**
     * This is a double rotation for case 2
     * @param k3 the root of the subtree to execute rotation
     * @return the new root of the subtree
     */
    private  AvlNode<T> dobleWithLeftChid(AvlNode<T> k3){
       k3.left = rotateWithRightChid(k3.left);
       return rotateWithLeftChid(k3);
    }

    /**
     * This is a double rotation for case 3
     * @param k1 the root of the subtree to execute rotation
     * @return the new root of the subtree
     */
    private  AvlNode<T> dobleWithRightChid(AvlNode<T> k1){
        k1.right = rotateWithLeftChid(k1.right);
        return rotateWithRightChid(k1);
    }

    /**
     * To traverse the AVL tree
     * @param t the root of a subtree to traverse
     * @param <T> the type of the element held by the node
     */
    private static <T> void traverse(AvlNode<T> t){
        if(t == null)
            return;
        if(t.left != null)
            traverse(t.left);
        System.out.println(t.element);
        if (t.right != null)
            traverse(t.right);
    }


    static void test( AvlTree<Integer> avlTree,int n){

        avlTree.makeEmpty();

        long insertStime = System.nanoTime();
        for(int i=1;i<=n;i++){
            avlTree.insert(i);
        }
        long insertEtime = System.nanoTime();
        System.out.println("n: "+ n +", insert time spent: "+(insertEtime-insertStime));

        long insertStime1 = System.nanoTime();
        Integer min = avlTree.findMin(avlTree.root).element;
        long insertEtime1 = System.nanoTime();
        System.out.println("n: "+ n +", findMin time spent: "+(insertEtime1-insertStime1) + " min: "+min);

        long insertStime2 = System.nanoTime();
        avlTree.remove(100);
        long insertEtime2 = System.nanoTime();
        System.out.println("n: "+ n +", remove time spent: "+(insertEtime2-insertStime2));

        long insertStime3 = System.nanoTime();
        boolean b =avlTree.contains(1000);
        long insertEtime3 = System.nanoTime();
        System.out.println("n: "+ n +", contains time spent: "+(insertEtime3-insertStime3) + " contains: "+b);

    }

    public static void main(String[] args) {

        AvlTree<Integer> avlTree = new AvlTree<Integer>();

        //测试AVL树的插入、删除、找值等操作时间界
        for (int i=1000;i<1000001;i=i*10)
            test(avlTree,i);

        //测试使用AVL树进行有序遍历
        avlTree.makeEmpty();
        Random random = new Random();
        for(int i=1;i<=100;i++)
            avlTree.insert(random.nextInt(100));
        traverse(avlTree);


    }



}
