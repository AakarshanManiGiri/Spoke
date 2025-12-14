package ast;

public final class JumpStmt implements Stmt {

    private final String name;
    private final int delta;

    public JumpStmt(String name, int delta) {
        this.name = name;
        this.delta = delta;
    }

    public String getName() {
        return name;
    }

    public int getDelta() {
        return delta;
    }
}
