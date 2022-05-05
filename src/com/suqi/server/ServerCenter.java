package com.suqi.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 10:01
 * @desc 注册中心
 */
public class ServerCenter implements Server{

    private static int port;
    private static Boolean isRunning = false;
    // key : 接口名，value ：接口实现类
    private static Map<String,Class> serviceRegister = new HashMap<>();
    // 连接池
    private static ExecutorService  executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ServerCenter(int port){
        this.port = port;
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        isRunning = true;
        System.out.println("服务器启动完毕");
        while (isRunning){
            Socket socket = serverSocket.accept();// 等待客户端连接
            // 接收到一个连接，就交给一个线程去处理具体业务
            executor.execute(new Task(socket));
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
        System.out.println("服务器关闭");
    }

    /**
     * @param service 接口的类
     * @param serviceImpl 接口的实现类
     */
    @Override
    public void register(Class service, Class serviceImpl) {
        serviceRegister.put(service.getName(),serviceImpl);
    }

    class Task implements Runnable{
        Socket socket;
        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {

                // 接收用户请求
                input = new ObjectInputStream( socket.getInputStream());
                String serviceName = input.readUTF();  // 接口名
                String methodName = input.readUTF();   // 方法名
                Class[] parameterTypes = (Class[])input.readObject(); // 参数类型
                Object[] arguments = (Object[])input.readObject(); // 参数

                // 去注册中心serviceRegister拿具体接口,反射实现类
                Class serviceClass = serviceRegister.get(serviceName);
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // 回馈用户
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(result);
            } catch (Exception exception) {
                exception.printStackTrace();
            }  finally {
                try {
                    output.close();
                    input.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
