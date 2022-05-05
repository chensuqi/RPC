package com.suqi;

import com.suqi.client.Client;
import com.suqi.service.RpcService;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 11:22
 * @desc
 */
public class RPCClientTest {
    public static void main(String[] args) {
        RpcService remoteProxyObj = new Client().getRemoteProxyObj(RpcService.class, new InetSocketAddress("127.0.0.1", 9999));

        System.out.println(remoteProxyObj.getName("555"));
    }

}
