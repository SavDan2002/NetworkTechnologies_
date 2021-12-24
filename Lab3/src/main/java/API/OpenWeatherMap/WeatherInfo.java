package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class WeatherInfo {

    @JSONField(name = "coord")
    public OWMCoord coord;

    @JSONField(name = "weather")
    public OWMWeather[] weather;

    @JSONField(name = "base")
    public String base;

    @JSONField(name = "main")
    public OWMMain main;

    @JSONField(name = "visibility")
    public double visibility;

    @JSONField(name = "wind")
    public OWMWind wind;

    @JSONField(name = "clouds")
    public OWMClouds clouds;

    @JSONField(name = "rain")
    public OWMRain rain;

    @JSONField(name = "snow")
    public OWMSnow snow;

    @JSONField(name = "dt")
    public double dayTime;

    @JSONField(name = "sys")
    public OWMSys sys;

    @JSONField(name = "timezone")
    public int timezone;

    @JSONField(name = "id")
    public long id;

    @JSONField(name = "name")
    public String name;

    @JSONField(name = "cod")
    public int cod;

}
