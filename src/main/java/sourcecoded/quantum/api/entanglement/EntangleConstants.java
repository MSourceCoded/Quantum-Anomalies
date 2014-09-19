package sourcecoded.quantum.api.entanglement;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class EntangleConstants {

    //Blacklisted blocks
    public static Block[] vanillaBlacklist = new Block[]{
            Blocks.beacon,
            Blocks.command_block,
            Blocks.enchanting_table,
            Blocks.hopper,
            Blocks.mob_spawner,
            Blocks.piston,
            Blocks.sticky_piston,
            Blocks.piston_extension,
            Blocks.piston_head
    };

    public static boolean blockBlacklisted(Block block) {
        for (Block b : vanillaBlacklist)
            if (b == block) return true;
        return false;
    }

}
