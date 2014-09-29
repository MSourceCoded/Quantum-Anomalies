package sourcecoded.quantum.api.energy;

/**
 * Used for containers that withhold Rift Energy
 * without necessarily being contained to a
 * TileEntity or Item.
 *
 * @author SourceCoded
 */
public interface IRiftEnergyStorage {

    /**
     * Take energy from the storage
     *
     * @param amount The amount of energy
     *               to be taken
     * @return The amount of energy
     * that was taken from the storage
     */
    public int takeRiftEnergy(int amount);

    /**
     * Give the storage energy.
     * @param amount The amount of energy
     *               to be given
     * @return The amount of energy that
     * was accepted
     */
    public int giveRiftEnergy(int amount);

    /**
     * Get the current amount of Rift Energy
     * in the storage
     */
    public int getRiftEnergy();

    /**
     * Get the Maximum capacity of the
     * storage
     */
    public int getMaxRiftEnergy();

}
