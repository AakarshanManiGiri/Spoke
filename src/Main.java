import lexer.Lexer;
import lexer.Token;
import parser.Parser;
import interpreter.Interpreter;
import interpreter.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public final class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -cp <classes> Main <file.spoke>");
            return;
        }

        String source = Files.readString(Path.of(args[0]));
        try {
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.tokenize();

            Parser parser = new Parser(tokens);
            var program = parser.parse();

            Interpreter interp = new Interpreter();
            interp.execute(program);

            System.out.println("Program finished. Environment snapshot:");
            for (Map.Entry<String, Value> e : interp.snapshot().entrySet()) {
                System.out.println(e.getKey() + " = " + e.getValue());
            }

        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
