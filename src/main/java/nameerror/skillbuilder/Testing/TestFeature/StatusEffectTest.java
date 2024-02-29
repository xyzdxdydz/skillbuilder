package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;
import nameerror.skillbuilder.Fundamental.StatusEffect.DoT;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowControl.Snare;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;

public class StatusEffectTest extends TestModule {

    public StatusEffectTest() { initTest(); }

    private void initTest() {
        this.addTestCase("cc_snare", this::CCSnareTest);
        this.addTestCase("DoT_basic", this::basicDoT);
    }

    private Integer CCSnareTest(Player player) {
        Snare.addSnared(player);
        player.sendMessage("A 'snared' effect will be removed in next 10 seconds.");
        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            Snare.removeSnared(player);
        }, 20 * 10);

        return 0;
    }

    private Integer basicDoT(Player player) {
        Location spawnLoc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(5));
        Creeper creeper = player.getWorld().spawn(spawnLoc, Creeper.class);
        DoT dot = new DoT(LegacyEntity.get(player), LegacyEntity.get(creeper),
                20*8,20,1,1, 2);

        StatusEffectManager.applyEffect(creeper, dot);
        return 0;
    }
}
