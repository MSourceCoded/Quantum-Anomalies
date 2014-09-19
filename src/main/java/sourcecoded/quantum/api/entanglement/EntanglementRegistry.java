package sourcecoded.quantum.api.entanglement;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import sourcecoded.quantum.registry.BlockRegistry;

import java.util.ArrayList;

/**
 * The registry for the Quantum Entanglement Platform.
 *
 * The logic behind this is semi-complex, the rules
 * are listed below.
 *
 * If the block DOES NOT have a TileEntity, the block
 * is allowed to be entangled, UNLESS it is registered
 * as blacklistBlock()
 *
 * If the block DOES have a TileEntity, the block
 * is not allowed to be entangled, UNLESS it is
 * registered under whitelistTileBlock();
 */
public class EntanglementRegistry {

    public static ArrayList<Block> allowedBlocks = new ArrayList<Block>();
    public static ArrayList<Block> blacklistedBlocks = new ArrayList<Block>();

    /**
     * Whitelist an ITileEntityProvider
     */
    public static void whitelistTileBlock(Block block) {
        allowedBlocks.add(block);
    }

    /**
     * Blacklist a standard block
     */
    public static void blacklistBlock(Block block) {
        blacklistedBlocks.add(block);
    }

    /**
     * Is this block allowed to be entangled?
     */
    public static boolean isAllowed(Block block) {
        String modid = GameRegistry.findUniqueIdentifierFor(block).modId;
        if (EntangleConstants.blockBlacklisted(block) && modid.equals("minecraft")) return false;
        else if (modid.equals("minecraft")) return true;
        if (blacklistedBlocks.contains(block)) return false;

        if (block instanceof ITileEntityProvider) {
            if (allowedBlocks.contains(block)) return true;
        } else
            return true;

        return false;
    }

}
