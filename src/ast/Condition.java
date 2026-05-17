package ast;

public final class Condition {

    public enum Operator {
        LESS_THAN,
        GREATER_THAN,
        EQUAL_TO
    }

    private final String identifier;
    private final Operator operator;
    private final Expr value;

    public Condition(String identifier, Operator operator, Expr value) {
        this.identifier = identifier;
        this.operator = operator;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expr getValue() {
        return value;
    }
}
