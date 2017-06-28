package android.bfop.kftc.com.useorgsampleapprenewal.restclient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static android.bfop.kftc.com.useorgsampleapprenewal.App.getApiBaseUrl;

/**
 * retrofit 커스터마이징을 위한 어댑터 클래스
 *
 * Created by LeeHyeonJae on 2017-06-27.
 */
public class RestClientAdapter {

    public static final int CONNECT_TIMEOUT = 10;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 20;
    private static OkHttpClient client;
    private static RetrofitInterface retrofitInterface;

    public synchronized static RetrofitInterface getInstance(){

        if(retrofitInterface != null){ return retrofitInterface; }

        // http 로깅을 위한 인터셉터
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 쿠키매니저 설정 변경
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = configureClient(new OkHttpClient().newBuilder()) // 인증서 무시 설정 적용
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(httpLoggingInterceptor)
                .build();

        // Retrofit 설정
        retrofitInterface = new Retrofit.Builder()
                .baseUrl(getApiBaseUrl())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(RetrofitInterface.class);

        return retrofitInterface;
    }

    /**
     * UnCertificated 허용
     *
     * @param builder
     * @return
     */
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder){

        final TrustManager[] certs = new TrustManager[]{ new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // keep empty
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // keep empty
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        SSLContext ctx = null;

        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true; // true로 변경함
            }
        };

        builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);

        return builder;
    }

}
