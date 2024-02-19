package nameerror.skillbuilder.Utils;

import nameerror.skillbuilder.Fundamental.ObjectManagement.Field;
import nameerror.skillbuilder.Math.SetSpace;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class FallingBlockNullifierField extends Field {
    public FallingBlockNullifierField(SetSpace setSpace) {
        super(setSpace.getLocation(), setSpace);
    }
    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        super.setSpace.setLocation(location);
        return true;
    }

    @Override
    public void applyToEntity(Entity entity) {
        if (entity instanceof FallingBlock) {
            ItemStack itemStack = new ItemStack(((FallingBlock) entity).getBlockData().getMaterial());
            Item droppedItem = entity.getWorld().dropItem(entity.getLocation(), itemStack);
            entity.remove();
        }
    }
}
