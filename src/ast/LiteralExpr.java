package ast;

public final class LiteralExpr implements Expr {

    private final int value;

    public LiteralExpr(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
