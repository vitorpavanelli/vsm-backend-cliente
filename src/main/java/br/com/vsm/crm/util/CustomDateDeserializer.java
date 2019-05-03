package br.com.vsm.crm.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends StdDeserializer<LocalDate> {

    public static final String _DATE_TIME_PATTERN = "yyyy-MM-dd";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(_DATE_TIME_PATTERN);

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String dateTime = jsonParser.getText();
        if (dateTime != null) {
            return LocalDate.parse(dateTime, formatter);
        }

        return null;
    }

}
