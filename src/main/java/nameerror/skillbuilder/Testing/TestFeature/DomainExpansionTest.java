package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Fundamental.ObjectManagement.FieldManager;
import nameerror.skillbuilder.Fundamental.ObjectManagement.InnateDomain;
import nameerror.skillbuilder.Fundamental.ObjectManagement.SolidBarrier;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.Math.Shape.WholeWorld;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import nameerror.skillbuilder.Utils.BlockRestorer;
import nameerror.skillbuilder.Utils.FreezePositionField;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DomainExpansionTest extends TestModule {
    public DomainExpansionTest() {
        initTest();
    }

    private void initTest() {
        // Add function references to the map
        this.addTestCase("basic", this::domainExpansion); // passed
    }

    public Integer domainExpansion(Player player) {
        int domainTime = 20; // sec
        int radius = 20;
        Sphere sphere = new Sphere(player.getLocation(), radius);
        ArrayList<Entity> nearbyEntities = sphere.findEntities(false);

        // clear area
        player.sendMessage("Clearing area.");
        ArrayList<Block> nearByBlocks = sphere.findBlocks(false);
        BlockRestorer blockRestorer = new BlockRestorer(player.getWorld());
        blockRestorer.add(nearByBlocks);
        nearByBlocks.forEach(block -> block.setType(Material.AIR));
        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            player.sendMessage("All blocks has been restored.");
            blockRestorer.restoreAll();
        }, 20 * domainTime +1);

        // deploy barrier
        player.sendMessage("Deploy barrier.");
        SolidBarrier solidBarrier = new SolidBarrier(player, sphere);
        solidBarrier.setAge(20 * domainTime);
        solidBarrier.setMaterial(Material.WHITE_CONCRETE);
        solidBarrier.build();
        Bukkit.getServer().getScheduler().runTaskTimer(SkillBuilder.getPlugin(), solidBarrier::step, 0, 1);

        // teleport with relative
        InnateDomain domain = new InnateDomain(player, "domain_infinite_void");
        domain.expand();
        domain.bring(nearbyEntities);
        // world field
        FreezePositionField field = new FreezePositionField(new WholeWorld(domain.getWorld().getSpawnLocation()));
        field.setOwner(player);
        field.setApplyToEntities(true);
        field.setIgnoreOwner(true);

        FieldManager.register(field);

        // set no grav, ai
        for (Entity e: nearbyEntities) {
            e.setGravity(false);
            if (e instanceof LivingEntity) {
                ((LivingEntity) e).setAI(false);
            }
        }

        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            for (Entity e: nearbyEntities) {
                e.setGravity(true);
            }
            domain.collapse();

            FieldManager.remove(field);
            MovementTrackingHandler.remove(field);
        }, 20 * domainTime);

        return 1;
    }
}
