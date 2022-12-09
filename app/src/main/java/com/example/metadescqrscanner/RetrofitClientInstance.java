package com.example.metadescqrscanner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://116.203.61.236:4000/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
//                Request newRequest  = chain.request().newBuilder()
////                        .addHeader("Authorization", "Bearer " + token)
//                        .build();
//                return chain.proceed(newRequest);
//            }).build();

            retrofit = new retrofit2.Retrofit.Builder()
//                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}