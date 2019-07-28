package com.example.test.requests;


import android.util.Base64;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit1 =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build();


    private static Retrofit retrofit = retrofitBuilder.build();

    private static RequestApi requestApi = retrofit.create(RequestApi.class);
    private static RequestApi requestApi1 = retrofit1.create(RequestApi.class);

    public static RequestApi getRequestApi(){
        return requestApi;
    }

    public static RequestApi getRequestApi1(){
        return requestApi1;
    }

   private static Retrofit retrofittask=
            new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RequestApi requestApitask=retrofittask.create(RequestApi.class);

    public static RequestApi getRequestApitask()
    {
        return requestApitask;
    }

}
