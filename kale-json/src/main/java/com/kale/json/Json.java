package com.kale.json;

import com.kale.json.util.JsonUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Json {

    public static final Json JSON_NULL = new Json(JsonType.NULL);

    private final JsonType type;

    private Map<String, Json> jsonMap;

    private List<Json> jsonList;

    private Object value;

    public Json(JsonType type) {
        this.type = type;
        if (type == JsonType.OBJECT) {
            this.jsonMap = new LinkedHashMap<>();
        } else if (type == JsonType.ARRAY) {
            this.jsonList = new ArrayList<>();
        }
    }

    public JsonType type() {
        return type;
    }

    public Object value() {
        return value;
    }

    public Json get(String key) {
        if (type == JsonType.NULL) {
            return JSON_NULL;
        }
        if (type != JsonType.OBJECT) {
            throw new IllegalArgumentException(String.format("Type [%s] can not get key", type));
        }
        return jsonMap.getOrDefault(key, JSON_NULL);
    }

    public Json getByPath(String path) {
        if (type == JsonType.NULL) {
            return JSON_NULL;
        }
        String[] keys = path.split("\\.");
        Json current = this;
        for (String key : keys) {
            current = current.get(key);
        }
        return current;
    }

    public int size() {
        if (type != JsonType.ARRAY) {
            throw new IllegalArgumentException(String.format("Type [%s] can not size", type));
        }
        return jsonList.size();
    }

    public Json indexOf(int index) {
        if (type != JsonType.ARRAY) {
            throw new IllegalArgumentException(String.format("Type [%s] can not indexOf", type));
        }
        return jsonList.get(index);
    }

    public Json setValue(Object value) {
        if (type == JsonType.OBJECT || type == JsonType.ARRAY || type == JsonType.NULL) {
            throw new IllegalArgumentException(String.format("Type [%s] can not set value", type));
        }
        if (type == JsonType.STRING && value instanceof CharSequence) {
            this.value = value.toString();
        } else {
            this.value = value;
        }
        return this;
    }

    public Json put(String key, Object value) {
        if (type != JsonType.OBJECT) {
            throw new IllegalArgumentException(String.format("Type [%s] can not put key-value", type));
        }
        Json jsonValue = JsonUtil.fromObject(value);
        jsonMap.put(key, jsonValue);
        return this;
    }

    public Json add(Object element) {
        if (type != JsonType.ARRAY) {
            throw new IllegalArgumentException(String.format("Type [%s] can not add element", type));
        }
        Json jsonElement = JsonUtil.fromObject(element);
        return addValue(jsonElement);
    }

    public Json addValue(Json jsonElement) {
        jsonList.add(jsonElement);
        return this;
    }

    @Override
    public String toString() {
        switch (type) {
            case NULL:
            case BOOLEAN:
            case NUMBER:
                return String.valueOf(value);
            case STRING:
                return String.format("\"%s\"", value);
            case OBJECT:
                StringBuilder objectBuilder = new StringBuilder("{");
                int count = 0;
                for (Map.Entry<String, Json> entry : jsonMap.entrySet()) {
                    String key = entry.getKey();
                    Json value = entry.getValue();
                    objectBuilder.append(String.format("\"%s\"", key));
                    objectBuilder.append(": ");
                    objectBuilder.append(value.toString());
                    if (count++ < jsonMap.size() - 1) {
                        objectBuilder.append(", ");
                    }
                }
                objectBuilder.append('}');
                return objectBuilder.toString();
            case ARRAY:
                StringBuilder arrayBuilder = new StringBuilder("[");
                for (int index = 0; index < jsonList.size(); index++) {
                    Json element = jsonList.get(index);
                    arrayBuilder.append(element.toString());
                    if (index < jsonList.size() - 1) {
                        arrayBuilder.append(", ");
                    }
                }
                arrayBuilder.append(']');
                return arrayBuilder.toString();
        }
        return "";
    }
}
