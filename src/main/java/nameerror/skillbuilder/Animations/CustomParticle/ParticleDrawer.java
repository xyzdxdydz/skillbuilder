package nameerror.skillbuilder.Animations.CustomParticle;

import nameerror.skillbuilder.Math.VectorManager;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import static java.lang.Math.max;

public class ParticleDrawer {

    public static void createLine(Location start, Location stop, ParticleMaker particlePackage, int samplePerBlock) {
        Vector direction = stop.toVector().subtract(start.toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        start.setDirection(direction);

        double distance = start.distance(stop);
        createLine(start, distance, particlePackage, samplePerBlock);
    }

    public static void createLine(Location locationDoF5, double range, ParticleMaker particlePackage, int samplePerBlock) {
        double step = 1.0 / samplePerBlock;
        Vector stepVec = locationDoF5.getDirection().multiply(step);
        Location currentLoc = locationDoF5.clone();

        for (double i = 0; i <= range; i += step) {
            particlePackage.spawn(currentLoc);

            currentLoc.add(stepVec);
        }
    }

    public static void createCircle(Location locationDoF5, double size, ParticleMaker particlePackage, int samplePerBlock) {
        Vector normalVec = locationDoF5.getDirection();
        Vector rotVec = VectorManager.getLeftVectorByReference(locationDoF5).multiply(-size);

        double interval = 360.0 / (4 * samplePerBlock * max(1, size));
        double amount = 4 * size * samplePerBlock;

        for (int j = 0; j < amount; j++) {
            Location currentLocation = locationDoF5.clone().add(rotVec);
            particlePackage.spawn(currentLocation);
            rotVec = VectorManager.rotateDCWAboutVector(rotVec, normalVec, (float) interval);
        }
    }
}
