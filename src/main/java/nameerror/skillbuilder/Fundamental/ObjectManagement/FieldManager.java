package nameerror.skillbuilder.Fundamental.ObjectManagement;

import java.util.HashSet;
import java.util.Set;

public class FieldManager {
    private static final Set<Field> fieldMap = new HashSet<>();

    public static void register(Field field) {
        fieldMap.add(field);
    }

    public static void remove(Field field) {
        fieldMap.remove(field);
    }

    public static Runnable update() {
        for (Field field : fieldMap) {
            field.step();
        }
        return null;
    }

    public static void clear() {
        fieldMap.clear();
    }
}
