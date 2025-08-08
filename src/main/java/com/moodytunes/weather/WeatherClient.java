package com.moodytunes.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import com.moodytunes.MoodyTunesApp;

public class WeatherClient {
    private static final String API_KEY = loadAPIKey();
    
    public static WeatherData fetchWeatherData(String location) {
        // Read OWM API Key
        if (API_KEY == null) {
            return null;
        }

        // Initialize API Request URL
        String jsonResponse = getWeatherJSON(location);
        if (jsonResponse == null) {
            System.out.println("Failed to fetch weather data");
            return null;
        }

        WeatherData data = MoodyTunesApp.GSON.fromJson(jsonResponse, WeatherData.class);

        return data;
    }

    private static String loadAPIKey() {
        Properties secrets = new Properties();
        try (InputStream input = WeatherClient.class.getClassLoader().getResourceAsStream("secrets.properties")) {
            if (input == null) {
                System.out.println("(loadAPIKey) Could not find secrets.properties in resources.");
                return null;
            }
            secrets.load(input);
        }
        catch (Exception e) {
            System.out.println("(loadAPIKey) Error reading secrets.properties: " + e.getMessage());
            return null;
        }

        String apiKey = secrets.getProperty("OWM_API_KEY");
        if (apiKey == null) {
            System.out.println("(loadAPIKey) Key not found in secrets.properties.");
            return null;
        }

        return apiKey;
    }

    private static String getWeatherJSON(String location) {
        // Make GET Request
        HttpRequest request;
        try {
            String uriString = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY;

            request = HttpRequest.newBuilder()
            .uri(URI.create(uriString))
            .GET()
            .build()
            ;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(getWeatherJSON) Invalid URI: " + e);
            return null;
        }

        // Collect Response
        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (InterruptedException e) {
            System.out.println("(getWeatherJSON) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(getWeatherJSON) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(getWeatherJSON) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(getWeatherJSON) Access denied: " + e);
            return null;
        }

        return responseJson.body();
    }
}