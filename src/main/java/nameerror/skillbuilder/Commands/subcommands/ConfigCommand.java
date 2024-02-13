package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Configuration.PluginConfiguration;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Verbose;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommand extends SubCommandManager { // /skillbuilder explode <args>

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public String getDescription() {
        return "Command manage about SkillBuilder configuration";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder config <options>";
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

        if (args.length == 1 || args.length == 2 && args[1].equals("")) {
            sender.sendMessage(Verbose.invalidCommandSyntax());
            return;
        }

        if (args[1].equals("reload")) {
            for (PluginConfiguration cfg : SkillBuilder.configurationDatabase.values()) {
                if (!cfg.reload()) {
                    sender.sendMessage(Verbose.configReloadFail(cfg.getDirPath().replace("\\", "/")
                            + "/" + cfg.getFileName()));
                    return;
                }
            }
            sender.sendMessage(Verbose.configReloadSuccess());

        } else {
            sender.sendMessage(Verbose.invalidCommandSyntax());
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            result.add("reload");
        }

        return result;
    }
}
