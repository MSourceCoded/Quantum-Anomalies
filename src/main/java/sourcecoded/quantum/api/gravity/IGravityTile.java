package sourcecoded.quantum.api.gravity;

/**
 * Any TileEntities that emit a gravitational pull to
 * {@link sourcecoded.quantum.api.gravity.IGravityEntity}.
 * Recommended that you hook into {@link GravityHandler}
 * for storing this data, unless you have a custom implementation
 *
 * @author SourceCoded
 */
public interface IGravityTile {

    /**
     * Set the maximum amount of force it can provide
     */
    public void setMaxForce(float max);

    /**
     * Get the maximum amount of force it can provide
     */
    public float getMaxForce();

    /**
     * Get the amount of attraction from the distance specified
     */
    public float getAttraction(float distance);

    /**
     * The maximum range this object can attract from
     */
    public float getMaxRange();

    /**
     * Can I be attracted from this distance?
     */
    public boolean canAttract(float distance);
}
