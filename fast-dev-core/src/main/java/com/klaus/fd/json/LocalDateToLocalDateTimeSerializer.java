package com.klaus.fd.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * LocalDate转UTC LocalDateTime
 *
 * @author Klaus
 */
public class LocalDateToLocalDateTimeSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(localDate.atStartOfDay(ZoneOffset.UTC).toLocalDateTime());
    }
}
