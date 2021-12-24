package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMPlace {
    @JSONField(name = "xid")
    public String xid;

    @JSONField(name = "name")
    public String name;

    @JSONField(name = "kinds")
    public String kinds;

    @JSONField(name = "osm")
    public String osm;

    @JSONField(name = "wikidata")
    public String wikidata;

    @JSONField(name = "dist")
    public double dist;

    @JSONField(name = "point")
    public OTMPoint point;

    @Override
    public String toString() {
        return "OTMPlace{" +
                "xid='" + xid + '\'' +
                ", name='" + name + '\'' +
                ", kinds='" + kinds + '\'' +
                ", osm='" + osm + '\'' +
                ", wikidata='" + wikidata + '\'' +
                ", dist=" + dist +
                ", point=" + point +
                '}';
    }
}
