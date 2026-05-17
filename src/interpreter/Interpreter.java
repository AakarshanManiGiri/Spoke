package interpreter;

import ast.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Interpreter {

    private final Map<String, Value> environment = new HashMap<>();

    public void execute(List<Stmt> program) {
        for (Stmt stmt : program) {
            executeStmt(stmt);
        }
    }

    private void executeStmt(Stmt stmt) {

        if (stmt instanceof LetStmt letStmt) {
            Value value = evaluateExpr(letStmt.getInitializer());
            environment.put(letStmt.getName(), value);
            return;
        }

        if (stmt instanceof JumpStmt jumpStmt) {
            Value current = getVariable(jumpStmt.getName());
            Value delta = evaluateExpr(jumpStmt.getDelta());
            environment.put(
                jumpStmt.getName(),
                Value.ofInt(current.asInt() + delta.asInt())
            );
            return;
        }

        if (stmt instanceof LoopStmt loopStmt) {
            while (evaluateCondition(loopStmt.getCondition())) {
                for (Stmt inner : loopStmt.getBody()) {
                    executeStmt(inner);
                }
            }
            return;
        }

        throw new RuntimeError("Unknown statement type: " + stmt.getClass().getSimpleName());
    }

    private boolean evaluateCondition(Condition condition) {
        Value left = getVariable(condition.getIdentifier());
        Value right = evaluateExpr(condition.getValue());

        switch (condition.getOperator()) {
            case LESS_THAN:
                return left.asInt() < right.asInt();
            case GREATER_THAN:
                return left.asInt() > right.asInt();
            case EQUAL_TO:
                return left.asInt() == right.asInt();
            default:
                throw new RuntimeError("Unknown operator: " + condition.getOperator());
        }

    }

    private Value evaluateExpr(Expr expr) {
        if (expr instanceof LiteralExpr lit) {
            return Value.ofInt(lit.getValue());
        }

        if (expr instanceof VariableExpr var) {
            return getVariable(var.getName());
        }

        if (expr instanceof BinaryExpr bin) {
            Value left = evaluateExpr(bin.getLeft());
            Value right = evaluateExpr(bin.getRight());
            switch (bin.getOperator()) {
                case ADD:
                    return Value.ofInt(left.asInt() + right.asInt());
                case SUB:
                    return Value.ofInt(left.asInt() - right.asInt());
                case MUL:
                    return Value.ofInt(left.asInt() * right.asInt());
                case DIV:
                    int rv = right.asInt();
                    if (rv == 0) throw new RuntimeError("Division by zero");
                    return Value.ofInt(left.asInt() / rv);
                default:
                    throw new RuntimeError("Unknown binary operator: " + bin.getOperator());
            }
        }

        throw new RuntimeError("Unsupported expression type: " + expr.getClass().getSimpleName());
    }

    private Value getVariable(String name) {
        if (!environment.containsKey(name)) {
            throw new RuntimeError("variable '" + name + "' is not defined");
        }
        return environment.get(name);
    }

    public Map<String, Value> snapshot() {
        return Map.copyOf(environment);
    }
}
