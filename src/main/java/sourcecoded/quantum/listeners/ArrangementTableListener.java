package sourcecoded.quantum.listeners;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import sourcecoded.quantum.api.QuantumAPI;
import sourcecoded.quantum.api.event.crafting.ArrangementCraftingEvent;
import sourcecoded.quantum.block.BlockArrangement;
import sourcecoded.quantum.registry.QABlocks;

public class ArrangementTableListener {

    @SubscribeEvent(receiveCanceled = true)         //Damn mods cancelling events >.<
    public void playerClick(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;


        EntityPlayer thePlayer = event.entityPlayer;
        World world = thePlayer.getEntityWorld();
        ItemStack itemInUse = thePlayer.getHeldItem();
        if (thePlayer.isSneaking() && itemInUse != null && itemInUse.getItem() == Items.diamond) {
            if (!BlockArrangement.multiblock.valid(world, event.x, event.y, event.z)) return;

            for (int x = -1; x <=1; x++)
                for (int z = -1; z <=1; z++) {
                    Block block = world.getBlock(event.x + x, event.y, event.z + z);
                    int meta = world.getBlockMetadata(event.x + x, event.y, event.z + z);

                    if (!world.isRemote) {
                        world.setBlockToAir(event.x + x, event.y, event.z + z);
                    }
                    else {
                        Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(event.x + x, event.y, event.z + z, block, meta);
                    }
                }
            if (!world.isRemote) {
                world.setBlock(event.x, event.y, event.z, QABlocks.ARRANGEMENT.getBlock());

                QuantumAPI.eventBus.post(new ArrangementCraftingEvent.Formed(world, event.x, event.y, event.z, event.entityPlayer));

                if (!thePlayer.capabilities.isCreativeMode)
                    itemInUse.stackSize -= 1;
            }

        }
    }

}
