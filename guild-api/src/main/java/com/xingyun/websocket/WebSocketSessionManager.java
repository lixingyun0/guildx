package com.xingyun.websocket;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionManager {

    private Map<String,Session> container = new ConcurrentHashMap<>();


    public void addSession(String token, Session session){
        container.put(token,session);
    }

    public Session getSession(String token){
        return container.get(token);
    }

    public void removeInvalidSession(String token){
        Session session = container.get(token);
        if (session == null){
            container.remove(token);
            return;
        }
        try {
            if (session.isOpen()){
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.remove(token);
    }

    public Set<Map.Entry<String, Session>> getAll(){
        return container.entrySet();
    }

}
