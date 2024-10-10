package com.kale.json.lexer;

import com.kale.json.token.Token;
import com.kale.json.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON词法分析器
 *
 * @author kale
 * @since 2024-10-10
 */
public class JsonLexer {

    private final String input;
    private int position = 0;

    public JsonLexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char currentChar = input.charAt(position);
            if (Character.isWhitespace(currentChar)) {
                position++;
            } else if (currentChar == '{') {
                tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                position++;
            } else if (currentChar == '}') {
                tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                position++;
            } else if (currentChar == '[') {
                tokens.add(new Token(TokenType.LEFT_BRACKET, "["));
                position++;
            } else if (currentChar == ']') {
                tokens.add(new Token(TokenType.RIGHT_BRACKET, "]"));
                position++;
            } else if (currentChar == ',') {
                tokens.add(new Token(TokenType.COMMA, ","));
                position++;
            } else if (currentChar == ':') {
                tokens.add(new Token(TokenType.COLON, ":"));
                position++;
            } else if (currentChar == '"') {
                tokens.add(new Token(TokenType.STRING, readString()));
            } else if (Character.isDigit(currentChar) || currentChar == '-') {
                tokens.add(new Token(TokenType.NUMBER, readNumber()));
            } else if (input.startsWith("true", position)) {
                tokens.add(new Token(TokenType.TRUE, "true"));
                position += 4;
            } else if (input.startsWith("false", position)) {
                tokens.add(new Token(TokenType.FALSE, "false"));
                position += 5;
            } else if (input.startsWith("null", position)) {
                tokens.add(new Token(TokenType.NULL, "null"));
                position += 4;
            } else {
                throw new IllegalArgumentException("Unexpected character " + currentChar + " found at index " + position);
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private String readString() {
        int start = ++position;
        while (position < input.length() && input.charAt(position) != '"') {
            // escape
            if (input.charAt(position) == '\\') {
                position++;
            }
            position++;
        }
        if (position >= input.length()) {
            throw new IllegalArgumentException("Unexpected terminated string literal");
        }
        String stringLiteral = input.substring(start, position);
        position++;
        return stringLiteral;
    }

    private String readNumber() {
        int start = position;
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '-')) {
            position++;
        }
        return input.substring(start, position);
    }
}
