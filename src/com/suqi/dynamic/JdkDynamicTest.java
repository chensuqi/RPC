package com.suqi.dynamic;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 16:05
 * @desc
 */
public class JdkDynamicTest {
    public static void main(String[] args) {

        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(new HelloServiceImpl());
        JdkDynamicProxyFactory proxyFactory = new JdkDynamicProxyFactory(jdkDynamicProxy);
        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.hello();
    }
}
