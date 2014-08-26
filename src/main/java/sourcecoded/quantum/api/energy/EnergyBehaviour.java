package sourcecoded.quantum.api.energy;

/**
 * Specifies how a rift handler should behave. By default,
 * this should be set to drain.
 *
 * In Australia it's spelled "Behaviour". Shut up.
 */
public enum EnergyBehaviour {

    /**
     * Equalize the energy between each device
     */
    EQUALIZE,

    /**
     * Drain the energy from a provider
     */
    DRAIN,

    /**
     * Will not accept energy
     */
    NOT_ACCEPTING

}
