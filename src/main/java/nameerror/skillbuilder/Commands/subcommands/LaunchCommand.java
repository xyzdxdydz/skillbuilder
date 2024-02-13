package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.CommandManager;
import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Fundamental.SkillManagement.SkillManager;
import nameerror.skillbuilder.Verbose;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LaunchCommand extends SubCommandManager {
    @Override
    public String getName() {
        return "launch";
    }

    @Override
    public String getDescription() {
        return "launch skill";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder launch <skill>";
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
        if (args.length == 2) {
            if (args[1].equals("")) {
                sender.sendMessage(Verbose.invalidCommandSyntax());
                return;
            }

            sender.sendMessage(Verbose.featureUnderMaintenance("launch cmd"));
//            new SkillManager((Player) sender, args[1]).launch();
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            result = CommandManager.filterStartsWith(SkillManager.getAllSkills(), args[1]);
        }

        return result;
    }
}