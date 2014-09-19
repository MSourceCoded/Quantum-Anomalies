package sourcecoded.quantum;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;

public class DebugCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "qa";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return null;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender send, String[] a) {
        EntityPlayer player = (EntityPlayer) send;

        //FXManager.portalFX1Fragment(send.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);

        EntityLightningBolt bolt = new EntityLightningBolt(player.worldObj, player.posX, player.posY + 3, player.posZ);
        player.worldObj.addWeatherEffect(bolt);
    }
}
