package ast;

public final class JumpStmt implements Stmt {

    private final String name;
    private final Expr delta;

    public JumpStmt(String name, Expr delta) {
        this.name = name;
        this.delta = delta;
    }

    public String getName() {
        return name;
    }

    public Expr getDelta() {
        return delta;
    }
}
