package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 ** 사용자인증 개선버전 Fragment (Case1)
 */
public class AuthNewWebPageAuthorize2Case1Fragment extends BaseFragment {

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

        AuthNewWebPageAuthorize2Case1Fragment fragment = new AuthNewWebPageAuthorize2Case1Fragment();
        Bundle args = new Bundle();
        args.putString(Constants.ACTIONBAR_TITLE, actionBarTitle);
        fragment.setArguments(args);
        return fragment;
    }

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authnewweb_authorize2_case1, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true)); // true/false 주의

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * authorize2 를 호출한다.
     */
    public void invokeAuth2(){

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
        pMap.put("client_info", clientId);
        pMap.put("auth_type", "0"); // 고정값 (Case1)
        pMap.put("bg_color", clientId);
        pMap.put("txt_color", clientId);
        pMap.put("btn1_color", clientId);
        pMap.put("btn2_color", clientId);

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2Fragment.URI) + "?" + StringUtil.convertMapToQuerystring(pMap);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        BaseFragment webViewFragment = FragmentUtil.newFragment(AuthNewWebCommonWebViewFragment.class);
        Bundle args = webViewFragment.getArguments();
        args.putString(Constants.ACTIONBAR_TITLE, webViewFragment.getActionBarTitle()); // 액션바 타이틀
        args.putString("urlToLoad", urlToLoad); // 호출 URL
        webViewFragment.setArguments(args);
        ft.replace(android.R.id.tabcontent, webViewFragment);
        ft.commit();
    }

    /**
     * 버튼 이벤트핸들러 바인딩
     *
     * @param view
     */
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
                invokeAuth2();
                break;
            default:
                break;
        }
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }
}
