package sourcecoded.quantum.entity.properties;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PropertiesItem implements IExtendedEntityProperties {

    //Ha... ha.... ha...
    public String tosser;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        if (tosser != null)
            compound.setString("tossUUID", tosser);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey("tossUUID"))
            tosser = compound.getString("tossUUID");
    }

    @Override
    public void init(Entity entity, World world) {
    }
}
