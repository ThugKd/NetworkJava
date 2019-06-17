package com.thugkd.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.thugkd.network.NetworkManager;
import com.thugkd.network.type.NetType;

public class NetworkUtils {

    /**
     * 判断当前网络是否可用
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return false;
        }

        NetworkInfo[] networkInfos = manager.getAllNetworkInfo();
        if (null != networkInfos) {
            for (NetworkInfo info : networkInfos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获取当前网络类型
     * @return
     */
    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return NetType.NONE;
        }

        NetworkInfo networkInfos = manager.getActiveNetworkInfo();
        if (null == networkInfos) {
            return NetType.NONE;
        }

        int type = networkInfos.getType();

        if (type == ConnectivityManager.TYPE_MOBILE) {
            if ("cmnet".equalsIgnoreCase(networkInfos.getExtraInfo())) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (type == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }

        return NetType.NONE;
    }
}
