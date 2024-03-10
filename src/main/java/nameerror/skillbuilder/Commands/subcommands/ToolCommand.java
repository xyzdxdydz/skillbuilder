package nameerror.skillbuilder.Commands.subcommands;

import nameerror.skillbuilder.Commands.SubCommandManager;
import nameerror.skillbuilder.Fundamental.MetaManager;
import nameerror.skillbuilder.Utils.DevTools.EntityGrid;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if (args.length == 2) {
            if (args[1].equals("status_effect_checker")) {
                Player player = (Player) sender;
                ItemStack itemStack = new ItemStack(Material.BLAZE_ROD, 1);

                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("Status Doctor");

                itemStack.setItemMeta(itemMeta);

                player.getInventory().addItem(itemStack);
                player.sendMessage("Added status effect checker to your inventory." +
                        " Right click 'Status doctor' to any entity to check status effects");
            }

        } else if (args.length == 4) {
            if (args[3].equals("")) {
                sender.sendMessage(Verbose.invalidCommandSyntax());
                return;
            }

            switch (args[1]) {
                case "creeper_grid":
                    EntityGrid.makeGrid(((Player) sender).getLocation(), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    sender.sendMessage("Tool been has run");

                    break;
                case "manager":
                    String result = MetaManager.op(args[3], args[2]);

                    sender.sendMessage("Tool been has run.");
                    sender.sendMessage("Status: " + result);

                    break;
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 2) {
            result.add("creeper_grid");
            result.add("manager");
            result.add("status_effect_checker");

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
