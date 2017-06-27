package android.bfop.kftc.com.useorgsampleapprenewal.util;

import android.app.AlertDialog;
import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.layout.BaseWebAuthInterface;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeeHyeonJae on 2017-06-23.
 */

public class WebViewUtil {

    /**
     * 매개변수로 받은 url을 WebView로 로딩한다.
     *
     * @param view
     * @param urlToLoad
     */
    public static void loadUrlOnWebView(View view, String urlToLoad, final BaseWebAuthInterface fragment) {

        Log.d("##", "WebView 호출 URL: ["+ urlToLoad +"]");

        EditText etUrl = (EditText)view.findViewById(R.id.etUrl);
        WebView webView = (WebView)view.findViewById(R.id.webView);

        etUrl.setText(urlToLoad);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); //HSY: 로그인을 위해 필요
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(App.getAppContext()) // TODO: 작동 확인
                        .setTitle("확인")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which){
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                Log.d("##", "url: "+ url);

                /*
                 * AuthorizationCode 발급이 완료된 이후에, 해당 코드를 사용하여 토큰발급까지의 흐름을 UI상에 보여주기 위해서 추가한 코드
                 * 이용기관에 이렇게 사용하도록 가이드 하는 것은 아님에 주의할 것.
                 * 여러 요청 중에서 web callback url 요청에 대한 것만 필터링하여 수행한다.
                 */
                String callbackUrl = StringUtil.getPropStringForEnv("WEB_CALLBACK_URL");
                if(url.startsWith(callbackUrl)){
                    String authCode = StringUtil.getParamValFromUrlString(url, "code");
                    String scope = StringUtil.getParamValFromUrlString(url, "scope");
                    Log.d("##", "authCode: ["+authCode+"], scope: ["+scope+"]");

//                    Intent intent = new Intent(App.getAppContext(), TokenRequestViewWebActivity.class);
//                    intent.putExtra("AuthorizationCode", authCode);
//                    intent.putExtra("Scope", scope);
//                    startActivity(intent);

                    Map<String, Object> pMap = new HashMap<>();
                    pMap.put("AuthorizationCode", authCode);
                    pMap.put("Scope", scope);

                    fragment.onAuthCodeResponse(pMap);
                }

                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }
        });

        // WebView로 URL 호출
        webView.loadUrl(urlToLoad);
    }
}
