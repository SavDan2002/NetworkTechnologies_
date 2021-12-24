package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMSys {

    @JSONField(name = "type")
    public int type;

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "message")
    public double message;

    @JSONField(name = "country")
    public String country;

    @JSONField(name = "sunrise")
    public long sunrise;

    @JSONField(name = "sunset")
    public long sunset;

}
