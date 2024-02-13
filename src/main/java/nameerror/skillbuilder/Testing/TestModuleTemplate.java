package nameerror.skillbuilder.Testing;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class TestModuleTemplate {
    public abstract ArrayList<String> getTestCases();

    public abstract void test(String testCaseName, Player requester);
}
