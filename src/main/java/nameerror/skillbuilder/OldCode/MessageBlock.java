package nameerror.skillbuilder.OldCode;

import org.bukkit.configuration.ConfigurationSection;

public class MessageBlock {
    private final long initCooldown;
    private final long cooldown;
    private long lastCall = 0;
    private final ConfigurationSection sequenceSection;

    public MessageBlock(long initCooldown, long cooldown, ConfigurationSection sequenceSection) {
        this.initCooldown = initCooldown;
        this.cooldown = cooldown;
        this.sequenceSection = sequenceSection;

    }

    public ConfigurationSection response() {
        lastCall = System.currentTimeMillis();
        return sequenceSection;
    }

    public long getInitCooldown() {
        return initCooldown;
    }

    public long getCooldown() {
        return cooldown;
    }

    public long getLastCall() {
        return lastCall;
    }
}
