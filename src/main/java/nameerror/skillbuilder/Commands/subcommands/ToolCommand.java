package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Fundamental.MetaManager;
import nameerror.skillbuilder.Utils.DevTools.EntityGrid;
import nameerror.skillbuilder.Verbose;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ToolCommand extends SubCommandManager {
    @Override
    public String getName() {
        return "tool";
    }

    @Override
    public String getDescription() {
        return "activate tool (for debug and develop)";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder tool <tool> <tool args>";
    }

    @Override
    public boolean isForPlayer() {
        return true;
    }

    @Override
    public boolean isForConsole() {
        return false;
    }

    @Override
    public boolean isForCommandBlock() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 4) {
            if (args[3].equals("")) {
                sender.sendMessage(Verbose.invalidCommandSyntax());
                return;
            }

            if (args[1].equals("creeper_grid")) {
                EntityGrid.makeGrid(((Player) sender).getLocation(), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                sender.sendMessage("Tool been has run");

            } else if (args[1].equals("manager")) {
                String result = MetaManager.op(args[3], args[2]);

                sender.sendMessage("Tool been has run.");
                sender.sendMessage("Status: " + result);
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            result.add("creeper_grid");
            result.add("manager");

        } else if (args.length == 3) {
            if (args[1].equals("manager")) {
                result = MetaManager.getManagers();
            }
        } else if (args.length == 4) {
            if (args[1].equals("manager")) {
                result.add("reset");
            }
        }

        return result;
    }
}
