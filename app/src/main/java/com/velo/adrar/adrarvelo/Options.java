package com.velo.adrar.adrarvelo;

public class Options {

    private int nearbyNumber;
    private int mode;

    // CONSTRUCTEUR
    public Options(int nearbyNumber, int mode) {

        this.nearbyNumber = nearbyNumber;
        this.mode = mode;
    }

    //GETTERS SETTERS
    public int getNearbyNumber() {
        return nearbyNumber;
    }

    public void setNearbyNumber(int nearbyNumber) {
        this.nearbyNumber = nearbyNumber;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
