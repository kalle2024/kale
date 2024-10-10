package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * MonthDay转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class MonthDayConverter implements Converter<MonthDay> {

    @Override
    public Json convertToJson(MonthDay monthDay) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM-dd");
        String monthDayString = monthDay.format(timeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(monthDayString);
        return stringJson;
    }
}
