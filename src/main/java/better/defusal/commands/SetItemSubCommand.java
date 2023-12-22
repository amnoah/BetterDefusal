package better.defusal.commands;

import better.defusal.BetterDefusal;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class SetItemSubCommand extends SubCommand {

    public SetItemSubCommand() {
        setName("SetItem");
        setPermission("better.defusal.command");
        setRequiresArgs(true);
        setTabCompletions(BetterDefusal.MATERIALS);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!BetterDefusal.MATERIALS.contains(strings[1].toLowerCase())) {
            commandSender.sendMessage("§7Unrecognized material!");
            return;
        }

        BetterDefusal.getInstance().setDefusalItem(Material.getMaterial(strings[1].toUpperCase()));
        commandSender.sendMessage("§7Set defusal item!");
    }
}
