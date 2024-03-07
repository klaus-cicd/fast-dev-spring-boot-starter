package com.klaus.fd.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaus.fd.exception.JsonException;
import com.klaus.fd.exception.JsonExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Jackson Json工具类
 *
 * @author Silas
 */
@Slf4j
public class JsonUtil {

    @Getter
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = BeanUtil.getBean(ObjectMapper.class);
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
            return OBJECT_MAPPER.writeValueAsString(object);
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
            return OBJECT_MAPPER.readValue(jsonStr, targetClass);
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
            return OBJECT_MAPPER.readValue(listJsonStr, List.class);
        } catch (Exception e) {
            throw new JsonException(JsonExceptionCode.PARSE_LIST_ERROR);
        }
    }
}
