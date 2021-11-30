package com.xingyun.websocket;

import com.xingyun.enums.IndexTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class WsConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory){
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(btcMessageListener(),new ChannelTopic("ws-btc"));
        return container;

    }

    //=========btc
    @Bean
    IndexDataListener btcMessageListener() {
        return new IndexDataListener(btcSessionManager(),btcDataContainer());
    }

    @Bean
    public WebSocketSessionManager btcSessionManager(){
        return new WebSocketSessionManager();
    }
    @Bean
    public TempDataContainer btcDataContainer(){
        return new TempDataContainer(IndexTypeEnum.BTC.getType());
    }
    //=========btc


    //=========eth
    @Bean("ethSessionManager")
    @Qualifier("ethSessionManager")
    public WebSocketSessionManager ethSessionManager(){
        return new WebSocketSessionManager();
    }
    //=========eth
}
