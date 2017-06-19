package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;


/**
 * Fragment 공통 부모 클래스
 */
public abstract class BaseFragment extends Fragment implements Button.OnClickListener {

    protected String actionBarTitle;

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
     * 뒤로가기 눌렀을 때 동작
     *
     */
    public abstract void doBackBehavior();

}
