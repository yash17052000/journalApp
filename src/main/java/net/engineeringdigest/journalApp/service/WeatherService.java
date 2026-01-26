package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather_api_key}")
    public String apiKey;

    @Autowired
    private AppCache appCache;


    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String weatherApi = appCache.App_Cache.get("weather_api");
        if (weatherApi == null)
            return null;
        String finalAPI = appCache.App_Cache.get("weather_api").replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);


//This is for Post
// String requestBody="{\n" +
//         "\n" +
//         "    \"username\":\"Yash\",\n" +
//         "    \"password\":\"yash\"\n" +
//         "}";
//
//        HttpEntity<String> httpEntity= new HttpEntity(requestBody);
//        restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);

        //This is for get
        ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return exchange.getBody();
    }
}
