package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Instant转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class InstantConverter implements Converter<Instant> {

    @Override
    public Json convertToJson(Instant instant) {
        return convertToJsonInner(instant);
    }

    public static Json convertToJsonInner(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        // default pattern yyyy-MM-dd HH:mm:ss
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String instantString = zonedDateTime.format(dateTimeFormatter);
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(instantString);
        return stringJson;
    }
}
