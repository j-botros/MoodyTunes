package com.moodytunes.weather;

public class WeatherData {
    public String name;
    public Sys sys;
    public Weather[] weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;

    public static class Sys {
        public String country;
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
    }

    public static class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
    }

    public static class Wind {
        public double speed;
    }

    public static class Clouds {
        public int all;
    }
}