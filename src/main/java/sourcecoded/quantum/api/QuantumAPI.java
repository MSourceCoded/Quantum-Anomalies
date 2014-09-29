package sourcecoded.quantum.api;

/**
 * This is the main class for the Quantum Anomalies mod
 * API. If you're doing any kind of integration, the methods
 * in this class should be useful to you.
 *
 * @author SourceCoded
 */
public class QuantumAPI {

    /**
     * Don't touch this variable, hook in via
     * the isQAPresent() method.
     */
    public static boolean isQAPresent = false;

    /**
     * Returns 'true' is the Quantum Anomalies mod
     * is present. This is set in Pre-Init, so
     * it is recommended to check for in Init or Later.
     */
    public static boolean isQAPresent() {
        return isQAPresent;
    }

}
