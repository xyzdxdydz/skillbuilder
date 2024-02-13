package nameerror.skillbuilder.Animations.CustomParticle;

import org.bukkit.Particle;

public class ParticlePackage implements Cloneable {
    private final Particle particle;
    private final int count;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final float extra;
    private final Object data;
    private final boolean force;

    public ParticlePackage(Particle particle, int count, float offsetX, float offsetY, float offsetZ, float extra, Object data, boolean force) {
        this.particle = particle;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.extra = extra;
        this.data = data;
        this.force = force;
    }

    public Particle getParticle() {return this.particle; }
    public int getCount() {return this.count; }
    public float getOffsetX() {return this.offsetX;}
    public float getOffsetY() {return this.offsetY;}
    public float getOffsetZ() {return this.offsetZ;}
    public float getExtra() {return this.extra; }
    public Object getData() {return this.data; }
    public boolean getForce() {return force; }

    @Override
    public ParticlePackage clone() {
        try {
            return (ParticlePackage) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
