package com.moodytunes.weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moodytunes.MoodyTunesApp;

@Service
public class WeatherService {
    @Value("${OWM_API_KEY}")
    private static String OwmApiKey;

    public static WeatherData fetchWeatherData(String location) {
        // Read OWM API Key
        if (OwmApiKey == null) {
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

    private static String getWeatherJSON(String location) {
        // Make GET Request
        HttpRequest request;
        try {
            String uriString = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + OwmApiKey;

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