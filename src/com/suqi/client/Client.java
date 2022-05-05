package com.suqi.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 10:12
 * @desc 客户端， 动态代理
 */
public class Client {

    //  获取代表服务端接口的动态代理对象

    /**
     *
     * @param serviceInterface 请求接口名
     * @param address ip和端口
     * @param <T>
     */
    public static <T> T getRemoteProxyObj(Class serviceInterface , InetSocketAddress address){
        /**
         * Proxy.newProxyInstance(a,b,c)
         * a ： 类加载器，需要代理哪个类
         * b ： 需要代理的对象，具备哪些方法
         * c ： 动态代理的对象
         */
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            // proxy ：newProxyInstance返回代理的对象  mothod：代理对象的哪个方法： args：参数
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                Socket socket =new Socket();
                ObjectOutputStream outputStream = null;
                ObjectInputStream input = null;
                try {
                    socket.connect(address);

                    outputStream = new ObjectOutputStream(socket.getOutputStream());

                    // 顺序自身定义 - 告诉注册中心你要执行哪些方法
                    // 接口名、方法名
                    outputStream.writeUTF(serviceInterface.getName());
                    outputStream.writeUTF(method.getName());
                    // 方法参数类型 方法参数
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);



                    // 等待服务端
                    // 接收服务的处理后的值
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                } finally {
                    try {
                        outputStream.close();
                        input.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }
}
