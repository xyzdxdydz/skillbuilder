package nameerror.skillbuilder.Fundamental.ObjectManagement;

import nameerror.skillbuilder.Utils.DomainCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class InnateDomain {

    private String worldName;
    private Entity owner;
    private double duration;
    private World world;
    private Location expandLocation;
    private HashMap<Entity, Location> beforeDomainLocation = new HashMap<>();

    // TODO; make singleton
    // TODO; track field
    public InnateDomain(Entity owner, String worldName) {
        this.owner = owner;
        this.worldName = worldName;
    }

    public void expand() {
        DomainCreator wc = new DomainCreator(worldName);
        wc.create();
        this.world = Bukkit.getServer().getWorld(worldName);
    }

    public World getWorld() { return this.world; }

    public void bring(ArrayList<Entity> entities) {
        expandLocation = owner.getLocation();
        Location baseLocation = expandLocation.clone();

        for (Entity e: entities) {
            if (!e.equals(owner)) {
                beforeDomainLocation.put(e, e.getLocation());
                Location dff = e.getLocation().subtract(baseLocation);
                dff.setWorld(world);
                e.teleport(world.getSpawnLocation().add(dff));
            }
        }
        owner.teleport(world.getSpawnLocation());
    }

    public void collapse() {
        Location baseLocation = expandLocation.clone();
        baseLocation.setWorld(world);

        for (HashMap.Entry<Entity, Location> entry : beforeDomainLocation.entrySet()) {
            entry.getKey().teleport(entry.getValue());
        }

        owner.teleport(expandLocation);

        beforeDomainLocation = null;
        expandLocation = null;
    }
}
