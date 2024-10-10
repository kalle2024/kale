package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Year转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class YearConverter implements Converter<Year> {

    @Override
    public Json convertToJson(Year year) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy");
        String yearString = year.format(timeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(yearString);
        return stringJson;
    }
}
