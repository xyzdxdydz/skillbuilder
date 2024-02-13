//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.CustomObjectType.StaticAOE;
//import nameerror.skillbuilder.Math.VectorManager;
//import org.bukkit.Location;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.entity.Entity;
//import org.bukkit.util.Vector;
//
//public class StaticAOETransmission extends PluginObjectTransmission {
//    private final String properties = "AoEProperties";
//
//    private final Location startLocation;
//    private Vector normalDirection;
//    private final double range;
//
//
//    public StaticAOETransmission(Entity caller, ConfigurationSection objectSection, PluginObject parentInfo) {
//        super(caller, objectSection, parentInfo);
//        this.startLocation = super.locationDecode(super.getObjectSection().getString(properties + ".Location"));
//        this.normalDirection = directionDecode();
//        this.range = rangeDecode();
//    }
//
//    @Override
//    public String getTypeName() {
//        return "StaticAOE";
//    }
//
//    @Override
//    public void buildSkill() {
//        ConfigurationSection objectSubtype = super.getObjectSection().getConfigurationSection(properties + ".AreaOfEffectSubtype");
//        StaticAOE damageObject = new StaticAOE(getRoot(), getRoot().getWorld() , startLocation, normalDirection, range);
//        String[] coder = objectSubtype.getString("Type").split(" ");
//        if (coder[0].equalsIgnoreCase("CONIC")) {
//            boolean sphereCrop = coder.length == 2 && coder[1].equalsIgnoreCase("SPHERECROP");
//            damageObject.spawnConic(objectSubtype.getDouble("Angle"), sphereCrop);
//        }
//    }
//
//    private Vector directionDecode() {
//        String[] coder;
//        try {
//            coder = super.getObjectSection().getString(properties + ".Direction").split(" ");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        if (coder[0].equalsIgnoreCase("FACING")) {
//            this.normalDirection = startLocation.getDirection();
//
//            if (coder.length == 4 && coder[1].equalsIgnoreCase("RANDOM")) {
//                double minValue = Double.parseDouble(coder[2]);
//                double maxValue = Double.parseDouble(coder[3]);
//                double angle = (maxValue - minValue) *  Math.random() + minValue;
//
//                Vector vectorAxis = this.normalDirection;
//                Vector dummy = vectorAxis.clone().crossProduct(VectorManager.getLeftVectorByReference(vectorAxis)) ;
//                Vector velocity = VectorManager.rotateDCWAboutVector(vectorAxis, dummy, (float) angle).normalize().multiply(1);
//                double rand = Math.random() * 360;
//
//                return VectorManager.rotateDCWAboutVector(velocity, vectorAxis, (float) rand);
//            } else {
//                return this.startLocation.getDirection();
//            }
//
//        } else {
//            return null;
//        }
//    }
//
//    private double rangeDecode() {
//        return super.getObjectSection().getDouble(properties + ".Range");
//    }
//
//    public Location getStartLocation() {
//        return startLocation;
//    }
//
//    public void setDirection(Vector direction) {
//        this.normalDirection = direction;
//    }
//}
