package com.xingyun.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class IndexDataListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(IndexDataListener.class);

    private WebSocketSessionManager webSocketSessionManager;

    private TempDataContainer tempDataContainer;

    public IndexDataListener(WebSocketSessionManager webSocketSessionManager,TempDataContainer tempDataContainer){
        this.webSocketSessionManager = webSocketSessionManager;
        this.tempDataContainer = tempDataContainer;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String content = new String(message.getBody(), StandardCharsets.UTF_8);
        //new String(message.getBody())
        //System.out.println("redis接收信息："+content);

        addData(content);

        Set<Map.Entry<String, Session>> entries = webSocketSessionManager.getAll();

        for (Map.Entry<String, Session> entry : entries) {
            Session session = entry.getValue();
            try {
                session.getBasicRemote().sendText(content);
            } catch (IOException e) {
                logger.error("ws发送数据失败:{}",content,e);
            }
        }

    }

    private  void addData(String content){
        tempDataContainer.m3.add(content);
        tempDataContainer.m15.add(content);
        tempDataContainer.m30.add(content);
        tempDataContainer.m60.add(content);
    }
}
