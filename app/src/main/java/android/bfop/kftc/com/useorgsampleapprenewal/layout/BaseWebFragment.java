package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.bfop.kftc.com.useorgsampleapprenewal.R.id.btnFold;

/**
 * WebView를 탑재한 Fragment들의 공통 부모 클래스
 */
public abstract class BaseWebFragment extends BaseFragment {

    @Override
    public void initBaseFragment(View view){

        super.initBaseFragment(view); // BaseFragment의 동일 메서드 선 호출
//        ((Button)view.findViewById(btnFold)).setOnClickListener(this); // 접음/펼침 버튼
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btnFold){
            onFold(v);
        }
        switch(v.getId()){
            case R.id.btnFold:
                onFold(v);
                break;
            case R.id.btnGo:
                onFold(v);
                break;
            default:
                break;
        }


    }

    /**
     * URL 펼침/접음 버튼의 버튼명/기능 토글
     *
     * @param v
     */
    protected void onFold(View v) {

        Log.d("##", "call onFold!!!!!!!!!!!");

        EditText edt = (EditText) this.getView().findViewById(R.id.etUrl); // URL 표시부
        Button btn = (Button) this.getView().findViewById(btnFold); // 펼침/접음 버튼

        if(Constants.BTN_NAME_UNFOLD.equals(btn.getText())){
            edt.setSingleLine(false);
            btn.setText(Constants.BTN_NAME_FOLD);
        }else{
            edt.setSingleLine(true);
            btn.setText(Constants.BTN_NAME_UNFOLD);
        }
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        Log.d("@@@@@ onDetach()", "view: "+this.getView());
//    }
}
