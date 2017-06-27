package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * 발급받은 authcode를 눈으로 확인할 수 있도록 보여주는 페이지 (웹 방식)
 *
 *  - 각 앱의 callback 페이지가 호출될 때 querystring 으로 제공되는 authcode를 눈으로 확인할 수 있도록 하기 위한 목적으로 작성
 *  - 또한 Token 발급까지도 시뮬레이션 해 볼 수 있도록 하기 위한 목적도 있음.
 *  - 실제 업무상에서는 불필요한 페이지임. 오직 확인의 편의를 위해 제공.
 */
public class AuthcodeViewWebFragment extends BaseWebFragment {

    private static String URI = "/oauth/2.0/token";

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthcodeViewWebFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthcodeViewWebFragment newInstance(String actionBarTitle) {

        AuthcodeViewWebFragment fragment = new AuthcodeViewWebFragment();
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
        View view = inflater.inflate(R.layout.fragment_token_req_web, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

//        String authCode = intent.getStringExtra("AuthorizationCode")
        String authCode = ""; //TODO: authcode 전달받기
//        String invoker = StringUtil.defaultString(intent.getStringExtra("Invoker"));
        String invoker = ""; // TODO: invoker 전달받기
        String redirectUriKey = StringUtil.EMPTY;

        Log.d("## invoker", invoker);
        if("APP".equals(invoker)){
            redirectUriKey = "APP_CALLBACK_URL";
        } else if("WEB".equals(invoker)){
            redirectUriKey = "WEB_CALLBACK_URL";
        }

        // querystring을 만들기 위한 Map
        Map<String, String> pMap = new HashMap<>();
        pMap.put("code", authCode);
        pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        pMap.put("client_secret", StringUtil.getPropStringForEnv("APP_SECRET"));
        pMap.put("redirect_uri", StringUtil.getPropStringForEnv(redirectUriKey));
        pMap.put("grant_type", "authorization_code");

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + URI) + "?" + StringUtil.converMapToQuerystring(pMap);

        // WebView로 url 호출
//        WebViewUtil.loadUrlOnWebView(view, urlToLoad);

        // TODO: 그냥 retrofit을 사용하자.

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
