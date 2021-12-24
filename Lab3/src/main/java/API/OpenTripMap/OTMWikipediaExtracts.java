package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMWikipediaExtracts {

    @JSONField(name = "title")
    public String title;

    @JSONField(name = "text")
    public String text;

    @JSONField(name = "html")
    public String html;

}
