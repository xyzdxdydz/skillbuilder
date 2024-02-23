package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Testing.TestModule;
import nameerror.skillbuilder.Utils.FloatingBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FloatingBlockTest extends TestModule {

    public FloatingBlockTest() {
        initTest();
    }

    /**
     * Expected behavior
     * The complete version of each feature must get 'passed' on both 'basic' and 'advanced'
     * basic: // passed
     *      Basic: // passed
     *          1. falling block 5x5 around player should turn to FallingBlock and fly up to the air
     *      Advanced: // passed
     *          - no additional information -
     * basic_with_block_data: // passed
     *      Basic: // passed
     *          1. falling block 5x5 around player should turn to FallingBlock and fly up to the air
     *          2. capture data of orientation of each block
     *      Advanced: // passed
     *          - no additional information -
     */
    private void initTest() {
        // Add function references to the map
        this.addTestCase("basic", this::floatingBlockTest); // passed
        this.addTestCase("basic_with_block_data", this::floatingBlockTestWithBlockData); // passed`
    }

    private void floatingBlockAroundPlayer(Player player, boolean blockData) {
        for (int x=-2; x<=2; x++) {
            for (int z=-2; z<=2; z++) {
                Block block = player.getLocation().clone().add(new Vector(x, -1, z)).getBlock();
                FallingBlock fb = FloatingBlock.makeFloatBlock(block, blockData);
                if (fb != null)
                    fb.setVelocity(new Vector(0, 5/Math.sqrt(x*x + z*z + 1),0));
            }
        }
    }

    // TODO; keep data in block (chest, cmd block)

    private Integer floatingBlockTest(Player player) {
        floatingBlockAroundPlayer(player, false);
        return 0;
    }

    private Integer floatingBlockTestWithBlockData(Player player) {
        floatingBlockAroundPlayer(player, true);
        return 0;
    }
}
