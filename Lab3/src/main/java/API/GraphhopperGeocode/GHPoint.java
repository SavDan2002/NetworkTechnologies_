package API.GraphhopperGeocode;

import com.alibaba.fastjson.annotation.JSONField;

public class GHPoint {
    @JSONField(name = "lat")
    public double lat;

    @JSONField(name = "lng")
    public double lng;

    @Override
    public String toString() {
        return "Point{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
