//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.Math.VectorManager;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.craftbukkit.v1_20_R3.entity.CraftSnowball;
//import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
//import org.bukkit.entity.*;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.util.Vector;
//
//import java.util.Set;
//
//public class ProjectileTransmission extends PluginObjectTransmission implements TargetMarkable, Destroyable {
//
//    private final String properties = "ProjectileProperties";
//
//    private final Location startLocation;
//    private Vector projectileDirection;
//    private final double velocity;
//    private final boolean gravity;
//    private final double range;
//    private Set<Entity> target;
//
//    Projectile bullet;
//
//    public ProjectileTransmission(Entity entity, ConfigurationSection objectSection, PluginObject parent) {
//        super(entity, objectSection, parent);
//        this.startLocation = super.locationDecode(super.getObjectSection().getString(properties + ".Location"));
//        this.projectileDirection = directionDecode();
//        this.velocity = velocityDecode();
//        this.gravity = gravityDecode();
//        this.range = rangeDecode();
//    }
//
//    @Override
//    public String getTypeName() {
//        return "Projectile";
//    }
//
//    @Override
//    public void buildSkill() {
//        String[] coder;
//        try {
//            coder = super.getObjectSection().getString("TransmissionType").split(" ");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (coder[1].equalsIgnoreCase("SNOWBALL")) {
//            bullet = (Snowball) super.getRoot().getWorld().spawnEntity(startLocation, EntityType.SNOWBALL);
//            bullet.setGravity(gravity);
//            bullet.setVelocity(projectileDirection.multiply(velocity));
//            if (coder[2] != null && coder[2].equals("WITHER_STAR")) {
//                ((CraftSnowball) bullet).getHandle().setItem(CraftItemStack.asNMSCopy(new ItemStack((Material.NETHER_STAR))));
//            }
//
//        } else if (coder[1].equalsIgnoreCase("ARROW")) {
//            bullet = (Arrow) super.getRoot().getWorld().spawnEntity(startLocation, EntityType.ARROW);
//            bullet.setGravity(gravity);
//            bullet.setVelocity(projectileDirection.multiply(velocity));
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
//            this.projectileDirection = startLocation.getDirection();
//
//            if (coder[1].equalsIgnoreCase("RANDOM")) {
//                double minValue = Double.parseDouble(coder[2]);
//                double maxValue = Double.parseDouble(coder[3]);
//                double angle = (maxValue - minValue) *  Math.random() + minValue;
//
//                Vector vectorAxis = this.projectileDirection;
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
//    private double velocityDecode() {
//        String[] coder;
//        try {
//            coder = super.getObjectSection().getString(properties + ".Velocity").split(" ");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//        if (coder[0].equalsIgnoreCase("CONST")) {
//            return Double.parseDouble(coder[1]);
//        }
//        return 0;
//    }
//
//    private boolean gravityDecode() {
//        return super.getObjectSection().getBoolean(properties + ".Gravity");
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
//    public void  setDirection(Vector direction) {
//        this.projectileDirection = direction;
//        if (!direction.equals(new Vector(0, 0, 0))) {
//            direction.normalize();
//        }
//    }
//
//    @Override
//    public Set<Entity> getAllTargets() {
//        return null;
//    }
//
//    @Override
//    public Entity getOwner() {
//        return bullet;
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
