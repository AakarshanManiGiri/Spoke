package interpreter;

import ast.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Interpreter {

    private final Map<String, Integer> environment = new HashMap<>();

    public void execute(List<Stmt> program) {
        for (Stmt stmt : program) {
            executeStmt(stmt);
        }
    }

    private void executeStmt(Stmt stmt) {

        if (stmt instanceof LetStmt letStmt) {
            environment.put(letStmt.getName(), letStmt.getValue());
            return;
        }

        if (stmt instanceof JumpStmt jumpStmt) {
            int current = getVariable(jumpStmt.getName());
            environment.put(
                jumpStmt.getName(),
                current + jumpStmt.getDelta()
            );
            return;
        }

        if (stmt instanceof LoopStmt loopStmt) {
            while (evaluate(loopStmt.getCondition())) {
                for (Stmt inner : loopStmt.getBody()) {
                    executeStmt(inner);
                }
            }
            return;
        }

        throw new RuntimeException(
            "Unknown statement type: " + stmt.getClass().getSimpleName()
        );
    }

    private boolean evaluate(Condition condition) {
        int left = getVariable(condition.getIdentifier());
        int right = condition.getValue();

        switch (condition.getOperator()) {
            case LESS_THAN:
                return left < right;
            case GREATER_THAN:
                return left > right;
            case EQUAL_TO:
                return left == right;
            default:
                throw new IllegalStateException(
                    "Unknown operator: " + condition.getOperator()
                );
        }

    }

    private int getVariable(String name) {
        if (!environment.containsKey(name)) {
            throw new RuntimeException(
                "Runtime error: variable '" + name + "' is not defined"
            );
        }
        return environment.get(name);
    }

    public Map<String, Integer> snapshot() {
        return Map.copyOf(environment);
    }
}
