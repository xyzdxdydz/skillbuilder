package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.CommandManager;
import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Fundamental.SkillManagement.SkillManager;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LaunchAsCommand extends SubCommandManager {

    @Override
    public String getName() {
        return "launch_as";
    }

    @Override
    public String getDescription() {
        return "make <Player Name> launch skill";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder launchas <Player> <Skill Name>";
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
        if (args.length == 3) {
            if (args[2].equals("")) {
                sender.sendMessage(Verbose.invalidCommandSyntax());
                return;
            }

//            new SkillManager(Bukkit.getPlayer(args[1]), args[2]).launch();
            Bukkit.broadcastMessage(Verbose.featureUnderMaintenance("launch_as cmd"));

        } else {
            sender.sendMessage(Verbose.invalidCommandSyntax());
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);

            for (Player p : players) {
                result.add(p.getName());
            }
            CommandManager.filterStartsWith(result, args[1]);

        } else if (args.length == 3) {
            result = CommandManager.filterStartsWith(SkillManager.getAllSkills(), args[2]);
        }

        return result;
    }
}
