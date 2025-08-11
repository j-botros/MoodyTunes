package com.moodytunes.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moodytunes.spotify.Recommendation;

@Component
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
            
            case WeatherCodes.THUNDERSTORM:

            case WeatherCodes.THUNDERSTORM_DRIZZLE:

            case WeatherCodes.THUNDERSTORM_RAIN:

            case WeatherCodes.THUNDERSTORM_HEAVY:

            case WeatherCodes.THUNDERSTORM_HEAVY_DRIZZLE:

            case WeatherCodes.THUNDERSTORM_RAIN_HEAVY:

            case WeatherCodes.THUNDERSTORM_RAGGED:
                recommendation.setDanceability(0.4);
                recommendation.setEnergy(0.4);
                recommendation.setLoudness(-10.0);
                recommendation.setSpeechiness(0.4);
                recommendation.setTempo(110.0);
                recommendation.setValence(0.2);
                recommendation.setGenre(Recommendation.GENRES.get("thunder"));
                break;

            // Drizzle
            case WeatherCodes.DRIZZLE_LIGHT:

            case WeatherCodes.DRIZZLE_SHOWER_LIGHT:

            case WeatherCodes.DRIZZLE_RAIN_LIGHT:
                
            case WeatherCodes.DRIZZLE:

            case WeatherCodes.DRIZZLE_RAIN:

            case WeatherCodes.DRIZZLE_HEAVY:

            case WeatherCodes.DRIZZLE_SHOWER_HEAVY:

            case WeatherCodes.DRIZZLE_RAIN_HEAVY:

            case WeatherCodes.DRIZZLE_SHOWER_RAGGED:
                recommendation.setDanceability(0.3);
                recommendation.setEnergy(0.4);
                recommendation.setLoudness(-30.0);
                recommendation.setSpeechiness(0.3);
                recommendation.setTempo(80.0);
                recommendation.setValence(0.35);
                recommendation.setGenre(Recommendation.GENRES.get("drizzle"));
                break;

            // Rain
            case WeatherCodes.RAIN_SHOWER_LIGHT:
            
            case WeatherCodes.RAIN_LIGHT:
                
            case WeatherCodes.RAIN_SHOWER:

            case WeatherCodes.RAIN:
            
            case WeatherCodes.RAIN_SHOWER_HEAVY:

            case WeatherCodes.RAIN_HEAVY:

            case WeatherCodes.RAIN_VERY_HEAVY:

            case WeatherCodes.RAIN_SHOWER_RAGGED:

            case WeatherCodes.RAIN_EXTREME:

            case WeatherCodes.RAIN_FREEZING:
                recommendation.setDanceability(0.4);
                recommendation.setEnergy(0.4);
                recommendation.setLoudness(-25.0);
                recommendation.setSpeechiness(0.3);
                recommendation.setTempo(80.0);
                recommendation.setValence(0.20);
                recommendation.setGenre(Recommendation.GENRES.get("rain"));
                break;

            // Snow
            case WeatherCodes.SNOW_SHOWER_LIGHT:

            case WeatherCodes.SNOW_SLEET_SHOWER_LIGHT:
            
            case WeatherCodes.SNOW_RAIN_LIGHT:

            case WeatherCodes.SNOW_LIGHT:

            case WeatherCodes.SNOW_SHOWER:

            case WeatherCodes.SNOW_SLEET_SHOWER:

            case WeatherCodes.SNOW_SLEET:

            case WeatherCodes.SNOW_RAIN:

            case WeatherCodes.SNOW:

            case WeatherCodes.SNOW_SHOWER_HEAVY:

            case WeatherCodes.SNOW_HEAVY:
                recommendation.setDanceability(0.3);
                recommendation.setEnergy(0.3);
                recommendation.setLoudness(-45.0);
                recommendation.setSpeechiness(0.3);
                recommendation.setTempo(80.0);
                recommendation.setValence(0.7);
                recommendation.setGenre(Recommendation.GENRES.get("snow"));
                break;

            // Atmosphere
            case WeatherCodes.MIST:

            case WeatherCodes.SMOKE:

            case WeatherCodes.HAZE:

            case WeatherCodes.FOG:
                recommendation.setDanceability(0.4);
                recommendation.setEnergy(0.2);
                recommendation.setLoudness(-40.0);
                recommendation.setSpeechiness(0.1);
                recommendation.setTempo(60.0);
                recommendation.setValence(0.4);
                recommendation.setGenre(Recommendation.GENRES.get("foggy"));
                break;

            case WeatherCodes.DUST:

            case WeatherCodes.SAND_WHIRLS:

            case WeatherCodes.SAND:
                recommendation.setDanceability(0.3);
                recommendation.setEnergy(0.5);
                recommendation.setLoudness(-30.0);
                recommendation.setSpeechiness(0.2);
                recommendation.setTempo(100.0);
                recommendation.setValence(0.4);
                recommendation.setGenre(Recommendation.GENRES.get("sandy"));
                break;

            case WeatherCodes.VOLCANIC_ASH:
                recommendation.setDanceability(0.2);
                recommendation.setEnergy(0.8);
                recommendation.setLoudness(-30.0);
                recommendation.setSpeechiness(0.5);
                recommendation.setTempo(120.0);
                recommendation.setValence(0.3);
                recommendation.setGenre(Recommendation.GENRES.get("volcanic-ash"));
                break;

            case WeatherCodes.SQUALLS:
                recommendation.setDanceability(0.4);
                recommendation.setEnergy(0.8);
                recommendation.setLoudness(-30.0);
                recommendation.setSpeechiness(0.3);
                recommendation.setTempo(140.0);
                recommendation.setValence(0.4);
                recommendation.setGenre(Recommendation.GENRES.get("squalls"));
                break;

            case WeatherCodes.TORNADO:
                recommendation.setDanceability(0.4);
                recommendation.setEnergy(1.0);
                recommendation.setLoudness(-10.0);
                recommendation.setSpeechiness(0.6);
                recommendation.setTempo(160.0);
                recommendation.setValence(0.1);
                recommendation.setGenre(Recommendation.GENRES.get("tornado"));
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
                    recommendation.setDanceability(1.0);
                    recommendation.setEnergy(0.9);
                    recommendation.setLoudness(-60.0);
                    recommendation.setSpeechiness(0.6);
                    recommendation.setTempo(120.0);
                    recommendation.setValence(1.0);
                    recommendation.setGenre(Recommendation.GENRES.get("hot"));
                }
                else if (TEMPERATURE >= 293.15) {
                    recommendation.setDanceability(0.7);
                    recommendation.setEnergy(0.7);
                    recommendation.setLoudness(-60.0);
                    recommendation.setSpeechiness(0.6);
                    recommendation.setTempo(90.0);
                    recommendation.setValence(0.7);
                    recommendation.setGenre(Recommendation.GENRES.get("warm"));
                }
                else if (TEMPERATURE >= 285.15) {
                    recommendation.setDanceability(0.5);
                    recommendation.setEnergy(0.6);
                    recommendation.setLoudness(-60.0);
                    recommendation.setSpeechiness(0.5);
                    recommendation.setTempo(75.0);
                    recommendation.setValence(0.6);
                    recommendation.setGenre(Recommendation.GENRES.get("mild"));
                }
                else {
                    recommendation.setDanceability(045);
                    recommendation.setEnergy(0.5);
                    recommendation.setLoudness(-45.0);
                    recommendation.setSpeechiness(0.4);
                    recommendation.setTempo(60.0);
                    recommendation.setValence(0.4);
                    recommendation.setGenre(Recommendation.GENRES.get("cold"));
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