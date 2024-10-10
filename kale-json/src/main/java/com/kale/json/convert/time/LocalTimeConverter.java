package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class LocalTimeConverter implements Converter<LocalTime> {

    @Override
    public Json convertToJson(LocalTime localTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localTimeString = localTime.format(timeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(localTimeString);
        return stringJson;
    }
}
