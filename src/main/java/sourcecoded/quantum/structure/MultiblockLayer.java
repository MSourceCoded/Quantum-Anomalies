package sourcecoded.quantum.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.HashMap;

public class MultiblockLayer {

    Object[][] blocks;

    public MultiblockLayer(Object... params) {
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

        HashMap<Character, Object> matches;

        for (matches = new HashMap<Character, Object>(); i < params.length; i += 2) {
            Character character = (Character)params[i];

            matches.put(character, params[i + 1]);
        }

        blocks = new Object[x][z];

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
                Object compare = blocks[xO][zO];
                Block block = world.getBlock(x + xO, y, z + zO);
                if (compare == null) continue;

                if (compare == Blocks.air) {                //F**ing railcraft and it's hidden blocks.
                    if (!block.isAir(world, x + xO, y, z + zO)) return false;
                    continue;
                }

                if (!compare.getClass().isAssignableFrom(block.getClass())) return false;           //Inheritance
            }
        return true;
    }

}
