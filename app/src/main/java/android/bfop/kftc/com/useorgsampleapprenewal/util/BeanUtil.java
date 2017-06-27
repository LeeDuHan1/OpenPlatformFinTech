package android.bfop.kftc.com.useorgsampleapprenewal.util;

/**
 * java 객체 핸들링 관련 유틸 클래스
 *
 * Created by LeeHyeonJae on 2017-06-27.
 */
public class BeanUtil {

    /**
     * 클래스명 리턴
     *
     * @param o
     * @return
     */
    public static String getClassName(Object o){

        return o.getClass().getSimpleName();
    }
}
