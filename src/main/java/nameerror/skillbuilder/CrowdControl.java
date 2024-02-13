package nameerror.skillbuilder;

public class CrowdControl {

    // Categorization
    private boolean lossOfControlCat = false; // false is partial CC
    private final boolean disruptingCat = false;
    private boolean immobilizingCat = false;
    private boolean disarmingCat = false;
    private boolean impairedMovementCat = false;
    private final boolean impairedActionCat = false;

    public CrowdControl(String s) {
    }

    // Effect flag


    // Properties
    long duration = 0; // duration is in tick

    private void setLossOfControlCat() {
        this.lossOfControlCat = true;
    }

    private void setDisruptingCat() {
        this.disarmingCat = true;
    }

    private void setImmobilizingCat() {
        this.immobilizingCat = true;
    }

    private void setDisarmingCat() {
        this.disarmingCat = true;
    }

    private void setImpairedMovementCat() {
        this.impairedMovementCat = true;
    }

    private void setImpairedActionCat() { this.impairedMovementCat = true; }

    protected CrowdControl stun(long ticks) {
        setLossOfControlCat();
        setDisruptingCat();
        setImmobilizingCat();
        setDisarmingCat();
        setImpairedMovementCat();
        setImpairedActionCat();
        this.duration = ticks;
        return this;
    }

}
