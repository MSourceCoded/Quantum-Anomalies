package sourcecoded.quantum.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;

public class Multiblock {

    Block[][] blocks;

    public Multiblock(Object... params) {
        String s = "";
        int i = 0;
        int j = 0;
        int x = 0;
        int z = 0;

        if (params[i] instanceof String[]) {
            String[] astring = (String[])((String[])params[i++]);

            for (String s1 : astring) {
                ++z;
                x = s1.length();
                s = s + s1;
            }
        } else {
            while (params[i] instanceof String) {
                String s2 = (String)params[i++];
                ++z;
                x = s2.length();
                s = s + s2;
            }
        }

        HashMap<Character, Block> matches;

        for (matches = new HashMap<Character, Block>(); i < params.length; i += 2) {
            Character character = (Character)params[i];

            if (params[i + 1] instanceof Block)
                matches.put(character, (Block)params[i + 1]);
        }

        blocks = new Block[x][z];

        for (int xC = 0; xC < x; ++xC)
            for (int zC = 0; zC < z; ++zC) {
                char c = s.charAt(j);
                if (matches.containsKey(c))
                    blocks[xC][zC] = matches.get(c);
                else blocks[xC][zC] = null;
                j++;
            }
    }

    /**
     * Checks if the multiblock is valid, taken from the centre
     */
    public boolean valid(World world, int x, int y, int z) {
        x -= blocks.length / 2;
        z -= blocks[0].length / 2;

        for (int xO = 0; xO < blocks.length; xO++)
            for (int zO = 0; zO < blocks[xO].length; zO++) {
                Block compare = blocks[xO][zO];
                if (compare == null) continue;
                if (!compare.getClass().isAssignableFrom(world.getBlock(x + xO, y, z + zO).getClass())) return false;
            }
        return true;
    }

}
