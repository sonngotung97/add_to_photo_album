package com.tinhvan.photo_album.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class API {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static Response sendRequest(RequestBody requestBody, String url, Headers headers, String methodName) {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .method(methodName, requestBody)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
