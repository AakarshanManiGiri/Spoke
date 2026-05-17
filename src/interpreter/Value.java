package interpreter;

public final class Value {

    public enum Type {
        INT,
        BOOL
    }

    private final Type type;
    private final int intValue;
    private final boolean boolValue;

    private Value(Type type, int intValue, boolean boolValue) {
        this.type = type;
        this.intValue = intValue;
        this.boolValue = boolValue;
    }

    public static Value ofInt(int v) {
        return new Value(Type.INT, v, false);
    }

    public static Value ofBool(boolean b) {
        return new Value(Type.BOOL, 0, b);
    }

    public Type type() {
        return type;
    }

    public int asInt() {
        if (type != Type.INT) throw new RuntimeError("Expected integer value");
        return intValue;
    }

    public boolean asBool() {
        if (type != Type.BOOL) throw new RuntimeError("Expected boolean value");
        return boolValue;
    }

    @Override
    public String toString() {
        return switch (type) {
            case INT -> Integer.toString(intValue);
            case BOOL -> Boolean.toString(boolValue);
        };
    }
}
