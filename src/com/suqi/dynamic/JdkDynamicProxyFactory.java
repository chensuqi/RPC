package com.suqi.dynamic;

import java.lang.reflect.Proxy;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 16:05
 * @desc
 */
public class JdkDynamicProxyFactory {

    private JdkDynamicProxy jdkDynamicProxy;

    public JdkDynamicProxyFactory(JdkDynamicProxy helloServiceJdkDynamicProxy) {
        this.jdkDynamicProxy = helloServiceJdkDynamicProxy;
    }

    public Object getProxy() {
        Object target = jdkDynamicProxy.getTarget();
        return Proxy.newProxyInstance(jdkDynamicProxy.getClass().getClassLoader(), target.getClass().getInterfaces(), jdkDynamicProxy);
    }
}