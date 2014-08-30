package sourcecoded.quantum.api;

public class QuantumAPI {

    /**
     * Don't touch this variable, hook in via
     * the isQAPresent() method.
     */
    public static boolean isQAPresent = false;

    /**
     * Returns 'true' is the Quantum Anomalies mod
     * is present. This is set in Pre-Init, so
     * it is recommended to check for in Init.
     */
    public static boolean isQAPresent() {
        return isQAPresent;
    }

}
