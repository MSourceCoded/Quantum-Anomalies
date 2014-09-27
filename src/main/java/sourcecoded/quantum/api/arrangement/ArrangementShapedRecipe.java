package sourcecoded.quantum.api.arrangement;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class ArrangementShapedRecipe implements IArrangementRecipe{

    ItemStack output;
    ItemMatrix grid;

    public ArrangementShapedRecipe(ItemStack output, Object... params) {
        this.output = output;

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

        HashMap<Character, ItemStack> matches;

        for (matches = new HashMap<Character, ItemStack>(); i < params.length; i += 2) {
            Character character = (Character)params[i];

            Object obj = params[i + 1];
            if (obj instanceof ItemStack)
                matches.put(character, (ItemStack) obj);
            else if (obj instanceof Block)
                matches.put(character, new ItemStack((Block) obj));
            else if (obj instanceof Item)
                matches.put(character, new ItemStack((Item) obj));
        }

        grid = new ItemMatrix(3, 3);

        for (int xC = 0; xC < x; ++xC)
            for (int zC = 0; zC < z; ++zC) {
                char c = s.charAt(j);
                if (matches.containsKey(c))
                    grid.setItemAt(xC, zC, matches.get(c));
                j++;
            }
    }

    @Override
    public boolean matches(ItemMatrix grid) {
        return matchesAny(grid);
    }

    public boolean matchesAny(ItemMatrix i) {
        ItemMatrix i90 = ItemMatrix.rotate90(i);
        ItemMatrix i180 = ItemMatrix.rotate180(i);
        ItemMatrix i270 = ItemMatrix.rotate270(i);
        return grid.matches(i) || grid.matches(i90) || grid.matches(i180) || grid.matches(i270);
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }
}