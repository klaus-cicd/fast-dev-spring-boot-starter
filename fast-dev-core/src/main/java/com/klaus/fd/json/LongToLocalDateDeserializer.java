package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * 时间戳转LocalDate反序列化器
 *
 * @author klaus
 * @date 2023/12/29
 */
public class LongToLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DateUtil.parseToLocalDate(jsonParser.getLongValue());
    }
}
