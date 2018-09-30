package algorithm;

import java.util.Arrays;

/**
 * union/find集合操作
 *
 * Created by lizhen on 2018/9/21.
 */
public class DisjSets {

    private int[] s;

    /**
     * 初始化元素集合，-1表示元素不属于任何类
     *
     * @param numElements 元素个数
     */
    public DisjSets(int numElements){
        s = new int[numElements];
        for (int i=0;i<s.length;i++)
            s[i] = -1;
    }

    /**
     * 合并2个集合，高度小的集合合并到高度大的集合中
     *
     * @param root1 集合1的根节点
     * @param root2 集合2的根节点
     */
    public void union(int root1, int root2){
        if(s[root2]<s[root1])
            s[root1] = root2;
        else {
            if(s[root1]==s[root2])
                s[root1]--;
            s[root2]=root1;
        }
    }

    /**
     * 寻找下标为x的元素的所属类
     *
     * @param x 要寻找所属类的元素的序号
     * @return 下标为x的元素的所属类的根节点的下标
     */
    public int find(int x){
        if(s[x]<0)
            return x;
        else
            return s[x] = find(s[x]);
    }

    public void pirnt(){
        System.out.println(Arrays.toString(s));
    }

    public static void main(String[] args) {

        DisjSets disjSets = new DisjSets(8);
        disjSets.union(6,7);
        disjSets.union(4,5);
        disjSets.union(4,6);
        disjSets.union(3,4);
        disjSets.pirnt();
        System.out.println(disjSets.find(7)); //采用路径压缩后，改变将第8个元素的根节点由6变成了4,可以发现第5个元素的高度应该变成1，但还是2(显示-3,是按定义-1-2得到的)
        disjSets.pirnt();

    }
}
