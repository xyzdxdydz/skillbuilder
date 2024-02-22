package nameerror.skillbuilder.Utils;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.Collection;

public class BlockRestorer {
    private ArrayList<BlockState> backups = new ArrayList<>();
    private World world;

    // TODO; make feature that keep change cause by player placing blocks
    // TODO; change it to SptialRestorer and implement itemFrame and Painting keeping feature
    public BlockRestorer(World world) {
        this.world = world;
    }

    public void add(Collection<Block> blocks) {
        for (Block b: blocks) {
            backups.add(b.getState());
        }
    }

    public void remove(Collection<BlockState> blockStates) {
        backups.clear();
    }

    public void restoreAll() {
        for (BlockState blockState: backups) {
            blockState.update(true, true);
        }
    }
}
