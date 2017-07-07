package and.bfop.kftc.com.useorgsampleapprenewal.layout.common;

import android.app.Dialog;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * API 호출 후 결과 JSON을 보여줄 목적으로 생성한 DialogFragment
 */
public class APICallResultFragment extends DialogFragment {

    TextView tvJsonResult;

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    /**
     * Only for DialogFragment
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_apicall_result, null);

//        TextView view = new TextView(getActivity());
//        view.setTextIsSelectable(true);
//        view.setText(getArguments().getString("rspJson"));

        builder.setView(view)
                .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing to do
                    }
                });
        Dialog dialog = builder.create();

        // TextView가 scroll 가능하도록 처리
        tvJsonResult = (TextView)view.findViewById(R.id.tvJsonResult);
//        tvJsonResult.setMovementMethod(new ScrollingMovementMethod());


//        ButterKnife.bind(this, view);
//        registerForContextMenu(tvJsonResult);


        // 결과값 채우기
        tvJsonResult.setText(getArguments().getString("rspJson"));


        return dialog;
    }

    @Override
    public void onResume() {

        // Dialog 사이즈 조정
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

        super.onResume();
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

//    @OnLongClick(R.id.tvJsonResult)
//    public boolean onLongClick(){
//
//        MessageUtil.showToast("@@@@@@@@@@@@@@@@@@");
//
//        return true;
//    }

}
