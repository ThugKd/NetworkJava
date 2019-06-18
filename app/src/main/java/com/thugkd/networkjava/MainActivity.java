package com.thugkd.networkjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.thugkd.network.NetworkManager;
import com.thugkd.network.annotation.Network;
import com.thugkd.network.type.NetType;
import com.thugkd.network.utils.Constants;

/**
 * @author thugkd
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkManager.getInstance().registerObserver(this);
    }

    @Network(netType = NetType.WIFI)
    public void network(NetType netType) {
        switch (netType) {
            case WIFI:
                Log.e(Constants.LOG_TAG, "MainActivity >>> " + netType.name());
                break;
            case CMNET:
            case CMWAP:
                Log.e(Constants.LOG_TAG, "MainActivity >>> " + netType.name());
                break;
            case NONE:
                Log.e(Constants.LOG_TAG, "MainActivity >>> 没有网络");
                break;
            default:
                break;
        }
    }

    @Network(netType = NetType.WIFI)
    public void abcs(NetType netType) {

    }

    private void dsds() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(this);
    }
}
