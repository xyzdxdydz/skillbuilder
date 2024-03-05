package nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class PlayerControl implements Listener {

    // explicit moves
    @EventHandler(priority = EventPriority.LOWEST)
    // only flying
    public void controlPlayerFly(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_FLY)) {
            player.setFlying(false);
//            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void controlPlayerGliding(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_GLIDING)) {
                player.setGliding(false);
//                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void controlPlayerRun(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_RUN)) {
            player.setSprinting(false);
//            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void controlPlayerSneak(EntityToggleSwimEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_SWIM)) {
                player.setSwimming(false);
//                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void controlPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_SNEAK)) {
            player.setSneaking(false);
//            event.setCancelled(true);
        }
    }

    // TODO; add on teleport (careful with movement tracking)
    // TODO; create movement manager (water, air, lava, etc.)
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (CrowdControlManager.isInflictCC(player)) {

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_FLY) && player.isFlying()) {
                player.setFlying(false);
            }

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_GLIDING) && player.isGliding()) {
                player.setGliding(false);
            }

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_RUN) && player.isSprinting()) {
                player.setSprinting(false);
            }

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_SWIM) && player.isSwimming()) {
                player.setSwimming(false);
            }

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_SNEAK) && player.isSneaking()) {
                player.setSneaking(false);
            }

            // implicit moves
            // include player.isRiptiding();
            if (CrowdControlManager.entityContainsCC(player,ControlType.DISABLE_WALK) ||
                    CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_DASH)) {
                Location prevLoc = event.getFrom();
                Location currentLoc = event.getTo().clone();

                // prevent jumping
                if (currentLoc.getY() > prevLoc.getY()) {
                    currentLoc.setY(prevLoc.getY());
//                    Vector velocity = player.getVelocity();
//                    velocity.setY(0);
//                    player.setVelocity(velocity);
                }

                currentLoc.setX(prevLoc.getX());
                currentLoc.setZ(prevLoc.getZ());

                event.setTo(currentLoc);

                // prevent boarder stutter
                player.setVelocity(player.getVelocity());
            }

            if (CrowdControlManager.entityContainsCC(player, ControlType.DISABLE_LOOK)) {
                Location prevLoc = event.getFrom();
                Location currentLoc = event.getTo().clone();

                currentLoc.setPitch(prevLoc.getPitch());
                currentLoc.setYaw(prevLoc.getYaw());

                event.setTo(currentLoc);
            }
        }

//        note:
//            player.isSilent(); // confuse
//            player.isCollidable(); // confuse
//            player.isPlayerTimeRelative(); // what
//            player.isValid();
    }
 }
