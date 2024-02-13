package nameerror.skillbuilder.EventListener;

import nameerror.skillbuilder.CustomEvents.SkillLaunchEvent;
import nameerror.skillbuilder.Fundamental.PluginEntityType;
import nameerror.skillbuilder.Verbose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillListener implements Listener {

    @EventHandler
    public void onLaunch(SkillLaunchEvent event) {
        if (event.getOwner().getCasterType().equals(PluginEntityType.PLAYER)) {
            event.getOwner().getGameEntity().sendMessage(Verbose.skillLaunchSuccess());
        }
    }
}
