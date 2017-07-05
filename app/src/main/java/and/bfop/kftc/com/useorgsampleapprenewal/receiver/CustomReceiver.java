package and.bfop.kftc.com.useorgsampleapprenewal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import and.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;

/**
 * 사용자정의 BroadcaseReceiver
 *
 *  - SMS수신 처리 목적으로 생성
 *
 * Created by LeeHyeonJae on 2017-07-04.
 */
public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // SMS 수신일 경우
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())){
            MessageUtil.showToast("SMS 수신!! >, <");



        }

    }
}
