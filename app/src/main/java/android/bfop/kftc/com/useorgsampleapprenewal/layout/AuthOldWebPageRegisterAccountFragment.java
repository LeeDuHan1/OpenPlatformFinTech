package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.WebViewUtil;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static android.bfop.kftc.com.useorgsampleapprenewal.R.id.btnFold;


/**
 * 계좌등록 기존버전 (웹 방식) Fragment
 *
 *      - TODO: 테스트가 까다로워서 일단 authorize 먼저 하기로 하고 일시 중단
 */
public class AuthOldWebPageRegisterAccountFragment extends BaseWebFragment implements BaseWebAuthInterface {

    private static String URI = "/oauth/2.0/register_account";

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

        // querystring을 만들기 위한 Map
        Map<String, String> pMap = new HashMap<>();
        pMap.put("response_type", "code");
        pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        pMap.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"));
        pMap.put("scope", StringUtil.getPropStringForEnv("SCOPE"));
        pMap.put("client_info", "whatever_you_want");

        // 호출 URL (querystring 포함)
        String urlToLoad = (App.getApiBaseUrl() + URI) + "?" + StringUtil.converMapToQuerystring(pMap);

        // WebView로 url 호출
        WebViewUtil.loadUrlOnWebView(view, urlToLoad, this);

        return view;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onDetach() {

        super.onDetach();

        // TODO: 공통버튼 이벤트바인딩 해제해야 할 듯
        ((Button)this.getView().findViewById(btnFold)).setOnClickListener(null); // 접음/펼침 버튼
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

    /**
     * url 에서 AuthorizationCode와 Scope를 추출하여 TokenRequestViewWebActivity 로 이동한다.
     *
     * @param url
     */
    public void goWebAuthCodeView(String url) {

/*        String callbackUrl = StringUtil.getPropStringForEnv("WEB_CALLBACK_URL"); // 여러 요청 중에서 web callback url 요청에 대한 것만 필터링하여 수행한다.
        if(url.startsWith(callbackUrl)){
            String authCode = StringUtil.getParamValFromUrlString(url, "code");
            String scope = StringUtil.getParamValFromUrlString(url, "scope");
            Log.d("", "## authCode: ["+authCode+"], scope: ["+scope+"]");
//                    MessageUtil.showToast("AuthorizationCode: "+authCode, 3000);
            Intent intent = new Intent(App.getAppContext(), TokenRequestViewWebActivity.class);
            intent.putExtra("AuthorizationCode", authCode);
            intent.putExtra("Scope", scope);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Activity가 아닌 Context에서 Intent를 사용하여 Activity를 호출할 때에는 이 플래그가 필요하다고 한다.
            startActivity(intent);
        }*/
    }

    @Override
    public void onAuthCodeResponse(Map<String, Object> pMap) {

        Log.d("##", BeanUtil.getClassName(this)+".onAuthCodeResponse() invoked!");
    }
}
