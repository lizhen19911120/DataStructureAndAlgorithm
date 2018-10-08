package algorithm;


import java.util.Random;

/**
 *卡特-韦格曼绝招，给出一个通用散列函数，实现散列函数冲突概率至多是1/M
 * Created by lizhen on 2018/9/19.
 */
public class UniversalHash {

    public static final int DIGS=31;
    public static final int MERSENNEP = (1<<DIGS)-1;

    /**
     * 给出一个散列函数，对于任意指定的A,B,M：x和y的散列函数值冲突的概率至多是1/M
     *
     * @param x 要求散列值的数
     * @param A hash值ax+b的a系数；A可以任意取，只要满足1≤A≤MERSENNEP-1
     * @param B hash值ax+b的b系数；B可以任意取，只要满足0≤B≤MERSENNEP-1
     * @param M x的hashcode值进行取模求散列值的系数；M可以任意取，只要小于MERSENNEP即可
     * @return 散列值
     */
    public static int universalHash(int x,int A,int B,int M){
        long hashVal = (long) A*x+B;
        hashVal = ((hashVal>>DIGS)+(hashVal & MERSENNEP));
        if(hashVal>=MERSENNEP)
            hashVal-=MERSENNEP;
        return (int)hashVal % M;
    }


    public static void main(String[] args) {
        int count =0;
        Random random = new Random(10);
        int x = universalHash(random.nextInt(1000),1,2,10);
        for (int i =0;i<10000;i++){
            int y = universalHash(random.nextInt(1000),1,2,10);
            if (x==y){
                count++;
            }
        }
        System.out.printf("%d%%",count/100);
    }
}
