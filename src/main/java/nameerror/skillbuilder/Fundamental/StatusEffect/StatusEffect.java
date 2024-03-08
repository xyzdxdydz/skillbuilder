package nameerror.skillbuilder.Fundamental.StatusEffect;
import nameerror.skillbuilder.Fundamental.Triggers;
import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;

public abstract class StatusEffect implements Triggers {
    /**
     * Definition: status effect that effect the entity and Matter
     *
     * there are 2 types of mechanism
     * 1. applied for some period
     * 2. Trigger-based (conditional)
     *
     * Limitation priority:
     * 1. Only-one, no matter other entity inflict same name of status effect (can implement mutual stack)
     * 2. Multiple, one for each applier
     * 3.
     * 4. Any
     */
    private LegacyEntity victim;

    private String name; // tick
    private Matter applier;
    private int priority = 255; // 0 is highest
    private int duration; // tick
    private int interval; // tick
    private int level;
    private int stack;

    private int tickCounter = 0; // tick
    private boolean expired = false;

    public StatusEffect(Matter applier, LegacyEntity victim, String name, int ticks, int interval, int level, int stack) {
        this.applier = applier;
        this.victim = victim;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }

    public int getPriority() {
        return priority;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setIntervalTicks(int tick) {
        this.interval = tick;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStack(int stack, boolean triggerEvent) {
        this.stack = stack;
        if (triggerEvent) {
            onStackChange();
        }
    }

    public void setExpired(boolean b) { this.expired = b; }

    public void addLevel(int level) {
        this.level += level;
    }

    public void addStack(int stack, boolean triggerEvent) {
        this.stack += stack;
        if (triggerEvent) {
            onStackChange();
        }
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

    public void onStackChange() { }

    public void onExpired() { }
}
