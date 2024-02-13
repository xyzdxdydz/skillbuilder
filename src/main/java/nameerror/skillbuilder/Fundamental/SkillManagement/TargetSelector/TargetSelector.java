package nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector;

import nameerror.skillbuilder.Fundamental.SBComponent;
import nameerror.skillbuilder.Math.Shape.Cylinder;
import nameerror.skillbuilder.Math.Shape.ShapeType;
import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.Enum.SelectionMode;
import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.Enum.TargetSelection;
import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.Enum.TargetSort;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.*;

public class TargetSelector {

    private final SBComponent caller;
    private final Location start;
    private final ConfigurationSection targetSelector;

    private SelectionMode selectionMode;

    private TargetSelection targetSelection = TargetSelection.ALL;
    private ShapeType shapeType;
    private TargetSort targetSort;
    private Boolean includeSelf = false;
    private final Set<EntityType> filterEntity = new HashSet<>();
    private boolean allEntities = false;
    private boolean selectInFilterEntity = true; // ถ้า false จะไม้่เลือก type ที่อยู่ใน filterEntity
    private int limit = Integer.MAX_VALUE;

    private Set<Entity> finalTarget = new HashSet<>();

    // Coming Soon
    private boolean targetLock;
    private boolean OtherFilter;

    public TargetSelector(SBComponent caller, Location searchArea, ConfigurationSection targetSelector) {
        this.caller = caller;
        this.start = searchArea;
        this.targetSelector = targetSelector;

        initMode();

        if (this.getMode().equals(SelectionMode.ENTITY)) {
            initIncludeSelf();
            initSelectionType();
            initEntitySelection();
            initExcludeFilterEntity();
            initTargetSort();
        }
        initShapeType();
        initLimit();
    }

    public SelectionMode getMode() {
        return selectionMode;
    }

    public Location getSearchLocation() {
        return start;
    }

    public void findTarget() {
        List<Entity> stepFilter = start.getWorld().getEntities();

        // Step 1: Filter Team
        Bukkit.broadcastMessage(Verbose.featureUnderMaintenance("Filter Team target selector"));

//        if (targetSelection.equals(TargetSelection.SELF)) {
//            finalTarget.add(caller);
//            return;
//
//        } else {
//
//        }

        // Step 2: Filter in area
        if (shapeType.equals(ShapeType.CYLINDER)) {
            String[] strings = targetSelector.getString("Parameter").split(" ");
            double[] param = new double[6];
            for (int i = 0; i < 5; i++) {
                param[i] = Double.parseDouble(strings[i]);
            }

            Cylinder cylinder = new Cylinder(start, new Vector(param[0], param[1], param[2]), param[3]);
            stepFilter = Search.treeMapAsList(Search.entityInCylinder(stepFilter, cylinder, param[4], Boolean.parseBoolean(strings[5]),
                    Boolean.parseBoolean(strings[6])));

        } else if (true) {

        }

        // Step 3: Filter entity type
        if (allEntities) {
            if (!selectInFilterEntity) {
                return;
            }
        } else {
            stepFilter.removeIf(e -> (selectInFilterEntity && !filterEntity.contains(e.getType()) ||
                    !selectInFilterEntity && filterEntity.contains(e.getType())));
        }

        // Step 4: Other Filter

        // Final : Sorting and Convert to set
        stepFilter = new ArrayList<>(stepFilter);
        int size = stepFilter.size();
        int count = 0;

        if (targetSort.equals(TargetSort.NEAREST) || targetSort.equals(TargetSort.FURTHEST)) {
            for (int i = 0; i < size; i++) {
                if (count == limit) {
                    break;
                }

                int x = targetSort.equals(TargetSort.NEAREST) ? i : size -1 -i;
                if (stepFilter.get(x).equals(caller)) {
                    if (includeSelf) {
                        finalTarget.add(stepFilter.get(x));
                        count++;
                    }
                } else {
                    finalTarget.add(stepFilter.get(x));
                    count++;
                }
            }

        } else if (targetSort.equals(TargetSort.RANDOM)) {
            for (int i = 0; i < size; i++) {
                if (count == limit) {
                    break;
                }
                int x = (int) Math.round(Math.random() * (size - 1));
                if (stepFilter.get(x).equals(caller)) {
                    if (includeSelf) {
                        finalTarget.add(stepFilter.get(x));
                        count++;
                    }
                } else {
                    finalTarget.add(stepFilter.get(x));
                    count++;
                }
            }
        }
    }

    public Set<Entity> getTarget() {
        return finalTarget;
    }

    public Set<Location> getRandomLocation() {
        Set<Location> ans = new HashSet<>();
        if (limit == Integer.MAX_VALUE) {
            return ans;
        }
        for (int j = 0; j < limit; j++) {
            switch (shapeType) {
                case CYLINDER:
                    String[] strings = targetSelector.getString("Parameter").split(" ");
                    double[] param = new double[6];
                    for (int i = 0; i < 5; i++) {
                        param[i] = Double.parseDouble(strings[i]);
                    }

                    Cylinder cylinder = new Cylinder(start, new Vector(param[0], param[1], param[2]), param[3]);
                    ans.add(cylinder.getRandomLocation(param[4]));
                    break;
                default:
                    break;
            }
        }

        return ans;
    }

    public void setTarget(Set<Entity> provided) {
        this.finalTarget = provided;
    }

    private void initSelectionType() {
        try {
            this.targetSelection = TargetSelection.valueOf(targetSelector.getString("Selection").toUpperCase(Locale.ROOT));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEntitySelection() {
        String entitySelect = targetSelector.getString("EntitySelectionType");

        if (entitySelect.equalsIgnoreCase("ALL")) {
            allEntities = true;
            return;
        }

        String[] entityFilterList = entitySelect.split(" ");
        for (String s : entityFilterList) {
            filterEntity.add(EntityType.valueOf(s));
        }
    }

    private void initMode() {
        if (targetSelector.getString("Mode") == null) {
            this.selectionMode = SelectionMode.ENTITY;
            return;
        }

        this.selectionMode = SelectionMode.valueOf(targetSelector.getString("Mode"));
    }

    public void initShapeType() {
        try {
            this.shapeType = ShapeType.valueOf(targetSelector.getString("SearchVolumeType").toUpperCase(Locale.ROOT));

        } catch (Exception e) {
            e.printStackTrace();
            this.shapeType = null;
        }
    }

    private void initExcludeFilterEntity() {
        this.selectInFilterEntity = targetSelector.getBoolean("SelectInList");
    }

    private void initLimit() {
        int limitResult = targetSelector.getInt("Limit");
        this.limit = limitResult == -1 ? Integer.MAX_VALUE : limitResult;
    }

    private void initIncludeSelf() {
        this.includeSelf = targetSelector.getBoolean("IncludeSelf");
    }

    private void initTargetSort() {
        try {
            this.targetSort = TargetSort.valueOf(targetSelector.getString("Sort").toUpperCase(Locale.ROOT));

        } catch (Exception e) {
            e.printStackTrace();
            this.targetSort = null;
        }
    }
}
