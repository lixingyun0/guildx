package com.xingyun.component;

import com.xingyun.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//@Component
//@Scope("singleton")
public class WebsocketServerStartConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger("baseLog");


    @Autowired
    private WebSocketServer webSocketServer;

    private Thread nettyThread;

    /**
     * 描述：Tomcat加载完ApplicationContext-main和netty文件后：
     * 1. 启动Netty WebSocket服务器；
     * 2. 加载用户数据；
     * 3. 加载用户交流群数据。
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        nettyThread = new Thread(webSocketServer);
        logger.info("重新开启独立线程，启动Netty WebSocket服务器...");
        nettyThread.start();
    }
}
