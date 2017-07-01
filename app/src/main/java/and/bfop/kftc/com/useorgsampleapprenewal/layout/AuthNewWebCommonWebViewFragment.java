package and.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.ActionBarChangeEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.WebViewUtil;


/**
 * 사용자인증 개선버전에서 공통적으로 사용하는 WebView Fragment
 */
public class AuthNewWebCommonWebViewFragment extends BaseWebFragment implements BaseWebAuthInterface {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthNewWebCommonWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthNewWebCommonWebViewFragment newInstance(String actionBarTitle) {

        AuthNewWebCommonWebViewFragment fragment = new AuthNewWebCommonWebViewFragment();
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

        // 액션바 타이틀 교체
        EventBus.getDefault().post(new ActionBarChangeEvent(this.getArguments().getString(Constants.ACTIONBAR_TITLE)));

        // Bundle 파라미터로 받은 url 풀셋
        String urlToLoad = StringUtil.defaultString(this.getArguments().getString("urlToLoad"));
        Log.d("##", "urlToLoad: ["+urlToLoad+"]");

        // WebView로 url 호출
        WebViewUtil.loadUrlOnWebView(view, urlToLoad, this);

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    @Override
    public void onAuthCodeResponse(Map<String, Object> pMap) {

        // TokenRequestFragment 페이지에서의 뒤로가기 분기를 위해서 추가
        pMap.put("invokerType", "WEB_AUTH_NEW");

        Log.d("##", BeanUtil.getClassName(this)+".onAuthCodeResponse() > pMap: "+pMap);

        // token 발급 요청 Fragment로 이동한다.
        BaseFragment tokenRequestFragment = FragmentUtil.newFragment(TokenRequestFragment.class);
        BeanUtil.putAllMapToBundle(tokenRequestFragment.getArguments(), pMap); // webview resposne 에서 추출한 authcode 등을 TokenRequestFragment 에 넣어준다.
        FragmentUtil.replaceFragment(tokenRequestFragment);
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }
}
