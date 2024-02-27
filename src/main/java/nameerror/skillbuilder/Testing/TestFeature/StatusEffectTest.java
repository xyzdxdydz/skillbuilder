package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowControl.Snare;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatusEffectTest extends TestModule {

    public StatusEffectTest() { initTest(); }

    private void initTest() {
        this.addTestCase("cc_snare", this::CCSnareTest);
    }

    public Integer CCSnareTest(Player player) {
        Snare.addSnared(player);
        player.sendMessage("A 'snared' effect will be removed in next 10 seconds.");
        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            Snare.removeSnared(player);
        }, 20 * 10);

        return 0;
    }

}
