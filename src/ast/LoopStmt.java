package ast;

import java.util.List;

public final class LoopStmt implements Stmt {

    private final Condition condition;
    private final List<Stmt> body;

    public LoopStmt(Condition condition, List<Stmt> body) {
        this.condition = condition;
        this.body = body;
    }

    public Condition getCondition() {
        return condition;
    }

    public List<Stmt> getBody() {
        return body;
    }
}
