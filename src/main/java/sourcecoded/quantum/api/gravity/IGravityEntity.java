package sourcecoded.quantum.api.gravity;

/**
 * Any particle affected by TileEntities with a gravitational pull
 *
 * @author SourceCoded
 */
public interface IGravityEntity {

    /**
     * Called whenever gravity affects the entity
     */
    public void onGravityAffected(float force);

}
