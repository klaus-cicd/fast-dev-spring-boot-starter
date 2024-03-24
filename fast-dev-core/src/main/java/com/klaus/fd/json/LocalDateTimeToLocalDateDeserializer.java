package com.klaus.fd.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.klaus.fd.constant.DateConstant;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * 时间戳转LocalDate反序列化器
 *
 * @author klaus
 * @date 2023/12/29
 */
public class LocalDateTimeToLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueAsString = jsonParser.getValueAsString();
        if (StrUtil.isBlankIfStr(valueAsString)) {
            return null;
        }
        return DateUtil.parse(valueAsString, DateConstant.DF_ISO8601).toLocalDate();
    }
}
