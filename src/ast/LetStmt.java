package ast;

public final class LetStmt implements Stmt {

    private final String name;
    private final Expr initializer;

    public LetStmt(String name, Expr initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public String getName() {
        return name;
    }

    public Expr getInitializer() {
        return initializer;
    }
}
