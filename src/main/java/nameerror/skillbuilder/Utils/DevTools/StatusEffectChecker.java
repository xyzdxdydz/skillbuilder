package nameerror.skillbuilder.Utils.DevTools;

import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffect;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class StatusEffectChecker implements Listener {

    @EventHandler
    public void checkStatusEffects(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (!itemStack.getType().equals(Material.AIR)) {
            String itemName = itemStack.getItemMeta().getDisplayName();

            // click with main hand
            if (event.getHand().equals(EquipmentSlot.HAND) && itemName.equals("Status Doctor")) {
                Entity victim = event.getRightClicked();
                HashSet<StatusEffect> ee =  StatusEffectManager.getStatusEffect(victim);
                ArrayList<String> eeName = new ArrayList<>();

                ee.forEach(se -> eeName.add(se.getName()));
                player.sendMessage("==================== " +
                        ChatColor.GREEN + "Status doctor" +
                        ChatColor.RESET + " ====================");
                player.sendMessage("Entity: " + ChatColor.GREEN + victim.getType().getTranslationKey());
                player.sendMessage("Status effects: "  + eeName);
            }
        }

    }
}
