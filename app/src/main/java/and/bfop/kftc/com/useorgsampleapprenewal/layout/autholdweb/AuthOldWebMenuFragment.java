package and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.MainFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import butterknife.OnClick;


/**
 * 사용자인증 기존버전 (웹 방식) 메뉴 Fragment
 */
public class AuthOldWebMenuFragment extends BaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthOldWebMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthOldWebMenuFragment newInstance(String actionBarTitle) {

        AuthOldWebMenuFragment fragment = new AuthOldWebMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_autholdweb_menu, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), false)); // true/false 주의

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick({ R.id.btnAuthOldWebAuth, R.id.btnAuthOldWebRegAcnt, R.id.btnAuthOldWebAuthAcnt })
    public void onClick(View v) {

        Class fragmentClass = null;
        switch(v.getId()){
            case R.id.btnAuthOldWebAuth:
                fragmentClass = AuthOldWebPageAuthorizeFragment.class;
                break;
            case R.id.btnAuthOldWebRegAcnt:
                fragmentClass = AuthOldWebPageRegisterAccountFragment.class;
                break;
            case R.id.btnAuthOldWebAuthAcnt:
                fragmentClass = AuthOldWebPageAuthorizeAccountFragment.class;
                break;
            default:
                break;
        }
        if(fragmentClass != null){
            FragmentUtil.replaceNewFragment(fragmentClass);
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