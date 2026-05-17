package ast;

public final class BinaryExpr implements Expr {

    public enum Operator {
        ADD,
        SUB,
        MUL,
        DIV
    }

    private final Expr left;
    private final Operator operator;
    private final Expr right;

    public BinaryExpr(Expr left, Operator operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }
}
