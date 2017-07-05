package and.bfop.kftc.com.useorgsampleapprenewal;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test01(){

        String s = "한국 모바일 인증 어쩌구 789456 으헤헤헤헤";
        String ret = s.replaceAll("(.*)(\\d{6})(.*)", "$2");
        System.out.println("["+ret+"]");
    }
}