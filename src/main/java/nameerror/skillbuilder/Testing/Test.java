package nameerror.skillbuilder.Testing;

import nameerror.skillbuilder.Testing.TestModule.TrackedMatterTest;
import nameerror.skillbuilder.Testing.TestModule.VectorManagerTest;

import java.util.*;
import java.util.function.Supplier; // receive no args.

public class Test {
    private static Map<String, TestModuleTemplate> testTable = new HashMap<>();

    public static void registerTest() {
        testTable.put("movement_tracking", new TrackedMatterTest());
        testTable.put("vector_mgmt", new VectorManagerTest());
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
