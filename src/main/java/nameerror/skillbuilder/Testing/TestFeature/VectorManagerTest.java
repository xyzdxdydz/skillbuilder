package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class VectorManagerTest extends TestModule {

    public VectorManagerTest() {
        initTest();
    }

    /**
     * Expected behavior
     * The complete version of each feature must get 'passed' on both 'basic' and 'advanced'
     * left_reference:
     *      Basic: // passed
     *          1. Allay will be on 4 block away from your left of your view from your foot's location.
     *      Advanced: // passed
     *          - no additional information -
     * right_reference:
     *      Basic: // passed
     *          1. Allay will be on 4 block away from your right of your view from your foot's location.
     *      Advanced: // passed
     *          - no additional information -
     * above_reference:
     *      Basic: // passed
     *          1. Allay will be on 4 block above of your view from your foot's location.
     *      Advanced: // passed
     *          - no additional information -
     * bottom_reference:
     *      Basic: // passed
     *          1. Allay will be on 4 block below of your view from your foot's location
     *      Advanced: // passed
     *          - no additional information -
     * front_reference:
     *      Basic: // passed
     *          1.Allay will be on 4 block in front of your view from your foot's location
     *      Advanced: // passed
     *          - no additional information -
     * back_reference:
     *      Basic: // passed
     *          1. Allay will be on 4 block on the back of your view from your foot's location
     *      Advanced: // passed
     *          - no additional information -
     */
    private void initTest() {
        // Add function references to the map
        this.addTestCase("left_reference", this::LeftVectorReferenceTest); // passed
        this.addTestCase("right_reference", this::RightVectorReferenceTest); // passed
        this.addTestCase("above_reference",  this::AboveVectorReferenceTest); // passed
        this.addTestCase("bottom_reference", this::BottomVectorReferenceTest); // passed
        this.addTestCase("front_reference", this::FrontVectorReferenceTest); // passed
        this.addTestCase("back_reference", this::BackVectorReferenceTest); // passed
    }

    private Integer LeftVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(4, 0, 0));
        return 1;
    }

    private Integer RightVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(-4, 0, 0));
        return 1;
    }

    private Integer AboveVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 4, 0));
        return 1;
    }

    private Integer BottomVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, -4, 0));
        return 1;
    }

    private Integer FrontVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 0, 4));
        return 1;
    }

    private Integer BackVectorReferenceTest(Player requester) {
        World world = requester.getWorld();
        Allay allay = world.spawn(requester.getEyeLocation(), Allay.class);
        TrackedMatterTest.createSlave(requester, allay, new Vector(0, 0, -4));
        return 1;
    }
}
