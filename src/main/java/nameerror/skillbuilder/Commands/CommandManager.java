package nameerror.skillbuilder.Commands;

import nameerror.skillbuilder.Commands.subcommands.*;
import nameerror.skillbuilder.Verbose;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CommandManager implements TabExecutor {

    private final HashMap<String, SubCommandManager> subCommands = new HashMap<>();

    public CommandManager() {
        subCommands.put("cancel", new CancelCommand());
        subCommands.put("config", new ConfigCommand());
        subCommands.put("launch", new LaunchCommand());
        subCommands.put("launch_as", new LaunchAsCommand());
        subCommands.put("test", new TestCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            // /skillbulder
            if (args.length > 0) {
                if (this.subCommands.containsKey(args[0])) {
                    subCommands.get(args[0]).perform(player, args);
                } else {
                    sender.sendMessage(Verbose.commandNotExist());
                }

            } else {
                player.sendMessage(Verbose.invalidCommandSyntax());
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) { // /skillbuilder <subcommands> <args>
            return this.getSubCommands();

        } else {
            if (!this.subCommands.containsKey(args[0]))
                return new ArrayList<>();

            return this.subCommands.get(args[0]).getSubcommandArguments(sender, args);
        }
    }

    public List<String> getSubCommands() {
        return new ArrayList<>(subCommands.keySet());
    }

    public static List<String> filterStartsWith(List<String> stringList, String keyword) {
        stringList.removeIf(s -> !s.toLowerCase(Locale.ROOT).startsWith(keyword.toLowerCase(Locale.ROOT)));
        return stringList;
    }
}
