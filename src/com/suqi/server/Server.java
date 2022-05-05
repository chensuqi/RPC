package com.suqi.server;

import java.io.IOException;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 10:00
 * @desc 注册中心
 */
public interface Server {

    public void start() throws IOException;

    public void  stop();

    public void register(Class service, Class serviceImpl);
}
