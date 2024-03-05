package nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl;

import java.util.ArrayList;
import java.util.Arrays;

public enum ControlType {
    DISABLE_WALK,
    DISABLE_LOOK,
    DISABLE_SNEAK,
    DISABLE_SWIM,
    DISABLE_RUN,
    DISABLE_DASH,
    DISABLE_GLIDING,
    DISABLE_FLY;

    // temporary
//    private boolean lossOfControlCat = false; // false is partial CC
//    private final boolean disruptingCat = false;
//    private boolean immobilizingCat = false;
//    private boolean disarmingCat = false;
//    private boolean impairedMovementCat = false;
//    private final boolean impairedActionCat = false;


    public static ArrayList<ControlType> getLossOfControl() {
        return new ArrayList<>(Arrays.asList(DISABLE_FLY, DISABLE_WALK, DISABLE_LOOK)) ;
    }

    public static ArrayList<ControlType> getLossControlCategory() {
        return new ArrayList<>(Arrays.asList(
                DISABLE_WALK,
                DISABLE_LOOK,
                DISABLE_SNEAK,
                DISABLE_SWIM,
                DISABLE_RUN,
                DISABLE_DASH,
                DISABLE_GLIDING,
                DISABLE_FLY
        ));
    }
}
