package com.kale.json.token;

/**
 * Json Token实例
 *
 * @author kale
 * @since 2024-10-10
 */
public class Token {

    private final TokenType type;
    private final String literal;

    public Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    public TokenType getType() {
        return type;
    }

    public String getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", literal='" + literal + '\'' +
                '}';
    }
}
