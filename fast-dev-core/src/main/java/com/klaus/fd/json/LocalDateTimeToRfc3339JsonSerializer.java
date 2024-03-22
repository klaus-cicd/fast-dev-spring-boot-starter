package com.klaus.fd.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 本地日期时间到rfc3339格式时间字符串 json序列化器
 *
 * @author Klaus
 * @date 2024/03/22
 */
public class LocalDateTimeToRfc3339JsonSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(null == value ? StrUtil.EMPTY : DateUtil.toUtcRfc3339(value));
    }
}
