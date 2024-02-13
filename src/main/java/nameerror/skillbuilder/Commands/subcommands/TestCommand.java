package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.CommandManager;
import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Fundamental.SkillManagement.SkillManager;
import nameerror.skillbuilder.Testing.Test;
import nameerror.skillbuilder.Testing.TestModule.TrackedMatterTest;
import nameerror.skillbuilder.Testing.TestModule.VectorManagerTest;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TestCommand extends SubCommandManager {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "test existing modules.";
    }

    @Override
    public String getSyntax() {
        return "/skillbuilder test <module>";
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
            if (args[1].equals("")) {
                sender.sendMessage(Verbose.invalidCommandSyntax());
                return;
            }

            if (Test.getTestCases(args[1]).contains(args[2])) {
                Test.get(args[1]).test(args[2], (Player) sender);
                sender.sendMessage("Test was launched.");

            } else {
                sender.sendMessage(Verbose.commandArgumentNotFound());
            }

        } else {
        sender.sendMessage(Verbose.invalidCommandSyntax());
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            result = Test.getTestModules();
            CommandManager.filterStartsWith(result, args[1]);

        } else if (args.length == 3) {
            result = CommandManager.filterStartsWith(Test.getTestCases(args[1]), args[2]);
        }

        return result;
    }
}
