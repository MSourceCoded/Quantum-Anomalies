package sourcecoded.quantum.api.gesture;

public enum GestureDirection {

    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    byte b;

    GestureDirection(int theIndex) {
        this.b = (byte)theIndex;
    }

    public byte getByteValue() {
        return b;
    }

    public static float tolerance() {
        return 10F;
    }

}
