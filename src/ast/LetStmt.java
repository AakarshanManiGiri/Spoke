package ast;

public final class LetStmt implements Stmt {

    private final String name;
    private final int value;

    public LetStmt(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
