package nameerror.skillbuilder.Fundamental.ObjectManagement.SequenceBlock;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Enum.ActionType;
import nameerror.skillbuilder.Fundamental.SBComponent;
import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.TargetSelector;

import java.util.Locale;

public class ActionBlock {
    private final ActionType actionType;
    private final String name;
    private final SBComponent caller;
    private final long delay;
    private final int amount;
    private final TargetSelector targetSelector;

    public ActionBlock(
            SBComponent caller,
            String executeType,
            String name,
            int amount,
            long delay,
            TargetSelector targetSelector) {

        this.caller = caller;
        this.name = name;
        this.actionType = ActionType.valueOf(executeType.toUpperCase(Locale.ROOT));
        this.amount = amount;
        this.delay = delay;
        this.targetSelector = targetSelector;
    }

    public SBComponent getCaller() {
        return caller;
    }

    public ActionType getExecuteType() {
        return actionType;
    }

    public String getName() {
        return name;
    }

    public long getDelay() {
        return delay;
    }

    public int getAmount() {
        return amount;
    }

    public TargetSelector getTargetSelector() {
        return targetSelector;
    }
}
