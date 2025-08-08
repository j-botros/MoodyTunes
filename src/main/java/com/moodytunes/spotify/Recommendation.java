package com.moodytunes.spotify;

import java.util.HashMap;

public class Recommendation {
    private HashMap<String, Double> acousticness;
    private HashMap<String, Double> danceability;
    private HashMap<String, Double> energy;
    private HashMap<String, Double> instrumentalness;
    private HashMap<String, Double> liveness;
    private HashMap<String, Double> loudness;
    private HashMap<String, Integer> mode;
    private HashMap<String, Double> speechiness;
    private HashMap<String, Double> tempo;
    private HashMap<String, Double> valence;

    @Override
    public String toString() {
        return getClass().getName() + '@' + Integer.toHexString(hashCode());
    }

    public Recommendation() {
        acousticness = range(0.5, 0.5);
        danceability = range(0.5, 0.5);
        energy = range(0.6, 0.6);
        instrumentalness = range(0.1, 0.1);
        liveness = range(0.2, 0.2);
        loudness = range(-10.0, -10.0);
        mode = range(1, 1);
        speechiness = range(0.1, 0.1);
        tempo = range(120.0, 120.0);
        valence = range(0.5, 0.5);
    }

    // Generic factory for Double-based maps
    public static HashMap<String, Double> range(double min, double max) {
        HashMap<String, Double> map = new HashMap<>();
        map.put("min", min);
        map.put("max", max);
        return map;
    }

    // Generic factory for Integer-based maps
    public static HashMap<String, Integer> range(int min, int max) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("min", min);
        map.put("max", max);
        return map;
    }

    public Recommendation(Builder builder) {
        this.acousticness = builder.acousticness;
        this.danceability = builder.danceability;
        this.energy = builder.energy;
        this.tempo = builder.tempo;
        this.valence = builder.valence;
        this.mode = builder.mode;
        this.instrumentalness = builder.instrumentalness;
        this.liveness = builder.liveness;
        this.loudness = builder.loudness;
        this.speechiness = builder.speechiness;
    }

    // Getters
    public HashMap<String, Double> getAcousticness() {
        return acousticness;
    }
    public HashMap<String, Double> getDanceability() {
        return danceability;
    }
    public HashMap<String, Double> getEnergy() {
        return energy;
    }
    public HashMap<String, Double> getInstrumentalness() {
        return instrumentalness;
    }
    public HashMap<String, Double> getLiveness() {
        return liveness;
    }
    public HashMap<String, Double> getLoudness() {
        return loudness;
    }
    public HashMap<String, Integer> getMode() {
        return mode;
    }
    public HashMap<String, Double> getSpeechiness() {
        return speechiness;
    }
    public HashMap<String, Double> getTempo() {
        return tempo;
    }
    public HashMap<String, Double> getValence() {
        return valence;
    }

    // Setters
    public void setAcousticness(HashMap<String, Double> acousticness) {
        this.acousticness = acousticness;
    }
    public void setDanceability(HashMap<String, Double> danceability) {
        this.danceability = danceability;
    }
    public void setEnergy(HashMap<String, Double> energy) {
        this.energy = energy;
    }
    public void setInstrumentalness(HashMap<String, Double> instrumentalness) {
        this.instrumentalness = instrumentalness;
    }
    public void setLiveness(HashMap<String, Double> liveness) {
        this.liveness = liveness;
    }
    public void setLoudness(HashMap<String, Double> loudness) {
        this.loudness = loudness;
    }
    public void setMode(HashMap<String, Integer> mode) {
        this.mode = mode;
    }
    public void setSpeechiness(HashMap<String, Double>speechiness) {
        this.speechiness = speechiness;
    }
    public void setTempo(HashMap<String, Double> tempo) {
        this.tempo = tempo;
    }
    public void setValence(HashMap<String, Double> valence) {
        this.valence = valence;
    }

    public class Builder {
        HashMap<String, Double> acousticness;
        HashMap<String, Double> danceability;
        HashMap<String, Double> energy;
        HashMap<String, Double> instrumentalness;
        HashMap<String, Double> liveness;
        HashMap<String, Double> loudness;
        HashMap<String, Integer> mode;
        HashMap<String, Double> speechiness;
        HashMap<String, Double> tempo;
        HashMap<String, Double> valence;

        public Recommendation build() {
            return new Recommendation(this);
        }
        
        public Builder acousticness(HashMap<String, Double> val) {
            this.acousticness = val;
            return this;
        }

        public Builder danceability(HashMap<String, Double> val) {
            this.danceability = val;
            return this;
        }

        public Builder energy(HashMap<String, Double> val) {
            this.energy = val;
            return this;
        }

        public Builder instrumentalness(HashMap<String, Double> val) {
            this.instrumentalness = val;
            return this;
        }

        public Builder liveness(HashMap<String, Double> val) {
            this.liveness = val;
            return this;
        }

        public Builder loudness(HashMap<String, Double> val) {
            this.loudness = val;
            return this;
        }

        public Builder mode(HashMap<String, Integer> val) {
            this.mode = val;
            return this;
        }

        public Builder speechiness(HashMap<String, Double> val) {
            this.speechiness = val;
            return this;
        }

        public Builder tempo(HashMap<String, Double> val) {
            this.tempo = val;
            return this;
        }

        public Builder valence(HashMap<String, Double> val) {
            this.valence = val;
            return this;
        }
    }
}