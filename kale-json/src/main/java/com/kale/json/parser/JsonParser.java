package com.kale.json.parser;

import com.kale.json.Json;
import com.kale.json.JsonType;
import com.kale.json.token.Token;
import com.kale.json.token.TokenType;

import java.math.BigDecimal;
import java.util.List;

/**
 * Json解析器
 *
 * @author kale
 * @since 2024-08-20
 */
public class JsonParser {

    private final List<Token> tokens;
    private int position = 0;

    public JsonParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Json parse() {
        Json json = parseValue();
        if (tokens.get(position).getType() != TokenType.EOF) {
            throw new IllegalArgumentException("Extra data after json value at position " + position);
        }
        return json;
    }

    private Json parseValue() {
        Token token = tokens.get(position);
        switch (token.getType()) {
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            case STRING:
                return parseString();
            case NUMBER:
                return parseNumber();
            case TRUE:
            case FALSE:
                position++;
                Json booleanJson = new Json(JsonType.BOOLEAN);
                booleanJson.setValue(Boolean.valueOf(token.getLiteral()));
                return booleanJson;
            case NULL:
                position++;
                return Json.JSON_NULL;
            default:
                throw new IllegalArgumentException("Unexpected token " + token + " at position " + position);
        }
    }

    private Json parseObject() {
        Json objectJson = new Json(JsonType.OBJECT);
        position++;
        while (tokens.get(position).getType() != TokenType.RIGHT_BRACE) {
            String key = parseStringValue();
            Token token = tokens.get(position);
            if (token.getType() != TokenType.COLON) {
                throw new IllegalArgumentException("Expected token type COLON but got " + token + " at position " + position);
            }
            position++;
            Json jsonValue = parseValue();
            objectJson.put(key, jsonValue);
            if (tokens.get(position).getType() == TokenType.COMMA) {
                position++;
            } else if (tokens.get(position).getType() != TokenType.RIGHT_BRACE) {
                throw new IllegalArgumentException("Expected ',' or '}' in object at position " + position);
            }
        }
        position++;
        return objectJson;
    }

    private Json parseArray() {
        Json arrayJson = new Json(JsonType.ARRAY);
        position++;
        while (tokens.get(position).getType() != TokenType.RIGHT_BRACKET) {
            Json jsonElement = parseValue();
            arrayJson.addValue(jsonElement);
            if (tokens.get(position).getType() == TokenType.COMMA) {
                position++;
            } else if (tokens.get(position).getType() != TokenType.RIGHT_BRACKET) {
                throw new IllegalArgumentException("Expected ',' or ']' in object at position " + position);
            }
        }
        position++;
        return arrayJson;
    }

    private Json parseString() {
        String stringValue = parseStringValue();
        Json stringJson = new Json(JsonType.STRING);
        stringJson.setValue(stringValue);
        return stringJson;
    }

    private Json parseNumber() {
        String stringValue = parseStringValue();
        BigDecimal numberValue = new BigDecimal(stringValue);
        Json numberJson = new Json(JsonType.NUMBER);
        numberJson.setValue(numberValue);
        return numberJson;
    }

    private String parseStringValue() {
        Token token = tokens.get(position);
        position++;
        return token.getLiteral();
    }
}
