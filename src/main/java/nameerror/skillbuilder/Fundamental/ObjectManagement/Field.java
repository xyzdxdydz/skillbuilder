package nameerror.skillbuilder.Fundamental.ObjectManagement;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public abstract class Field extends Matter {
    /**
    Definition: and area of effect (AoE) that take and actions overtime instead of activate only once
    This is different from run AoE in SequenceRunner that they can handle Triggers and more unique properties.
    - Theoretically continuous in time (interpolating between time step)
    - Size & shape dynamic;
    - Can move around or move along with entity (AoE can't do that)
     */

    private Entity owner; // TODO; change to matter
    protected SetSpace setSpace;

    private ArrayList<Entity> ignoreEntities = new ArrayList<>();
    private ArrayList<Block> ignoreBlocks = new ArrayList<>();
    // TODO; use filter class for two of these (Type or specific or tag or bla bla)
    // entities.removeIf(e -> Filter.meetCondition(e));

    private boolean enable = true;
    private boolean ignoreOwnerFlag = false;
    private boolean applyToEntities = false;
    private boolean applyToBlocks   = false;

    public Field(Location location, SetSpace setSpace) {
        super(location);
        this.setSpace = setSpace;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public void setApplyToEntities(boolean b) {
        this.applyToEntities = b;
    }
    public void setApplyToBlocks(boolean b) {
        this.applyToBlocks = b;
    }

    public void setIgnoreOwner(boolean b) {
        this.ignoreOwnerFlag = b;
    }

    public void setIgnoreEntities(ArrayList<Entity> ignoreList) {
        this.ignoreEntities = ignoreList;
    }

    public void setIgnoreBlock(ArrayList<Block> ignoreList) {
        this.ignoreBlocks = ignoreList;
    }

    public void step() {// TODO; change API (ambiguous)
        // TODO; add hook for trigger event
        if (enable) {
            if (applyToEntities) {
                ArrayList<Entity> entities = setSpace.findEntities(false);

                for (Entity e: entities) {
                    if (!ignoreEntities.contains(e)) {
                        if (e.equals(owner) && ignoreOwnerFlag)
                            continue;
                        applyToEntity(e);
                    }
                }
            }

            if (applyToBlocks) {
                ArrayList<Block> blocks = setSpace.findBlocks(false);

                for (Block b: blocks) {
                    if (!ignoreBlocks.contains(b)) {
                        applyToBlock(b);
                    }
                }
            }
        }
    }

//    Do something
    // TODO; pass config to these two
    public void applyToEntity(Entity entity) { }

    public void applyToBlock(Block block) { }

    // TODO; hook call API
}
