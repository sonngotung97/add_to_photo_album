package com.tinhvan.photo_album;

import com.tinhvan.photo_album.utils.API;
import com.tinhvan.photo_album.utils.ImageTool;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    // main module và java
    public static void main(String[] args) {
        int DEFAULT_THREAD_POOL_SIZE = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
        // Dữ liệu đầu tiên là tên thư mục ảnh
        String url = "http://localhost:8080/v4/photos";
        Headers headers = new Headers.Builder()
                .add("accept", "application/json")
                .build();
        // Dữ liệu thứ hai là photoAlbumId

        try {
            if (args != null && args.length == 2) {
                File dir = new File(args[0]);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File child : directoryListing) {
                        executorService.execute(() -> {
                            try {
                                // Get base64 from file
                                String base64 = ImageTool.encodeFileToBase64Binary(child);
                                JSONObject jsonRequest = new JSONObject()
                                        .put("photoAlbumId", args[1])
                                        .put("name", child.getName())
                                        .put("description", "")
                                        .put("preAllocatedId", "")
                                        .put("format", "image/jpeg")
                                        .put("id", "")
                                        .put("data", base64)
                                        .put("detectionMode", "DEFAULT")
                                        .put("ignoreQualityFilter", false);

                                Response response = API.sendRequest(RequestBody.create(API.JSON, jsonRequest.toString()), url, headers, API.POST);
                                assert response.body() != null;

                                String responseString = response.body().string();
                                JSONObject jsonResponse = new JSONObject(responseString);
                                if (jsonResponse.has("message")) {
                                    System.err.println(child.getName() + " : " + jsonResponse.getString("message"));
                                }
                                else if (jsonResponse.has("name")){
                                    System.out.println(child.getName() + " : " + "OK");
                                }
                                else {
                                    System.err.println(child.getName() + " : " + "Error request");
                                }
                            }
                            catch (Exception e) {
                                System.err.println(child.getName() + " : " + "Error request");
                            }
                        });
                    }

                    executorService.shutdown();
                    try {
                        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException ignored) {

                    }
                }
            }
            else {
                System.out.println("Add parameter to statement");
            }
        }
        catch (Exception ignored) {

        }
    }
}
