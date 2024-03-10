//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import org.bukkit.Location;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.util.Vector;
//
//public class ObjectCompiler {
//
//    public static Location compileLocation(String s) {
//        String[] coder;
//        try {
//            coder = s.split(" ");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return location = null;
//        }
//        if (coder[0].equalsIgnoreCase("CASTER")) {
//            if (coder.length > 1 && coder[1].equalsIgnoreCase("HEAD")) {
//
//                if (parent != null && ((TargetMarkable) parent).getOwner() instanceof LivingEntity) {
//                    return location = ((LivingEntity) ((TargetMarkable) parent).getOwner()).getEyeLocation();
//
//                } else {
//                    return location = ((LivingEntity) this.getRoot()).getEyeLocation();
//                }
//
//            } else if (coder.length > 2 && coder[1].equalsIgnoreCase("DOWN")) {
//                Location a;
//
//                if (parent instanceof TargetMarkable) {
//                    a = ((TargetMarkable) parent).getOwner().getLocation();
//                } else {
//                    a = this.getRoot().getLocation();
//                }
//
//                double addY = - Double.parseDouble(coder[2]);
//                return location = a.add(new Vector(0, addY, 0));
//
//            } else {
//                if (parent instanceof TargetMarkable) {
//                    return location = ((TargetMarkable) parent).getOwner().getLocation();
//                }
//
//                return location = this.getRoot().getLocation();
//            }
//
//        } else {
//            return location = null;
//        }
//    }
//}
