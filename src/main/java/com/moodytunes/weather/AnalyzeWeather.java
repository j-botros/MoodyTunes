package com.moodytunes.weather;

import org.springframework.beans.factory.annotation.Autowired;

import com.moodytunes.spotify.Recommendation;

public class AnalyzeWeather {
    @Autowired
    WeatherService weatherService;
    
    public void recommend(Recommendation recommendation, String location) {
        // Get weather
        WeatherData weatherData = weatherService.fetchWeatherData(location);
        if (weatherData == null) {
            System.out.println("(recommend) Null WeatherData");
            return;
        }
        
        final int WEATHER = weatherData.weather[0].id;

        switch (WEATHER) {
            // Thunderstorm
            case WeatherCodes.THUNDERSTORM_LIGHT:

            case WeatherCodes.THUNDERSTORM_LIGHT_DRIZZLE:

            case WeatherCodes.THUNDERSTORM_RAIN_LIGHT:
                recommendation.setAcousticness(Recommendation.range(0.5, 0.8));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-15.0, -8.0));
                recommendation.setMode(Recommendation.range(0, 1));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(80.0, 110.0));
                recommendation.setValence(Recommendation.range(0.25, 0.45));
                break;
            
            case WeatherCodes.THUNDERSTORM:

            case WeatherCodes.THUNDERSTORM_DRIZZLE:

            case WeatherCodes.THUNDERSTORM_RAIN:
                recommendation.setAcousticness(Recommendation.range(0.3, 0.6));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.5));
                recommendation.setLiveness(Recommendation.range(0.3, 0.6));
                recommendation.setLoudness(Recommendation.range(-10.0, -2.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.4));
                recommendation.setTempo(Recommendation.range(90.0, 120.0));
                recommendation.setValence(Recommendation.range(0.1, 0.3));
                break;

            case WeatherCodes.THUNDERSTORM_HEAVY:

            case WeatherCodes.THUNDERSTORM_HEAVY_DRIZZLE:

            case WeatherCodes.THUNDERSTORM_RAIN_HEAVY:
                recommendation.setAcousticness(Recommendation.range(0.1, 0.3));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.7, 0.9));
                recommendation.setInstrumentalness(Recommendation.range(0.1, 0.4));
                recommendation.setLiveness(Recommendation.range(0.5, 0.9));
                recommendation.setLoudness(Recommendation.range(-6.0, 0.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.5));
                recommendation.setTempo(Recommendation.range(110.0, 140.0));
                recommendation.setValence(Recommendation.range(0.05, 0.20));
                break;

            case WeatherCodes.THUNDERSTORM_RAGGED:
                recommendation.setAcousticness(Recommendation.range(0.0, 0.2));
                recommendation.setDanceability(Recommendation.range(0.1, 0.4));
                recommendation.setEnergy(Recommendation.range(0.7, 1.0));
                recommendation.setInstrumentalness(Recommendation.range(0.0, 0.3));
                recommendation.setLiveness(Recommendation.range(0.7, 1.0));
                recommendation.setLoudness(Recommendation.range(-6.0, 0.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.3, 0.7));
                recommendation.setTempo(Recommendation.range(130.0, 160.0));
                recommendation.setValence(Recommendation.range(0.05, 0.20));
                break;

            // Drizzle
            case WeatherCodes.DRIZZLE_LIGHT:

            case WeatherCodes.DRIZZLE_SHOWER_LIGHT:

            case WeatherCodes.DRIZZLE_RAIN_LIGHT:
                recommendation.setAcousticness(Recommendation.range(0.5, 0.8));
                recommendation.setDanceability(Recommendation.range(0.4, 0.6));
                recommendation.setEnergy(Recommendation.range(0.2, 0.4));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                recommendation.setMode(Recommendation.range(0, 1));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.2));
                recommendation.setTempo(Recommendation.range(70.0, 90.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;
                
            case WeatherCodes.DRIZZLE:

            case WeatherCodes.DRIZZLE_RAIN:
                recommendation.setAcousticness(Recommendation.range(0.4, 0.7));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.5));
                recommendation.setLiveness(Recommendation.range(0.3, 0.5));
                recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(80.0, 100.0));
                recommendation.setValence(Recommendation.range(0.25, 0.45));
                break;

            case WeatherCodes.DRIZZLE_HEAVY:

            case WeatherCodes.DRIZZLE_SHOWER_HEAVY:

            case WeatherCodes.DRIZZLE_RAIN_HEAVY:
                recommendation.setAcousticness(Recommendation.range(0.2, 0.5));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.5));
                recommendation.setLiveness(Recommendation.range(0.3, 0.6));
                recommendation.setLoudness(Recommendation.range(-50.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(85.0, 110.0));
                recommendation.setValence(Recommendation.range(0.2, 0.3));
                break;

            case WeatherCodes.DRIZZLE_SHOWER_RAGGED:
                recommendation.setAcousticness(Recommendation.range(0.2, 0.4));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.5, 0.7));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.4));
                recommendation.setLiveness(Recommendation.range(0.4, 0.7));
                recommendation.setLoudness(Recommendation.range(-50.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(90.0, 120.0));
                recommendation.setValence(Recommendation.range(0.2, 0.3));
                break;

            // Rain
            case WeatherCodes.RAIN_SHOWER_LIGHT:
            
            case WeatherCodes.RAIN_LIGHT:
                recommendation.setAcousticness(Recommendation.range(0.5, 0.8));
                recommendation.setDanceability(Recommendation.range(0.4, 0.6));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-50.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 1));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(70.0, 90.0));
                recommendation.setValence(Recommendation.range(0.2, 0.3));
                break;
                
            case WeatherCodes.RAIN_SHOWER:

            case WeatherCodes.RAIN:
                recommendation.setAcousticness(Recommendation.range(0.3, 0.6));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.5));
                recommendation.setLiveness(Recommendation.range(0.3, 0.5));
                recommendation.setLoudness(Recommendation.range(-40.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(80.0, 100.0));
                recommendation.setValence(Recommendation.range(0.15, 0.3));
                break;
            
            case WeatherCodes.RAIN_SHOWER_HEAVY:

            case WeatherCodes.RAIN_HEAVY:
                recommendation.setAcousticness(Recommendation.range(0.2, 0.5));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.6, 0.8));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.4));
                recommendation.setLiveness(Recommendation.range(0.4, 0.6));
                recommendation.setLoudness(Recommendation.range(-30.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(90.0, 110.0));
                recommendation.setValence(Recommendation.range(0.1, 0.25));
                break;

            case WeatherCodes.RAIN_VERY_HEAVY:
                recommendation.setAcousticness(Recommendation.range(0.1, 0.3));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.7, 0.9));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.4));
                recommendation.setLiveness(Recommendation.range(0.5, 0.7));
                recommendation.setLoudness(Recommendation.range(-25.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.3, 0.5));
                recommendation.setTempo(Recommendation.range(95.0, 120.0));
                recommendation.setValence(Recommendation.range(0.05, 0.2));
                break;

            case WeatherCodes.RAIN_SHOWER_RAGGED:

            case WeatherCodes.RAIN_EXTREME:
                recommendation.setAcousticness(Recommendation.range(0.0, 0.2));
                recommendation.setDanceability(Recommendation.range(0.1, 0.3));
                recommendation.setEnergy(Recommendation.range(0.8, 1.0));
                recommendation.setInstrumentalness(Recommendation.range(0.0, 0.3));
                recommendation.setLiveness(Recommendation.range(0.6, 1.0));
                recommendation.setLoudness(Recommendation.range(-20.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.3, 0.6));
                recommendation.setTempo(Recommendation.range(100.0, 130.0));
                recommendation.setValence(Recommendation.range(0.05, 0.2));
                break;

            case WeatherCodes.RAIN_FREEZING:
                recommendation.setAcousticness(Recommendation.range(0.6, 0.9));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.2, 0.4));
                recommendation.setInstrumentalness(Recommendation.range(0.4, 0.7));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-40.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.2));
                recommendation.setTempo(Recommendation.range(60.0, 80.0));
                recommendation.setValence(Recommendation.range(0.1, 0.25));
                break;

            // Snow
            case WeatherCodes.SNOW_SHOWER_LIGHT:

            case WeatherCodes.SNOW_SLEET_SHOWER_LIGHT:
            
            case WeatherCodes.SNOW_RAIN_LIGHT:

            case WeatherCodes.SNOW_LIGHT:
                recommendation.setAcousticness(Recommendation.range(0.7, 1.0));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.2, 0.4));
                recommendation.setInstrumentalness(Recommendation.range(0.5, 0.8));
                recommendation.setLiveness(Recommendation.range(0.1, 0.3));
                recommendation.setLoudness(Recommendation.range(-60.0, -20.0));
                recommendation.setMode(Recommendation.range(1, 1));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(60.0, 90.0));
                recommendation.setValence(Recommendation.range(0.5, 0.7));
                break;

            case WeatherCodes.SNOW_SHOWER:

            case WeatherCodes.SNOW_SLEET_SHOWER:

            case WeatherCodes.SNOW_SLEET:

            case WeatherCodes.SNOW_RAIN:

            case WeatherCodes.SNOW:
                recommendation.setAcousticness(Recommendation.range(0.5, 0.8));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.4, 0.7));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-60.0, -15.0));
                recommendation.setMode(Recommendation.range(0, 1));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.3));
                recommendation.setTempo(Recommendation.range(70.0, 100.0));
                recommendation.setValence(Recommendation.range(0.5, 0.7));
                break;

            case WeatherCodes.SNOW_SHOWER_HEAVY:

            case WeatherCodes.SNOW_HEAVY:
                recommendation.setAcousticness(Recommendation.range(0.3, 0.6));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.3, 0.6));
                recommendation.setLoudness(Recommendation.range(-50.0, -15.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(80.0, 110.0));
                recommendation.setValence(Recommendation.range(0.4, 0.6));
                break;

            // Atmosphere
            case WeatherCodes.MIST:
                recommendation.setAcousticness(Recommendation.range(0.6, 0.9));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.2, 0.4));
                recommendation.setInstrumentalness(Recommendation.range(0.4, 0.7));
                recommendation.setLiveness(Recommendation.range(0.1, 0.3));
                recommendation.setLoudness(Recommendation.range(-60.0, -15.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.2));
                recommendation.setTempo(Recommendation.range(60.0, 85.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;

            case WeatherCodes.SMOKE:
                recommendation.setAcousticness(Recommendation.range(0.2, 0.5));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-60.0, -15.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(70.0, 95.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;

            case WeatherCodes.HAZE:
                recommendation.setAcousticness(Recommendation.range(0.4, 0.7));
                recommendation.setDanceability(Recommendation.range(0.4, 0.6));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.6));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-60.0, -15.0));
                recommendation.setMode(Recommendation.range(0, 1));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(70.0, 95.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;

            case WeatherCodes.SAND_WHIRLS:
                recommendation.setAcousticness(Recommendation.range(0.1, 0.3));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.6, 0.9));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.5));
                recommendation.setLiveness(Recommendation.range(0.4, 0.7));
                recommendation.setLoudness(Recommendation.range(-30.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(100.0, 130.0));
                recommendation.setValence(Recommendation.range(0.2, 0.4));
                break;

            case WeatherCodes.FOG:
                recommendation.setAcousticness(Recommendation.range(0.6, 0.9));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.1, 0.3));
                recommendation.setInstrumentalness(Recommendation.range(0.5, 0.9));
                recommendation.setLiveness(Recommendation.range(0.1, 0.3));
                recommendation.setLoudness(Recommendation.range(-60.0, -10.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.0, 0.2));
                recommendation.setTempo(Recommendation.range(50.0, 75.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;

            case WeatherCodes.SAND:
                recommendation.setAcousticness(Recommendation.range(0.2, 0.5));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.4, 0.6));
                recommendation.setInstrumentalness(Recommendation.range(0.4, 0.7));
                recommendation.setLiveness(Recommendation.range(0.3, 0.5));
                recommendation.setLoudness(Recommendation.range(-40.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.1, 0.3));
                recommendation.setTempo(Recommendation.range(75.0, 100.0));
                recommendation.setValence(Recommendation.range(0.3, 0.5));
                break;

            case WeatherCodes.DUST:
                recommendation.setAcousticness(Recommendation.range(0.3, 0.6));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.3, 0.5));
                recommendation.setInstrumentalness(Recommendation.range(0.4, 0.7));
                recommendation.setLiveness(Recommendation.range(0.2, 0.4));
                recommendation.setLoudness(Recommendation.range(-40.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.4));
                recommendation.setTempo(Recommendation.range(60.0, 85.0));
                recommendation.setValence(Recommendation.range(0.4, 0.6));
                break;

            case WeatherCodes.VOLCANIC_ASH:
                recommendation.setAcousticness(Recommendation.range(0.0, 0.2));
                recommendation.setDanceability(Recommendation.range(0.1, 0.3));
                recommendation.setEnergy(Recommendation.range(0.6, 1.0));
                recommendation.setInstrumentalness(Recommendation.range(0.3, 0.7));
                recommendation.setLiveness(Recommendation.range(0.5, 0.8));
                recommendation.setLoudness(Recommendation.range(-40.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.3, 0.6));
                recommendation.setTempo(Recommendation.range(100.0, 140.0));
                recommendation.setValence(Recommendation.range(0.2, 0.4));
                break;

            case WeatherCodes.SQUALLS:
                recommendation.setAcousticness(Recommendation.range(0.1, 0.3));
                recommendation.setDanceability(Recommendation.range(0.3, 0.5));
                recommendation.setEnergy(Recommendation.range(0.7, 1.0));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.5));
                recommendation.setLiveness(Recommendation.range(0.5, 0.8));
                recommendation.setLoudness(Recommendation.range(-40.0, -5.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.2, 0.5));
                recommendation.setTempo(Recommendation.range(110.0, 150.0));
                recommendation.setValence(Recommendation.range(0.3, 0.6));
                break;

            case WeatherCodes.TORNADO:
                recommendation.setAcousticness(Recommendation.range(0.0, 0.2));
                recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                recommendation.setEnergy(Recommendation.range(0.9, 1.0));
                recommendation.setInstrumentalness(Recommendation.range(0.2, 0.6));
                recommendation.setLiveness(Recommendation.range(0.7, 1.0));
                recommendation.setLoudness(Recommendation.range(-10.0, 0.0));
                recommendation.setMode(Recommendation.range(0, 0));
                recommendation.setSpeechiness(Recommendation.range(0.4, 0.6));
                recommendation.setTempo(Recommendation.range(120.0, 160.0));
                recommendation.setValence(Recommendation.range(0.0, 0.2));
                break;

            // Clear
            case WeatherCodes.CLEAR_SKY:

            // Clouds
            case WeatherCodes.CLOUDS_FEW:
            case WeatherCodes.CLOUDS_SCATTERED:
            case WeatherCodes.CLOUDS_BROKEN:
            case WeatherCodes.CLOUDS_OVERCAST:
            default:
                final double TEMPERATURE = weatherData.main.temp;
                
                if (TEMPERATURE >= 303.15) {
                    recommendation.setAcousticness(Recommendation.range(0.0, 0.2));
                    recommendation.setDanceability(Recommendation.range(0.7, 1.0));
                    recommendation.setEnergy(Recommendation.range(0.8, 1.0));
                    recommendation.setInstrumentalness(Recommendation.range(0.0, 0.4));
                    recommendation.setLiveness(Recommendation.range(0.0, 0.7));
                    recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                    recommendation.setMode(Recommendation.range(0, 1));
                    recommendation.setSpeechiness(Recommendation.range(0.2, 0.7));
                    recommendation.setTempo(Recommendation.range(90.0, 180.0));
                    recommendation.setValence(Recommendation.range(0.7, 1.0));
                }
                else if (TEMPERATURE >= 293.15) {
                    recommendation.setAcousticness(Recommendation.range(0.1, 0.4));
                    recommendation.setDanceability(Recommendation.range(0.6, 0.9));
                    recommendation.setEnergy(Recommendation.range(0.6, 0.9));
                    recommendation.setInstrumentalness(Recommendation.range(0.0, 0.4));
                    recommendation.setLiveness(Recommendation.range(0.0, 0.7));
                    recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                    recommendation.setMode(Recommendation.range(1, 1));
                    recommendation.setSpeechiness(Recommendation.range(0.2, 0.7));
                    recommendation.setTempo(Recommendation.range(80.0, 150.0));
                    recommendation.setValence(Recommendation.range(0.4, 0.7));
                }
                else if (TEMPERATURE >= 285.15) {
                    recommendation.setAcousticness(Recommendation.range(0.4, 0.8));
                    recommendation.setDanceability(Recommendation.range(0.4, 0.6));
                    recommendation.setEnergy(Recommendation.range(0.3, 0.6));
                    recommendation.setInstrumentalness(Recommendation.range(0.3, 0.7));
                    recommendation.setLiveness(Recommendation.range(0.0, 0.4));
                    recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                    recommendation.setMode(Recommendation.range(1, 1));
                    recommendation.setSpeechiness(Recommendation.range(0.0, 0.3));
                    recommendation.setTempo(Recommendation.range(70.0, 120.0));
                    recommendation.setValence(Recommendation.range(0.4, 0.6));
                    recommendation.setValence(Recommendation.range(0.4, 0.7));
                }
                else {
                    recommendation.setAcousticness(Recommendation.range(0.7, 1.0));
                    recommendation.setDanceability(Recommendation.range(0.2, 0.4));
                    recommendation.setEnergy(Recommendation.range(0.1, 0.4));
                    recommendation.setInstrumentalness(Recommendation.range(0.5, 1.0));
                    recommendation.setLiveness(Recommendation.range(0.0, 0.2));
                    recommendation.setLoudness(Recommendation.range(-60.0, 0.0));
                    recommendation.setMode(Recommendation.range(0, 0));
                    recommendation.setSpeechiness(Recommendation.range(0.0, 0.3));
                    recommendation.setTempo(Recommendation.range(60.0, 90.0));
                    recommendation.setValence(Recommendation.range(0.4, 0.7));
                }
        }
    }

    public class WeatherCodes {
        public static final int
        
        // Thunderstorm
        THUNDERSTORM_RAIN_LIGHT     = 200,
        THUNDERSTORM_RAIN           = 201,
        THUNDERSTORM_RAIN_HEAVY     = 202,
        THUNDERSTORM_LIGHT          = 210,
        THUNDERSTORM                = 211,
        THUNDERSTORM_HEAVY          = 212,
        THUNDERSTORM_RAGGED         = 221,
        THUNDERSTORM_LIGHT_DRIZZLE  = 230,
        THUNDERSTORM_DRIZZLE        = 231,
        THUNDERSTORM_HEAVY_DRIZZLE  = 232,

        // Drizzle
        DRIZZLE_LIGHT               = 300,
        DRIZZLE                     = 301,
        DRIZZLE_HEAVY               = 302,
        DRIZZLE_RAIN_LIGHT          = 310,
        DRIZZLE_RAIN                = 311,
        DRIZZLE_RAIN_HEAVY          = 312,
        DRIZZLE_SHOWER_RAGGED       = 313,
        DRIZZLE_SHOWER_HEAVY        = 314,
        DRIZZLE_SHOWER_LIGHT        = 321,

        // Rain
        RAIN_LIGHT                  = 500,
        RAIN                        = 501,
        RAIN_HEAVY                  = 502,
        RAIN_VERY_HEAVY             = 503,
        RAIN_EXTREME                = 504,
        RAIN_FREEZING               = 511,
        RAIN_SHOWER_LIGHT           = 520,
        RAIN_SHOWER                 = 521,
        RAIN_SHOWER_HEAVY           = 522,
        RAIN_SHOWER_RAGGED          = 531,

        // Snow
        SNOW_LIGHT                  = 600,
        SNOW                        = 601,
        SNOW_HEAVY                  = 602,
        SNOW_SLEET                  = 611,
        SNOW_SLEET_SHOWER_LIGHT     = 612,
        SNOW_SLEET_SHOWER           = 613,
        SNOW_RAIN_LIGHT             = 615,
        SNOW_RAIN                   = 616,
        SNOW_SHOWER_LIGHT           = 620,
        SNOW_SHOWER                 = 621,
        SNOW_SHOWER_HEAVY           = 622,

        // Atmosphere
        MIST                        = 701,
        SMOKE                       = 711,
        HAZE                        = 721,
        SAND_WHIRLS                 = 731,
        FOG                         = 741,
        SAND                        = 751,
        DUST                        = 761,
        VOLCANIC_ASH                = 762,
        SQUALLS                     = 771,
        TORNADO                     = 781,

        // Clear
        CLEAR_SKY                   = 800,

        // Clouds
        CLOUDS_FEW                  = 801,
        CLOUDS_SCATTERED            = 802,
        CLOUDS_BROKEN               = 803,
        CLOUDS_OVERCAST             = 804;
    }
}