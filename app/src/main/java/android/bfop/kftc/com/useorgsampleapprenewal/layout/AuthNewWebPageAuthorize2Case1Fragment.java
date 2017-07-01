package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 ** 사용자인증 개선버전 Fragment (Case1)
 */
public class AuthNewWebPageAuthorize2Case1Fragment extends AuthNewWebPageBaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthNewWebPageAuthorize2Case1Fragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthNewWebPageAuthorize2Case1Fragment newInstance(String actionBarTitle) {

        return AuthNewWebPageBaseFragment.getInstance(actionBarTitle, AuthNewWebPageAuthorize2Case1Fragment.class);
    }

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.initView(inflater, container, R.layout.fragment_authnewweb_authorize2_case1);
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 사용자인증 개선버전 호출
     */
    public void invokeAuth(){

        String clientId = StringUtil.getPropStringForEnv("APP_KEY");
        String clientSecret = StringUtil.getPropStringForEnv("APP_SECRET");
        String redirectUri = StringUtil.getPropStringForEnv("WEB_CALLBACK_URL");
        String scope = "login transfer";
        String clientInfo = "[test] whatever you want";
        String bgColor = "#FBEFF2";
        String txtColor = "#088A08";
        String btn1Color = "#FF8000";
        String btn2Color = "#F3E2A9";

        Map<String, String> pMap = new LinkedHashMap<>();
        pMap.put("client_id", clientId);
        pMap.put("client_secret", clientSecret);
        pMap.put("response_type", "code"); // 고정값
        pMap.put("scope", scope);
        pMap.put("redirect_uri", redirectUri);
        pMap.put("client_info", clientInfo);
        pMap.put("auth_type", "0"); // 고정값 (Case1)
        pMap.put("bg_color", bgColor);
        pMap.put("txt_color", txtColor);
        pMap.put("btn1_color", btn1Color);
        pMap.put("btn2_color", btn2Color);

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2TabFragment.URI) + "?" + StringUtil.convertMapToQuerystring(pMap);

        super.callUrlUsingWebView(urlToLoad);
    }

    /**
     * 버튼 이벤트핸들러 바인딩
     *
     * @param view
     */
    @Override
    public void bindButtonClickEvents(View view){

        ((Button)view.findViewById(R.id.btnAuthNewWebAuth2Case1)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnAuthNewWebAuth2Case1:
                invokeAuth();
                break;
            default:
                break;
        }
    }

}
