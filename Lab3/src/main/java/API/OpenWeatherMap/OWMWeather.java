package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMWeather {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "main")
    public String main;

    @JSONField(name = "description")
    public String description;

    @JSONField(name = "icon")
    public String icon;

}
