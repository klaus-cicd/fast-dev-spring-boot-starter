package com.klaus.fd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klaus.fd.json.*;
import com.klaus.fd.utils.BeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Silas
 */
public class CoreAutoConfiguration {


    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // 时间相关字段序列化, 全部序列化为时间戳
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateToLongSerializer());
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeToLongSerializer());
        javaTimeModule.addSerializer(Timestamp.class, new TimestampToLongSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LongToLocalDateDeserializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LongToLocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(Timestamp.class, new LongToTimestampDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        // objectMapper.findAndRegisterModules();

        return objectMapper;
    }
}
