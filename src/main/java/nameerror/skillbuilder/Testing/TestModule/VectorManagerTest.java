package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Testing.TestModuleTemplate;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class VectorManagerTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public VectorManagerTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        functionMap.put("left_reference", VectorManagerTest::LeftVectorReferenceTest); // passed
        functionMap.put("right_reference", VectorManagerTest::RightVectorReferenceTest); // passed
        functionMap.put("above_reference",  VectorManagerTest::AboveVectorReferenceTest); // passed
        functionMap.put("bottom_reference", VectorManagerTest::BottomVectorReferenceTest); // passed
        functionMap.put("front_reference", VectorManagerTest::FrontVectorReferenceTest); // passed
        functionMap.put("back_reference", VectorManagerTest::BackVectorReferenceTest); // passed
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) {
        functionMap.get(testCaseName).apply(requester);
    }

    private static Integer LeftVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(4, 0, 0));
        return 1;
    }

    private static Integer RightVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(-4, 0, 0));
        return 1;
    }

    private static Integer AboveVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 4, 0));
        return 1;
    }

    private static Integer BottomVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, -4, 0));
        return 1;
    }

    private static Integer FrontVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 0, 4));
        return 1;
    }

    private static Integer BackVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 0, -4));
        return 1;
    }
}
