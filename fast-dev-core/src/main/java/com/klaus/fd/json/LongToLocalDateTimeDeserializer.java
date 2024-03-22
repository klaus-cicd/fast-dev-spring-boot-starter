package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 自定义反序列化器: 毫秒级时间戳解析为UTC的LocalDateTime对象
 *
 * @author Klaus
 */
public class LongToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DateUtil.parse(jsonParser.getLongValue());
    }
}