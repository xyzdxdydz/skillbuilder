package nameerror.skillbuilder.Testing;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class TestModule {

    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public void addTestCase(String testName, Function<Player, Integer> testFunction) {
        functionMap.put(testName, testFunction);
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) {
        functionMap.get(testCaseName).apply(requester);
    }
}
