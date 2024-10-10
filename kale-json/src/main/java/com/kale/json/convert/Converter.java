package com.kale.json.convert;

import com.kale.json.Json;

/**
 * 转换器
 *
 * @param <T> 泛型
 * @author kale
 * @since 2024-10-10
 */
public interface Converter<T> {

    Json convertToJson(T target);
}
