package com.xingyun.websocket;

import com.xingyun.util.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;

@ServerEndpoint("/wsdata")
@Component
public class WebSocketServerEndPoint {

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {



        System.out.println("连接成功");

        //获取token
        URI requestURI = session.getRequestURI();
        String query = requestURI.getQuery();
        String token = query.substring(query.indexOf("=") + 1);
        WebSocketSessionManager webSocketSessionManager = SpringContextUtil.getContext().getBean("btcSessionManager", WebSocketSessionManager.class);
        webSocketSessionManager.addSession(token,session);

        System.out.println(session.getId());

    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        URI requestURI = session.getRequestURI();
        String query = requestURI.getQuery();
        String token = query.substring(query.indexOf("=") + 1);
        WebSocketSessionManager webSocketSessionManager = SpringContextUtil.getContext().getBean("btcSessionManager", WebSocketSessionManager.class);
        webSocketSessionManager.removeInvalidSession(token);
        System.out.println("连接关闭");
    }

    /**
     * 连接发生错误
     *
     * @param session
     */
    @OnError
    public void onError(Session session,Throwable ex){

        ex.printStackTrace();
        System.out.println("连接发生错误");
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public String onMsg(String text) throws IOException {
        System.out.println("服务端收到消息："+text);
        return "servet 发送：" + text;
    }

}
