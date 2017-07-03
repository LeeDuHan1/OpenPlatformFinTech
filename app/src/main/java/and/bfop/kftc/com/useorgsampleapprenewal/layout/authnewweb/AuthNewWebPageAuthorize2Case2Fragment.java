package and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.OnClick;

/**
 ** 사용자인증 개선버전 Fragment (Case2)
 */
public class AuthNewWebPageAuthorize2Case2Fragment extends AuthNewWebPageBaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthNewWebPageAuthorize2Case2Fragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthNewWebPageAuthorize2Case2Fragment newInstance(String actionBarTitle) {

        AuthNewWebPageAuthorize2Case2Fragment fragment = new AuthNewWebPageAuthorize2Case2Fragment();
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

        return super.initView(inflater, container, R.layout.fragment_authnewweb_authorize2_case2);
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 사용자인증 개선버전 호출
     */
    public void invokeAuth(){

        Map<String, String> hMap = new LinkedHashMap<>();
        hMap.put("Kftc-Bfop-UserCellNo", "01026683306");
        hMap.put("Kftc-Bfop-UserCI", "8lVNGtFACsr6wWKe1kS34tM+tUODqwZxhYZqfdVFpYjg/TXrEclBzag2e8CzsemJVbRLQIt2EhawiQypch6sVg==");
        hMap.put("Kftc-Bfop-UserEmail", "itpsolver@gmail.com");
        hMap.put("Kftc-Bfop-UserInfo", "19781204");
        hMap.put("Kftc-Bfop-UserName", "이현재");
        hMap.put("Kftc-Bfop-UserSeqNo", "1100002505");
        String headerJson = BeanUtil.GSON.toJson(hMap, LinkedHashMap.class);

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
        pMap.put("auth_type", "1"); // 고정값 (Case2)
        pMap.put("bg_color", bgColor);
        pMap.put("txt_color", txtColor);
        pMap.put("btn1_color", btn1Color);
        pMap.put("btn2_color", btn2Color);

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2TabFragment.URI) + "?" + StringUtil.convertMapToQuerystring(pMap);

        super.callUrlUsingWebView(urlToLoad, headerJson);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick(R.id.btnAuthNewWebAuth2Case2)
    public void onClick(View v) {

        invokeAuth();
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }
}
