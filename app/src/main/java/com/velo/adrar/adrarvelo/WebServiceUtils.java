package com.velo.adrar.adrarvelo;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.Response;

import static com.velo.adrar.adrarvelo.MapsActivity.ON_BIKE_MODE;
import static com.velo.adrar.adrarvelo.MapsActivity.ON_FOOT_MODE;

public class WebServiceUtils {

    private static final String GET_VELO_URL = "https://api.jcdecaux.com/vls/v1/stations?contract=Toulouse&apiKey=edd6eedab27fb3e48b84d4e68fd57e648ccc0432";
    private static final String GET_ROUTE_BASE_URL = "https://maps.googleapis.com/maps/api/directions/json?";

    private static Gson gson = new Gson();

    public static ArrayList<VeloStation> getVeloStations() throws Exception {
        Response response = OkHttpUtils.sendGetOkHttpRequest(GET_VELO_URL);
        // OkHttp nous renvoie la réponse en entier (et pas juste le json).
        InputStreamReader isr = new InputStreamReader(response.body().byteStream());
        ArrayList<VeloStation> stations = gson.fromJson(isr, new TypeToken<ArrayList<VeloStation>>() {
        }.getType());

        for (VeloStation station : stations) {
            station.setName(
                    station.getName().substring(station.getName().indexOf("-") + 1).trim()
            );
            station.setLogo(R.drawable.ic_velo);
        }
        return stations;
    }

    public static String getRoutePolyline(Location location, VeloStation veloStation, Options options) throws Exception {

        String urlRoute = GET_ROUTE_BASE_URL
                + "origin=" + location.getLatitude() + "," + location.getLongitude()
                + "&destination=" + veloStation.getPosition().latitude + "," + veloStation.getPosition().longitude;
        if (options.getMode() == ON_FOOT_MODE) {
            urlRoute += "&mode=walking";
        } else if (options.getMode() == ON_BIKE_MODE) {
            urlRoute += "&mode=bicycling";
        }

        Response response = OkHttpUtils.sendGetOkHttpRequest(urlRoute);
        InputStreamReader isr = new InputStreamReader(response.body().byteStream());
        RoutePolyline routePolyline = gson.fromJson(isr, RoutePolyline.class);

        return routePolyline.getRoutes().get(0).getOverviewPolyline().getPoints();
    }
}
