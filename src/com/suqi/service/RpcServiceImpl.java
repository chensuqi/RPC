package com.suqi.service;

/**
 * @author Suqi
 * @version 1.0
 * @date 2022/5/5 11:18
 * @desc
 */
public class RpcServiceImpl implements RpcService{
    @Override
    public String getName(String name) {
        return name + " 你好呀！";
    }
}
