package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.CommandManager;
import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.OldCode.PluginEntityType;
import nameerror.skillbuilder.OldCode.SBComponent;
import nameerror.skillbuilder.OldCode.SkillManagement.SkillManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CancelCommand extends SubCommandManager {
    @Override
    public String getName() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "Cancel all skill from Player that have the name specified. if the name not specific will, remove all skill";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder cancel <Player> <Skill>";
    }

    @Override
    public boolean isForPlayer() {
        return true;
    }

    @Override
    public boolean isForConsole() {
        return true;
    }

    @Override
    public boolean isForCommandBlock() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        SBComponent targetPlayer = new SBComponent(PluginEntityType.PLAYER);
        targetPlayer.setGameEntity(Bukkit.getPlayer(args[1]));

        if (args.length == 2) {

            SkillManager.cancelAll(targetPlayer);

        } else if (args.length == 3) {
            if (args[2].equals(""))
                SkillManager.cancelAll(targetPlayer);
            else
                SkillManager.cancel(targetPlayer, args[2]);
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