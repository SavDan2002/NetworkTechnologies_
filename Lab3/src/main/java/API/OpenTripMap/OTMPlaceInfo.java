package API.OpenTripMap;

import com.alibaba.fastjson.annotation.JSONField;

public class OTMPlaceInfo {

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

    @JSONField(name = "rate")
    public String rate;

    @JSONField(name = "image")
    public String image;

    @JSONField(name = "preview")
    public OTMPlacePreview preview;

    @JSONField(name = "wikipedia")
    public String wikipedia;

    @JSONField(name = "wikipedia_extracts")
    public OTMWikipediaExtracts wikipediaExtracts;

    @JSONField(name = "voyage")
    public String voyage;

    @JSONField(name = "url")
    public String url;

    @JSONField(name = "otm")
    public String otm;

    @JSONField(name = "info")
    public OTMInfo info;

    @JSONField(name = "bbox")
    public OTMBBoxInfo bbox;

    @JSONField(name = "point")
    public OTMPoint point;

}
