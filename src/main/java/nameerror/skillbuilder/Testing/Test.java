package nameerror.skillbuilder.Testing;

import nameerror.skillbuilder.Testing.TestModule.*;

import java.util.*;

public class Test {
    private static Map<String, TestModuleTemplate> testTable = new HashMap<>();

    public static void registerTest() {
        testTable.put("movement_tracking", new TrackedMatterTest());
        testTable.put("vector_mgmt", new VectorManagerTest());
        testTable.put("floating_block", new FloatingBlockTest());
        testTable.put("field", new FieldTest());
        testTable.put("block_manipulate", new BlockManipulationTest());
    }

    public static ArrayList<String> getTestModules() {
        return new ArrayList<>(testTable.keySet());
    }

    public static ArrayList<String> getTestCases(String name) {
        return testTable.containsKey(name) ? testTable.get(name).getTestCases() : new ArrayList<>();
    }

    public static TestModuleTemplate get(String name) {
        return testTable.getOrDefault(name, null);
    }
}
