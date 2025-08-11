package com.moodytunes.spotify;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        //getTop5Items(accessToken);
        //testAuth(accessToken);
        //final String[] recTracks = recommendTracks(accessToken, location);
        final String[] tracks = searchTracks(accessToken, location);
        final String playlistId = createEmptyPlaylist(accessToken, userId, playlistName, playlistDesc);
        addToPlaylist(accessToken, tracks, playlistId);
    }

    private void testAuth(String accessToken) {
        String url = "https://api.spotify.com/v1/recommendations/available-genre-seeds";
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build()
            ;
            
            HttpResponse<String> response = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Auth test status: " + response.statusCode());
            System.out.println("Auth test body: " + response.body());
            
        } catch (Exception e) {
            System.out.println("Auth test error: " + e);
        }
    }

    private String[] searchTracks(String accessToken, String location) {
        final String baseUrl =  "https://api.spotify.com/v1/search";

        Recommendation recommendation = new Recommendation();
        analyzeWeather.recommend(recommendation, location);

        final String encodedQuery = URLEncoder.encode(recommendation.getGenre(), StandardCharsets.UTF_8);
        
        final String urlString = baseUrl
            + "?q=" + encodedQuery
            + "&type=track"
            + "&market=US"
            + "&limit=20"
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

        String[] trackUris = new String[searched.tracks.items.length];

        for (int i = 0; i < searched.tracks.items.length; i++) {
            trackUris[i] = searched.tracks.items[i].uri;
        }

        System.out.println("Searched tracks: " + Arrays.toString(trackUris));

        return trackUris;
    }

    /*
    public String exchangeCredentialsForToken() {
        final String grantType = "client_credentials";
        final String urlString = "https://accounts.spotify.com/api/token";

        final String formData = "grant_type=" + grantType;

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
                System.out.println("(exchangeCredentialsForToken) Null POST request.");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("(exchangeCredentialsForToken) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(exchangeCredentialsForToken) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(exchangeCredentialsForToken) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(exchangeCredentialsForToken) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(exchangeCredentialsForToken) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(exchangeCredentialsForToken) Access denied: " + e);
            return null;
        }

        SpotifyData.ClientAccessData accessData = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.ClientAccessData.class);

        if (accessData == null || accessData.access_token == null) {
            System.out.println("(exchangeCredentialsForToken) Failed to return access data and/or access token.");
            return null;
        }

        return accessData.access_token;
    }
    */

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

    private String getTop5Items(String accessToken) {
        final int limit = 5;
        final String urlString = "https://api.spotify.com/v1/me/top/tracks"
            + "?limit=" + limit
        ;

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
            System.out.println("(getTop5Items) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("top5 json: " + responseJson);

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(getTop5Items) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(getTop5Items) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(getTop5Items) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(getTop5Items) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(getTop5Items) Access denied: " + e);
            return null;
        }

        SpotifyData.UserTopItems topItems = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.UserTopItems.class);

        StringBuilder trackIds = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < limit; i++) {
            if (validateTrackId(topItems.items[i].id, accessToken)) {
                if (!first) {
                    trackIds.append(",");
                }
                trackIds.append(topItems.items[i].id);
                first = false;
            }
        }

        String result = trackIds.toString();

        return result;
    }

    private String[] recommendTracks(String accessToken, String location) {
        final Recommendation recommendation = new Recommendation();
        analyzeWeather.recommend(recommendation, location);

        System.out.println("=== DEBUG INFO ===");
        System.out.println("Access token: " + (accessToken != null ? "Present" : "NULL"));
        System.out.println("Location: " + location);
        
        final String market = "US";
        final double danceability = recommendation.getDanceability();
        System.out.println("Danceability: " + danceability);
        final double energy = recommendation.getEnergy();
        System.out.println("Energy: " + energy);
        final double loudness = recommendation.getLoudness();
        System.out.println("Loudness: " + loudness);
        final double speechiness = recommendation.getSpeechiness();
        System.out.println("Speechiness: " + speechiness);
        final double tempo = recommendation.getTempo();
        System.out.println("Tempo: " + tempo);
        final double valence = recommendation.getValence();
        System.out.println("Valence: " + valence);
        final String genre = recommendation.getGenre();
        System.out.println("Genre: " + genre);
        final String urlString = "https://api.spotify.com/v1/recommendations"
            //+ "?market=" + market
            + "?seed_genres=[\"pop\"]" //+ genre
            /*+ "&target_danceability=" + danceability
            + "&target_energy=" + energy
            + "&target_loudness=" + loudness
            + "&target_speechiness=" + speechiness
            + "&target_tempo=" + tempo
            + "&target_valence=" + valence*/
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
                System.out.println("(recommendTracks) Null request.");
                return null;
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("(recommendTracks) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunesApp.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(recommendTracks) Bad Response status code: " + responseJson.statusCode());
                System.out.println("Response body: " + responseJson.body());
                System.out.println("Request URL: " + urlString);
                System.out.println("Request Headers: " + request.headers().map());
                System.out.println("Full Response: " + responseJson);
                return null;
            }
        }
        catch (InterruptedException e) {
            System.out.println("(recommendTracks) Response interrupted: " + e);
            return null;
        }
        catch (IOException e) {
            System.out.println("(recommendTracks) Failed to Send or Receive response: " + e);
            return null;
        }
        catch (IllegalArgumentException e) {
            System.out.println("(recommendTracks) Invalid Response 'request' argument: " + e);
            return null;
        }
        catch (SecurityException e) {
            System.out.println("(recommendTracks) Access denied: " + e);
            return null;
        }

        SpotifyData.RecommendedSongs recommendedSongs = MoodyTunesApp.GSON.fromJson(responseJson.body(), SpotifyData.RecommendedSongs.class);

        String[] trackUris = new String[recommendedSongs.tracks.length];
        for (int i = 0; i < recommendedSongs.tracks.length; i++) {
            trackUris[i] = recommendedSongs.tracks[i].uri;
        }

        return trackUris;
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

    private void addToPlaylist(String accessToken, String[] tracks, String playlistId) {
        // Add tracks to playlist
        final String urlString = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("uris", Arrays.asList(tracks));
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