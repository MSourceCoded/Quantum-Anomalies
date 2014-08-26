package sourcecoded.quantum.api.energy;

public interface IRiftEnergyStorage {

    /**
     * Takes rift energy from the storage
     *
     * @param amount The amount requested
     * @return The amount that was received
     */
    public int takeRiftEnergy(int amount);

    /**
     * Gives rift energy to the storage
     *
     * @param amount     The amount offered
     * @return The amount taken
     */
    public int giveRiftEnergy(int amount);

    /**
     * Get the amount of Rift Energy present in the storage
     */
    public int getRiftEnergy();

    /**
     * Get the maximum amount of Rift Energy the storage can hold
     */
    public int getMaxRiftEnergy();

}
