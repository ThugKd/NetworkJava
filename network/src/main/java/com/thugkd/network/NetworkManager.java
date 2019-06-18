package com.thugkd.network;

import android.app.Application;
import android.content.IntentFilter;

import com.thugkd.network.utils.Constants;

/**
 * @author thugkd
 */
public class NetworkManager {
    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getInstance() {
        if (null == instance) {
            synchronized (NetworkManager.class) {
                if (null == instance) {
                    instance = new NetworkManager();
                }
            }
        }

        return instance;
    }

    /**
     * 注册
     * @param register
     */
    public void registerObserver(Object register) {
        receiver.registerObserver(register);
    }

    /**
     * 注销
     * @param register
     */
    public void unRegisterObserver(Object register) {
        receiver.unRegisterObserver(register);
    }

    public void init(Application application) {
        this.application = application;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);
    }

    public Application getApplication() {
        return application;
    }
}
