package com.moodytunes.spotify;

import java.util.Map;

public class Recommendation {
    public static final Map<String, String> SEED_GENRES = Map.ofEntries(
        Map.entry("thunder", "heavy-metal,hard-rock"),
        Map.entry("drizzle", "rainy-day,ambient,chill,r-n-b"),
        Map.entry("rain", "rainy-day,ambient,chill,r-n-b"),
        Map.entry("snow", "ambient,chill,r-n-b"),
        Map.entry("foggy", "soul,chill,r-n-b,trip-hop"),
        Map.entry("sandy", "psych-rock,trip-hop,trance,groove"),
        Map.entry("volcanic-ash", "grunge,rock,metal"),
        Map.entry("squalls", "ambient,chill,r-n-b"),
        Map.entry("cold", "chill"),
        Map.entry("mild", "pop,rock,hip-hop"),
        Map.entry("warm", "summer,dance"),
        Map.entry("hot", "summer"),
        Map.entry("tornado", "hardcore")
    );
    
    private double danceability;
    private double energy;
    private double loudness;
    private double speechiness;
    private double tempo;
    private double valence;
    private String genre;

    @Override
    public String toString() {
        return getClass().getName() + '@' + Integer.toHexString(hashCode());
    }

    public Recommendation() {
        danceability = 0.5;
        energy = 0.6;
        loudness = -10;
        speechiness = 0.1;
        tempo = 120.0;
        valence = 0.5;
    }

    public Recommendation(Builder builder) {
        this.danceability = builder.danceability;
        this.energy = builder.energy;
        this.tempo = builder.tempo;
        this.valence = builder.valence;
        this.loudness = builder.loudness;
        this.speechiness = builder.speechiness;
        this.genre = builder.genre;
    }

    // Getters
    public double getDanceability() {
        return danceability;
    }
    public double getEnergy() {
        return energy;
    }
    public Double getLoudness() {
        return loudness;
    }
    public Double getSpeechiness() {
        return speechiness;
    }
    public Double getTempo() {
        return tempo;
    }
    public Double getValence() {
        return valence;
    }
    public String getGenre() {
        return genre;
    }

    // Setters
    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void setLoudness(Double loudness) {
        this.loudness = loudness;
    }
    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public void setValence(double valence) {
        this.valence = valence;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public class Builder {
        double danceability;
        double energy;
        double loudness;
        double speechiness;
        double tempo;
        double valence;
        String genre;

        public Recommendation build() {
            return new Recommendation(this);
        }

        public Builder danceability(double val) {
            this.danceability = val;
            return this;
        }

        public Builder energy(double val) {
            this.energy = val;
            return this;
        }

        public Builder loudness(double val) {
            this.loudness = val;
            return this;
        }

        public Builder speechiness(double val) {
            this.speechiness = val;
            return this;
        }

        public Builder tempo(double val) {
            this.tempo = val;
            return this;
        }

        public Builder valence(double val) {
            this.valence = val;
            return this;
        }

        public Builder genre(String val) {
            this.genre = val;
            return this;
        }
    }
}