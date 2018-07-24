package com.velo.adrar.adrarvelo;

import android.app.Application;

import com.squareup.otto.Bus;

public class BusApplication extends Application {

    private static Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
    }

    public static Bus getBus() {
        return bus;
    }
}