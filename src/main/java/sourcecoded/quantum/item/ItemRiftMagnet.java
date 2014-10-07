package sourcecoded.quantum.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import sourcecoded.core.crafting.ICraftableItem;
import sourcecoded.quantum.api.translation.LocalizationUtils;
import sourcecoded.quantum.api.vacuum.VacuumRegistry;
import sourcecoded.quantum.entity.EntityItemMagnet;
import sourcecoded.quantum.entity.properties.PropertiesItem;
import sourcecoded.quantum.listeners.ItemTossListener;
import sourcecoded.quantum.vacuum.recipes.VacuumMagnet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemRiftMagnet extends ItemQuantum implements IBauble, ICraftableItem {

    public ItemRiftMagnet() {
        this.setUnlocalizedName("itemRiftMagnet");
        this.setTextureName("tools/magnet");

        this.setHasSubtypes(true);
    }

    public boolean hasEffect(ItemStack stack, int pass) {
        return this.getDamage(stack) == 1;
    }

    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityItemMagnet(world, location, itemstack);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (this.getDamage(stack) == 0)
                this.setDamage(stack, 1);
            else
                this.setDamage(stack, 0);
        }

        return stack;
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        checkCompound(stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IInventory) {
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            IInventory inv = (IInventory) tile;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack c = inv.getStackInSlot(i);
                if (c != null) stacks.add(c);
            }

            boolean whitelist = player.isSneaking();

            ArrayList<String> messages = new ArrayList<String>();

            NBTTagList list = (NBTTagList) stack.stackTagCompound.getTag("blacklist");
            if (list == null)
                list = new NBTTagList();

            for (ItemStack cs : stacks) {
                boolean inTag = false;
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound compound = list.getCompoundTagAt(i);
                    if (compound.getString("id").equals(cs.getUnlocalizedName()) && compound.getInteger("dmg") == cs.getItemDamage()) {
                        inTag = true;
                    }
                }

                if (!inTag && !whitelist) {
                    NBTTagCompound compound = new NBTTagCompound();
                    compound.setString("id", cs.getUnlocalizedName());
                    compound.setInteger("dmg", cs.getItemDamage());
                    messages.add(LocalizationUtils.translateLocalWithColours("qa.items.itemRiftMagnet.add", "Added Item To Blacklist: ") + " " + cs.getDisplayName() + " @ " + cs.getItemDamage());
                    list.appendTag(compound);
                }
            }

            stack.stackTagCompound.setTag("blacklist", list);
            for (String s : messages) {
                player.addChatComponentMessage(new ChatComponentText(s));
            }

            return !world.isRemote && !whitelist;
        }

        return false;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        checkCompound(stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IInventory) {
            ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            IInventory inv = (IInventory) tile;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack c = inv.getStackInSlot(i);
                if (c != null) stacks.add(c);
            }

            boolean whitelist = player.isSneaking();

            ArrayList<String> messages = new ArrayList<String>();

            NBTTagList list = (NBTTagList) stack.stackTagCompound.getTag("blacklist");
            if (list == null)
                list = new NBTTagList();

            for (ItemStack cs : stacks) {
                boolean inTag = false;
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound compound = list.getCompoundTagAt(i);
                    if (compound.getString("id").equals(cs.getUnlocalizedName()) && compound.getInteger("dmg") == cs.getItemDamage()) {
                        if (whitelist) {
                            list.removeTag(i);
                            messages.add(LocalizationUtils.translateLocalWithColours("qa.items.itemRiftMagnet.remove", "Removed Item From Blacklist: ") + " " + cs.getDisplayName() + " @ " + cs.getItemDamage());
                        }
                        inTag = true;
                    }
                }

            }

            stack.stackTagCompound.setTag("blacklist", list);
            for (String s : messages) {
                player.addChatComponentMessage(new ChatComponentText(s));
            }

            return world.isRemote;
        }

        return false;
    }

    public void checkCompound(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.stackTagCompound = new NBTTagCompound();
    }

    @SuppressWarnings("unchecked")
    public void onUpdate(ItemStack stack, World world, Entity p, int slot, boolean held) {
        checkCompound(stack);
        if (this.getDamage(stack) == 1 && !world.isRemote) {
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(p.posX - 8, p.posY - 4, p.posZ - 8, p.posX + 8, p.posY + 4, p.posZ + 8));

            itemLoop:
            for (EntityItem item : items) {
                IExtendedEntityProperties properties = item.getExtendedProperties("tossData");
                if (properties != null) {
                    PropertiesItem prop = (PropertiesItem) properties;
                    if (p.getUniqueID().toString().equals(prop.tosser) && item.age <= 300) return;
                }

                NBTTagList list = (NBTTagList) stack.stackTagCompound.getTag("blacklist");
                if (list != null)
                    for (int i = 0; i < list.tagCount(); i++) {
                        int dmg = list.getCompoundTagAt(i).getInteger("dmg");
                        String nm = list.getCompoundTagAt(i).getString("id");

                        if (item.getEntityItem().getUnlocalizedName().equals(nm) && item.getEntityItem().getItemDamage() == dmg) continue itemLoop;
                    }

                item.delayBeforeCanPickup = 0;
                item.setPosition(p.posX, p.posY, p.posZ);
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        onUpdate(itemstack, player.worldObj, player, 0, false);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public IRecipe[] getRecipes(Item item) {
        VacuumRegistry.addRecipe(new VacuumMagnet());
        return new IRecipe[0];
    }
}