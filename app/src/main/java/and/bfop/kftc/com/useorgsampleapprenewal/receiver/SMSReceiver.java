package and.bfop.kftc.com.useorgsampleapprenewal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import and.bfop.kftc.com.useorgsampleapprenewal.util.MessageUtil;

/**
 * SMS 수신 BroadcaseReceiver
 *
 * Created by LeeHyeonJae on 2017-07-04.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("##", "SMSReceiver.onReceive() called!");
        MessageUtil.showToast("SMSReceiver.onReceive() called!");

        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())){
            // sms 수신
        }

    }
}
