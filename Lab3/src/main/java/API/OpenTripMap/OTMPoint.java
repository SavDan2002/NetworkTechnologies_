package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMPoint {
    @JSONField(name = "lon")
    public double lon;

    @JSONField(name = "lat")
    public double lat;

    @Override
    public String toString() {
        return "OTMPoint{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
