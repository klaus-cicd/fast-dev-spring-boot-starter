package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.klaus.fd.utils.DateUtil;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author Klaus
 */
public class TimestampToLongSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(DateUtil.toLong(timestamp));
    }
}
