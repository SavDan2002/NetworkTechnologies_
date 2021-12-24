package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMRain {

    @JSONField(name = "1h")
    public double h1;

    @JSONField(name = "3h")
    public double h3;

}
