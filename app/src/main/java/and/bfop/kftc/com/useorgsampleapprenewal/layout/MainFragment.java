package and.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.BackButtonPressedInMainEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import butterknife.OnClick;


/**
 * 메인 메뉴 Fragment (최초 로딩 페이지)
 */
public class MainFragment extends BaseFragment {

    /**
     * 생성자
     *  - 매개변수가 있는 생성자를 사용할 수 없는 제약이 있다.
     */
    public MainFragment() {
       // Required empty public constructor
    }

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        MessageUtil.showToast(BeanUtil.getClassName(this) + ".onCreateView called!");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), false)); // false

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick({ R.id.btnAuthNewMenu, R.id.btnAuthOldAppMenu, R.id.btnAuthOldWebMenu, R.id.btnAPICallMenu, R.id.btnSettings })
    public void onClick(View v) {

        Fragment fm = null;
        String title = null;

        int btnId = v.getId();
        MainActivity mainActivity = ((MainActivity)getActivity());

        switch(btnId){
            case R.id.btnAuthNewMenu:
                break;
            case R.id.btnAuthOldAppMenu:
                break;
            case R.id.btnAuthOldWebMenu:
                break;
            case R.id.btnAPICallMenu:
                break;
            case R.id.btnSettings:
                break;
            default:
                break;
        }

        mainActivity.goPage(btnId);

    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 기본동작
     */
    @Override
    public void onBackPressedForFragment() {

        EventBus.getDefault().post(new BackButtonPressedInMainEvent());
    }
}
