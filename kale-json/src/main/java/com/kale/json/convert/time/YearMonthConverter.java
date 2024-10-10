package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * YearMonth转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class YearMonthConverter implements Converter<YearMonth> {

    @Override
    public Json convertToJson(YearMonth yearMonth) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String yearMonthString = yearMonth.format(timeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(yearMonthString);
        return stringJson;
    }
}
