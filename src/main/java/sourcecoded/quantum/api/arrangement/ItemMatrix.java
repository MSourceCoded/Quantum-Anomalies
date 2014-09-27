package sourcecoded.quantum.api.arrangement;

import net.minecraft.item.ItemStack;

public class ItemMatrix {

    private ItemStack[][] items;

    public ItemMatrix(int width, int height) {
        items = new ItemStack[width][height];
    }

    public ItemStack[][] getGrid() {
        return items.clone();
    }

    public void setGrid(ItemStack[][] stack) {
        items = stack;
    }

    public int getHeight() {
        return items[0].length;
    }

    public int getWidth() {
        return items.length;
    }

    public ItemStack getItemAt(int x, int y) {
        return items[x][y];
    }

    public void setItemAt(int x, int y, ItemStack item) {
        items[x][y] = item;
    }

    public boolean matches(ItemMatrix grid) {
        if (grid.getHeight() != getHeight()) return false;
        if (grid.getWidth() != getWidth()) return false;

        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++) {
                ItemStack compare = grid.getItemAt(x, y);
                ItemStack itemstack = getItemAt(x, y);

                if (compare == null && itemstack == null) continue;
                if (compare == null || itemstack == null) return false;
                if (!compare.isItemEqual(itemstack)) return false;
            }

        return true;
    }

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

    public static ItemMatrix rotate180(ItemMatrix matrix) {
        return rotate90(rotate90(matrix));
    }

    public static ItemMatrix rotate270(ItemMatrix matrix) {
        return rotate180(rotate90(matrix));
    }

}
