package and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.OnClick;

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

        return getInstance(actionBarTitle, AuthNewWebPageAuthorize2Case1Fragment.class);
    }

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = super.initView(inflater, container, R.layout.fragment_authnewweb_authorize2_case1);

        // 저장되어 있던 폼데이터를 화면에 채워넣기
        FragmentUtil.fillSavedDataToForm(view, R.id.auth2Case1FormTable);

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 사용자인증 개선버전 호출
     */
    public void invokeAuth(){

        View v = getView();

        // 현재 폼데이터를 SharedPreferences에 저장
        FragmentUtil.saveFormData(v, R.id.auth2Case1FormTable);

        Map<String, String> pMap = new LinkedHashMap<>();
        pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        pMap.put("client_secret", StringUtil.getPropStringForEnv("APP_SECRET"));
        pMap.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"));
        pMap.put("response_type", "code"); // 고정값
        pMap.put("auth_type", "0"); // 고정값 (Case1)
        pMap.put("scope", FragmentUtil.getEtVal(v, R.id.et_ANW_SCOPE));
        pMap.put("client_info", FragmentUtil.getEtVal(v, R.id.et_ANW_CLIENT_INFO));
        pMap.put("bg_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BG_COLOR));
        pMap.put("txt_color", FragmentUtil.getEtVal(v, R.id.et_ANW_TXT_COLOR));
        pMap.put("btn1_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BTN1_COLOR));
        pMap.put("btn2_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BTN2_COLOR));

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2TabFragment.URI) + "?" + StringUtil.convertMapToQuerystring(pMap);

        super.callUrlUsingWebView(urlToLoad, null);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick(R.id.btnAuthNewWebAuth2Case1)
    public void onClick(View v) {

        invokeAuth();
    }

}
