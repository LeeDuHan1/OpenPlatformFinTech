package and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdapp;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.MainFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.OnClick;


/**
 * 사용자인증 기존버전 (앱 방식) 메뉴 Fragment
 */
public class AuthOldAppMenuFragment extends BaseFragment {

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_autholdapp_menu, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), false)); // true/false 주의

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick({ R.id.btnAuthOldApp, R.id.btnRegAcntOldApp })
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnAuthOldApp:
                goAuthOldAppAuthorize(v);
                break;
            case R.id.btnRegAcntOldApp:
                goAuthOldAppRegisterAccount(v);
                break;
            default:
                break;
        }

    }

    /**
     * 사용자 로그인 연결 (앱 방식)
     */
    public void goAuthOldAppAuthorize(View v) {

        try {
            // querystring을 만들기 위한 Map
            Map<String, String> pMap = new LinkedHashMap<>();
            pMap.put("response_type", "code");
            pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
            pMap.put("redirect_uri", StringUtil.getPropStringForEnv("APP_CALLBACK_URL"));

            // 호출 URL (querystring 포함)
            String urlToLoad = App.getAppScheme() + "authorize?" + StringUtil.convertMapToQuerystring(pMap);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlToLoad));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); // 원래 있던 코드. 반드시 필요한가?
            startActivity(intent); // 오픈플랫폼 앱 Activity 시작

        } catch (ActivityNotFoundException e) {
            MessageUtil.showToast("오픈플랫폼 앱("+App.getEnvName(App.getEnv())+")을 설치해 주십시오");
        }
    }

    /**
     * 계좌등록 (앱 방식)
     */
    public void goAuthOldAppRegisterAccount(View v) {

        try {
            // querystring을 만들기 위한 Map
            Map<String, String> pMap = new LinkedHashMap<>();
            pMap.put("response_type", "code");
            pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
            pMap.put("scope", StringUtil.getPropStringForEnv("SCOPE"));
            pMap.put("redirect_uri", StringUtil.getPropStringForEnv("APP_CALLBACK_URL"));

            // 호출 URL (querystring 포함)
            String urlToLoad = App.getAppScheme() + "register?" + StringUtil.convertMapToQuerystring(pMap);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlToLoad));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); // 원래 있던 코드. 반드시 필요한가?
            startActivity(intent); // 오픈플랫폼 앱 Activity 시작

        } catch (ActivityNotFoundException e) {
            MessageUtil.showToast("오픈플랫폼 앱("+App.getEnvName(App.getEnv())+")을 설치해 주십시오");
        }
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(MainFragment.class);
    }
}
