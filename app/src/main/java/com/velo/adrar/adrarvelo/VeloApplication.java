package com.velo.adrar.adrarvelo;

import android.app.Application;

public class VeloApplication extends Application {

    private static VeloApplication veloApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        veloApplication = this;
    }

    public static VeloApplication getVeloApplication() {
        return veloApplication;
    }
}