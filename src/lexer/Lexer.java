package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static lexer.Token.Type;

public final class Lexer {

    private static final Map<String, Type> KEYWORDS = Map.ofEntries(
        Map.entry("let", Type.LET),
        Map.entry("be", Type.BE),
        Map.entry("number", Type.NUMBER),
        Map.entry("loop", Type.LOOP),
        Map.entry("while", Type.WHILE),
        Map.entry("less", Type.LESS),
        Map.entry("greater", Type.GREATER),
        Map.entry("than", Type.THAN),
        Map.entry("equal", Type.EQUAL),
        Map.entry("to", Type.TO),
        Map.entry("jump", Type.JUMP),
        Map.entry("by", Type.BY),
        Map.entry("end", Type.END)
    );

    private final List<String> words;
    private int index = 0;
    private int line = 1;

    public Lexer(String source) {
        this.words = splitIntoWords(source);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            String word = advance();

            if (word.equals("\n")) {
                tokens.add(new Token(Type.NEWLINE, "\\n", line));
                line++;
                continue;
            }

            String normalized = word.toLowerCase(Locale.ROOT);

            // Keyword
            if (KEYWORDS.containsKey(normalized)) {
                tokens.add(new Token(KEYWORDS.get(normalized), word, line));
                continue;
            }

            // Integer literal
            if (isInteger(word)) {
                tokens.add(new Token(Type.INTEGER, word, line));
                continue;
            }

            // Identifier
            if (isIdentifier(word)) {
                tokens.add(new Token(Type.IDENTIFIER, word, line));
                continue;
            }

            throw new LexerException(
                "Unexpected token '" + word + "' at line " + line
            );
        }

        tokens.add(new Token(Type.EOF, "", line));
        return tokens;
    }

    // -------------------- Helpers --------------------

    private boolean isAtEnd() {
        return index >= words.size();
    }

    private String advance() {
        return words.get(index++);
    }

    private static boolean isInteger(String word) {
        return word.matches("-?\\d+");
    }

    private static boolean isIdentifier(String word) {
        return word.matches("[A-Za-z_][A-Za-z0-9_]*");
    }

    private static List<String> splitIntoWords(String source) {
        List<String> result = new ArrayList<>();
        String[] lines = source.split("\n", -1);

        for (String line : lines) {
            if (!line.isBlank()) {
                for (String word : line.trim().split("\\s+")) {
                    result.add(word);
                }
            }
            result.add("\n"); // Preserve newlines explicitly
        }

        return result;
    }

    // -------------------- Exception --------------------

    public static final class LexerException extends RuntimeException {
        public LexerException(String message) {
            super(message);
        }
    }
}
