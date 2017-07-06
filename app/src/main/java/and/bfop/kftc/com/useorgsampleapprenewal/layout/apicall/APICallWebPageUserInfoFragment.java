package and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.APICallResultFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.restclient.RetrofitCustomAdapter;
import and.bfop.kftc.com.useorgsampleapprenewal.util.BeanUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 사용자정보조회 API호출 Fragment
 */
public class APICallWebPageUserInfoFragment extends BaseFragment {

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apicall_userinfo, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행

        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @OnClick(R.id.btnUserInfo)
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnUserInfo:
                getUserMe();
                break;
            default:
                break;
        }
    }

    /**
     * 버튼 onTouch 이벤트 핸들러
     *
     * @param v
     * @param event
     * @return
     */
    @OnTouch(R.id.btnUserInfo)
    public boolean onTouch(View v, MotionEvent event) {
        return FragmentUtil.onTouchSetColorFilter(v, event);
    }

    /**
     * 사용자정보조회
     */
    private void getUserMe(){

        String token = Constants.TOKEN_PREFIX + "9c7186ef-385c-43f1-a2c1-a8c8ede3437c";

        Map params = new LinkedHashMap<>();
        params.put("user_seq_no", "1100002505");

        Call<Map> call = RetrofitCustomAdapter.getInstance().userMe(token, params);

        call.enqueue(new Callback<Map>() { // retrofit 비동기 호출 (동기호출시 NetworkOnMainThreadException 발생)
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {

                Log.d("##", "onResponse() called!");
                Map rspMap = response.body();
                Log.d("##", "token() rspMap: " + rspMap);
                String rspJson = BeanUtil.GSON.toJson(rspMap);

                Log.d("##", "rspJson: "+rspJson);

                // 조회 성공시 해당 내용을 TextView에 출력
//                ((TextView)getView().findViewById(R.id.tvTokenResult)).setText(rspJson);

                // DialogFragment에 호출 결과 출력
                FragmentManager fm = getActivity().getSupportFragmentManager();
                APICallResultFragment dialogFragment = new APICallResultFragment();
                dialogFragment.show(fm, "fragment_dialog_test");
            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {

                Log.d("##", "onFailure() called!");
                t.printStackTrace();
            }
        });
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
    @Override
    public void onBackPressedForFragment() {

        FragmentUtil.replaceNewFragment(APICallMenuFragment.class);
    }
}
