package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMWind {

    @JSONField(name = "speed")
    public double speed;

    @JSONField(name = "deg")
    public double deg;

}
