package nameerror.skillbuilder.Fundamental.StatusEffect;
import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;

public abstract class StatusEffect {
    /**
     * Definition: status effect that effect the entity and Matter
     *
     * there are 2 types of mechanism
     * 1. applied for some period
     * 2. Trigger-based (conditional)
     */

    private Matter applier;
    private LegacyEntity victim;
    private int duration; // tick
    private int interval; // tick
    private int level;
    private int stack;

    private int tickCounter = 0; // tick
    private boolean expired = false;

    public StatusEffect(Matter applier, LegacyEntity victim, int ticks, int interval, int level, int stack) {
        this.applier = applier;
        this.victim = victim;
        this.duration = ticks;
        this.interval = interval;
        this.level = level;
        this.stack = stack;
    }

    public Matter getApplier() {
        return applier;
    }

    public LegacyEntity getVictim() {
        return victim;
    }

    public double getDuration() {
        return duration;
    }

    public int getIntervalTicks() { return interval; }

    public int getLevel() {
        return level;
    }

    public int getStack() {
        return stack;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setApplier(Matter applier) {
        this.applier = applier;
    }

    public void setVictim(LegacyEntity victim) {
        this.victim = victim;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIntervalTicks(int tick) {
        this.interval = tick;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public void setExpired(boolean b) { this.expired = b; }

    public void addLevel(int level) {
        this.level += level;
    }

    public void addStack(int stack) {
        this.stack += stack;
    }


    public void step() {
        if (victim.getEntity().isDead()) {
            expired = true;
        }

        if (!isExpired() && tickCounter % interval == 0) {
            trigger();
        }

        this.tickCounter++;
        if (tickCounter > duration) {
            expired = true;
        }
    }

    public abstract void trigger();
}
