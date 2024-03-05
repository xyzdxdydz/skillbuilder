package nameerror.skillbuilder.Fundamental.StatusEffect;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;

public abstract class Debuff extends StatusEffect {
    public Debuff(Matter applier, LegacyEntity victim, String name, int ticks, int interval, int level, int stack) {
        super(applier, victim, name, ticks, interval, level, stack);
    }
}
