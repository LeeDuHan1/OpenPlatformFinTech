package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.handler.BackPressCloseHandler;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener,
        AuthNewMenuFragment.OnFragmentInteractionListener, AuthOldAppMenuFragment.OnFragmentInteractionListener,
        AuthOldWebMenuFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener
{

    private BackPressCloseHandler backPressCloseHandler;
    public BackPressCloseHandler getBackPressCloseHandler() {
        return backPressCloseHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 뒤로가기 버튼 종료 관련 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);

        //================================ fragment 추가 - start ================================
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment)
              .addToBackStack(null)
              .commit();
        }
        //================================ fragment 추가 - end ==================================

    }

    /**
     * 뒤로가기 버튼이 눌렸을 때 최초로 호출되는 메서드
     */
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // 현재 활성화된 fragment를 찾아서, 거기에 구현된 뒤로가기 핸들러를 호출해야 한다.
            FragmentManager fm = getSupportFragmentManager();
            BaseFragment fragment = (BaseFragment)fm.findFragmentById(R.id.fragment_container);
            Log.d("", "## onBackPressed() > fragment:" + fragment.getClass().getName());
            fragment.doBackBehavior();
        }
    }

    /**
     * 일반적으로 호출되는 onBackPressed()에 Navigation Drawer로 인한 분기처리가 들어가 있으므로,
     * 단순히 원래의 뒤로가기 행위를 각 Fragment에서 호출할 수 있도록 별도의 메서드로 분리하였음
     */
    public void back(){

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 네비게이션메뉴 선택 이벤트핸들러
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_authNew) {

        } else if (id == R.id.nav_authOldApp) {

        } else if (id == R.id.nav_authOldWeb) {

        } else if (id == R.id.nav_APICall) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goPage(int id){

        Fragment fm = null;
        String title = null;

        switch(id){
            case R.id.btnAuthNewMenu:
                fm = new AuthNewMenuFragment();
                title = "사용자인증 개선버전";
                break;
            case R.id.btnAuthOldAppMenu:
                fm = new AuthOldAppMenuFragment();
                title = "사용자인증 기존버전 (앱 방식)";
                break;
            case R.id.btnAuthOldWebMenu:
                fm = new AuthOldWebMenuFragment();
                title = "사용자인증 기존버전 (웹 방식)";
                break;
            case R.id.btnAPICallMenu:
                break;
            case R.id.btnSettings:
                fm = new SettingsFragment();
                title = "설정";
                break;
            default:
                break;
        }

        if(fm != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fm);
            ft.addToBackStack(null); // 뒤로가기 버튼 클릭시 이전 Fragment 스택을 불러올 수 있게 하기 위한 사전작업
            ft.commit();
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }

    }

    //===================================== 각 Fragment 들과의 통신 접점 - start =====================================
    @Override
    public void onFragmentInteractionMainPage(Uri uri) {

    }

    @Override
    public void onFragmentInteractionAuthNewPage(Uri uri) {

    }

    @Override
    public void onFragmentInteractionAuthOldAppPage(Uri uri) {

    }

    @Override
    public void onFragmentInteractionAuthOldWebPage(Uri uri) {

    }

    @Override
    public void onFragmentInteractionSettings(Uri uri) {

    }
    //===================================== 각 Fragment 들과의 통신 접점 - end =======================================

}
