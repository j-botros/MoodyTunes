package com.moodytunes.spotify;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moodytunes.MoodyTunesApp;
import com.moodytunes.weather.AnalyzeWeather;

// FRONTEND:
// 1: Receive authorization code and location from website

// -----------

// BACKEND:
// 2: Exchange auth code for access json
// 3. Get access token from access json
// 4. Get User ID
// 5. Get top 5 items from user
// 6. Get weather recommendations
// 7. Get song recommendations using top 5 items & weather recommendations
// 8. Create empty playlist
// 9. Add recommendations to playlist

@Service
public class SpotifyService {
    @Autowired
    private AnalyzeWeather analyzeWeather;
    
    @Value("${SPOTIFY_CLIENT_ID}")
    private String clientId;
    
    @Value("${SPOTIFY_CLIENT_SECRET}")
    private String clientSecret;

    public void handlePlaylistRedirect(String accessToken, String location, String playlistName, String playlistDesc) {
        final String userId = getUserId(accessToken);
        final ArrayList<String> tracks = searchTracks(accessToken, location);
        final String playlistId = createEmptyPlaylist(accessToken, userId, playlistName, playlistDesc);
        addToPlaylist(accessToken, tracks, playlistId);
    }

    private ArrayList<String> searchTracks(String accessToken, String location) {
        final String baseUrl =  "https://api.spotify.com/v1/search";

        Recommendation recommendation = new Recommendation();
        analyzeWeather.recommend(recommendation, location);

        final int queryLimit = 20 / recommendation.getGenres().length;
        ArrayList<String> trackUris = new ArrayList<>(queryLimit * recommendation.getGenres().length);

        for (String genre :  recommendation.getGenres()) {

            final String encodedQuery = URLEncoder.encode(genre, StandardCharsets.UTF_8);
            
            final String urlString = baseUrl
                + "?q=" + encodedQuery
                + "&type=track"
                + "&market=US"
                + "&limit=" + queryLimit
            ;

            HttpRequest request;
            try {
                request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build()
                ;

                if (request == null) {
                    System.out.println("(searchTracks) Null request.");
                    return null;
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println("(searchTracks) Invalid URI: " + e);
                return null;
            }

            HttpResponse<String> responseJson;
            try {
                responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                if (notSuccessful(responseJson.statusCode())) {
                    System.out.println("(searchTracks) Bad Response status code: " + responseJson.statusCode());
                    System.out.println("Response body: " + responseJson.body());
                    System.out.println("Request URL: " + urlString);
                    System.out.println("Request Headers: " + request.headers().map());
                    System.out.println("Full Response: " + responseJson);
                    return null;
                }
                System.out.println("(searchTracks) Full Response: " + responseJson);
            }
            catch (InterruptedException e) {
                System.out.println("(searchTracks) Response interrupted: " + e);
                return null;
            }
            catch (IOException e) {
                System.out.println("(searchTracks) Failed to Send or Receive response: " + e);
                return null;
            }
            catch (IllegalArgumentException e) {
                System.out.println("(searchTracks) Invalid Response 'request' argument: " + e);
                return null;
            }
            catch (SecurityException e) {
                System.out.println("(searchTracks) Access denied: " + e);
                return null;
            }

            SpotifyData.SearchedTracks searched = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.SearchedTracks.class);

            for (SpotifyData.TrackObject song : searched.tracks.items) {
                trackUris.add(song.uri);
            }
        }

        System.out.println("Searched tracks: " + trackUris);

        return trackUris;
    }

    public String exchangeCodeForToken(String code) {
        final String grantType = "authorization_code";
        final String redirectUri = "https://moodytunes-xqx9.onrender.com/callback";
        
        // Create form data string instead of JSON
        final String formData = "grant_type=" + grantType +
                            "&code=" + code +
                            "&redirect_uri=" + redirectUri;
        
        final String authorization = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + authorization)
                .POST(HttpRequest.BodyPublishers.ofString(formData)) // Form data, not JSON
                .build()
            ;

            if (request == null) {
                System.out.println("(exchangeCodeForToken) Null POST request.");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("(exchangeCodeForToken) Invalid URI: " + e);
            return null;
        }
        
        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(exchangeCodeForToken) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(exchangeCodeForToken) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(exchangeCodeForToken) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(exchangeCodeForToken) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(exchangeCodeForToken) Access denied: " + e);
            return null;
        }

        SpotifyData.UserAccessData accessData = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.UserAccessData.class);

        if (accessData == null || accessData.access_token == null) {
            System.out.println("(exchangeCodeForToken) Failed to return access data and/or access token.");
            return null;
        }

        return accessData.access_token;
    }

    private String getUserId(String accessToken) {
        final String urlString = "https://api.spotify.com/v1/me";

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build()
            ;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(getUserId) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(getUserId) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(getUserId) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(getUserId) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(getUserId) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(getUserId) Access denied: " + e);
            return null;
        }

        SpotifyData.UserData userData = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.UserData.class);

        if (userData == null || userData.id == null) {
            System.out.println("(getUserId) Failed to get user ID: Empty or malformed response.");
            return null;
        }
        return userData.id;
    }

    private String createEmptyPlaylist(String accessToken, String userId, String playlistName, String playlistDescription) {
        if (userId == null || userId.isBlank()) return null;
        if (playlistName == null || playlistName.isBlank()) return null;
        if (playlistDescription == null || playlistDescription.isBlank()) return null;
        
        final boolean public_ = true;
        final boolean collaborative = false;
        final String urlString = "https://api.spotify.com/v1/users/" + userId + "/playlists";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", playlistName);
        bodyMap.put("public", public_);
        bodyMap.put("collaborative", collaborative);
        bodyMap.put("description", playlistDescription);

        final String jsonBody = MoodyTunesApp.GSON.toJson(bodyMap);
        final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(jsonBody);
        
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(bodyPublisher)
                .build()
            ;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(createEmptyPlaylist) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(createEmptyPlaylist) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(createEmptyPlaylist) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(createEmptyPlaylist) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(createEmptyPlaylist) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(createEmptyPlaylist) Access denied: " + e);
            return null;
        }

        SpotifyData.PlaylistData playlist = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.PlaylistData.class);

        if (playlist == null || playlist.id == null) {
            System.out.println("(createEmptyPlaylist) Playlist creation failed: Empty or malformed response.");
            return null;
        }
        return playlist.id;
    }

    private void addToPlaylist(String accessToken, ArrayList<String> tracks, String playlistId) {
        // Add tracks to playlist
        final String urlString = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("uris", tracks);
        bodyMap.put("position", 0);

        final String jsonBody = MoodyTunesApp.GSON.toJson(bodyMap);
        final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(jsonBody);
        
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .POST(bodyPublisher)
                .build()
            ;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(addToPlaylist) Invalid URI: " + e);
            return;
        }

        try {
            HttpResponse<String> responseJson = MoodyTunesApp.CLIENT.send(request, BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(addToPlaylist) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
            }
        }
        catch (InterruptedException e) {
            System.out.println("(addToPlaylist) Response interrupted: " + e);
        }
        catch (IOException e) {
            System.out.println("(addToPlaylist) Failed to Send or Receive response: " + e);
        }
        catch (IllegalArgumentException e) {
            System.out.println("(addToPlaylist) Invalid Response 'request' argument: " + e);
        }
        catch (SecurityException e) {
            System.out.println("(addToPlaylist) Access denied: " + e);
        }
    }

    public static boolean notSuccessful(int statusCode) {
        return (statusCode != 200 && statusCode != 201);
    }

    public static boolean validateTrackId(String trackId, String accessToken) {
        final String urlString = "https://api.spotify.com/v1/tracks/";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlString + trackId))
            .header("Authorization", "Bearer " + accessToken)
            .GET()
            .build();

        try {
            HttpResponse<String> response = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            return (!notSuccessful(statusCode));
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Error checking track ID " + trackId + ": " + e.getMessage());
            return false;
        }
    }

    public class AppData {
        public String token;
        public String location;
        public String playlist_name;
        public String playlist_desc;
    }
}