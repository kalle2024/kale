package com.kale.json.util;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.convert.Converter;
import com.kale.json.convert.time.*;
import com.kale.json.lexer.JsonLexer;
import com.kale.json.parser.JsonParser;
import com.kale.json.token.Token;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.*;
import java.util.*;

/**
 * Json工具
 *
 * @author kale
 * @since 2024-10-10
 */
public class JsonUtil {

    private static final Map<Class<?>, Converter<?>> TYPE_CONVERTER = new HashMap<>();

    static {
        // time
        TYPE_CONVERTER.put(Date.class, new DateConverter());
        TYPE_CONVERTER.put(Instant.class, new InstantConverter());
        TYPE_CONVERTER.put(LocalDateTime.class, new LocalDateTimeConverter());
        TYPE_CONVERTER.put(LocalDate.class, new LocalDateConverter());
        TYPE_CONVERTER.put(LocalTime.class, new LocalTimeConverter());
        TYPE_CONVERTER.put(Year.class, new YearConverter());
        TYPE_CONVERTER.put(Month.class, new MonthConverter());
        TYPE_CONVERTER.put(YearMonth.class, new YearMonthConverter());
        TYPE_CONVERTER.put(MonthDay.class, new MonthDayConverter());
    }

    public static void registerGlobalConverter(Class<?> clazz, Converter<?> converter) {
        TYPE_CONVERTER.put(clazz, converter);
    }

    public static Json fromJsonString(String input) {
        JsonLexer jsonLexer = new JsonLexer(input);
        List<Token> tokens = jsonLexer.tokenize();
        JsonParser jsonParser = new JsonParser(tokens);
        return jsonParser.parse();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Json fromObject(Object object) {
        if (object == null) {
            return Json.JSON_NULL;
        } else if (object instanceof Boolean) {
            Json booleanJson = new Json(JsonType.BOOLEAN);
            booleanJson.setValue(object);
            return booleanJson;
        } else if (object instanceof Number) {
            Json numericJson = new Json(JsonType.NUMBER);
            numericJson.setValue(object);
            return numericJson;
        } else if (object instanceof CharSequence) {
            Json stringJson = new Json(JsonType.STRING);
            stringJson.setValue(object.toString());
            return stringJson;
        } else if (object instanceof Map) {
            Json objectJson = new Json(JsonType.OBJECT);
            Map<String, Object> map = (Map<String, Object>) object;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Json value = fromObject(entry.getValue());
                objectJson.put(key, value);
            }
            return objectJson;
        } else if (object instanceof Collection) {
            Json arrayJson = new Json(JsonType.ARRAY);
            Collection<?> collection = (Collection<?>) object;
            for (Object element : collection) {
                arrayJson.add(element);
            }
            return arrayJson;
        } else if (object instanceof Json) {
            return (Json) object;
        }
        // non-null and type-converter contains this type
        Class<?> objectClass = object.getClass();
        Converter converter;
        if ((converter = TYPE_CONVERTER.get(objectClass)) != null) {
            return converter.convertToJson(object);
        }
        // for enum
        if (objectClass.isEnum()) {
            Enum<?> enumValue = (Enum<?>) object;
            Json stringJson = new Json(JsonType.STRING);
            stringJson.setValue(enumValue.name());
            return stringJson;
        }
        // others consider as object type
        return fromReflectObject(object);
    }

    private static Json fromReflectObject(Object object) {
        // others consider as object type
        Json objectJson = new Json(JsonType.OBJECT);
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        try {
            for (Field declaredField : declaredFields) {
                if (Modifier.isStatic(declaredField.getModifiers()) && Modifier.isFinal(declaredField.getModifiers())) {
                    continue;
                }
                declaredField.setAccessible(true);
                String filedName = declaredField.getName();
                Object filedValue = declaredField.get(object);
                objectJson.put(filedName, filedValue);
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // ignore ex
        }
        return objectJson;
    }
}
