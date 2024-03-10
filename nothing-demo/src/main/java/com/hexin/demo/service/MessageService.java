package com.hexin.demo.service;


import com.hexin.demo.entity.Message;

public interface MessageService {

    /**
     * 发送消息
     *
     * @param message
     */
    void sendMessage(Message message);

}