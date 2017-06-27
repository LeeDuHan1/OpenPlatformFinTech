package android.bfop.kftc.com.useorgsampleapprenewal.util;

import android.util.Log;

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

        if(o == null){
            Log.e("##", "BeanUtil.getClassName(o) > 매개변수 o 가 null 입니다.");
            return "";
        }

        return o.getClass().getSimpleName();
    }
}
