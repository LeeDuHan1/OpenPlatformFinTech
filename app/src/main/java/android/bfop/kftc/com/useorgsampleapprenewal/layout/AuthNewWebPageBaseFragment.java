package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 ** 사용자인증 개선버전 부모 Fragment
 */
public class AuthNewWebPageBaseFragment extends BaseFragment {

    /**
     * 매개변수로 받은 URL을 WebView(사용자인증 개선버전용)로 호출한다.
     *
     * @param urlToLoad
     */
    protected void callUrlUsingWebView(String urlToLoad) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        BaseFragment webViewFragment = FragmentUtil.newFragment(AuthNewWebCommonWebViewFragment.class);
        Bundle args = webViewFragment.getArguments();
        args.putString(Constants.ACTIONBAR_TITLE, this.getActionBarTitle()); // 액션바 타이틀 전달
        args.putString("urlToLoad", urlToLoad); // 호출 URL 전달
        webViewFragment.setArguments(args);
        ft.replace(android.R.id.tabcontent, webViewFragment);
        ft.commit();
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }

    @Override
    public void onClick(View v) {

    }
}
