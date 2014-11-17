package sourcecoded.quantum.sceptre.focus;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import sourcecoded.quantum.QuantumAnomalies;
import sourcecoded.quantum.api.block.Colourizer;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.block.BlockQuantumLock;
import sourcecoded.quantum.registry.QABlocks;
import sourcecoded.quantum.util.save.QAWorldSavedData;

import java.util.Map;

public class FocusDebug implements ISceptreFocus {

    @Override
    public String getFocusIdentifier() {
        return "QA|debug";
    }

    @Override
    public String getName() {
        return "DEBUG";
    }

    @Override
    public String[] getLore(ItemStack item) {
        return new String[0];
    }

    @Override
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack) {
        return QuantumAnomalies.isDevEnvironment();
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.GREEN;
    }

    @Override
    public void onActivated(ItemStack item) {
    }

    @Override
    public void onDeactivated(ItemStack item) {
    }

    @Override
    public void onClickBegin(EntityPlayer player, ItemStack item, World world) {
    }

    @Override
    public void onClickEnd(EntityPlayer player, ItemStack item, World world, int ticker) {
    }

    @Override
    public boolean onBlockClick(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null) tile.invalidate();
            Block block = world.getBlock(x, y, z);

            if (block instanceof BlockQuantumLock) return false;

            int meta = world.getBlockMetadata(x, y, z);
            QAWorldSavedData.getInstance(world).injectQuantumLock(world, block, meta, x, y, z);
            world.setBlock(x, y, z, QABlocks.LOCK.getBlock(), meta, 3);
            if (tile != null) {
                tile.validate();

                world.setTileEntity(x, y, z, tile);
            }
        } else {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null) tile.invalidate();
            Block block = world.getBlock(x, y, z);

            if (block != QABlocks.LOCK.getBlock()) return false;

            Map.Entry<Block, Integer> entry = QAWorldSavedData.getInstance(world).retrieveQuantumLock(x, y, z);
            if (entry != null) {
                world.setBlock(x, y, z, entry.getKey(), entry.getValue(), 3);
                if (tile != null) {
                    tile.validate();

                    world.setTileEntity(x, y, z, tile);
                }

                QAWorldSavedData.getInstance(world).destroyQuantumLock(world, x, y, z, true);
            }
        }
        return !world.isRemote;
    }

    @Override
    public void onItemTick(ItemStack item) {
    }

    @Override
    public void onUsingTick(ItemStack item) {
    }

    @Override
    public float[] getRGB() {
        return Colourizer.LIME.rgb;
    }
}
