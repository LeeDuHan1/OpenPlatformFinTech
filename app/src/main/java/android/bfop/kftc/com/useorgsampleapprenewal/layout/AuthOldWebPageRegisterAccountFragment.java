package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;


/**
 * 계좌등록 기존버전 (웹 방식) Fragment
 */
public class AuthOldWebPageRegisterAccountFragment extends BaseWebFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthOldWebPageRegisterAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthOldWebPageRegisterAccountFragment newInstance(String actionBarTitle) {

        AuthOldWebPageRegisterAccountFragment fragment = new AuthOldWebPageRegisterAccountFragment();
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

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));


        String queryString = null;
        Map<String, String> pMap = new HashMap<>();
        pMap.put("response_type", "code");
        pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        pMap.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"));
        pMap.put("scope", StringUtil.getPropStringForEnv("SCOPE"));
        pMap.put("client_info", "whatever_you_want");

//        pMap.entrySet().stream()
//                .map(p -> StringUtil.urlEncode(p.getKey()) + "=" + StringUtil.urlEncode(p.getValue()))
//                .reduce((p1, p2) -> p1 + "&" + p2)
//                .orElse("");





/*

        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair("response_type", "code"));
        params.add(new BasicNameValuePair("client_id", StringUtil.getPropStringForEnv("APP_KEY")));
        params.add(new BasicNameValuePair("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL")));
        params.add(new BasicNameValuePair("scope", StringUtil.getPropStringForEnv("SCOPE")));
        params.add(new BasicNameValuePair("client_info", "whatever_you_want"));

        // 20170214 - ARS 테스트를 위한 임시 파라미터 추가 (TODO: 추후에는 포털에서 자동 분기하므로 삭제할 것)
//        params.add(new BasicNameValuePair("type", "ars"));

        String paramString = URLEncodedUtils.format(params, "utf-8");
        String urlToLoad = (App.getApiBaseUrl() + URI) + "?" + paramString;

        m_etUrl = (EditText) findViewById(R.id.etUrl);
        m_webView = (WebView) findViewById(R.id.webView);

        m_etUrl.setText(urlToLoad);
        WebSettings settings = m_webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true); //HSY: 로그인을 위해 필요
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // WebView의 팝업처리를 위해서 설정 추가 - 20170227 => 의도한 대로 작동하지 않았기 때문에 주석 처리
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setSupportMultipleWindows(true);

        m_webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(AuthOldWebPageRegisterAccountFragment.this)
                        .setTitle("확인")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });
        m_webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d("@@ url", url);

                */
/*
                 * AuthorizationCode 발급이 완료된 이후에, 해당 코드를 사용하여 토큰발급까지의 흐름을 UI상에 보여주기 위해서 추가한 코드
                 * 이용기관에 이렇게 사용하도록 가이드 하는 것은 아님에 주의할 것.
                 *//*

                goWebAuthCodeView(url);

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }
        });

        m_webView.loadUrl(urlToLoad);

*/


        return view;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 버튼 이벤트핸들러 바인딩
     *
     * @param view
     */
    public void bindButtonClickEvents(View view){

//        ((Button)view.findViewById(R.id.btnAuthOldWeb)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        // BaseWebFragment 에서 공통으로 정의한 이벤트핸들러도 함께 호출해 주어야 한다. (ex: 접음/펼침 버튼)
        super.onClick(v);

        switch(v.getId()){
//            case R.id.btnAuthOldWeb:
//                break;
            default:
                break;
        }
    }

}
