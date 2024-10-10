package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Json convertToJson(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateString = localDate.format(dateTimeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(localDateString);
        return stringJson;
    }
}
