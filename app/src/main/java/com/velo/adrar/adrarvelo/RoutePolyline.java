package com.velo.adrar.adrarvelo;

import java.util.ArrayList;

public class RoutePolyline {

    private String status;

    public String getStatus() {
        return this.status;
    }

    private ArrayList<GeocodedWaypoint> geocoded_waypoints;

    public ArrayList<GeocodedWaypoint> getGeocodedWaypoints() {
        return this.geocoded_waypoints;
    }

    private ArrayList<Route> routes;

    public ArrayList<Route> getRoutes() {
        return this.routes;
    }
}

class Route {

    private String summary;

    public String getSummary() {
        return this.summary;
    }

    private ArrayList<Leg> legs;

    public ArrayList<Leg> getLegs() {
        return this.legs;
    }

    private String copyrights;

    public String getCopyrights() {
        return this.copyrights;
    }

    private OverviewPolyline overview_polyline;

    public OverviewPolyline getOverviewPolyline() {
        return this.overview_polyline;
    }

    private Bounds bounds;

    public Bounds getBounds() {
        return this.bounds;
    }
}

class GeocodedWaypoint {

    private String geocoder_status;

    public String getGeocoderStatus() {
        return this.geocoder_status;
    }

    private String place_id;

    public String getPlaceId() {
        return this.place_id;
    }

    private ArrayList<String> types;

    public ArrayList<String> getTypes() {
        return this.types;
    }
}

class StartLocation {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class EndLocation {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class Polyline {

    private String points;

    public String getPoints() {
        return this.points;
    }
}

class Duration {

    private int value;

    public int getValue() {
        return this.value;
    }

    private String text;

    public String getText() {
        return this.text;
    }
}

class Distance {

    private int value;

    public int getValue() {
        return this.value;
    }

    private String text;

    public String getText() {
        return this.text;
    }
}

class Step {

    private String travel_mode;

    public String getTravelMode() {
        return this.travel_mode;
    }

    private StartLocation start_location;

    public StartLocation getStartLocation() {
        return this.start_location;
    }

    private EndLocation end_location;

    public EndLocation getEndLocation() {
        return this.end_location;
    }

    private Polyline polyline;

    public Polyline getPolyline() {
        return this.polyline;
    }

    private Duration duration;

    public Duration getDuration() {
        return this.duration;
    }

    private String html_instructions;

    public String getHtmlInstructions() {
        return this.html_instructions;
    }

    private Distance distance;

    public Distance getDistance() {
        return this.distance;
    }
}

class Duration2 {

    private int value;

    public int getValue() {
        return this.value;
    }

    private String text;

    public String getText() {
        return this.text;
    }
}

class Distance2 {

    private int value;

    public int getValue() {
        return this.value;
    }

    private String text;

    public String getText() {
        return this.text;
    }
}

class StartLocation2 {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class EndLocation2 {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class Leg {

    private ArrayList<Step> steps;

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    private Duration2 duration;

    public Duration2 getDuration() {
        return this.duration;
    }

    private Distance2 distance;

    public Distance2 getDistance() {
        return this.distance;
    }

    private StartLocation2 start_location;

    public StartLocation2 getStartLocation() {
        return this.start_location;
    }

    private EndLocation2 end_location;

    public EndLocation2 getEndLocation() {
        return this.end_location;
    }

    private String start_address;

    public String getStartAddress() {
        return this.start_address;
    }

    private String end_address;

    public String getEndAddress() {
        return this.end_address;
    }
}

class OverviewPolyline {

    private String points;

    public String getPoints() {
        return this.points;
    }
}

class Southwest {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class Northeast {

    private double lat;

    public double getLat() {
        return this.lat;
    }

    private double lng;

    public double getLng() {
        return this.lng;
    }
}

class Bounds {

    private Southwest southwest;

    public Southwest getSouthwest() {
        return this.southwest;
    }

    private Northeast northeast;

    public Northeast getNortheast() {
        return this.northeast;
    }
}

