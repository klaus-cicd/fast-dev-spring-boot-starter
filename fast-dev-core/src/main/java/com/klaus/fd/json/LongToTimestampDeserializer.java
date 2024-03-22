package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * 自定义反序列化器: 毫秒级时间戳解析为Timestamp对象
 *
 * @author Klaus
 */
public class LongToTimestampDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DateUtil.parseToTimestamp(jsonParser.getLongValue());
    }
}