package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.Arrays;

// An extended version of projectile hit
public class ProjectileHitInfo {
    AbstractProjectile projectile;
    ArrayList<Entity> hitEntities;
    ArrayList<Block> hitBlocks;
    BlockFace hitBlocksFace;

    public ProjectileHitInfo(ProjectileHitEvent event) {
        projectile = ProjectileManager.getAbstractProjectile(event.getEntity());
        hitEntities = new ArrayList<>(Arrays.asList(event.getHitEntity()));
        hitBlocks = new ArrayList<>(Arrays.asList(event.getHitBlock()));
        hitBlocksFace = event.getHitBlockFace();
    }
}
