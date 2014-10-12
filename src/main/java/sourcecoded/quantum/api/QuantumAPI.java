package sourcecoded.quantum.api;

import cpw.mods.fml.common.eventhandler.EventBus;

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
     * The EventBus for the Quantum API. Events are in the
     * {@link sourcecoded.quantum.api.event} package
     */
    public static EventBus eventBus = new EventBus();

    /**
     * Returns 'true' is the Quantum Anomalies mod
     * is present. This is set in Pre-Init, so
     * it is recommended to check for in Init or Later.
     */
    public static boolean isQAPresent() {
        return isQAPresent;
    }

}
