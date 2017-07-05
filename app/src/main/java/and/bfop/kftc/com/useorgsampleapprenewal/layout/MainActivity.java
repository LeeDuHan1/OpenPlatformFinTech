package and.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.Intent;
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
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.ActionBarChangeEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.BackButtonPressedInMainEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentReplaceEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.SMSReceiveEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.handler.BackPressCloseHandler;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdapp.AuthOldAppMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.TokenRequestFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.settings.SettingsFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle drawerToggle;
    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    private BackPressCloseHandler backPressCloseHandler;

    private boolean toolbarNavigationListenerRegistered = false;

    ///////////////////////////////////// Activity Lifecycle Callbacks - start /////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onCreate() called!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // actionbar 좌측에 뒤로가기 화살표 표시

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 뒤로가기 버튼 종료 관련 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);

        //================================ fragment 추가 - start ================================
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = FragmentUtil.newFragment(MainFragment.class);
            fm.beginTransaction().add(R.id.fragment_container, fragment)
                    //.addToBackStack(null)
                    .commit();
        }
        //================================ fragment 추가 - end ==================================

        // 뒤로가기시 액션바 타이틀도 같이 변경해 주도록 OnBackStackChangedListener 추가
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                BaseFragment fragment = (BaseFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);;
                if(fragment != null){ // 이 조건을 넣지 않으면 초기 기동시 NPE가 발생한다.
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setTitle(fragment.getActionBarTitle());
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onStart() called!");
        super.onStart();
        EventBus.getDefault().register(this);   // EventBus 등록
    }

    @Override
    protected void onRestart() {
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onRestart() called!");
        super.onRestart();
    }

    @Override
    protected void onResume() {

        super.onResume();
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onResume() called!");

        // 오픈플랫폼 앱 호출후 결과를 받는 부분(ex: 사용자로그인연결 결과)에 대한 처리를 여기서 하자.
        Intent intent = getIntent();
//        MessageUtil.showToast("## intent:"+intent);
//        MessageUtil.showToast("## intent.getData():"+intent.getData());
        if (intent == null || intent.getData() == null) return;

//        MessageUtil.showToast("## intent.getAction():"+intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String rspCode = uri.getQueryParameter("rsp_code");
            String rspMsg = uri.getQueryParameter("rsp_msg");
            String authCode = uri.getQueryParameter("authorization_code");
            String scope = uri.getQueryParameter("scope");
//            MessageUtil.showToast(String.format("코드: %s\n메시지: %s\n인증 코드: %s\n인증 범위: %s", rspCode, rspMsg, authCode, scope));

            BaseFragment fragment = FragmentUtil.newFragment(TokenRequestFragment.class);
            Bundle args = fragment.getArguments();
            args.putString("rspCode", rspCode);
            args.putString("rspMsg", rspMsg);
            args.putString("authcode", authCode);
            args.putString("scope", scope);
            args.putString("invokerType", "APP"); // 앱에서의 요청과 웹에서의 요청을 구분해 주기 위해서 추가
            replaceFragment(fragment);
        }

    }

    @Override
    protected void onPause() {
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onPause() called!");
        super.onPause();
    }

    @Override
    protected void onStop() {
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onStop() called!");
        super.onStop();
        EventBus.getDefault().unregister(this); // EventBus 해지
    }

    @Override
    protected void onDestroy() {
//        MessageUtil.showToast(BeanUtil.getClassName(this)+".onDestroy() called!");
        super.onDestroy();
    }
    ///////////////////////////////////// Activity Lifecycle Callbacks - end ///////////////////////////////////////

    /**
     * 액션바 우측 옵션메뉴 생성
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 우상단 메뉴 선택 이벤트핸들러
     *
     * @param item
     * @return
     */
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

        goPage(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 뒤로가기 버튼이 눌렸을 때 최초로 호출되는 메서드
     */
    @Override
    public void onBackPressed() {

        // Navigation Drawer가 열려 있을 경우 닫는다.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        // Navigation Drawer가 닫혀 있을 경우
        } else {
            BaseFragment fragment = (BaseFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment != null) {
                fragment.onBackPressedForFragment();
            }

        }
    }

    /**
     * 페이지 이동
     *
     * @param id
     */
    public void goPage(int id){

        Class<? extends BaseFragment> fragmentClass = null;
        switch(id){
            case R.id.btnAuthNewMenu:   // 메인페이지 버튼
            case R.id.nav_authNew:      // Navigation Drawer 메뉴
                fragmentClass = AuthNewWebMenuFragment.class;
                break;
            case R.id.btnAuthOldAppMenu:
            case R.id.nav_authOldApp:
                fragmentClass = AuthOldAppMenuFragment.class;
                break;
            case R.id.btnAuthOldWebMenu:
            case R.id.nav_authOldWeb:
                fragmentClass = AuthOldWebMenuFragment.class;
                break;
            case R.id.btnAPICallMenu:
            case R.id.nav_APICall:
                fragmentClass = APICallMenuFragment.class;
                break;
            case R.id.btnSettings:
            case R.id.nav_setting:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                break;
        }
        FragmentUtil.replaceNewFragment(fragmentClass);
    }

    /**
     * MainActivity 위에 떠 있는 Fragment 교체
     *
     * @param fragment
     */
    private void replaceFragment(BaseFragment fragment) {

        if(fragment != null){

            // Fragment 교체
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            //ft.addToBackStack(null); // 뒤로가기 버튼 클릭시 이전 Fragment 스택을 불러올 수 있게 하기 위한 사전작업
            ft.commit();

            // 액션바 타이틀 교체
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(fragment.getActionBarTitle());
            }
        }
    }

    /**
     * 액션바에 뒤로가기 화살표 표시 및 뒤로가기 기능 추가
     *
     * @param enable true일 경우에만 뒤로가기 기능 추가를 수행한다.
     */
    public void showBackArrowOnActionBar(boolean enable){

        if (enable) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (! toolbarNavigationListenerRegistered) {
                drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                toolbarNavigationListenerRegistered = true;
            }

        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(null);
            toolbarNavigationListenerRegistered = false;
        }

        drawerToggle.syncState(); // 이걸 호출해 주지 않으면 햄버거 버튼이 없어져버리는 현상이 발생한다!
    }

    /**
     * FragmentInitEvent 에 대한 EventBus Subscriber
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentInitialized(FragmentInitEvent event){

        showBackArrowOnActionBar(event.isBackArrowOnActionBar());
    }

    /**
     * FragmentReplaceEvent 에 대한 EventBus Subscriber
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentReplace(FragmentReplaceEvent event){

        replaceFragment(event.getFragment());
    }

    /**
     * ActionBarChangeEvent 에 대한 EventBus Subscriber
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionBarChange(ActionBarChangeEvent event){

        String actionBarTitle = event.getActionBarTitle();
        Log.d("##", "ActionBarChangeEvent.getActionBarTitle(): "+actionBarTitle);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
        }
    }

    /**
     * SMSReceiveEvent 에 대한 EventBuss Subscriber
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSMSReceive(SMSReceiveEvent event){

        String sender = event.getSender();
        String contents = event.getContents();
        // 한국모바일인증에서 보낸 본인인증 메시지일 경우
        if("0220338500".equals(sender)){
            String authNum = contents.replaceAll("(.*)(\\d{6})(.*)", "$2"); // 문자 내용 중 6자리 숫자(인증번호)만 추출한다.
            MessageUtil.showToast("인증번호:["+authNum+"]");
        }
    }

    /**
     * BackButtonPressedInMainEvent 에 대한 EventBus Subscriber
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackButtonPressedInMain(BackButtonPressedInMainEvent event){

        FragmentManager fm = getSupportFragmentManager();
        int backStackCnt = fm.getBackStackEntryCount();
        Log.d("", "## backStackCnt: "+backStackCnt);
        // backstack이 없을 경우 backPressCloseHandler 를 호출한다
        if (backStackCnt <= 1) {
            backPressCloseHandler.onBackPressed();
        }
    }


}
