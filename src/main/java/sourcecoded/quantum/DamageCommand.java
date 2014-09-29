package sourcecoded.quantum;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class DamageCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "damage";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/damage <amt>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (args.length != 1) return;

        double amt = Double.parseDouble(args[0]);

        ((EntityPlayer) sender).attackEntityFrom(DamageSource.magic, (float)amt);

    }
}
