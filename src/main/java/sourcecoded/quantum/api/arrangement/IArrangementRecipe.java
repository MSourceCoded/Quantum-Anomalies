package sourcecoded.quantum.api.arrangement;

import javafx.geometry.Point3D;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IArrangementRecipe {

    public boolean matches(ItemMatrix grid);

    public ItemStack getOutput();

}
