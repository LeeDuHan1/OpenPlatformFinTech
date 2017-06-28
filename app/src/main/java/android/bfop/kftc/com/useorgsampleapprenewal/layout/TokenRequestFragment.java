package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.restclient.RetrofitCustomAdapter;
import android.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * authcode 발급 이후 token 발급 요청을 할 수 있는 Fragment 페이지
 *
 *  - 업무수행에 있어서 필수적인 페이지는 아님. token 발급 과정을 보여주기 위해서 작성한 페이지
 */
public class TokenRequestFragment extends BaseFragment {

    private String authcode;
    private String scope;

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public TokenRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static TokenRequestFragment newInstance(String actionBarTitle) {

        TokenRequestFragment fragment = new TokenRequestFragment();
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

        // EditText 에 authcode 등을 바인딩한다.
        Bundle args = this.getArguments();
        authcode = args.getString("authcode");
        scope = args.getString("scope");
        Log.d("##", "TokenRequestFragment > authcode:["+authcode+"], scope:["+scope+"]");

        EditText etAuthorizationCode = (EditText) view.findViewById(R.id.etAuthorizationCode);
        EditText etScope = (EditText) view.findViewById(R.id.etScope);
        etAuthorizationCode.setText(authcode);
        etScope.setText(scope);

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

        ((Button)view.findViewById(R.id.btnToken)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnToken:
                getToken();
                break;
            default:
                break;
        }
    }

    /**
     * 토큰 발급
     */
    private void getToken(){

        Map params = new LinkedHashMap<>();
        params.put("code", authcode);
        params.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
        params.put("client_secret", StringUtil.getPropStringForEnv("APP_SECRET"));
        params.put("redirect_uri", StringUtil.getPropStringForEnv("WEB_CALLBACK_URL")); //TODO: 앱쪽은 어떻게 처리해야 할 지 고민할 것
        params.put("grant_type", "authorization_code");

        Call<Map> call = RetrofitCustomAdapter.getInstance().token(params);

        call.enqueue(new Callback<Map>() { // retrofit 비동기 호출 (동기호출시 NetworkOnMainThreadException 발생)
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {

                Log.d("##", "onResponse() called!");
                Map rspMap = response.body();
                Log.d("##", "token() rspMap: " + rspMap);
                String rspJson = BeanUtil.GSON.toJson(rspMap);

                // 조회 성공시 해당 내용을 TextView에 출력
                ((TextView)getView().findViewById(R.id.tvTokenResult)).setText(rspJson);
            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {

                Log.d("##", "onFailure() called!");
                t.printStackTrace();
            }
        });
    }

}
