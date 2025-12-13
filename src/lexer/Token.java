package lexer;

public final class Token {

    public enum Type {
        // Keywords
        LET,
        BE,
        NUMBER,
        LOOP,
        WHILE,
        LESS,
        GREATER,
        THAN,
        EQUAL,
        TO,
        JUMP,
        BY,
        END,

        // Literals & identifiers
        IDENTIFIER,
        INTEGER,

        // Structure
        NEWLINE,
        EOF
    }

    private final Type type;
    private final String lexeme;
    private final int line;

    public Token(Type type, String lexeme, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
    }

    public Type getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return type + "('" + lexeme + "') at line " + line;
    }
}
