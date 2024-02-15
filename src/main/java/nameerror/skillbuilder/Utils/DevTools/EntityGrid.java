package nameerror.skillbuilder.Utils.DevTools;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.util.Vector;

public class EntityGrid {

    public static void makeGrid(Location center, int size, int stride) {
        if (stride < 1) {
            stride = 1;
        }
        for (int x=-size; x<=size; x+=stride) {
            for (int y=-size; y<=size; y+=stride) {
                for (int z=-size; z<=size; z+=stride) {
                    Location spawnLocation = center.clone().add(new Vector(x, y, z));
                    Creeper creeper = center.getWorld().spawn(spawnLocation, Creeper.class);

                    creeper.setGravity(false); // velocity can apply
//                    allay.setAI(false);
//                    allay.setCollidable(false);
                }
            }
        }
    }
}
