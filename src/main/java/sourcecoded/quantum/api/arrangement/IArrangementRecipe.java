package sourcecoded.quantum.api.arrangement;

import javafx.geometry.Point3D;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * An interface for use with custom ArrangementRecipes.
 * This is registered in the ArrangementRegistry
 *
 * @see sourcecoded.quantum.api.arrangement.ArrangementRegistry
 *
 * @author SourceCoded
 */
public interface IArrangementRecipe {

    /**
     * Does this recipe match the given
     * ItemMatrix grid
     */
    public boolean matches(ItemMatrix grid);

    /**
     * Get the Output ItemStack for the
     * recipe. It is recommended
     * to use .copy() here
     */
    public ItemStack getOutput();

}
