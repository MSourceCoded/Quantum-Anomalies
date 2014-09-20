package sourcecoded.quantum.api.block;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Implement this on any block you wish to have
 * a custom action happen with the Diagnostics
 * sceptre focus
 */
public interface IDiagnostic {

    /**
     * Called when the block is being diagnosed
     * @param phase Before or After
     *              the information of ITileRiftHandler
     *              is displayed
     */
    public void onDiagnose(DiagnosticsPhase phase, World world, int x, int y, int z, EntityPlayer player);

    public enum DiagnosticsPhase {
        BEFORE, AFTER
    }
}
