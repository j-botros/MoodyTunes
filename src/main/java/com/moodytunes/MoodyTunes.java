package com.moodytunes;

import java.io.UncheckedIOException;
import java.net.http.HttpClient;

import com.google.gson.Gson;

public class MoodyTunes {
    public static final HttpClient CLIENT = createHttpClient();
    public static final Gson GSON = createGson();

    private static HttpClient createHttpClient() {
        HttpClient client;
        try {
            client = HttpClient.newHttpClient();
            return client;
        }
        catch (UncheckedIOException e) {
            System.out.println("(createHttpClient) Insufficient I/O resources to build new HttpClient: " + e);
            return null;
        }
    }

    private static Gson createGson() {
        Gson gson = new Gson();
        return gson;
    }
}