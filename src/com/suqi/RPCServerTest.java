package com.suqi;

import com.suqi.server.Server;
import com.suqi.server.ServerCenter;
import com.suqi.service.RpcService;
import com.suqi.service.RpcServiceImpl;

import java.io.IOException;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 11:16
 * @desc
 */
public class RPCServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new ServerCenter(9999);
        server.register(RpcService.class, RpcServiceImpl.class);

        server.start();
    }
}
