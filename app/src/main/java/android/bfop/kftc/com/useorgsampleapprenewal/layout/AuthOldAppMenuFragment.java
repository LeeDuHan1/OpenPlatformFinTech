package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 사용자인증 기존버전 (앱 방식) 메뉴 Fragment
 */
public class AuthOldAppMenuFragment extends BaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthOldAppMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthOldAppMenuFragment newInstance(String actionBarTitle) {

        AuthOldAppMenuFragment fragment = new AuthOldAppMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_autholdapp_menu, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

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

        ((Button)view.findViewById(R.id.btnAuthOldApp)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnRegAcntOldApp)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnAuthAcntOldApp)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnAuthOldApp:
                goAuthOldApp(v);
                break;
            case R.id.btnRegAcntOldApp:
                break;
            case R.id.btnAuthAcntOldApp:
                break;
            default:
                break;
        }

    }

    /**
     * 사용자 로그인 연결 (앱 방식)
     */
    public void goAuthOldApp(View v) {

        try {
            // querystring을 만들기 위한 Map
            Map<String, String> pMap = new LinkedHashMap<>();
            pMap.put("response_type", "code");
            pMap.put("client_id", StringUtil.getPropStringForEnv("APP_KEY"));
            pMap.put("redirect_uri", StringUtil.getPropStringForEnv("APP_CALLBACK_URL"));

            // 호출 URL (querystring 포함)
            String urlToLoad = App.getAppScheme() + "authorize?" + StringUtil.convertMapToQuerystring(pMap);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlToLoad));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent); // 오픈플랫폼 앱 Activit 시작

        } catch (ActivityNotFoundException e) {
            MessageUtil.showToast("오픈플랫폼 앱("+App.getEnvName(App.getEnv())+")을 설치해 주십시오");
        }
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(MainFragment.class);
    }
}
