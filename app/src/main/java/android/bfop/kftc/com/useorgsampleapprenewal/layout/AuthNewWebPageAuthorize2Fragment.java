package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import org.greenrobot.eventbus.EventBus;


/**
 * 사용자인증 개선버전 Fragment
 */
public class AuthNewWebPageAuthorize2Fragment extends BaseFragment {

    private static String URI = "/oauth/2.0/authorize2";

    private FragmentTabHost tabHost;

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public AuthNewWebPageAuthorize2Fragment() {
        // Required empty public constructor
    }

    /**
     * Fragment 생성 메서드
     *
     * @param actionBarTitle
     * @return
     */
    public static AuthNewWebPageAuthorize2Fragment newInstance(String actionBarTitle) {

        AuthNewWebPageAuthorize2Fragment fragment = new AuthNewWebPageAuthorize2Fragment();
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
        final View view = inflater.inflate(R.layout.fragment_authnewweb_authorize2, container, false); // createTabContent() 때문에 final 처리해 줌.
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        tabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        tabHost.setup(this.getActivity(), this.getFragmentManager(), android.R.id.tabcontent);

        createAndAddTabSpec(view, "tab1", "Case1");
        createAndAddTabSpec(view, "tab2", "Case2");
        createAndAddTabSpec(view, "tab3", "Case3");

        // OnTabChangeListener 설정
        TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                changeFragmentInTabContents(tabId);
            }
        };
        tabHost.setOnTabChangedListener(listener);

        // 컨텐츠영역의 내용을 첫번째 Fragment로 기본 설정
        changeFragmentInTabContents("tab1");

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

        return view;
    }

    /**
     * 컨텐츠 영역의 Fragment를 교체한다.
     *
     * @param tabId
     */
    private void changeFragmentInTabContents(String tabId) {

        Class<? extends BaseFragment> fragmentClass = null;
        switch(tabId){
            case "tab1":
                fragmentClass = AuthOldAppMenuFragment.class;
                break;
            case "tab2":
                fragmentClass = AuthOldWebMenuFragment.class;
                break;
            case "tab3":
                fragmentClass = APICallMenuFragment.class;
                break;
            default:
                break;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        BaseFragment fragment = FragmentUtil.newFragment(fragmentClass);
        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }

    private void createAndAddTabSpec(final View view, String tag, String indicator) {

        TabHost.TabSpec spec = tabHost.newTabSpec(tag);
        spec.setIndicator(indicator);
        spec.setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String tag) {
                return view.findViewById(android.R.id.tabcontent);
            }
        });
        tabHost.addTab(spec);
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(AuthNewWebMenuFragment.class);
    }

    @Override
    public void onClick(View v) {
        // nothing to do
    }
}
