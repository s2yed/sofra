package com.sofra.sofra.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BaseUrlRestaurant = "http://ipda3-tech.com/sofra-v2/api/v2/";
        public static String BaseUrlClient = "http://ipda3-tech.com/sofra/api/v1/";
    public static Retrofit retrofit;

    public static Retrofit getRestaurant() {

        if (retrofit==null) {
            retrofit =new Retrofit.Builder().baseUrl(BaseUrlRestaurant).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    public static Retrofit getClient() {

        if (retrofit==null) {
            retrofit =new Retrofit.Builder().baseUrl(BaseUrlClient).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
