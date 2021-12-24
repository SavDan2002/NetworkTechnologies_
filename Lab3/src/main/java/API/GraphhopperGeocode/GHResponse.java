package API.GraphhopperGeocode;

import com.alibaba.fastjson.annotation.JSONField;

public class GHResponse {

    @JSONField(name = "hits")
    public GHPlace[] places;

    public GHPlace[] getPlaces() {
        return places;
    }
}
