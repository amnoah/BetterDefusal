package better.defusal.commands;

import better.defusal.BetterDefusal;
import com.imjustdoom.cmdinstruction.Command;
import org.bukkit.command.CommandSender;

public class BetterDefusalCommand extends Command {

    public BetterDefusalCommand() {
        setName("betterdefusal");
        setPermission("better.defusal.command");
        setSubcommands(new ReloadSubCommand(), new SetItemSubCommand());
        setRequiresArgs(false);
        setTabCompletions("Reload", "SetItem");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage("§7BetterDefusal version §8" + BetterDefusal.VERSION + "§7!");
    }
}
