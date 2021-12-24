package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMCoord {

    @JSONField(name = "lon")
    public double lon;

    @JSONField(name = "lat")
    public double lat;

    @Override
    public String toString() {
        return "OWMCoord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
