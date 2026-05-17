package interpreter;

public final class RuntimeError extends RuntimeException {
    public RuntimeError(String message) {
        super("Runtime error: " + message);
    }
}
