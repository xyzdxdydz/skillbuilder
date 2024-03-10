package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Building.SolidBarrier;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BuildingTest extends TestModule {
    public BuildingTest() {
        initTest();
    }

    private void initTest() {
        // acceleration field
        this.addTestCase("place_barrier", this::placeBarrier); // passed
    }

    public Integer placeBarrier(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 20);
        SolidBarrier solidBarrier = new SolidBarrier(player, sphere);
        solidBarrier.setAge(20 * 10);
        solidBarrier.setMaterial(Material.WHITE_CONCRETE);
        solidBarrier.build();

        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), solidBarrier::step, 0, 1);
        return 0;
    }
}
