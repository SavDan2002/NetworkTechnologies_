package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMPlacePreview {

    @JSONField(name = "source")
    public String source;

    @JSONField(name = "width")
    public int width;

    @JSONField(name = "height")
    public int height;

}
