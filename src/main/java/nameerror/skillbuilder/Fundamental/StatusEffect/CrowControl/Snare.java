package nameerror.skillbuilder.Fundamental.StatusEffect.CrowControl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

public class Snare implements Listener {
    private static Set<Player> snared = new HashSet<>();

    public static void addSnared(Player player) {
        snared.add(player);
    }

    public static void removeSnared(Player player) {
        snared.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (snared.contains(player)) {
            Location dest = event.getTo();
            float   nextYaw = dest.getYaw();
            float nextPitch = dest.getPitch();

            Location newDest = event.getFrom().clone();
            newDest.setYaw(nextYaw);
            newDest.setPitch(nextPitch);

            event.setTo(newDest);
        }
    }
 }
