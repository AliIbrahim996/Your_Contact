package com.example.your_contact.API;

import android.app.Activity;
import android.util.JsonReader;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static String BASE_URL;
    private String ip;
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    Activity activity;


    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        BASE_URL = "https://smarttracks.org/test/your_contact/public/";
        System.out.println("URL: " + BASE_URL);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static synchronized RetrofitClient getInstance() throws IOException {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
            System.out.println("New instance initialized");
        }

        System.out.println("Base Url: " + BASE_URL);

        return mInstance;
    }

    public API getApi() {
        return retrofit.create(API.class);
    }


}
