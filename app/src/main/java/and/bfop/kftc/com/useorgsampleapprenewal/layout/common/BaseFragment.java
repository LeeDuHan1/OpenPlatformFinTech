package and.bfop.kftc.com.useorgsampleapprenewal.layout.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;

import and.bfop.kftc.com.useorgsampleapprenewal.layout.MainActivity;
import and.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.ButterKnife;


/**
 * Fragment 공통 부모 클래스
 */
public abstract class BaseFragment extends Fragment {

    protected MainActivity mainActivity;
    protected ActionBar actionBar;
    public ActionBarDrawerToggle drawerToggle;
    protected View thisFragmentView;

    /**
     * 초기화 수행
     *
     * @param view
     */
    public void initBaseFragment(View view){

        mainActivity = ((MainActivity)this.getActivity());
        actionBar = mainActivity.getSupportActionBar();
        drawerToggle = mainActivity.getDrawerToggle();
        thisFragmentView = view;
        ButterKnife.bind(this, view); // ButterKnife의 전체 view 바인딩
    }

    /**
     * 액션바 타이틀 리턴
     *
     * @return
     */
    public String getActionBarTitle(){

        return StringUtil.defaultString(this.getArguments().get(Constants.ACTIONBAR_TITLE));
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 기본동작
     */
    public void onBackPressedForFragment(){

        // 원래는 기본 동작으로 그냥 backstack을 호출해 줄 생각이었으나,
        // 일부 Fragment에서는 특정 Fragment로 이동하고, 일부 Fragment는 backstack을 호출하고 하는 것이 일관성이 없고,
        // 루프에 빠질 우려가 있어서 기본동작을 제거하였음
        // this.getActivity().getSupportFragmentManager().popBackStackImmediate(); // backstack 호출

        MessageUtil.showToast(BeanUtil.getClassName(this)+" 에서의 뒤로가기 버튼 동작을 정의해 주십시오.");
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Log.d("##", BeanUtil.getClassName(this) + " onAttach() invoked!");
    }

    @Override
    public void onDetach() {

        super.onDetach();
        Log.d("##", BeanUtil.getClassName(this) + " onDetach() invoked!");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d("##", BeanUtil.getClassName(this) + " onDestroy() invoked!");
//        MessageUtil.showToast(BeanUtil.getClassName(this) + " onDestroy() invoked!");
    }
}
