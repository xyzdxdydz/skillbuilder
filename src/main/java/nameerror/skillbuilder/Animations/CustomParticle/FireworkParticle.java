package nameerror.skillbuilder.Animations.CustomParticle;

import nameerror.skillbuilder.SkillBuilder;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.Field;

public class FireworkParticle {
    private FireworkEffect.Type fireworkType;
    private Color color;
    private boolean trail;
    private boolean flicker;

    public FireworkParticle(FireworkEffect.Type fireworkType, Color color, boolean trail, boolean flicker) {
        this.fireworkType = fireworkType;
        this.color = color;
        this.trail = trail;
        this.flicker = flicker;
    }

    public void spawn(Location location) {
        World world  = location.getWorld();
        FireworkEffect fwFx = FireworkEffect.builder().trail(trail).flicker(flicker).withColor(color).with(fireworkType).build();

        final Firework fw = world.spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(fwFx);
        fw.setMetadata("particle", new FixedMetadataValue(SkillBuilder.getPlugin(), true));
        // this equivalent to
        // meta.setPower(10);
        Field power;
        try {
            power = meta.getClass().getDeclaredField("power");
            power.setAccessible(true);
            power.set(meta, 0);

        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        fw.setFireworkMeta(meta);
        fw.detonate();
    }
}