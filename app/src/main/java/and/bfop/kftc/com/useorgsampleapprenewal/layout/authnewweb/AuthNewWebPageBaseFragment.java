package and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;

/**
 ** 사용자인증 개선버전 부모 Fragment
 */
public class AuthNewWebPageBaseFragment extends BaseFragment {

    /**
     * Fragment 인스턴스를 생성하여 리턴한다.
     *
     * @param actionBarTitle
     * @param clazz
     * @param <T>
     * @return
     */
    protected static <T extends AuthNewWebPageBaseFragment> T getInstance(String actionBarTitle, Class<T> clazz) {

        T fragment = null;
        try {
            fragment = clazz.newInstance(); // 동적 객체 생성
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putString(Constants.ACTIONBAR_TITLE, actionBarTitle);
        fragment.setArguments(args);
        return clazz.cast(fragment); // 동적 형변환
    }

    /**
     * Fragment를 초기화하여 View를 리턴한다.
     *
     * @param inflater
     * @param container
     * @param fragmentResourceId
     * @return
     */
    protected View initView(LayoutInflater inflater, ViewGroup container, int fragmentResourceId) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(fragmentResourceId, container, false);
        initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true)); // true/false 주의

        return view;
    }

    /**
     * 매개변수로 받은 URL을 WebView(사용자인증 개선버전용)로 호출한다.
     *
     * @param urlToLoad
     * @param headerJson
     */
    protected void callUrlUsingWebView(String urlToLoad, String headerJson) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        BaseFragment webViewFragment = FragmentUtil.newFragment(AuthNewWebCommonWebViewFragment.class);
        Bundle args = webViewFragment.getArguments();
        args.putString(Constants.ACTIONBAR_TITLE, this.getActionBarTitle()); // 액션바 타이틀 전달
        args.putString("headerJson", headerJson); // 호출시 전달할 http header들을 json화 한 문자열
        args.putString("urlToLoad", urlToLoad); // 호출 URL 전달
        webViewFragment.setArguments(args);
        ft.replace(android.R.id.tabcontent, webViewFragment);
        ft.commit();
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }

}
