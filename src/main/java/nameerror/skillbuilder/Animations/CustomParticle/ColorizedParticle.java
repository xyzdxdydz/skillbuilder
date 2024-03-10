package nameerror.skillbuilder.Animations.CustomParticle;

import org.bukkit.Color;
import org.bukkit.Location;

public interface ColorizedParticle {
    Color getColor();

    void setColor(Color color);

    void spawn(Location location);
}
