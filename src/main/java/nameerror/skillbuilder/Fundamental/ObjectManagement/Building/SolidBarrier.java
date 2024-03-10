package nameerror.skillbuilder.Fundamental.ObjectManagement.Building;

import nameerror.skillbuilder.Math.SetSpace;
import nameerror.skillbuilder.Math.Shape.Shape3D;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class SolidBarrier extends Building {
    private final SetSpace shape3D;
    private Entity owner;
    private Material material = Material.GREEN_STAINED_GLASS;

    public SolidBarrier(Entity owner, SetSpace shape) {
        super(shape.getLocation());
        this.shape3D = shape;
        this.owner = owner;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void build() {
        ArrayList<Block> surfaceBlocks = ((Shape3D) shape3D).getBlockOnSurface();
        getBlockRestorer().add(surfaceBlocks);
        surfaceBlocks.forEach(block -> block.setType(material));
    }

    @Override
    public void destruct() { }
}
