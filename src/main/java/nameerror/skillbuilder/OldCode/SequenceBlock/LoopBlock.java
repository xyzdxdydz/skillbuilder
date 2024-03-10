package nameerror.skillbuilder.OldCode.SequenceBlock;

import nameerror.skillbuilder.OldCode.Enum.ActionType;
import nameerror.skillbuilder.OldCode.Enum.IterationType;
import nameerror.skillbuilder.OldCode.SBComponent;
import org.bukkit.configuration.ConfigurationSection;

public class LoopBlock extends ActionBlock {

    private final int FOREVER = -1;
    private final int UNDEFINED = -2;

    private final ConfigurationSection rawActionSequence;
    private IterationType iterationType;
    private int loopTicksRemain;
    private int loopCountRemain;
    private final long loopCooldown;
    private long lastUse = UNDEFINED;


    public LoopBlock(SBComponent caller, String name, long delay, ConfigurationSection loopBody) {
        super(caller, ActionType.NONE.toString(), name, 1, delay, null);
        this.rawActionSequence = loopBody.getConfigurationSection("Sequence") ;
        this.loopCooldown = loopBody.getLong("IterationCooldown");
        setupLoop(loopBody.getString("Iteration"));
    }

    public IterationType getIterationType() {
        return iterationType;
    }

    public int getLoopTicksRemain() {
        return loopTicksRemain;
    }

    public int getLoopCountRemain() {
        return loopCountRemain;
    }

    public long getLoopCooldown() {
        return loopCooldown;
    }

    public void setIterationType(IterationType iterationType) {
        this.iterationType = iterationType;
    }

    public void setLoopTicksRemain(int loopTicksRemain) {
        this.loopTicksRemain = loopTicksRemain;
    }

    public void setLoopCountRemain(int loopCountRemain) {
        this.loopCountRemain = loopCountRemain;
    }

    private void setupLoop(String iterationCodeString) {
        String[] coder;
        try {
            coder = iterationCodeString.split(" ");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        this.iterationType = IterationType.valueOf(coder[0]);

        switch (this.iterationType) {
            case LAP:
                this.loopTicksRemain = FOREVER;
                this.loopCountRemain = Integer.parseInt(coder[1]);
                break;
            case TICK:
                this.loopTicksRemain = Integer.parseInt(coder[1]);
                this.loopCountRemain = FOREVER;
                break;
            case UNTIL:
                // TODO: 23/6/2566 Future work
                break;
            case TOGGLE:
                // TODO: 23/6/2566 Future work
                break;
            case FOREVER:
                this.loopTicksRemain = FOREVER;
                this.loopCountRemain = FOREVER;
                break;
        }
    }

    /**
     *
     * @return boolean that the loop can extract the script and run in sequence runner again
     */
    public boolean isNotExpired() {
        return !(loopTicksRemain != FOREVER && lastUse != UNDEFINED && (System.currentTimeMillis() - lastUse) / 50 >= loopTicksRemain
                || loopCountRemain != FOREVER && loopCountRemain <= 0);
    }

    private void updateLoop() {
        switch (this.iterationType) {
            case LAP:
                this.loopCountRemain -= 1;
                break;
            case TICK:
                long time = System.currentTimeMillis();
                if (this.lastUse != UNDEFINED) {
                    this.loopTicksRemain -= (time - lastUse) / 50;
                }
                lastUse = time;
                break;
        }
    }

    /**
     * Update current iteration of the loop and send raw action sequences in that loop
     *
     */
    public ConfigurationSection use() {
        updateLoop();
        return rawActionSequence;
    }
}
