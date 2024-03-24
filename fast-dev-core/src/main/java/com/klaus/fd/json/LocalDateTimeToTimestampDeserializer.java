package com.klaus.fd.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klaus.fd.constant.DateConstant;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 自定义反序列化器: 将ISO8601时间字符串转为Timestamp对象
 *
 * @author Klaus
 */
public class LocalDateTimeToTimestampDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueAsString = jsonParser.getValueAsString();
        if (StrUtil.isBlankIfStr(valueAsString)) {
            return null;
        }
        LocalDateTime localDateTime = DateUtil.parse(valueAsString, DateConstant.DF_ISO8601);
        return Timestamp.from(localDateTime.toInstant(ZoneOffset.UTC));
    }
}