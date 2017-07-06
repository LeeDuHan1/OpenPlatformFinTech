package and.bfop.kftc.com.useorgsampleapprenewal.layout.common;

import android.app.Dialog;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.WindowManager;

/**
 * API 호출 후 결과 JSON을 보여줄 목적으로 생성한 DialogFragment
 */
public class APICallResultFragment extends DialogFragment {

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
        builder.setView(inflater.inflate(R.layout.fragment_apicall_result, null))
                .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing to do
                    }
                });
        return builder.create();
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

}
