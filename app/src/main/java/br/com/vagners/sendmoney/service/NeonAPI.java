package br.com.vagners.sendmoney.service;

import java.util.List;

import br.com.vagners.sendmoney.model.TransferModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vagnerss on 25/08/16.
 */

public interface NeonAPI {

    @GET("/GenerateToken")
    Call<String> generateToken(@Query("nome") String name, @Query("email") String email);

    @FormUrlEncoded
    @POST("/SendMoney")
    Call<Boolean> sendMoney(@Field("ClienteId") String clientId, @Field("token") String token, @Field("valor") Double value);

    @GET("/GetTransfers")
    Call<List<TransferModel>> getTransfers(@Query("token") String token);

}
