package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMBBoxInfo {

    @JSONField(name = "lon_min")
    public double lon_min;

    @JSONField(name = "lon_max")
    public double lon_max;

    @JSONField(name = "lat_min")
    public double lat_min;

    @JSONField(name = "lat_max")
    public double lat_max;

}
