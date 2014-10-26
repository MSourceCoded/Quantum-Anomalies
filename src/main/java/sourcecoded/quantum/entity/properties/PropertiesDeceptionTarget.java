package sourcecoded.quantum.entity.properties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PropertiesDeceptionTarget implements IExtendedEntityProperties {

    NBTTagCompound targets = new NBTTagCompound();

    public void injectTarget(EntityLivingBase target, boolean ignored) {
        targets.setBoolean(target.getUniqueID().toString(), ignored);
    }

    public boolean hasTarget(EntityLivingBase entity) {
        return targets.hasKey(entity.getUniqueID().toString());
    }

    public boolean shouldIgnoreTarget(EntityLivingBase entity) {
        return targets.getBoolean(entity.getUniqueID().toString());
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setTag("targets", targets);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        targets = compound.getCompoundTag("targets");
    }

    @Override
    public void init(Entity entity, World world) {
    }
}
