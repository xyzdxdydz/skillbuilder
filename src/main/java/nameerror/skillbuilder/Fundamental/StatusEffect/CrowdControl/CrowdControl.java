package nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl;

import nameerror.skillbuilder.Fundamental.Matter;
import nameerror.skillbuilder.Fundamental.EntityPlus.LegacyEntity;
import nameerror.skillbuilder.Fundamental.StatusEffect.Debuff;

import java.util.ArrayList;

public class CrowdControl extends Debuff {

    ArrayList<ControlType> controlProperties = new ArrayList<>();

    public CrowdControl(Matter applier, LegacyEntity victim, String name, int ticks, int level) {
        super(applier, victim, name, ticks, 1, level, 0);
    }

    public ArrayList<ControlType> getControlProperties() {
        return controlProperties;
    }

    public void setControlProperties(ArrayList<ControlType> controlProperties) {
        this.controlProperties = controlProperties;
    }

    @Override
    public void trigger() {
        CrowdControlManager.handleCrowdControl(this.getVictim().getEntity(), this.getControlProperties());
    }

}
