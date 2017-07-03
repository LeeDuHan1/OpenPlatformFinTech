package and.bfop.kftc.com.useorgsampleapprenewal.util;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentReplaceEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.MainFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebCommonWebViewFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case1Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case2Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case3Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2TabFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdapp.AuthOldAppMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageAuthorizeAccountFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageAuthorizeFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageRegisterAccountFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.TokenRequestFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.settings.SettingsFragment;

/**
 * Created by LeeHyeonJae on 2017-06-30.
 */
public class FragmentUtil {

    public static final Map<Class<? extends BaseFragment>, String> FRAGMENT_NAME_MAP = getFragmentNameMap();

    /**
     * Fragment 클래스와 해당 Fragment의 페이지명을 정의하여 Map으로 리턴한다.
     *
     * @return
     */
    public static Map<Class<? extends BaseFragment>, String> getFragmentNameMap(){

        Map<Class<? extends BaseFragment>, String> map = new LinkedHashMap<>();
        map.put(MainFragment.class, "이용기관 샘플앱 메인");
        map.put(AuthNewWebMenuFragment.class, "사용자인증 개선버전");
        map.put(AuthOldAppMenuFragment.class, "사용자인증 기존버전 (앱 방식)");
        map.put(AuthOldWebMenuFragment.class, "사용자인증 기존버전 (웹 방식)");
        map.put(APICallMenuFragment.class, "API 거래기능");
        map.put(SettingsFragment.class, "설정");
        map.put(AuthNewWebPageAuthorize2TabFragment.class, "사용자인증 개선버전");
        map.put(AuthNewWebPageAuthorize2Case1Fragment.class, "사용자인증 개선버전 (Case1)");
        map.put(AuthNewWebPageAuthorize2Case2Fragment.class, "사용자인증 개선버전 (Case2)");
        map.put(AuthNewWebPageAuthorize2Case3Fragment.class, "사용자인증 개선버전 (Case3)");
        map.put(AuthNewWebCommonWebViewFragment.class, "사용자인증 개선버전"); // 어차피 override 되는 값이다.
        map.put(AuthOldWebPageAuthorizeFragment.class, "사용자인증 기존버전 (웹 방식)");
        map.put(AuthOldWebPageRegisterAccountFragment.class, "계좌등록 기존버전 (웹 방식)");
        map.put(AuthOldWebPageAuthorizeAccountFragment.class, "계좌등록확인 기존버전 (웹 방식)");
        map.put(TokenRequestFragment.class, "Token 발급 요청");
        return map;
    }

    /**
     * Fragment 클래스를 입력받아 Fragment 명을 리턴한다.
     *
     * @param clazz
     * @return
     */
    public static String getFragmentName(Class<? extends BaseFragment> clazz){

        String name = StringUtil.defaultString(FRAGMENT_NAME_MAP.get(clazz));
        if(StringUtil.isBlank(name)){
            MessageUtil.showToast(BeanUtil.getClassName(clazz)+" 에 대한 Fragment 명을 정의해 주십시오");
        }
        return name;
    }

    /**
     * Fragment를 신규 생성하여 리턴한다.
     *
     * @param clazz
     * @return
     */
    public static BaseFragment newFragment(Class<? extends BaseFragment> clazz){

        BaseFragment ret = null;
        try {
            Method newMethod = clazz.getMethod("newInstance", String.class);
            if(newMethod != null){
                ret = (BaseFragment)newMethod.invoke(clazz, FragmentUtil.getFragmentName(clazz));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 매개변수로 받은 Fragment 클래스를 신규 생성하여 기존 활성화된 Fragment를 신규 생성한 Fragment로 교체하는 요청을 담은 EventBus 메시지를 호출한다.
     *
     * @param clazz
     */
    public static void replaceNewFragment(Class<? extends BaseFragment> clazz){

        replaceFragment(FragmentUtil.newFragment(clazz));
    }

    /**
     * 매개변수로 받은 fragment로 기존 활성화된 fragment를 교체하는 요청을 담은 EventBus 메시지를 호출한다.
     *
     * @param fragment
     */
    public static void replaceFragment(BaseFragment fragment){

        EventBus.getDefault().post(new FragmentReplaceEvent(fragment));
    }

    /**
     * view 하위의 특정 TableLayout의 TableRow를 순회하면서 EditText를 찾고,
     * 각 EditText의 id 문자열로 SharedPreferences(혹은 Constants)를 조회한 결과값을 EditText에 채워 넣는다.
     *
     * @param rootView
     * @param tableLayoutId
     */
    public static void fillDataToEditText(View rootView, int tableLayoutId) {

        TableLayout tableLayout = (TableLayout) rootView.findViewById(tableLayoutId);
        TableRow tableRow;
        View tmpView;
        EditText et;
        String id, key, val;
        for(int i=0; i<tableLayout.getChildCount(); i++){
            tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j<tableRow.getChildCount(); j++){
                tmpView = tableRow.getChildAt(j);
                if(tmpView instanceof EditText){
                    et = (EditText)tmpView;
                    id = et.getResources().getResourceEntryName(et.getId());
                    key = id.substring(3); // id에서 "et_" 제거
                    val = StringUtil.getPropStringForEnv(key);
                    et.setText(val);
                    Log.d("##", "EditText id:["+id+"], key:["+key+"], val:["+val+"]");
                }
            }
        }
    }

}
