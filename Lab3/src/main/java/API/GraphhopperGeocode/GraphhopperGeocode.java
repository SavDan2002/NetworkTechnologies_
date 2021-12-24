package API.GraphhopperGeocode;

import API.APIRequest;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Arrays;


public class GraphhopperGeocode {

    private static String API_KEY = "ee8f310a-472c-4537-b514-37a0aab7fc47";

    public static GHPlace[] getGeocode(String request) throws IOException {

        String response = APIRequest.request("https://graphhopper.com/api/1/geocode?q=" + request + "&locale=ru&debug=false&key=" + API_KEY);

        return JSON.parseObject(response, GHResponse.class).getPlaces();
    }

}
