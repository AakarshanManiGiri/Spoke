package ast;

public final class VariableExpr implements Expr {

    private final String name;

    public VariableExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
