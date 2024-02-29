package nameerror.skillbuilder.Fundamental.StatusEffect;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;

public abstract class Debuff extends StatusEffect {
    public Debuff(Matter applier, LegacyEntity victim, int ticks, int interval, int level, int stack) {
        super(applier, victim, ticks, interval, level, stack);
    }
}
