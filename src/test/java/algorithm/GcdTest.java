package algorithm;

import org.junit.*;
import org.junit.rules.Timeout;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
/**
 * Created by lizhen on 2018/10/25.
 */
//@RunWith
public class GcdTest {

    /**
     * 整个测试类加载时执行，必须是static方法
     */
    @BeforeClass
//    @Parameterized.Parameters
    public static void beforeClass(){
        System.out.println("before class..");
    }

    /**
     * 整个测试类结束时执行，必须是static方法
     */
    @AfterClass
    public static void afterClass(){
        System.out.println("after class..");
    }

    /**
     * 定义一个执行测试方法时的规则
     * 这里定义一个全部方法的执行时间必须在10秒内的规则
     * @Test 方法执行时会参考这个rule
     */
    @Rule
    public Timeout globalTimeout= new Timeout(10, TimeUnit.SECONDS);

    /**
     * 1. Failure一般由单元测试使用的断言方法判断失败所引起的，这经表示测试点发现了问题 ，就是说程序输出的结果和我们预期的不一样。
     * 2. error是由代码异常引起的，它可以产生于测试代码本身的错误，也可以是被测试代码中的一个隐藏的bug。
     * 3. 测试用例不是用来证明你是对的，而是用来证明你没有错。
     * @throws Exception
     */
    @Test
    public void gcd() throws Exception {
        int gcd = Gcd.gcd(100, 40);
        assertEquals(20,gcd);
        /**
         * 配合hamcrest包下的方法，方便实现assertEquals()等断言功能
         */
        assertThat(gcd,is(20));
    }


    /**
     * 对所有的@Test测试方法生效，方法执行前执行
     */
    @Before
    public void before() {
        System.out.println("before gcd1()");
    }

    /**
     * 忽略此测试方法
     * @throws Exception
     */
    @Ignore
    /**
     * expected=java.lang.ArithmeticException.class 期望抛出ArithmeticException异常
     */
    @Test(expected=java.lang.ArithmeticException.class)
    public void gcd1() throws Exception {
        long gcd = Gcd.gcd(90L, 39l);
        System.out.println(gcd);
    }

    /**
     * 对所有的@Test测试方法生效，方法执行后执行
     */
    @After
    public void after() {
        System.out.println("after gcd1()");
    }



}