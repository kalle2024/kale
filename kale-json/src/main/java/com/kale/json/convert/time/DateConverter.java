package com.kale.json.convert.time;

import com.kale.json.Json;
import com.kale.json.convert.Converter;

import java.time.Instant;
import java.util.Date;

/**
 * Date转换器
 *
 * @author kale
 * @since 2024-10-10
 */
public class DateConverter implements Converter<Date> {

    @Override
    public Json convertToJson(Date date) {
        Instant instant = date.toInstant();
        return InstantConverter.convertToJsonInner(instant);
    }
}
