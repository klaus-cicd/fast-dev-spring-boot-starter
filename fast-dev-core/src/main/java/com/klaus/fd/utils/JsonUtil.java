package com.klaus.fd.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.fd.exception.JsonException;
import com.klaus.fd.exception.JsonExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Jackson Json工具类
 *
 * @author Klaus
 */
@Getter
@Slf4j
@RequiredArgsConstructor
public class JsonUtil {

    private final ObjectMapper objectMapperBean;

    private static ObjectMapper objectMapper;

    @PostConstruct
    void init() {
        setObjectMapperBean(objectMapperBean);
    }

    /**
     * 转换为JSON字符串
     *
     * @param object 对象
     * @return {@link String}
     */
    public static String toJsonStr(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(JsonExceptionCode.CONVERT_JSON_STR_ERROR);
        }
    }

    /**
     * 解析JSON字符串为对象
     *
     * @param jsonStr     json str
     * @param targetClass 目标Class对象
     * @return {@link T}
     */
    public static <T> T parseToObject(String jsonStr, Class<T> targetClass) {
        if (!StringUtils.hasText(jsonStr) || targetClass == null) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonStr, targetClass);
        } catch (JsonProcessingException e) {
            throw new JsonException(JsonExceptionCode.PARSE_ENTITY_ERROR);
        }
    }


    /**
     * 解析JSON字符串为指定对象列表
     *
     * @param listJsonStr 列表对象JSON字符串
     * @param targetClass 目标Class对象
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> parseToList(String listJsonStr, Class<T> targetClass) {
        if (!StringUtils.hasText(listJsonStr) || targetClass == null) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(listJsonStr, List.class);
        } catch (Exception e) {
            throw new JsonException(JsonExceptionCode.PARSE_LIST_ERROR);
        }
    }

    public static void setObjectMapperBean(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

}
