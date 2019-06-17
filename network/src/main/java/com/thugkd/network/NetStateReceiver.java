package com.thugkd.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.thugkd.network.listener.NetChangeObserver;
import com.thugkd.network.type.NetType;
import com.thugkd.network.utils.Constants;
import com.thugkd.network.utils.NetworkUtils;

/**
 * @author thugkd
 */
public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private NetChangeObserver observer;

    public NetStateReceiver() {
        netType = NetType.NONE;
    }

    public void setObserver(NetChangeObserver observer) {
        this.observer = observer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || null == intent.getAction()) {
            Log.e(Constants.LOG_TAG, "Exception......");
            return;
        }

        // 处理广播事件
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "Network Change......");
            netType = NetworkUtils.getNetType();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.e(Constants.LOG_TAG, "connect...");
                if (null != observer) {
                    observer.onConnect(netType);
                }
            } else {
                Log.e(Constants.LOG_TAG, "disConnect...");
                if (null != observer) {
                    observer.onDisConnect();
                }
            }
        }
    }
}
