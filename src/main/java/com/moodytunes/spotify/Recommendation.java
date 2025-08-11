package com.moodytunes.spotify;

import java.util.Map;

public class Recommendation {
    public static final Map<String, String[]> GENRES = Map.ofEntries(
        Map.entry("thunder", new String[] {"genre:\"Metal\"", "genre:\"Nu Metal\"", "genre:\"Hard Rock\""}),
        Map.entry("drizzle", new String[] {"genre:\"Ambient\"", "genre:\"R&b\""}),
        Map.entry("rain", new String[] {"genre:\"Ambient\"", "genre:\"R&b\""}),
        Map.entry("snow", new String[] {"genre:\"Ambient\"", "genre:\"R&b\""}),
        Map.entry("foggy", new String[] {"genre:\"Soul\"", "genre:\"R&b\"", "genre:\"Psychedelic Rock\""}),
        Map.entry("sandy", new String[] {"genre:\"Soul\"", "genre:\"trance\"", "genre:\"groove\""}),
        Map.entry("volcanic-ash", new String[] {"genre:\"Grunge\"", "genre:\"Rock\"", "genre:\"Metal\""}),
        Map.entry("squalls", new String[] {"genre:\"Ambient\"", "genre:\"Rap\""}),
        Map.entry("cold", new String[] {"genre:\"R&b\"", "genre:\"Pop\""}),
        Map.entry("mild", new String[] {"genre:\"Pop\"", "genre:\"Rock\"", "genre:\"Hip Hop\"", "genre:\"Rap\""}),
        Map.entry("warm", new String[] {"genre:\"Pop\"", "genre:\"Rock\"", "genre:\"Hip Hop\"", "genre:\"Rap\"", "genre:\"Dance Pop\"", "genre:\"Dance Rock\""}),
        Map.entry("hot", new String[] {"genre:\"Pop\"", "genre:\"Dance Pop\"", "genre:\"Dance Rock\""}),
        Map.entry("tornado", new String[] {"genre:\"Hardcore\"", "genre:\"Screamo\""})
    );
    
    private String[] genre;

    @Override
    public String toString() {
        return getClass().getName() + '@' + Integer.toHexString(hashCode());
    }

    public Recommendation() {
        genre = new String[] {"genre:\"Pop\""};
    }

    public Recommendation(String[] genre) {
        this.genre = genre;
    }

    public String[] getGenre() {
        return genre;
    }
    public void setGenre(String[] genre) {
        this.genre = genre;
    }
}