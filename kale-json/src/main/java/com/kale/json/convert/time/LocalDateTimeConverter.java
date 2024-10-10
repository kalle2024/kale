package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public Json convertToJson(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localDateTimeString = localDateTime.format(dateTimeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(localDateTimeString);
        return stringJson;
    }
}
