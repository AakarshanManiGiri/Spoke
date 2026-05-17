package parser;

import ast.*;
import lexer.Token;
import lexer.Token.Type;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Entry point
    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();

        while (!isAtEnd()) {
            if (match(Type.NEWLINE)) continue;
            statements.add(statement());
        }

        return statements;
    }

    // ---------------- Statements ----------------

    private Stmt statement() {
        if (match(Type.LET)) return letStatement();
        if (match(Type.JUMP)) return jumpStatement();
        if (match(Type.LOOP)) return loopStatement();

        error(peek(), "Expected a statement.");
        return null; // unreachable
    }

    private Stmt letStatement() {
        Token name = consume(Type.IDENTIFIER, "Expected variable name after 'let'.");
        consume(Type.BE, "Expected 'be'.");

        // Support either explicit 'number' keyword or a general expression
        Expr initializer;
        if (match(Type.NUMBER)) {
            Token value = consume(Type.INTEGER, "Expected integer literal.");
            initializer = new LiteralExpr(Integer.parseInt(value.getLexeme()));
        } else {
            initializer = parseExpression();
        }

        return new LetStmt(name.getLexeme(), initializer);
    }

    private Stmt jumpStatement() {
        Token name = consume(Type.IDENTIFIER, "Expected variable name after 'jump'.");
        consume(Type.BY, "Expected 'by'.");

        Expr delta;
        if (match(Type.NUMBER)) {
            Token value = consume(Type.INTEGER, "Expected integer literal.");
            delta = new LiteralExpr(Integer.parseInt(value.getLexeme()));
        } else {
            delta = parseExpression();
        }

        return new JumpStmt(name.getLexeme(), delta);
    }

    private Stmt loopStatement() {
        consume(Type.WHILE, "Expected 'while' after 'loop'.");
        Condition condition = parseCondition();

        List<Stmt> body = new ArrayList<>();

        while (!check(Type.END)) {
            if (match(Type.NEWLINE)) continue;
            body.add(statement());
        }

        consume(Type.END, "Expected 'end'.");
        consume(Type.LOOP, "Expected 'loop' after 'end'.");

        return new LoopStmt(condition, body);
    }

    // ---------------- Condition ----------------

    private Condition parseCondition() {
        Token name = consume(Type.IDENTIFIER, "Expected identifier.");

        Condition.Operator op;
        if (match(Type.LESS)) {
            consume(Type.THAN, "Expected 'than'.");
            op = Condition.Operator.LESS_THAN;
        } else if (match(Type.GREATER)) {
            consume(Type.THAN, "Expected 'than'.");
            op = Condition.Operator.GREATER_THAN;
        } else if (match(Type.EQUAL)) {
            consume(Type.TO, "Expected 'to'.");
            op = Condition.Operator.EQUAL_TO;
        } else {
            error(peek(), "Expected comparison operator.");
            return null;
        }

        Expr value;
        if (match(Type.NUMBER)) {
            Token tok = consume(Type.INTEGER, "Expected integer.");
            value = new LiteralExpr(Integer.parseInt(tok.getLexeme()));
        } else {
            value = parseExpression();
        }

        return new Condition(name.getLexeme(), op, value);
    }

    // ---------------- Expressions (simple precedence) ----------------

    private Expr parseExpression() {
        return parseTerm();
    }

    private Expr parseTerm() {
        Expr expr = parseFactor();

        while (match(Type.PLUS) || match(Type.MINUS)) {
            Token operator = previous();
            Expr right = parseFactor();
            BinaryExpr.Operator op = operator.getType() == Type.PLUS ? BinaryExpr.Operator.ADD : BinaryExpr.Operator.SUB;
            expr = new BinaryExpr(expr, op, right);
        }

        return expr;
    }

    private Expr parseFactor() {
        Expr expr = parsePrimary();

        while (match(Type.TIMES) || match(Type.DIVIDE)) {
            Token operator = previous();
            Expr right = parsePrimary();
            BinaryExpr.Operator op = operator.getType() == Type.TIMES ? BinaryExpr.Operator.MUL : BinaryExpr.Operator.DIV;
            expr = new BinaryExpr(expr, op, right);
        }

        return expr;
    }

    private Expr parsePrimary() {
        if (match(Type.INTEGER)) {
            Token t = previous();
            return new LiteralExpr(Integer.parseInt(t.getLexeme()));
        }

        if (match(Type.IDENTIFIER)) {
            Token t = previous();
            return new VariableExpr(t.getLexeme());
        }

        error(peek(), "Expected expression.");
        return null;
    }

    // ---------------- Helpers ----------------

    private boolean match(Type type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private Token consume(Type type, String message) {
        if (check(type)) return advance();
        error(peek(), message);
        return null;
    }

    private boolean check(Type type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == Type.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private void error(Token token, String message) {
        throw new ParseException(token, message);
    }
}
