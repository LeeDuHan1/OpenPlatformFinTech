package and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.OnClick;

/**
 ** 사용자인증 개선버전 Fragment (Case3)
 */
public class AuthNewWebPageAuthorize2Case3Fragment extends AuthNewWebPageBaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthNewWebPageAuthorize2Case3Fragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthNewWebPageAuthorize2Case3Fragment newInstance(String actionBarTitle) {

        AuthNewWebPageAuthorize2Case3Fragment fragment = new AuthNewWebPageAuthorize2Case3Fragment();
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

        View view  = super.initView(inflater, container, R.layout.fragment_authnewweb_authorize2_case3);

        // 저장되어 있던 폼데이터를 화면에 채워넣기
        FragmentUtil.fillSavedDataToForm(view, R.id.auth2Case3FormTable);

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 사용자인증 개선버전 호출
     */
    public void invokeAuth(){

        View v = getView();

        // 현재 폼데이터를 SharedPreferences에 저장
        FragmentUtil.saveFormData(v, R.id.auth2Case3FormTable);

        // http header Map
        HashMap<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("Kftc-Bfop-UserSeqNo", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_SEQ_NO));
        headerMap.put("Kftc-Bfop-UserCI", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_CI));
        headerMap.put("Kftc-Bfop-AccessToken", FragmentUtil.getEtVal(v, R.id.et_ANW_ACCESS_TOKEN));

        // querystring 을 구성할 파라미터 Map
        Map<String, String> paramMap = super.getDefaultParamMap(v);
        paramMap.put("auth_type", "2"); // 고정값 (2 == Case3)

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2TabFragment.URI) + "?" + StringUtil.convertMapToQuerystring(paramMap);

        // WebView 호출용 Fragment로 전달
        super.callUrlUsingWebView(urlToLoad, headerMap);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick(R.id.btnAuthNewWebAuth2Case3)
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
