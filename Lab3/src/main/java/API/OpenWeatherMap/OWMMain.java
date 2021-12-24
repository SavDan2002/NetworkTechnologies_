package API.OpenWeatherMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OWMMain {

    @JSONField(name = "temp")
    public double temp;

    @JSONField(name = "feels_like")
    public double feelsLike;

    @JSONField(name = "temp_min")
    public double tempMin;

    @JSONField(name = "temp_max")
    public double tempMax;

    @JSONField(name = "pressure")
    public double pressure;

    @JSONField(name = "humidity")
    public double humidity;

}
