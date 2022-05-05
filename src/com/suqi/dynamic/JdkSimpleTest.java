package com.suqi.dynamic;

import java.lang.reflect.Proxy;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 16:11
 * @desc
 */
public class JdkSimpleTest {

    public static void main(String[] args) {

        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(new HelloServiceImpl());
        Object target = jdkDynamicProxy.getTarget();
        // 1、类加载器 2、具体实现的接口 3、动态代理对象
        HelloService proxy = (HelloService) Proxy.newProxyInstance(jdkDynamicProxy.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                jdkDynamicProxy);

        proxy.hello();
    }
}
