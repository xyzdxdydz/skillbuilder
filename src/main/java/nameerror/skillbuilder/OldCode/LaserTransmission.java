//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.OldCode.CustomObjectType.Laser;
//import nameerror.skillbuilder.Math.VectorManager;
//import org.bukkit.Location;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.entity.Entity;
//import org.bukkit.util.Vector;
//
//public class LaserTransmission extends PluginObjectTransmission {
//
//    private final String properties = "LaserProperties";
//
//    private final Location startLocation;
//    private Vector rayDirection;
//    private final double range;
//
//
//    public LaserTransmission(Entity caller, ConfigurationSection objectSection, PluginObject parentInfo) {
//        super(caller, objectSection, parentInfo);
//        this.startLocation = super.locationDecode(super.getObjectSection().getString(properties + ".Location"));
//        this.rayDirection = directionDecode();
//        this.range = rangeDecode();
//    }
//
//    @Override
//    public String getTypeName() {
//        return "Laser";
//    }
//
//    @Override
//    public void buildSkill() {
//        ConfigurationSection laserSubtype = super.getObjectSection().getConfigurationSection(properties + ".LaserSubtype");
//        Laser laser = new Laser(getRoot(), this.startLocation, this.rayDirection, this.range,
//                laserSubtype.getDouble("Thickness"),
//                laserSubtype.getInt("Duration"),
//                laserSubtype.getInt("RefreshRate"));
//
//                laser.setPiercing(laserSubtype.getInt("PiercingLevel"),
//                             laserSubtype.getInt("MaxEntityPiercing"),
//                             laserSubtype.getInt("MaxBlockPiercing"));
//                laser.setFollowLocation(getObjectSection().getBoolean(properties + ".FollowCaster"));
//                laser.setFollowDirection(getObjectSection().getBoolean(properties + ".FollowCasterDirection"));
//                laser.activate();
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
//            this.rayDirection = startLocation.getDirection();
//
//            if (coder.length == 4 && coder[1].equalsIgnoreCase("RANDOM")) {
//                double minValue = Double.parseDouble(coder[2]);
//                double maxValue = Double.parseDouble(coder[3]);
//                double angle = (maxValue - minValue) *  Math.random() + minValue;
//
//                Vector vectorAxis = this.rayDirection;
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
//    public void setRayDirection(Vector direction) {
//        this.rayDirection = direction;
//    }
//}
