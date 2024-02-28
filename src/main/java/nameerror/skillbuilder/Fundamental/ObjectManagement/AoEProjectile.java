package nameerror.skillbuilder.Fundamental.ObjectManagement;

import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class AoEProjectile extends SBProjectile {
    private SetSpace setSpace;

    public AoEProjectile(SetSpace setSpace) {
        super(setSpace.getLocation());
        this.setSpace = setSpace;
    }


    @Override
    public boolean teleport(Location location) {
        setSpace.setLocation(location);
        return super.teleport(location);
    }

    @Override
    public void onLaunch() {

    }

    @Override
    public void onHit() {

    }

    @Override
    public void onFly() {
        ArrayList<Block> blocks = setSpace.findBlocks(false);
        ArrayList<Entity> entities = setSpace.findEntities(false);

        blocks.forEach(block -> block.setType(Material.AIR));
        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).damage(10);
                ((LivingEntity) entity).setNoDamageTicks(0);
            }
        });
    }
}
