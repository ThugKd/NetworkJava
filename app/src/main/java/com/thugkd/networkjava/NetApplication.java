package com.thugkd.networkjava;

import android.app.Application;

import com.thugkd.network.NetworkManager;

public class NetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }
}
