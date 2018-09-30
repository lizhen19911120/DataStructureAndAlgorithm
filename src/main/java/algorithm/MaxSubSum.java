package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 求解给定（可能是负值）的整数A1,A2,A3...An的具有最大和的子序列，同时给出数组下标和最大值
 * Created by lizhen on 2018/9/19.
 */
public class MaxSubSum {

    /**
     * 联机算法——仅需要常量空间并以线性时间运行
     *
     * @param array 待求数组
     * @return 最大子序列值
     */
    public static int maxSubSum(int[] array){
        int maxSum=0,thisSum=0;
        List<Integer> subArray = new ArrayList<>();

        for (int j=0;j<array.length;j++){
            thisSum+=array[j];
            if(thisSum>maxSum){
                maxSum=thisSum;
            }else if(thisSum<0){
                thisSum=0;
                subArray.clear();
                continue;
            }
            subArray.add(j);
        }
        System.out.println(Arrays.toString(subArray.toArray()));
        return maxSum;
    }

    public static void main(String[] args) {
        int[] testArray = {1,-2,3,-10,9,2,-1,5};
        System.out.println(maxSubSum(testArray));
    }

}
