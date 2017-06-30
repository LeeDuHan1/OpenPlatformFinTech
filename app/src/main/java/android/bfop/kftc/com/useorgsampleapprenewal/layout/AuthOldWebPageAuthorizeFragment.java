package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentReplaceEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.WebViewUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 사용자인증 기존버전 (웹 방식) Fragment
 */
public class AuthOldWebPageAuthorizeFragment extends BaseWebFragment implements BaseWebAuthInterface {

    private static String URI = "/oauth/2.0/authorize";

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthOldWebPageAuthorizeFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthOldWebPageAuthorizeFragment newInstance(String actionBarTitle) {

        AuthOldWebPageAuthorizeFragment fragment = new AuthOldWebPageAuthorizeFragment();
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
        View view = inflater.inflate(R.layout.fragment_webview_common, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

        // querystring을 만들기 위한 Map
        Map<String, String> pMap = new LinkedHashMap<>();
        pMap.put("response_type", "code");
        pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        pMap.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"));
        pMap.put("scope", "login");
        pMap.put("client_info", "whatever_you_want");

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + URI) + "?" + StringUtil.convertMapToQuerystring(pMap);

        // WebView로 url 호출
        WebViewUtil.loadUrlOnWebView(view, urlToLoad, this);

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    @Override
    public void onAuthCodeResponse(Map<String, Object> pMap) {

        Log.d("##", BeanUtil.getClassName(this)+".onAuthCodeResponse() > pMap: "+pMap);

        // token 발급 요청 Fragment로 이동한다.
        BaseFragment tokenRequestFragment = FragmentUtil.newFragment(TokenRequestFragment.class);
        BeanUtil.putAllMapToBundle(tokenRequestFragment.getArguments(), pMap); // webview resposne 에서 추출한 authcode 등을 TokenRequestFragment 에 넣어준다.
        EventBus.getDefault().post(new FragmentReplaceEvent(tokenRequestFragment));
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthOldWebMenuFragment.class);
    }
}
