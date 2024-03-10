package nameerror.skillbuilder.OldCode;

import nameerror.skillbuilder.OldCode.SequenceBlock.LoopBlock;
import nameerror.skillbuilder.OldCode.SequenceBlock.ActionBlock;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.OldCode.SkillManagement.SkillManager;
import nameerror.skillbuilder.OldCode.SkillManagement.TargetSelector.TargetSelector;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class SequenceRunnerMachine {
    private final SBComponent owner;
    private final Stack<ActionBlock> blockStack = new Stack<>();

    private final SequenceRunnerCallback callback;
    private SkillManager rootSkill;
    private boolean isRunning = false;
    private boolean isCancel = false;

    public SequenceRunnerMachine(SBComponent owner, SequenceRunnerCallback callback) {
        this.owner = owner;
        this.callback = callback;
    }

    // TODO: 24/6/2566 return success status
    /**
     * Load to action from configuration file to a script runner.
     *
     * @param sequence sequence of action, typically is from a configuration file.
     *
     */
    public void load(ConfigurationSection sequence) {
        ArrayList<ActionBlock> sequenceArray = preCompileActionSequence(this.owner, sequence, 0);
        loadSequence(sequenceArray);
    }

    /**
     * Start a sequence running machine
     *
     */

    public void start() {
        isRunning = true;
        runSequence();
    }

    /**
     * Stop a sequence running machine
     *
     */

    public void stop() { 
        isCancel = true;
        // TODO: 23/6/2566 - maybe is running must be false
        isRunning = false;
    }

    public void clear() { blockStack.clear(); }
    public boolean isRunning() {
        return isRunning;
    }
    public boolean isCancelled() {return isCancel; }
    public boolean isOutOfSequence() {
        return blockStack.isEmpty();
    }

    /**
     * Load sequence block to sequence runner stack
     *
     * @param sequenceBlocks array of sequence block
     *
     */
    public void loadSequence(ArrayList<ActionBlock> sequenceBlocks) {
        for (int i = sequenceBlocks.size() - 1; i >= 0; i--) {
            blockStack.push(sequenceBlocks.get(i));
        }
    }

    /**
     * run a function continuously in a sequence machine like a code.
     *
     */
    private void runSequence() {
        if (isCancel) {
            callback.onCancel();
            // TODO: 23/6/2566 cannot false because it will run the latest action to complete
            return;
        }

        if (!blockStack.empty() && blockStack.peek() instanceof LoopBlock) {
            extractLoopBlock();
        }

        if (blockStack.empty() || this.isCancel) {
            if (blockStack.empty())
                callback.onOutOfSequence();
            else
                callback.onCancel();
            isRunning = false;
            return;
        }

        ActionBlock block = blockStack.pop();

        // support game tick in minecraft.
        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            if (isCancel) {
                callback.onCancel();
                isRunning = false;
                return;
            }

            for (int i = 0; i < block.getAmount(); i++) {
                Bukkit.broadcastMessage(Verbose.featureUnderMaintenance("Sequence block init"));
//                ObjectManager.initiate(root, rootSkill, block.getExecuteType(), block.getName(), block.getTargetSelector(), owner);
            }

            if (!blockStack.empty()) {
                runSequence();

            } else {
                callback.onOutOfSequence();
                isRunning = false;
            }

        }, block.getDelay());
    }

    /**
     * Load sequence in loop to this sequence machine.
     *
     */
    private void extractLoopBlock() {
        // if the latest block is a loop, extract the loop and load to sequence runner
        if (!blockStack.empty() && blockStack.peek() instanceof LoopBlock) {
            LoopBlock sequenceBlock = (LoopBlock) blockStack.peek();

            if (sequenceBlock.isNotExpired()) {
                ArrayList<ActionBlock> sequenceArray = preCompileActionSequence(
                        sequenceBlock.getCaller(),
                        sequenceBlock.use(),
                        sequenceBlock.getLoopCooldown()
                );
                loadSequence(sequenceArray);

            } else {
                blockStack.pop();
            }
            extractLoopBlock();
        }
    }

    /**
     * Convert each sequence from configuration script file to program
     *
     * @param source game object that request the script to run.
     * @param rawActionSequence a sequence script in .yml.
     * @param endDelay is the cooldown of that sequence frame e.g., in sequence loop.
     *
     */
    public ArrayList<ActionBlock> preCompileActionSequence(SBComponent source, ConfigurationSection rawActionSequence, long endDelay) {
        ArrayList<ActionBlock> blocks = new ArrayList<>();
        ArrayList<String> sequences = new ArrayList<>(rawActionSequence.getKeys(false));
        // TODO: 27/6/2566 target assignment 
        for (String s : sequences) {
            TargetSelector ts = null;

            // find whether if target filters are valid and enabled.
            // TODO: 23/6/2566 - I don't understand
            if (rawActionSequence.isConfigurationSection(s + ".TargetSelector")
                    && !rawActionSequence.getBoolean(s + ".TargetSelector.Disable")) {

                Location searchArea = source.getLocation();

                // set initial location to search which specified in the sequence e.g., relative location
                if (rawActionSequence.getString(s + ".TargetSelector.SearchLocation") != null) {
                    String[] x = rawActionSequence.getString(s + ".TargetSelector.SearchLocation").split(" ");
                    // TODO: 23/6/2566 - make a compile class
                    if (x[0].equalsIgnoreCase("FACING")) {
                        RayTraceResult rayTraceResult = searchArea.getWorld().rayTraceBlocks(
                                searchArea,
                                searchArea.getDirection(),
                                Double.parseDouble(x[1]),
                                FluidCollisionMode.ALWAYS,
                                false
                        );
                        // TODO: 23/6/2566 - check hit position validity
                        searchArea = rayTraceResult.getHitPosition().toLocation(source.getLocation().getWorld());
                    }
                }

                ts = new TargetSelector(source, searchArea, rawActionSequence.getConfigurationSection(s + ".TargetSelector"));

                Bukkit.broadcastMessage(Verbose.featureUnderMaintenance("TargetSelector.UseProvided"));

//                if (fromObject instanceof TargetMarkable && rawActionSequence.getBoolean(s +".TargetSelector.UseProvided")) {
//                    ts.setTarget(((TargetMarkable) fromObject).getAllTargets());
//
//                } else if (ts.getMode().equals(SelectionMode.ENTITY)) {
//                    ts.findTarget();
//                }
            }

            // convert loop sequence to sequence block (loop type) without recursive to other deeper level.
            if (s.startsWith("LOOP")) {
                LoopBlock a = new LoopBlock(source, s, rawActionSequence.getLong( s + ".delay"),
                        Objects.requireNonNull(rawActionSequence.getConfigurationSection(s)));
                blocks.add(a);

            // convert (normal sequence to sequence block
            } else {
                ActionBlock a = new ActionBlock(source, rawActionSequence.getString(s + ".ExecuteType"),
                        rawActionSequence.getString(s + ".name"),
                        rawActionSequence.getInt(s + ".amount"),
                        rawActionSequence.getLong( s + ".delay"),
                        ts);
                blocks.add(a);
            }
        }

        // generate dummy block to set cooldown for the sequence
        if (endDelay > 0) {
            ActionBlock a = new ActionBlock(source, "LAUNCHOBJECT","$BLANK", 1, endDelay, null);
            blocks.add(a);
        }
        return blocks;
    }
}