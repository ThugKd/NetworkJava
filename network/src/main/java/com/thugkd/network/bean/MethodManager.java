package com.thugkd.network.bean;

import com.thugkd.network.type.NetType;

import java.lang.reflect.Method;

/**
 * 保存符合要求的网络监听注解方法的
 * @author thugkd
 */
public class MethodManager {

    /**
     * 参数的类型：NetType netType
     */
    private Class<?> type;

    /**
     * 网络的类型：(netType = NetType.AUTO)
     */
    private NetType netType;

    /**
     * 执行的方法：
     */
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
