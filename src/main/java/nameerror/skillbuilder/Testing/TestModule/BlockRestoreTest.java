package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.BlockRestorer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockRestoreTest extends TestModuleTemplate {
    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public BlockRestoreTest() {
        initTest();
    }

    /**
     * Expected behavior
     * The complete version of each feature must get 'passed' on both 'basic' and 'advanced'
     * basic: // passed
     *      Basic: // passed
     *          1. All blocks reverted with original type after removed.
     *      Advanced: // passed
     *          1. The new change within that area will be reverted.
     *          2. The facing on every block will be the same after reverted.
     *          3. Item in chest (include Shulker), ordering will be the same.
     *          4. The item stack and metadata will be the same.
     *          5. Block state (Door, redstone) will be the same
     *          6. Beacon buff will be re-activated without doing anything
     *          7. Flower was not destroyed after reverted.
     *          8. Item still soaked in water after reverted.
     *          9. Still water logging (remain the same) after reverted.
     *          10. Water have the same shape (remain the same) after reverted.
     *          11. Lantern should hang (remain the same) after reverted.
     *          12. Command block data remain the same
     *          13. Light block remain the same after reverted.
     *          14. Barrier block remain the same after reverted.
     */
    private void initTest() {
        // Add function references to the map
        functionMap.put("basic", this::basic);
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }

    private int basic(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 10);
        ArrayList<Block> nearByBlocks = sphere.findBlocks(false);
        BlockRestorer blockRestore = new BlockRestorer(player.getWorld());
        blockRestore.add(nearByBlocks);
        nearByBlocks.forEach(block -> block.setType(Material.AIR));

        player.sendMessage("all blocks will be restored in 10 seconds.");
        Bukkit.getServer().getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            player.sendMessage("All blocks has been restored.");
            blockRestore.restoreAll();
        }, 20 * 10);
        return 1;
    }
}
