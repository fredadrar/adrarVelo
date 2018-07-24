package com.velo.adrar.adrarvelo;

import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.text.WordUtils;

import java.util.Locale;

public class VeloStation {

    private String contract_name;
    private String name;
    private String address;
    private Position position;
    private String status;
    private int bike_stands;
    private int available_bike_stands;
    private int available_bikes;
    private int logo;
    private boolean visible;
    private double distance;

    public VeloStation() {
        this.distance = 0;
        this.visible = true;
    }

    public String getAddress() {
        return WordUtils.capitalizeFully(address.toLowerCase(Locale.FRANCE));
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getPosition() {
        return new LatLng(this.position.getLat(), this.position.getLng());
    }

    public int getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public int getAvailable_bikes() {
        return available_bikes;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}

class Position {

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

