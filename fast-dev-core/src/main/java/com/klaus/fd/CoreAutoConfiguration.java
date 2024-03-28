package com.klaus.fd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klaus.fd.json.*;
import com.klaus.fd.utils.BeanUtil;
import com.klaus.fd.utils.JsonUtil;
import com.klaus.fd.utils.MessageUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Klaus
 */
public class CoreAutoConfiguration {


    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // 时间相关字段序列化, 全部序列号为LocalDateTime
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateToLocalDateTimeSerializer());
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeToISO8601Serializer());
        javaTimeModule.addSerializer(Timestamp.class, new TimestampToLocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateTimeToLocalDateDeserializer());
        // javaTimeModule.addDeserializer(LocalDateTime.class, new LongToLocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(Timestamp.class, new LocalDateTimeToTimestampDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        // objectMapper.findAndRegisterModules();

        return objectMapper;
    }

    @Bean
    public JsonUtil jsonUtil(ObjectMapper objectMapper) {
        return new JsonUtil(objectMapper);
    }

    @Bean
    public MessageUtil messageUtil(MessageSource messageSource) {
        return new MessageUtil(messageSource);
    }
}
