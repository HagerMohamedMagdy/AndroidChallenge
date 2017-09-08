package com.by2olak.android.challenge.models.RemoteDataSource;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public class ServerConfig {
    public static String BY2OLAK_SERVER_URl = "http://bey2ollak-places-task.eu-west-1.elasticbeanstalk.com/api/json/";
    public static String By2olak_Service = "places";
    public static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
}
