package better.defusal.commands;

import better.defusal.BetterDefusal;
import com.imjustdoom.cmdinstruction.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand() {
        setName("Reload");
        setPermission("better.defusal.command");
        setRequiresArgs(false);
        setTabCompletions();
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        BetterDefusal.getInstance().reload();
        commandSender.sendMessage("§7Successfully reloaded!");
    }
}
