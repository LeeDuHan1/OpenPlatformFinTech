package android.bfop.kftc.com.useorgsampleapprenewal.restclient;

import android.bfop.kftc.com.useorgsampleapprenewal.App;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * http call을 위한 retrofit2 interface 정의
 *
 * Created by LeeHyeonJae on 2017-06-27.
 */
public interface RetrofitInterface {


    /**
     * token 획득
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Call<String> token(@FieldMap Map<String, String> params);

    /**
     * retrofit 객체 정의
     */
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(App.getApiBaseUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

}
