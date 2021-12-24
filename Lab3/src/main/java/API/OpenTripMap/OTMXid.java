package API.OpenTripMap;

import API.APIRequest;
import com.alibaba.fastjson.JSON;

import java.io.IOException;


public class OTMXid {

    private static String API_KEY = "5ae2e3f221c38a28845f05b6b9c59a3cd63528fcf2ba2b0c71f93c85";

    public static OTMPlaceInfo getInfo(String xid) throws IOException {

        String response = APIRequest.request("https://api.opentripmap.com/0.1/ru/places/xid/"
        + xid + "?apikey=" + API_KEY);

        return JSON.parseObject(response, OTMPlaceInfo.class);

    }

    public static OTMPlaceInfo getInfo(OTMPlace otmPlace) throws IOException {
        return getInfo(otmPlace.xid);
    }

}
