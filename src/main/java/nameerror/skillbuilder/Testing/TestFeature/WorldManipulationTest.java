package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Testing.TestModule;
import nameerror.skillbuilder.Utils.DomainCreator;
import org.bukkit.entity.Player;

public class WorldManipulationTest extends TestModule {

    public WorldManipulationTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        this.addTestCase("create", this::createWorld);
    }

    private Integer createWorld(Player player) {
        new DomainCreator("domain_infinite_void").create();
        return 1;
    }

}
