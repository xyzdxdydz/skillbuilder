package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.AccelerationField;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }

    public Integer convergentTest(Player player) {
        Double x_init = player.getLocation().getX();
        Double y_init = player.getLocation().getY();
        Double z_init = player.getLocation().getZ();
        AccelerationField field = new AccelerationField(
                x -> (x_init - x),
                y -> (y_init - y),
                z -> (z_init - z)
        );
        Sphere sphere = new Sphere(player.getLocation(), 20);
        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), () -> field.apply(sphere), 0, 1);
        return 0;
    }
}
