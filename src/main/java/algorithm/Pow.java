package algorithm;

/**
 * 计算某个数的幂（减少乘法使用次数，提高运行速度）
 * Created by lizhen on 2018/9/19.
 */
public class Pow {

    /**
     *
     * @param x 根
     * @param n 幂
     * @return 值
     */
    public static long pow(long x,int n){
        if(n==0)
            return 1;
        if((n&1)==0)
            return pow(x*x,n/2);
        else
            return pow(x*x,n/2)*x;
    }

    public static void main(String[] args) {
        System.out.println(pow(2,10));
    }
}
