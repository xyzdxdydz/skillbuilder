package nameerror.skillbuilder.Fundamental;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Field.FieldManager;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;

import java.util.ArrayList;

public class MetaManager {

    public static ArrayList<String> managers = new ArrayList<>();

    // TODO; implement garbage collector
    static {
        managers.add("field");
        managers.add("movement_tracking");
        managers.add("status_effect");
    }

    public static ArrayList<String> getManagers() {
        return managers;
    }

    public static String op(String op, String manager) {
        if (op.equals("reset")) {
            switch (manager) {
                case "field":
                    FieldManager.clear();
                    return "Success";

                case "movement_tracking":
                    MovementTrackingHandler.clear();
                    return "Success";

                case "status_effect":
                    StatusEffectManager.clear();
                    return "Success";

                default:
                    return "Failed, no manager found";
            }
        }

        return "Failed, no operation found";
    }
}
