package com.xingyun.websocket;

import com.google.common.collect.EvictingQueue;

import java.util.Queue;

public class TempDataContainer {

    private String indexType;

    public TempDataContainer(String indexType){
        this.indexType = indexType;
    }

    public Queue<String> m3 = EvictingQueue.create(180);

    public Queue<String> m15 = EvictingQueue.create(60*15);

    public Queue<String> m30 = EvictingQueue.create(60*30);

    public Queue<String> m60 = EvictingQueue.create(60*60);

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
