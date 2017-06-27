package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 * Fragment 공통 부모 클래스
 */
public abstract class BaseFragment extends Fragment implements Button.OnClickListener {

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
    }

    /**
     * 액션바 타이틀 리턴
     *
     * @return
     */
    public String getActionBarTitle(){

        return StringUtil.defaultString(this.getArguments().get(Constants.ACTIONBAR_TITLE));
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
}
