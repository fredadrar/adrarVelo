package com.velo.adrar.adrarvelo;

import com.google.maps.android.geometry.Bounds;

import java.util.ArrayList;

public class RoutePolyline {

    private String status;

    public String getStatus() {
        return this.status;
    }

    private ArrayList<Route> routes;

    public ArrayList<Route> getRoutes() {
        return this.routes;
    }
}

class Route {

    private ArrayList<Leg> legs;

    public ArrayList<Leg> getLegs() {
        return this.legs;
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

class Leg {

    private ArrayList<Step> steps;

    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    private Duration duration;

    public Duration getDuration() {
        return this.duration;
    }

    private Distance distance;

    public Distance getDistance() {
        return this.distance;
    }

    private StartLocation start_location;

    public StartLocation getStartLocation() {
        return this.start_location;
    }

    private EndLocation end_location;

    public EndLocation getEndLocation() {
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

class Polyline {

    private String points;

    public String getPoints() {
        return this.points;
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

class OverviewPolyline {

    private String points;

    public String getPoints() {
        return this.points;
    }
}

