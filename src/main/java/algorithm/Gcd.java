package algorithm;

/**
 * 求解给定2个正整数的最大公约数
 * Created by lizhen on 2018/9/19.
 */
public class Gcd {

    /**
     * 欧几里得算法——两个正整数a和b（a>b），它们的最大公约数等于a除以b的余数c和b之间的最大公约数。比如10和25，25除以10商2余5，那么10和25的最大公约数，等同于10和5的最大公约数。
     *
     * @param m 正整数m，较大值
     * @param n 正整数n，较小值
     * @return 最大公约数
     */
    public static long gcd(long m,long n){
        if(m<n){
            return gcd(n,m);
        }else {
            while (n!=0){
                long rem =m%n;
                m=n;
                n=rem;
            }
            return m;
        }
    }


    /**
     * 更相减损术——两个正整数a和b（a>b），它们的最大公约数等于a-b的差值c和较小数b的最大公约数。比如10和25，25减去10的差是15,那么10和25的最大公约数，等同于10和15的最大公约数。
     *
     当a和b均为偶数，gcb(a,b) = 2*gcb(a/2, b/2) = 2*gcb(a>>1, b>>1)

     当a为偶数，b为奇数，gcb(a,b) = gcb(a/2, b) = gcb(a>>1, b)

     当a为奇数，b为偶数，gcb(a,b) = gcb(a, b/2) = gcb(a, b>>1)

     当a和b均为奇数，利用更相减损术运算一次，gcb(a,b) = gcb(b, a-b)， 此时a-b必然是偶数，又可以继续进行移位运算。

     比如计算10和25的最大公约数的步骤如下：

     整数10通过移位，可以转换成求5和25的最大公约数
     利用更相减损法，计算出25-5=20，转换成求5和20的最大公约数
     整数20通过移位，可以转换成求5和10的最大公约数
     整数10通过移位，可以转换成求5和5的最大公约数
     利用更相减损法，因为两数相等，所以最大公约数是5
     *
     * @param numberA 正整数A，较大值
     * @param numberB 正整数B，较小者
     * @return 最大公约数
     */
    public static int gcd(int numberA,int numberB){
        if(numberA==numberB)
            return numberA;
        if(numberA<numberB){
            return gcd(numberB,numberA);
        }else {
            if((numberA&1)==0 && (numberB&1)==0){
                return gcd(numberA>>1,numberB>>1)<<1;
            }else if((numberA&1)==0 && (numberB&1)!=0){
                return gcd(numberA>>1,numberB);
            }else if((numberA&1)!=0 && (numberB&1)==0){
                return gcd(numberA,numberB>>1);
            }else {
                return gcd(numberB,numberA-numberB);
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(gcd(10,25));
        System.out.println(gcd(10L,25L));
    }
}
