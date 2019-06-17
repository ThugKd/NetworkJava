package com.thugkd.networkjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.thugkd.network.NetworkManager;
import com.thugkd.network.listener.NetChangeObserver;
import com.thugkd.network.type.NetType;
import com.thugkd.network.utils.Constants;

public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册
        NetworkManager.getInstance().setObserver(this);
    }

    @Override
    public void onConnect(NetType type) {
        Log.e(Constants.LOG_TAG, "MainActivity nettype:" + type.name());
    }

    @Override
    public void onDisConnect() {
        Log.e(Constants.LOG_TAG, "MainActivity disconnect...");
    }
}
