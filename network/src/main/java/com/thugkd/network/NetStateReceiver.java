package com.thugkd.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.thugkd.network.annotation.Network;
import com.thugkd.network.bean.MethodManager;
import com.thugkd.network.type.NetType;
import com.thugkd.network.utils.Constants;
import com.thugkd.network.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author thugkd
 */
public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        netType = NetType.NONE;
        networkList = new HashMap<>();
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
            } else {
                Log.e(Constants.LOG_TAG, "disConnect...");
            }

            //总线：全局通知
            post(netType);
        }
    }

    /**
     * 同时分发的过程
     *
     * @param netType
     */
    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        for (Object o : set) {
            List<MethodManager> list = networkList.get(o);
            if (null != list) {
                for (MethodManager methodManager : list) {
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager, o, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(methodManager, o, netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(methodManager, o, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(methodManager, o, netType);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object object, NetType netType) {
        try {
            methodManager.getMethod().invoke(object, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object register) {
        List<MethodManager> methodManagerList = networkList.get(register);
        if (null == methodManagerList) {
            methodManagerList = findAnnotationMethod(register);
            networkList.put(register, methodManagerList);
        }
    }

    /**
     * 通过反射技术从注解中找到方法，添加到集合
     *
     * @param register
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodManagerList = new ArrayList<>();

        Class<?> clazz = register.getClass();
        // 获取register中的所有方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //获取方法的注解
            Network network = method.getAnnotation(Network.class);
            if (network != null) {
                // 方法返回值的校验

                // 方法参数的校验
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == NetType.class) {
                    //过滤完成，添加集合
                    MethodManager methodManager = new MethodManager(parameterTypes[0], network.netType(), method);
                    methodManagerList.add(methodManager);
                }
            }

        }
        return methodManagerList;
    }

    public void unRegisterObserver(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }

        Log.e(Constants.LOG_TAG, register.getClass().getName() + "注销成功");
    }
}
