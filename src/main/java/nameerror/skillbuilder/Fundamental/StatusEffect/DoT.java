package nameerror.skillbuilder.Fundamental.StatusEffect;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.EntityPlus.LegacyEntity;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DoT extends Debuff {

    private double baseDamage;

    public DoT(Matter applier, LegacyEntity victim, String name, int ticks, int interval, int level, int stack, double baseDamage) {
        super(applier, victim, name, ticks, interval, level, stack);
        this.baseDamage = baseDamage;
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(double baseDPS) {
        this.baseDamage = baseDPS;
    }

    public double getBaseDPS() {
        float tickRate = Bukkit.getServerTickManager().getTickRate();
        return baseDamage * tickRate / this.getIntervalTicks();
    }

    @Override
    public void trigger() {
        Entity entity = this.getVictim().getEntity();

        if (entity instanceof LivingEntity && !entity.isDead()) {
            // TODO; separate damage calculation module e.g., getActualDamage(applier, victim, baseDPS)
            double damage =  baseDamage;
            ((LivingEntity) entity).damage(damage);
            ((LivingEntity) entity).setNoDamageTicks(0);
            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2, 2);
        }
    }

    @Override
    public void onStackChange() {

    }

}
