package nameerror.skillbuilder.Fundamental;

import org.bukkit.Location;

public abstract class Matter {
    private Location location;

    public Matter(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location.clone();
    }

    public boolean teleport(Location location) {
        this.location = location;
        // TODO; Implement fail teleport.
        return true; // teleport successful
    }
}
