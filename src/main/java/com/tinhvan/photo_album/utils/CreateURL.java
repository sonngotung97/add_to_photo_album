package com.tinhvan.photo_album.utils;

public class CreateURL {
    public static String create(final SECURE kind, final String IP, final int port) {
        return ((kind == SECURE.HTTP) ? "http" : "https") + "://" + IP + ":" + port;
    }

    public static String create(final SECURE kind, final String IP) {
        return ((kind == SECURE.HTTP) ? "http" : "https") + "://" + IP;
    }
}

