package API.OpenTripMap;

import API.APIRequest;
import API.GraphhopperGeocode.GHPlace;
import com.alibaba.fastjson.JSON;

import java.io.IOException;

import java.util.List;

public class OTMRadius {

    private static double RADIUS = 1000;
    private static String API_KEY = "5ae2e3f221c38a28845f05b6b9c59a3cd63528fcf2ba2b0c71f93c85";

    public static List<OTMPlace> getPlaces(double lon, double lat) throws IOException {

        String response = APIRequest.request("https://api.opentripmap.com/0.1/ru/places/radius?radius="
                + RADIUS + "&lon=" + lon + "&lat=" + lat + "&format=json&apikey=" + API_KEY);

        List<OTMPlace> places = JSON.parseArray(response.toString(), OTMPlace.class);

        places.removeIf(otmPlace -> otmPlace.name.equals(""));

        return places;
    }

    public static List<OTMPlace> getPlaces(GHPlace place) throws IOException {
        return getPlaces(place.getPoint().getLng(), place.getPoint().getLat());
    }
}
