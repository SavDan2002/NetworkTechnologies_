package API.OpenWeatherMap;

import API.GraphhopperGeocode.GHPoint;
import com.alibaba.fastjson.JSON;

import java.io.IOException;

import API.APIRequest;

public class Weather {

    private static String API_KEY = "434819c174a7a1f0a3074c53885d8341";

    public static WeatherInfo getWeather(double lat, double lon) throws IOException {

        String response = APIRequest.request("https://api.openweathermap.org/data/2.5/weather?lat="
                                            + lat + "&lon=" + lon + "&units=metric&lang=ru&appid=" + API_KEY);

        return JSON.parseObject(response, WeatherInfo.class);
    }

    public static WeatherInfo getWeather(GHPoint point) throws IOException {
        return getWeather(point.getLat(), point.getLng());
    }
}
