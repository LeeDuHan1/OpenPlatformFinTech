package and.bfop.kftc.com.useorgsampleapprenewal.restclient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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
    @FormUrlEncoded // POST에만 붙는다고 한다.
    @POST("/oauth/2.0/token")
    Call<Map> token(@FieldMap Map<String, String> params);

    /**
     * 사용자정보조회
     *
     * @param params
     * @return
     */
    @GET("/user/me")
    Call<Map> userMe(@Header("Authorization") String token, @QueryMap Map<String, String> params);

}
