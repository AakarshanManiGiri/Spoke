package parser;

import lexer.Token;

public final class ParseException extends RuntimeException {
    public ParseException(Token token, String message) {
        super("Parse error at line " + token.getLine() + ": " + message);
    }
}
