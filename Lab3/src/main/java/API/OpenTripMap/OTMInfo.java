package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMInfo {

    @JSONField(name = "src")
    public String src;

    @JSONField(name = "src_id")
    public int src_id;

    @JSONField(name = "descr")
    public String descr;

}
