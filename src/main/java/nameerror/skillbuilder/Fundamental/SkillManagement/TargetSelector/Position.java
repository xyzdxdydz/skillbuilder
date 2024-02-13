package nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector;

public class Position<T> {

    private final T obj;

    public Position(T obj) {
        this.obj = obj;
    }

    public T get() {
        return obj;
    }
}
