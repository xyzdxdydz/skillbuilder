package nameerror.skillbuilder.Animations.CustomParticle;


import nameerror.skillbuilder.Math.VectorManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import static java.lang.Math.*;

public class BasicShape {

    private Location location;
    private Vector vectorAxis;

    private Particle particle;
    private int count;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float extra;
    private Object data;
    private boolean force;

    // Create rectangular from normal vector
    public BasicShape(Location initialLocation) {
        this.location = initialLocation;
        this.vectorAxis = initialLocation.getDirection();
    }

    public void setVectorAxis(Vector vector) {this.vectorAxis = vector;}

    public void setLocation(Location location) {this.location = location;}

    public void setParticle(ParticleMaker particlePackage) {
        this.particle = particlePackage.getParticle();
        this.count = particlePackage.getCount();
        this.offsetX = particlePackage.getOffsetX();
        this.offsetY = particlePackage.getOffsetY();
        this.offsetZ = particlePackage.getOffsetZ();
        this.extra = particlePackage.getExtra();
        this.data = particlePackage.getData();
        this.force = particlePackage.getForceDisplay();
    }

    @Deprecated
    public void createLine(Location destination, int samplePerBlock) {
        Vector direction = destination.clone().toVector().subtract(location.clone().toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        double distance = location.distance(destination);
        double intervalDistance = 1.0 / samplePerBlock;

        Vector adderVector = direction.clone().multiply(intervalDistance);
        Location it = location.clone();

        for (double i = 0; i < distance; i += intervalDistance) {
            it.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);
            it.add(adderVector);
        }
    }

    public void createSineWave(Location destination, double amplitude, double frequency, double phaseShift, double rotation, int samplePerBlock) {
        Vector direction = destination.clone().toVector().subtract(location.clone().toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        Vector rotator = VectorManager.getLeftVectorByReference(direction).multiply(-amplitude);
        if (rotator.equals(new Vector(0,0,0))) {
            rotator = new Vector(1,0,0);
        }

        Vector waveVector = VectorManager.getLeftVectorByReference(direction);
        if (waveVector.equals(new Vector(0,0,0))) {
            waveVector = new Vector(1,0,0);
        }

        waveVector = VectorManager.rotateDCWAboutVector(waveVector, direction, (float) -rotation);

        rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) -phaseShift);
        double distance = location.distance(destination);
        double intervalPhase = 360.0 / (4 * samplePerBlock * max(1, amplitude));
        double intervalDistance = 1.0 / (4 * samplePerBlock * max(1, amplitude) * frequency);

        Location locator = location.clone();
        Vector intervalVector = direction.clone().multiply(intervalDistance);

        for (double j = 0; j <= distance; j += intervalDistance) {
            Location it = locator.clone().add(waveVector.clone().multiply(rotator.clone().dot(waveVector)));
            locator.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);

            rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) intervalPhase);
            locator = locator.add(intervalVector);
        }
    }

    public void createPolygon(double size, int blend, float normalRotation, int samplePerBlock) {
        Vector leftByRef = VectorManager.getLeftVectorByReference(vectorAxis);
        if (leftByRef.equals(new Vector(0,0,0))) {
            leftByRef = new Vector(1,0,0);
        }

        leftByRef = VectorManager.rotateDCWAboutVector(leftByRef, vectorAxis, normalRotation).normalize().multiply(-1);

        double interval   = 1.0 / samplePerBlock;
        double rotator    = 360.0 / blend;
        double baseLength = size * tan(Math.toRadians(rotator / 2));
        double amount = 2 * baseLength * samplePerBlock;

        Location locIterator = location.clone().add(leftByRef.clone().multiply(size));
        Vector adderVector   = VectorManager.rotateDCWAboutVector(leftByRef, vectorAxis, -90);
        locIterator.add(adderVector.clone().multiply(-baseLength));
        adderVector.multiply(interval);

        for (int i = 0; i < blend; i++) {
            for (int j = 0; j < amount; j++) {
                locIterator.getWorld().spawnParticle(particle, locIterator, count, offsetX, offsetY, offsetZ, extra, data, force);
                locIterator.add(adderVector);
            }
            adderVector = VectorManager.rotateDCWAboutVector(adderVector, vectorAxis, (float) -rotator);
        }
    }

    @Deprecated
    public void createCircle(double size, int samplePerBlock) {
        Vector adderVector = VectorManager.getLeftVectorByReference(vectorAxis);
        if (adderVector.equals(new Vector(0,0,0))) {
            adderVector = new Vector(1,0,0);
        }

        Location locIterator = location.clone().add(adderVector.multiply(-size));

        double interval = 360.0 / (4 * samplePerBlock * max(1, size));
        double amount = 4 * size * samplePerBlock;

        for (int j = 0; j < amount; j++) {
            locIterator.getWorld().spawnParticle(particle, locIterator, count, offsetX, offsetY, offsetZ, extra, data, force);
            adderVector = VectorManager.rotateDCWAboutVector(adderVector, vectorAxis, (float) interval);
            locIterator = location.clone().add(adderVector);
        }
    }

    public void createEclipse(double sizeA, double sizeB, double normalRotation, int samplePerBlock) {
        Vector adderVector = VectorManager.getLeftVectorByReference(vectorAxis);
        if (adderVector.equals(new Vector(0,0,0))) {
            adderVector = new Vector(1,0,0);
        }

        sizeA = abs(sizeA);
        sizeB = abs(sizeB);

        if (sizeA == sizeB) {
            createCircle(sizeA, samplePerBlock);
            return;
        }

        double currentSize = sizeA;
        double interval;

        adderVector = VectorManager.rotateDCWAboutVector(adderVector, vectorAxis, (float) normalRotation).multiply(-1);
        Location locIterator = location.clone().add(adderVector.clone().multiply(sizeA));

//        j = currentRotation
        for (double j = 0; j < 360; j += interval) {
            locIterator.getWorld().spawnParticle(particle, locIterator, count, offsetX, offsetY, offsetZ, extra, data, force);

            interval = 360.0 / (4 * samplePerBlock * max(1, currentSize)) / (1 + abs(sin(2 * j)));
            currentSize = sizeA * sizeB / sqrt(pow(sizeB, 2) + (pow(sizeA, 2) - pow(sizeB, 2)) * pow(sin(toRadians(j + interval)), 2));

//            Optimal
            adderVector = VectorManager.rotateDCWAboutVector(adderVector, vectorAxis, (float) interval);
            locIterator = location.clone().add(adderVector.clone().multiply(currentSize));
        }
    }

    public void createSpiral(double size, double revPerBlock, double phaseShift, boolean CCW, int samplePerBlock) {
        Vector rotator = VectorManager.getRightVectorByReference(vectorAxis);
        if (rotator.equals(new Vector(0,0,0))) {
            rotator = new Vector(1,0,0);
        }

        rotator = VectorManager.rotateDCWAboutVector(rotator, vectorAxis, (float) phaseShift);

        int rotateDirection = 1;

        if (CCW)
            rotateDirection = -1;

        double multiplier = 1 / (2*PI) / revPerBlock;
        double degreeStop = 360 * size * revPerBlock;
        double intervalPhase = 0;
        Location locIterator = location.clone();


        for (double j = 0; j <= degreeStop; j += intervalPhase) {
            locIterator.getWorld().spawnParticle(particle, locIterator, count, offsetX, offsetY, offsetZ, extra, data, force);
            intervalPhase = 360.0 / (4 * samplePerBlock * max(1, j * PI/180 * multiplier));
            rotator = VectorManager.rotateDCWAboutVector(rotator, vectorAxis, (float) intervalPhase * rotateDirection);
            locIterator = location.clone().add(rotator.clone().multiply(multiplier * j * PI/180));
        }
    }

    public void createHelicalCoil(Location destination, double size, double frequency, double phaseShift, boolean CCW, int samplePerBlock) {
        Vector direction = destination.clone().toVector().subtract(location.clone().toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        Vector rotator = VectorManager.getLeftVectorByReference(direction).multiply(-size);
        if (rotator.equals(new Vector(0,0,0))) {
            rotator = new Vector(1,0,0);
        }

        rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) -phaseShift);
        double distance = location.distance(destination);
        double intervalPhase = 360.0 / (4 * samplePerBlock * max(1, size));
        double intervalDistance = 1.0 / (4 * samplePerBlock * max(1, size) * frequency);

        if (CCW)
            intervalPhase *= -1;

        Location locator = location.clone();
        Vector intervalVector = direction.clone().multiply(intervalDistance);

        for (double j = 0; j <= distance; j += intervalDistance) {
            Location it = locator.clone().add(rotator);
            locator.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);
            rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) intervalPhase);
            locator = locator.add(intervalVector);
        }
    }

    public void createHelicalConedCoil(Location destination, double wideness, double frequency, double phaseShift, boolean CCW, int samplePerBlock) {
        Vector direction = destination.clone().toVector().subtract(location.clone().toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        Vector rotator = VectorManager.getLeftVectorByReference(direction).multiply(-1);
        if (rotator.equals(new Vector(0,0,0))) {
            rotator = new Vector(1,0,0);
        }

        int rotateDirection = 1;

        if (CCW)
            rotateDirection = -1;

        rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) -phaseShift);
        double distance = location.distance(destination);
        double currentSize = 0;
        double intervalPhase;
        double intervalDistance;

        Location locator = location.clone();
        Vector intervalVector = direction.clone();

        for (double j = 0.1; j <= distance; j += intervalDistance) {
            Location it = locator.clone().add(rotator.clone().multiply(currentSize));
            locator.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);

            currentSize = wideness * j;
            intervalPhase = 360.0 / (4 * samplePerBlock * max(1, currentSize) * rotateDirection);
            intervalDistance = 1.0 / (4 * samplePerBlock * max(1, currentSize) * frequency);

            rotator = VectorManager.rotateDCWAboutVector(rotator, direction, (float) intervalPhase);
            locator = locator.add(intervalVector.clone().multiply(intervalDistance));
        }
    }

    // make it rotate 360
    public void createParabola(Location destination, double size, double phaseShift, int samplePerBlock) {
        Vector direction = destination.clone().toVector().subtract(location.clone().toVector());
        if (!direction.equals(new Vector(0, 0, 0))) {
            direction.normalize();
        }

        Vector adderVector = VectorManager.getRightVectorByReference(direction);
        if (adderVector.equals(new Vector(0,0,0))) {
            adderVector = new Vector(1,0,0);
        }

        adderVector = VectorManager.rotateDCWAboutVector(adderVector, direction, (float) phaseShift);

        double currentWidth = 0;
        double intervalDistance;
        double distance = location.distance(destination);

        Location locator = location.clone();

        for (double j = 0; j <= distance; j += intervalDistance) {
            double k = 1;
            if (j < 0.5)
                k = max(1, 2 / sqrt(currentWidth + 0.01));

            Location it = locator.clone().add(adderVector.clone().multiply(currentWidth));
            locator.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);
            it = locator.clone().add(adderVector.clone().multiply(-currentWidth));
            locator.getWorld().spawnParticle(particle, it, count, offsetX, offsetY, offsetZ, extra, data, force);
            currentWidth = size * sqrt(j);
            intervalDistance = 1.0 / (samplePerBlock * k);
            locator.add(direction.clone().multiply(intervalDistance));
        }
    }

    // zig-zag wave, square coned coil, V-Expand
}
