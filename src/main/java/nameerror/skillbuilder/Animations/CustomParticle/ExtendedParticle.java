package nameerror.skillbuilder.Animations.CustomParticle;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ExtendedParticle {

    public static void spawnColoredSpellParticle(Player player, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed, Color color) {

        double R = color.getRed() / 255.0;
        double G = color.getGreen() / 255.0;
        double B = color.getBlue() / 255.0;

        for (int i = 0; i < count; i++) {
            double ofX = Math.random() * 2 * offsetX - offsetX;
            double ofY = Math.random() * 2 * offsetY - offsetY;
            double ofZ = Math.random() * 2 * offsetZ - offsetZ;

            Location loc = location.clone().add(ofX, ofY, ofZ);

            // colored spell, count = 0, offset >> RGB
            if (player != null) {
                player.spawnParticle(Particle.SPELL_MOB, loc, 0, R, G, B, speed);

            } else {
                location.getWorld().spawnParticle(Particle.SPELL_MOB, loc, 0, R, G, B, speed);
            }
        }
    }

    public static void rayTraceDustParticle(Location start, Location end, int count,  int size, double offsetX, double offsetY, double offsetZ, double speed, Color color, boolean force) {
        World world = start.getWorld();
        Vector vector = start.getDirection().normalize().multiply(0.5);
        double distance = start.distance(end);

        boolean terminate = false;
        for (int i = 0; i <= 2 * Math.round(distance); i++) {
            start.add(vector);
            if (i % 2 == 0) {
                world.spawnParticle(Particle.REDSTONE, start, count, offsetX, offsetY, offsetZ, speed,
                        new Particle.DustOptions(color, size), force);
            }
        }
    }
}