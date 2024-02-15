package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.AccelerationField;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AccerelationFieldTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public AccerelationFieldTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        functionMap.put("convergent", this::convergentTest); // passed
        functionMap.put("divergent", this::divergentTest);
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }

    public Integer convergentTest(Player player) {
        Vector init_loc = player.getLocation().toVector();

        AccelerationField field = new AccelerationField(
//                law of universal gravitation
                (Vector posVector) -> {
                    Vector dff = init_loc.clone().subtract(posVector);
                    if (dff.isZero()) {
                        return new Vector(0, 0, 0);
                    }
                    return dff.normalize().multiply(posVector.distanceSquared(init_loc) > 4.0 ?
                            100.0 / posVector.distanceSquared(init_loc) : 0);
                });
        Sphere sphere = new Sphere(player.getLocation(), 20);
        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), () -> field.apply(sphere), 0, 1);
        return 0;
    }

    public Integer divergentTest(Player player) {
        Vector init_loc = player.getLocation().toVector();

        AccelerationField field = new AccelerationField(
//                law of universal gravitation
                (Vector posVector) -> {
                    Vector dff = posVector.clone().subtract(init_loc);
                    if (dff.isZero()) {
                        return new Vector(0, 0, 0);
                    }
                    return dff.normalize().multiply(posVector.distanceSquared(init_loc) > 1.0 ?
                            500.0 / posVector.distance(init_loc) : 0);
                });
        Sphere sphere = new Sphere(player.getLocation(), 20);
        field.apply(sphere);
        return 0;
    }
}
