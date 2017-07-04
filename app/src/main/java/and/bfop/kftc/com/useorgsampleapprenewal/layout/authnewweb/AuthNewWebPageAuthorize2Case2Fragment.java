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

        View view  = super.initView(inflater, container, R.layout.fragment_authnewweb_authorize2_case2);

        // 저장되어 있던 폼데이터를 화면에 채워넣기
        FragmentUtil.fillSavedDataToForm(view, R.id.auth2Case2FormTable);

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 사용자인증 개선버전 호출
     */
    public void invokeAuth(){

        View v = getView();

        // 현재 폼데이터를 SharedPreferences에 저장
        FragmentUtil.saveFormData(v, R.id.auth2Case2FormTable);

        HashMap<String, String> headerMap = new LinkedHashMap<>(); // Serializable 때문에 HashMap 형식으로 선언
        headerMap.put("Kftc-Bfop-UserSeqNo", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_SEQ_NO));
        headerMap.put("Kftc-Bfop-UserCI", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_CI));
        headerMap.put("Kftc-Bfop-UserName", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_NAME));
        headerMap.put("Kftc-Bfop-UserInfo", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_INFO));
        headerMap.put("Kftc-Bfop-UserCellNo", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_CELL_NO));
        headerMap.put("Kftc-Bfop-UserEmail", FragmentUtil.getEtVal(v, R.id.et_ANW_USER_EMAIL));

        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        paramMap.put("client_secret", StringUtil.getPropStringForEnv("APP_SECRET"));
        paramMap.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"));
        paramMap.put("response_type", "code"); // 고정값
        paramMap.put("auth_type", "1"); // 고정값 (Case2)
        paramMap.put("scope", FragmentUtil.getEtVal(v, R.id.et_ANW_SCOPE));
        paramMap.put("client_info", FragmentUtil.getEtVal(v, R.id.et_ANW_CLIENT_INFO));
        paramMap.put("bg_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BG_COLOR));
        paramMap.put("txt_color", FragmentUtil.getEtVal(v, R.id.et_ANW_TXT_COLOR));
        paramMap.put("btn1_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BTN1_COLOR));
        paramMap.put("btn2_color", FragmentUtil.getEtVal(v, R.id.et_ANW_BTN2_COLOR));

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + AuthNewWebPageAuthorize2TabFragment.URI) + "?" + StringUtil.convertMapToQuerystring(paramMap);

        super.callUrlUsingWebView(urlToLoad, headerMap);
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
