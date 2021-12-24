package API;

import API.GraphhopperGeocode.GHPlace;
import API.GraphhopperGeocode.GraphhopperGeocode;
import API.OpenTripMap.OTMPlace;
import API.OpenTripMap.OTMPlaceInfo;
import API.OpenTripMap.OTMRadius;
import API.OpenTripMap.OTMXid;
import API.OpenWeatherMap.Weather;
import UI.UI;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Requests {

    public static void getLocation(String request){

        try {
            GHPlace[] places = GraphhopperGeocode.getGeocode(request);
            UI.getInstance().addPlaces(places);
            for(int i = 0; i < places.length; i++){
                int finalI = i;
                CompletableFuture.runAsync(() -> {
                    try {
                        UI.getInstance().addPlaceWeather(finalI, Weather.getWeather(places[finalI].getPoint()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getRadius(GHPlace place){
        CompletableFuture<Void> fut = CompletableFuture.runAsync(() -> {
            try {
                UI.getInstance().setWeather(Weather.getWeather(place.getPoint()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<List<OTMPlace>> placesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return OTMRadius.getPlaces(place).stream().filter((p) -> p.wikidata != null).toList().subList(0, 5);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
        placesFuture.thenAcceptAsync((List<OTMPlace> list) -> UI.getInstance().setPlaces(list));

        placesFuture.thenAcceptAsync((List<OTMPlace> list) -> {
            for (int i = 0; i < list.size(); i++) {
                int finalI = i;
                CompletableFuture.runAsync(() -> {
                    try {
                        OTMPlaceInfo placeInfo = OTMXid.getInfo(list.get(finalI));
                        UI.getInstance().setPlaceDescription(finalI, placeInfo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

    }

}
