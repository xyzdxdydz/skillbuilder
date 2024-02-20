package nameerror.skillbuilder.Fundamental;

import nameerror.skillbuilder.Fundamental.ObjectManagement.FieldManager;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;

import java.util.ArrayList;

public class MetaManager {

    public static ArrayList<String> managers = new ArrayList<>();

    static {
        managers.add("field");
        managers.add("movement_tracking");
    }

    public static ArrayList<String> getManagers() {
        return managers;
    }

    public static String op(String op, String manager) {
        if (op.equals("reset")) {
            if (manager.equals("field")) {
                FieldManager.clear();
                return "Success";
            }

            else if (manager.equals("movement_tracking")) {
                MovementTrackingHandler.clear();
                return "Success";
            }

            return "Failed, no manager found";
        }

        return "Failed, no operation found";
    }
}
