package nameerror.skillbuilder.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommandManager {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract boolean isForPlayer();

    public abstract boolean isForConsole();

    public abstract boolean isForCommandBlock();

    public abstract void perform(CommandSender sender, String[] args);

    public abstract List<String> getSubcommandArguments(CommandSender sender, String[] args);

//    public abstract void perform(BlockCommandSender sender, String[] args);
//
//    public abstract void perform(ServerCommandSender sender, String[] args);
}
