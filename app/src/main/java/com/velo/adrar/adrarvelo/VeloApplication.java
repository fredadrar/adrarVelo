package com.velo.adrar.adrarvelo;

import android.app.Application;

import com.squareup.otto.Bus;

public class VeloApplication extends Application {

    private static Bus bus;
    private static VeloApplication veloApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new Bus();
        veloApplication = this;
    }

    public static Bus getBus() {
        return bus;
    }

    public static VeloApplication getVeloApplication() {
        return veloApplication;
    }
}