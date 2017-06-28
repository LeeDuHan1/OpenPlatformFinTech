package android.bfop.kftc.com.useorgsampleapprenewal.restclient;

import java.util.Map;

import retrofit2.Call;
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
    Call<Map> token(@FieldMap Map<String, String> params);

}
