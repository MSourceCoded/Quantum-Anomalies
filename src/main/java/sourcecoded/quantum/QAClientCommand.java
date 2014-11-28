package sourcecoded.quantum;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import sourcecoded.quantum.api.discovery.DiscoveryManager;
import sourcecoded.quantum.network.MessageDiscoveryImport;
import sourcecoded.quantum.network.NetworkHandler;
import sourcecoded.quantum.proxy.ClientProxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QAClientCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "quantumC";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/quantumC <discoveries> <import|export> [fileName]";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) wrong(sender);
        String filename = EnumChatFormatting.getTextWithoutFormattingCodes(sender.getCommandSenderName());
        if (args.length == 3)
            filename = args[2];

        if (!filename.contains(".")) {
            filename += ".discoveries";
        }

        if (args[0].equalsIgnoreCase("discoveries")) {
            if (args[1].equalsIgnoreCase("import")) {
                File importFile = new File(ClientProxy.discoveryImportDir, filename);
                if (importFile.exists()) {
                    //Import discoveries from JSON file
                    try {
                        NBTTagCompound compound = CompressedStreamTools.func_152458_a(importFile, NBTSizeTracker.field_152451_a);
                        NBTTagCompound quantumTag = DiscoveryManager.getQuantumTag((EntityPlayer) sender);

                        if (quantumTag.hasKey("discoveries"))
                            quantumTag.removeTag("discoveries");

                        quantumTag.setTag("discoveries", compound);

                        NetworkHandler.wrapper.sendToServer(new MessageDiscoveryImport(quantumTag));
                        importSuccessful(sender);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else doesNotExist(sender, filename, importFile);
            } else if (args[1].equalsIgnoreCase("export")) {
                File exportFile = new File(ClientProxy.discoveryExportDir, filename);
                //Export discoveries to file
                try {
                    if (exportFile.exists()) {
                        doesExist(sender, filename, exportFile);
                        return;
                    }
                    CompressedStreamTools.write(DiscoveryManager.getDiscoveriesTag((EntityPlayer) sender), exportFile);

                    exportSuccessful(sender, exportFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else wrong(sender);
        } else wrong(sender);
    }

    public void wrong(ICommandSender sender) {
        throw new WrongUsageException(getCommandUsage(sender));
    }

    public void doesNotExist(ICommandSender sender, String filename, File path) {
        sender.addChatMessage(new ChatComponentText(String.format("File: %s does not exist in path: %s", filename, path.getAbsolutePath())).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    }

    public void doesExist(ICommandSender sender, String filename, File path) {
        sender.addChatMessage(new ChatComponentText(String.format("File: %s already exists in path: %s", filename, path.getAbsolutePath())).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
    }

    public void exportSuccessful(ICommandSender sender, File file) {
        String message = "[{\"text\":\"Discoveries Export Successful! \",\"color\":\"aqua\"},{\"text\":\"%s\",\"color\":\"light_purple\",\"italic\":\"true\",\"underlined\":\"true\",\"clickEvent\":{\"action\":\"open_file\",\"value\":\"%s\"}}]";
        message = String.format(message, file.getName(), file.getAbsolutePath());

        sender.addChatMessage(IChatComponent.Serializer.func_150699_a(message));
    }

    public void importSuccessful(ICommandSender sender) {
        String message = "[{\"text\":\"Discoveries Import Successful!\",\"color\":\"aqua\"}]";
        sender.addChatMessage(IChatComponent.Serializer.func_150699_a(message));
    }

}
