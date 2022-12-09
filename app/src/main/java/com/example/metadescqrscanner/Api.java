package com.example.metadescqrscanner;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("channels/mychannel/chaincodes/broilerChickenCC/asset/history")
    Call<HistoryReqResult> getAssetHistory(
            @Query("id") String id, @Header("Authorization") String auth
    );

    @GET("channels/mychannel/chaincodes/broilerChickenCC/assets/owner/phone")
    Call<GetAllAssetsResponse> getAllAssets(
            @Query("owner") String owner, @Header("Authorization") String auth
    );

    @POST("users")
    Call<AuthResponse> authentication(@Body AuthReqBody body);

    @POST("channels/mychannel/chaincodes/broilerChickenCC/asset/owner/change/phone")
    Call<ChangeOwnerReqResponse> changeOwner(
            @Body ChangeOwnerReqBody body, @Header("Authorization") String auth
    );

}