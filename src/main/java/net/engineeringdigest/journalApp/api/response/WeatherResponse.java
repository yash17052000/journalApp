package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {


    public Current current;


   @Getter
   @Setter
    public static class Current {
        @JsonProperty("observation_time")
        public String observationTime;

        public int temperature;

        @JsonProperty("weather_code")
        public int weatherCode;

        @JsonProperty("weather_icons")
        public ArrayList<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        public ArrayList<String> weatherDescriptions;


        @JsonProperty("wind_speed")
        public int windSpeed;

        @JsonProperty("wind_degree")
        public int windDegree;

        @JsonProperty("wind_dir")
        public String windDir;

        public int pressure;
        public int precip;
        public int humidity;
        public int cloudcover;
        public int feelslike;

        @JsonProperty("uv_index")
        public int uvIndex;

        public int visibility;

        @JsonProperty("is_day")
        public String isDay;
    }

    public static class Location {
        public String name;
        public String country;
        public String region;
        public String lat;
        public String lon;

        @JsonProperty("timezone_id")
        public String timezoneId;

        public String localtime;

        @JsonProperty("localtime_epoch")
        public int localtimeEpoch;

        @JsonProperty("utc_offset")
        public String utcOffset;
    }

    public static class Request {
        public String type;
        public String query;
        public String language;
        public String unit;
    }
}
