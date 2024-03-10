package nameerror.skillbuilder.Fundamental.ObjectManagement.Building;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Utils.BlockRestorer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public abstract class Building extends Matter {
    private boolean enable = true;
    private long ageTick = Long.MAX_VALUE;
    private final BlockRestorer blockRestorer;

    /**
     Definition: Everything that have a composite of blocks and need to track from this plugin.
     */
    public Building(Location location) {
        super(location);
        blockRestorer = new BlockRestorer(location.getWorld());
    }

    public BlockRestorer getBlockRestorer() {
        return blockRestorer;
    }

    public void setAge(long tick) {
        this.ageTick = tick;
    }

    public void grow() {
        if (ageTick != Long.MAX_VALUE) {
            ageTick--;
        }
    }

    public Runnable step() {
        if (enable) {
            grow();
            if (ageTick == 0) {
                destruct();
                clean();
            }
        }

        return null;
    }


    public abstract void build();

    public abstract void destruct();

    public void clean()  {
        blockRestorer.restoreAll();
        Bukkit.broadcastMessage("all blocks hase been restored");
    }

    // TODO; override teleport
}
