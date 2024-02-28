package nameerror.skillbuilder.Testing;

import nameerror.skillbuilder.Testing.TestFeature.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Test {
    private static Map<String, TestModule> testTable = new HashMap<>();

    public static void registerTest() {
        testTable.put("movement_tracking", new TrackedMatterTest());
        testTable.put("vector_mgmt", new VectorManagerTest());
        testTable.put("floating_block", new FloatingBlockTest());
        testTable.put("field", new FieldTest());
        testTable.put("building", new BuildingTest());
        testTable.put("block_manipulate", new BlockManipulationTest());
        testTable.put("world_manipulate", new WorldManipulationTest());
        testTable.put("domain", new DomainExpansionTest());
        testTable.put("status_effect", new StatusEffectTest());
        testTable.put("projectile", new ProjectileTest());
        testTable.put("particle", new ParticleTest());
    }

    public static ArrayList<String> getTestModules() {
        return new ArrayList<>(testTable.keySet());
    }

    public static ArrayList<String> getTestCases(String name) {
        return testTable.containsKey(name) ? testTable.get(name).getTestCases() : new ArrayList<>();
    }

    public static TestModule get(String name) {
        return testTable.getOrDefault(name, null);
    }
}
