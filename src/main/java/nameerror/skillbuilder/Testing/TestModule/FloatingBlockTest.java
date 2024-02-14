package nameerror.skillbuilder.Testing.TestModule;

import nameerror.skillbuilder.Testing.TestModuleTemplate;
import nameerror.skillbuilder.Utils.FloatingBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FloatingBlockTest extends TestModuleTemplate {

    private final Map<String, Function<Player, Integer>> functionMap = new HashMap<>();

    public FloatingBlockTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        functionMap.put("basic", this::floatingBlockTest); // passed
        functionMap.put("basic_with_block_data", this::floatingBlockTestWithBlockData); // passed`
    }

    public ArrayList<String> getTestCases() {
        return new ArrayList<>(functionMap.keySet());
    }

    public void test(String testCaseName, Player requester) { functionMap.get(testCaseName).apply(requester); }


    private void floatingBlockAroundPlayer(Player player, boolean blockData) {
        for (int x=-2; x<=2; x++) {
            for (int z=-2; z<=2; z++) {
                Block block = player.getLocation().clone().add(new Vector(x, -1, z)).getBlock();
                FallingBlock fb = FloatingBlock.makeFloatBlock(block, blockData);
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
