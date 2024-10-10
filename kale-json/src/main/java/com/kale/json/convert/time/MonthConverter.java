package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.Month;

/**
 * Month转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class MonthConverter implements Converter<Month> {

    @Override
    public Json convertToJson(Month month) {
        Json stringJson = new Json(JsonType.STRING);
        String monthString = String.format("%02d", month.getValue());
        stringJson.setValue(monthString);
        return stringJson;
    }
}
