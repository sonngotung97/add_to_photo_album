package com.tinhvan.photo_album;

import com.google.gson.Gson;
import com.tinhvan.photo_album.utils.API;
import com.tinhvan.photo_album.utils.ImageTool;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {
    // main module và java
    public static void main(String[] args) {
        int DEFAULT_THREAD_POOL_SIZE = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
        // Dữ liệu đầu tiên là tên thư mục ảnh
        String url = "http://172.16.1.2:8080/v4/query/search";
        Headers headers = new Headers.Builder()
                .add("accept", "application/json")
                .build();
        // Dữ liệu thứ hai là photoAlbumId

        try {
            File parent = new File("/home/hacker/data");
            File[] children = parent.listFiles();
            long[] time = new long[children.length];
            if (children != null) {
                int i = 0;
                for (File child : children) {
                    int finalI = i;
                    executorService.execute(() -> {
                        try {
                            // Get base64 from file
                            String base64 = ImageTool.encodeFileToBase64Binary(child);
                            List<PhotoAlbum> ranges = new ArrayList<>();
                            ranges.add(new PhotoAlbum("d20e624b-b184-4269-9d13-5835dd52d22a"));
                            ranges.add(new PhotoAlbum("c059bb94-936f-46ff-ad66-6b38745bcfc1"));
                            ranges.add(new PhotoAlbum("a1e217c6-2e29-4614-8b02-a4086e7316e7"));
                            ranges.add(new PhotoAlbum("33e2be1f-704b-4155-94ac-48878f55ed4d"));
                            ranges.add(new PhotoAlbum("a164763f-bd03-4f84-a9ba-db6efce432d9"));
                            ranges.add(new PhotoAlbum("a12c69f6-a2e0-43ba-9ef5-10008b86fc8e"));
                            ranges.add(new PhotoAlbum("920f654b-7c8c-46b0-ac65-63459fa2de9c"));
                            ranges.add(new PhotoAlbum("312665ca-265c-47d7-9223-5ce19d985745"));
                            ranges.add(new PhotoAlbum("9bae2c15-c22e-4e5c-8029-fe4cb1cc64bb"));
                            ranges.add(new PhotoAlbum("0dd1cac6-d274-4cda-b0df-f8b1d4cb67e9"));

                            List<Target> targets = new ArrayList<>();
                            targets.add(new Target(base64));

                            String jsonRequest = new Gson().toJson(new Data(targets, ranges), Data.class);
                            long start = System.nanoTime();
                            Response response = API.sendRequest(RequestBody.create(API.JSON, jsonRequest), url, headers, API.POST);
                            long end = System.nanoTime();
                            time[finalI] = end - start;
                            assert response.body() != null;

//                            String responseString = response.body().string();
//
//                            JSONObject jsonResponse = new JSONObject(responseString);
//                            if (jsonResponse.has("message")) {
//                                System.err.println(child.getName() + " : " + jsonResponse.getString("message"));
//                            } else if (jsonResponse.has("name")) {
//                                System.out.println(child.getName() + " : " + "OK");
//                            } else {
//                                System.err.println(child.getName() + " : " + "Error request");
//                            }
                        } catch (Exception e) {
                            System.err.println(child.getName() + " : " + "Error request");
                        }
                    });
                    i++;
                }

                executorService.shutdown();
                try {
                    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException ignored) {
                }

            }

            long result = 0;
            for (int j = 0; j < time.length; j++) {
                result += time[j];
            }
            System.out.println("Total = " + result / 1.0e9);
        } catch (Exception ignored) {

        }
    }

    public static class PhotoAlbum {
        private String faceGroupId = "";
        private String photoAlbumId;
        private String videoId = "";

        public PhotoAlbum(String photoAlbumId) {
            this.photoAlbumId = photoAlbumId;
        }

        public PhotoAlbum(String faceGroupId, String photoAlbumId, String videoId) {
            this.faceGroupId = faceGroupId;
            this.photoAlbumId = photoAlbumId;
            this.videoId = videoId;
        }

        public String getFaceGroupId() {
            return faceGroupId;
        }

        public void setFaceGroupId(String faceGroupId) {
            this.faceGroupId = faceGroupId;
        }

        public String getPhotoAlbumId() {
            return photoAlbumId;
        }

        public void setPhotoAlbumId(String photoAlbumId) {
            this.photoAlbumId = photoAlbumId;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }

    public static class Target{
        private String photoFormat = "image/jpeg";
        private String photoData;
        private String faceLocator = null;
        private String detectionMode = "DEFAULT";

        public Target(String photoData) {
            this.photoData = photoData;
        }

        public String getPhotoFormat() {
            return photoFormat;
        }

        public void setPhotoFormat(String photoFormat) {
            this.photoFormat = photoFormat;
        }

        public String getPhotoData() {
            return photoData;
        }

        public void setPhotoData(String photoData) {
            this.photoData = photoData;
        }

        public String getFaceLocator() {
            return faceLocator;
        }

        public void setFaceLocator(String faceLocator) {
            this.faceLocator = faceLocator;
        }

        public String getDetectionMode() {
            return detectionMode;
        }

        public void setDetectionMode(String detectionMode) {
            this.detectionMode = detectionMode;
        }
    }

    static class Data {
        private List<Target> targets;
        private List<PhotoAlbum> ranges;

        private int threshold = 0;
        private int limit = 1;
        private boolean withFaceMeta = false;
        private String searchMode = "AUTO";

        public Data(List<Target> targets, List<PhotoAlbum> ranges) {
            this.targets = targets;
            this.ranges = ranges;
        }

        public Data(List<Target> targets, List<PhotoAlbum> ranges, int threshold, int limit, boolean withFaceMeta, String searchMode) {
            this.targets = targets;
            this.ranges = ranges;
            this.threshold = threshold;
            this.limit = limit;
            this.withFaceMeta = withFaceMeta;
            this.searchMode = searchMode;
        }

        public List<Target> getTargets() {
            return targets;
        }

        public void setTargets(List<Target> targets) {
            this.targets = targets;
        }

        public List<PhotoAlbum> getRanges() {
            return ranges;
        }

        public void setRanges(List<PhotoAlbum> ranges) {
            this.ranges = ranges;
        }

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public boolean isWithFaceMeta() {
            return withFaceMeta;
        }

        public void setWithFaceMeta(boolean withFaceMeta) {
            this.withFaceMeta = withFaceMeta;
        }

        public String getSearchMode() {
            return searchMode;
        }

        public void setSearchMode(String searchMode) {
            this.searchMode = searchMode;
        }
    }
}
