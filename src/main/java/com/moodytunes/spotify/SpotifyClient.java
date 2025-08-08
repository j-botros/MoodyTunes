package com.moodytunes.spotify;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moodytunes.MoodyTunes;
import com.moodytunes.weather.AnalyzeWeather;

// FRONTEND:
// 1: Receive authorization code and location from website
// 2: Exchange auth code for access json
// 3. Get access token from access json

// -----------

// BACKEND:
// 4. Get User ID
// 5. Get top 5 items from user
// 6. Get weather recommendations
// 7. Get song recommendations using top 5 items & weather recommendations
// 8. Create empty playlist
// 9. Add recommendations to playlist

@RestController
public class SpotifyClient {
    private static String accessToken;
    
    @RequestMapping("/create-playlist")
    public void buildPlaylist(@RequestBody AppData request) {
        accessToken = request.token;
        String location = request.location;
        String playlistName = request.playlist_name;
        String playlistDesc = request.playlist_desc;

        String userId = getUserId();
        String top5 = getTop5Items();
        String[] recTracks = recommendTracks(top5, location);
        String playlistId = createEmptyPlaylist(userId, playlistName, playlistDesc);
        addToPlaylist(recTracks, playlistId);
    }

    private static String getUserId() {
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
            responseJson = MoodyTunes.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(getUserId) Bad Response status code");
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

        SpotifyData.UserData userData = MoodyTunes.GSON.fromJson(responseJson.body(), SpotifyData.UserData.class);

        if (userData == null || userData.id == null) {
            System.out.println("(getUserId) Failed to get user ID: Empty or malformed response.");
            return null;
        }
        return userData.id;
    }

    private static String getTop5Items() {
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
            responseJson = MoodyTunes.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(getTop5Items) Bad Response status code");
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

        SpotifyData.UserTopItems topItems = MoodyTunes.GSON.fromJson(responseJson.body(), SpotifyData.UserTopItems.class);

        String trackIds = new String();
        for (int i = 0; i < limit; i++) {
            trackIds += topItems.items[i].id + ",";
        }
        trackIds += topItems.items[limit - 1].id;

        return trackIds;
    }

    private static String[] recommendTracks(String tracks, String location) {
        final Recommendation recommendation = new Recommendation();
        AnalyzeWeather.recommend(recommendation, location);

        final int limit = 20;
        final String market = "US";
        final double minAccousticness = recommendation.getAcousticness().get("min");
        final double maxAcousticness = recommendation.getAcousticness().get("max");
        final double minDanceability = recommendation.getDanceability().get("min");
        final double maxDanceability = recommendation.getDanceability().get("max");
        final double minEnergy = recommendation.getEnergy().get("min");
        final double maxEnergy = recommendation.getEnergy().get("max");
        final double minInstrumentalness = recommendation.getInstrumentalness().get("min");
        final double maxInstrumentalness = recommendation.getInstrumentalness().get("max");
        final double minLiveness = recommendation.getLiveness().get("min");
        final double maxLiveness = recommendation.getLiveness().get("max");
        final double minLoudness = recommendation.getLoudness().get("min");
        final double maxLoudness = recommendation.getLoudness().get("max");
        final int minMode = recommendation.getMode().get("min");
        final int maxMode = recommendation.getMode().get("max");
        final double minSpeechiness = recommendation.getSpeechiness().get("min");
        final double maxSpeechiness = recommendation.getSpeechiness().get("max");
        final double minTempo = recommendation.getTempo().get("min");
        final double maxTempo = recommendation.getTempo().get("max");
        final double minValence = recommendation.getValence().get("min");
        final double maxValence = recommendation.getValence().get("max");
        final int targetPopularity = 70;
        final String urlString = "https://api.spotify.com/v1/recommendations"
            + "?limit=" + limit
            + "&market=" + market
            + "&tracks=" + tracks
            + "&min_acousticness=" + minAccousticness
            + "&max_acousticness=" + maxAcousticness
            + "&min_danceability=" + minDanceability
            + "&max_danceability=" + maxDanceability
            + "&min_energy=" + minEnergy
            + "&max_energy=" + maxEnergy
            + "&min_instrumentalness=" + minInstrumentalness
            + "&max_instrumentalness=" + maxInstrumentalness
            + "&min_liveness=" + minLiveness
            + "&max_liveness=" + maxLiveness
            + "&min_loudness=" + minLoudness
            + "&max_loudness=" + maxLoudness
            + "&min_mode=" + minMode
            + "&max_mode=" + maxMode
            + "&min_speechiness=" + minSpeechiness
            + "&max_speechiness=" + maxSpeechiness
            + "&min_tempo=" + minTempo
            + "&max_tempo=" + maxTempo
            + "&min_valence=" + minValence
            + "&max_valence=" + maxValence
            + "&target_popularity=" + targetPopularity
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
            System.out.println("(recommendTracks) Invalid URI: " + e);
            return null;
        }

        HttpResponse<String> responseJson;
        try {
            responseJson = MoodyTunes.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(recommendTracks) Bad Response status code");
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

        SpotifyData.RecommendedSongs recommendedSongs = MoodyTunes.GSON.fromJson(responseJson.body(), SpotifyData.RecommendedSongs.class);

        String[] trackUris = new String[limit];
        for (int i = 0; i < limit; i++) {
            trackUris[i] = recommendedSongs.tracks[i].uri;
        }

        return trackUris;
    }

    private static String createEmptyPlaylist(String userId, String playlistName, String playlistDescription) {
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

        final String jsonBody = MoodyTunes.GSON.toJson(bodyMap);
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
            responseJson = MoodyTunes.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(createEmptyPlaylist) Bad Response status code");
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

        SpotifyData.PlaylistData playlist = MoodyTunes.GSON.fromJson(responseJson.body(), SpotifyData.PlaylistData.class);

        if (playlist == null || playlist.id == null) {
            System.out.println("(createEmptyPlaylist) Playlist creation failed: Empty or malformed response.");
            return null;
        }
        return playlist.id;
    }

    private static void addToPlaylist(String[] tracks, String playlistId) {
        // Add tracks to playlist
        final String urlString = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("uris", Arrays.asList(tracks));
        bodyMap.put("position", 0);

        final String jsonBody = MoodyTunes.GSON.toJson(bodyMap);
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
            HttpResponse<String> responseJson = MoodyTunes.CLIENT.send(request, BodyHandlers.ofString());

            if (notSuccessful(responseJson.statusCode())) {
                System.out.println("(addToPlaylist) Bad Response status code");
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

    private static boolean notSuccessful(int statusCode) {
        return (statusCode != 200 && statusCode != 201);
    }

    public class AppData {
        public String token;
        public String location;
        public String playlist_name;
        public String playlist_desc;
    }
}