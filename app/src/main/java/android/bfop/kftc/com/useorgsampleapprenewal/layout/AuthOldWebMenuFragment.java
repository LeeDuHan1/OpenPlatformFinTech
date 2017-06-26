package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentReplaceEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;


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

        ((Button)view.findViewById(R.id.btnAuthOldWeb)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnRegAcntOldWeb)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnAuthAcntOldWeb)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        Log.d("", "@@@@@@@@@@@@@@@@@@@@#####################@@@@@@@@@@@@@@@@@");

        BaseFragment fragment = null;
        String title = null;

        switch(v.getId()){
            case R.id.btnAuthOldWeb:
                fragment = AuthOldWebPageAuthorizeFragment.newInstance("사용자인증 기존버전 (웹 방식)");
                break;
            case R.id.btnRegAcntOldWeb:
                fragment = AuthOldWebPageRegisterAccountFragment.newInstance("계좌등록 기존버전 (웹 방식)");
                break;
            case R.id.btnAuthAcntOldWeb:
                break;
            default:
                break;
        }

        // TODO: Fragment 를 교체하는 EventBus를 작성해야 한다.
        EventBus.getDefault().post(new FragmentReplaceEvent(fragment));

//        if(fragment != null){
//
//            // Fragment 교체
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container, fragment);
//            ft.addToBackStack(null); // 뒤로가기 버튼 클릭시 이전 Fragment 스택을 불러올 수 있게 하기 위한 사전작업
//            ft.commit();
//
//            // 액션바 타이틀 교체
//            if(getSupportActionBar() != null){
//                getSupportActionBar().setTitle(fragment.getActionBarTitle());
//            }
    }

}
