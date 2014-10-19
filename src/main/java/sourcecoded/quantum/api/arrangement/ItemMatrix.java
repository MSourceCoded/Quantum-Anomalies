package sourcecoded.quantum.api.arrangement;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;

/**
 * The ItemMatrix object is used to
 * store a pattern of ItemStacks
 * in a specific Matrix/Grid.
 *
 * This is mostly used for Arrangement
 * crafting
 *
 * @see sourcecoded.quantum.api.arrangement.IArrangementRecipe
 *
 * @author SourceCoded
 */
public class ItemMatrix {

    private ItemStack[][] items;

    /**
     * Create a new ItemMatrix with the
     * specified Width and Height
     */
    public ItemMatrix(int width, int height) {
        items = new ItemStack[width][height];
    }

    /**
     * Get the grid as a 2D array
     */
    public ItemStack[][] getGrid() {
        return items.clone();
    }

    /**
     * Set the grid with the 2D array
     * given
     */
    public void setGrid(ItemStack[][] stack) {
        items = stack;
    }

    /**
     * Get the height [y] of the matrix
     */
    public int getHeight() {
        return items[0].length;
    }

    /**
     * Get the width [x] of the matrix
     */
    public int getWidth() {
        return items.length;
    }

    /**
     * Get the ItemStack at the specified x/y
     */
    public ItemStack getItemAt(int x, int y) {
        return items[x][y];
    }

    /**
     * Set the ItemStack at the specified x/y
     */
    public void setItemAt(int x, int y, ItemStack item) {
        items[x][y] = item;
    }

    /**
     * Returns true if the ItemMatrix matches the one
     * provided. This only works in one direction, so
     * if your algorithm doesn't care about orientation,
     * call this multiple times, each time with the
     * matrix rotated.
     */
    public boolean matches(ItemMatrix grid) {
        if (grid.getHeight() != getHeight()) return false;
        if (grid.getWidth() != getWidth()) return false;

        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                ItemStack compare = grid.getItemAt(x, y);
                ItemStack itemstack = getItemAt(x, y);

                if (compare == null && itemstack == null) continue;
                if (compare == null || itemstack == null) return false;
                //if (!compare.isItemEqual(itemstack)) return false;
                if (!OreDictionary.itemMatches(itemstack, compare, false)) return false;
            }

        return true;
    }

    /**
     * Rotate the contents of the Matrix 90 deg
     * clockwise
     */
    public static ItemMatrix rotate90(ItemMatrix matrix) {
        ItemStack[][] piece = matrix.getGrid();

        ItemStack[][] res = new ItemStack[piece[0].length][piece.length];
        for (int x = 0; x < piece.length; x++)
            for (int y = 0; y < piece[0].length; y++)
                res[(res.length-1)-y][x] = piece[x][y];

        ItemMatrix newIM = new ItemMatrix(res.length, res[0].length);
        newIM.setGrid(res);
        return newIM;
    }

    /**
     * Rotate the contents of the Matrix 180 deg
     * clockwise
     */
    public static ItemMatrix rotate180(ItemMatrix matrix) {
        return rotate90(rotate90(matrix));
    }

    /**
     * Rotate the contents of the Matrix 270 deg
     * clockwise
     */
    public static ItemMatrix rotate270(ItemMatrix matrix) {
        return rotate180(rotate90(matrix));
    }

    public static ItemMatrix createFromRecipe(ShapedRecipes recipe) {
        ItemStack[][] stacks = new ItemStack[recipe.recipeWidth][recipe.recipeHeight];
        int level = 0;
        int index = 0;
        for (int i = 0; i < recipe.getRecipeSize(); i++) {
            ItemStack stack = recipe.recipeItems[i];
            if (index == stacks.length) {
                level++;
                index = 0;
            }

            stacks[level][index] = stack;
            index++;
        }

        ItemMatrix matrix = new ItemMatrix(recipe.recipeWidth, recipe.recipeHeight);
        matrix.setGrid(stacks);
        return matrix;
    }

}
