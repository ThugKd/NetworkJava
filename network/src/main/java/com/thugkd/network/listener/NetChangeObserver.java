package com.thugkd.network.listener;

import com.thugkd.network.type.NetType;

public interface NetChangeObserver {
    void onConnect(NetType type);

    void onDisConnect();
}
