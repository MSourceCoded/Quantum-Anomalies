package sourcecoded.quantum.sceptre.focus;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import sourcecoded.quantum.api.block.IDiagnostic;
import sourcecoded.quantum.api.energy.EnergyBehaviour;
import sourcecoded.quantum.api.energy.ITileRiftHandler;
import sourcecoded.quantum.api.gesture.AbstractGesture;
import sourcecoded.quantum.api.sceptre.ISceptreFocus;
import sourcecoded.quantum.api.translation.LocalizationUtils;

import java.text.DecimalFormat;

public class FocusDiagnostic implements ISceptreFocus {
    @Override
    public String getFocusIdentifier() {
        return "QA|Diagnostic";
    }

    @Override
    public String getName() {
        return "qa.sceptre.focus.diagnostic";
    }

    @Override
    public String[] getLore(ItemStack item) {
        return new String[]{"qa.sceptre.focus.diagnostic.lore.0"};
    }

    @Override
    public boolean canBeUsed(EntityPlayer player, ItemStack itemstack) {
        return true;
    }

    @Override
    public EnumChatFormatting getNameColour() {
        return EnumChatFormatting.GOLD;
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
        if (!world.isRemote && player.isSneaking()) {
            Block block = world.getBlock(x, y, z);
            TileEntity tile = world.getTileEntity(x, y, z);

            if (block instanceof IDiagnostic)
                ((IDiagnostic) block).onDiagnose(IDiagnostic.DiagnosticsPhase.BEFORE, world, x, y, z, player);

            if (tile != null && tile instanceof ITileRiftHandler) {
                ITileRiftHandler t = (ITileRiftHandler) tile;
                String containedEnergy = LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.diagnostic.energyContained", "Energy: %s QRE");
                String capacityEnergy = LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.diagnostic.energyCapacity", "Capacity: %s QRE");
                String behaviourEnergy = LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.diagnostic.energyBehaviour", "Behaviour: %s");

                double energy = t.getRiftEnergy();
                double capacity = t.getMaxRiftEnergy();
                EnergyBehaviour behaviour = t.getBehaviour();

                DecimalFormat format = new DecimalFormat("#,##0");

                player.addChatComponentMessage(new ChatComponentText(String.format(containedEnergy, format.format(energy))));
                player.addChatComponentMessage(new ChatComponentText(String.format(capacityEnergy, format.format(capacity))));
                player.addChatComponentMessage(new ChatComponentText(String.format(behaviourEnergy, behaviour.toString())));
            } else {
                player.addChatComponentMessage(new ChatComponentText(LocalizationUtils.translateLocalWithColours("qa.sceptre.focus.diagnostic.notEnergy", "{c:GRAY}{c:ITALIC}This block does not support energy")));
            }

            if (block instanceof IDiagnostic)
                ((IDiagnostic) block).onDiagnose(IDiagnostic.DiagnosticsPhase.AFTER, world, x, y, z, player);

            return true;
        }
        return false;
    }

    @Override
    public void onItemTick(ItemStack item) {
    }

    @Override
    public void onUsingTick(ItemStack item) {
    }

    @Override
    public AbstractGesture[] getAvailableGestures() {
        return null;
    }

    @Override
    public float[] getRGB() {
        return new float[] {0.93F, 0.5F, 0.1F};
    }
}
