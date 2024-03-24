package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Klaus
 */
public class LocalDateTimeToISO8601Serializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateUtil.toUtcIso8601(localDateTime));
    }
}
