package com.xingyun.component;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class GlobalJacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer numberCustomizer() {
        return (jacksonObjectMapperBuilder) -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Float.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Float.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Double.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Double.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }
}
