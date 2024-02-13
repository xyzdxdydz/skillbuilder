package nameerror.skillbuilder.Fundamental.SkillManagement;

import nameerror.skillbuilder.Configuration.PluginConfiguration;
import nameerror.skillbuilder.Fundamental.SBComponent;
import nameerror.skillbuilder.SkillBuilder;
import org.bukkit.Bukkit;

import java.util.*;

public class SkillManager {

    private static final HashMap<SBComponent, List<Skill>> skillTable = new HashMap<>();

    // skill will launch without casting (and ignore cast effect like charging)

    /**
     * register one skill to SkillManager
     * @param owner
     * @param skill
     */
    public static void register(SBComponent owner, Skill skill) {
        if (skillTable.containsKey(owner)) {
            skillTable.get(owner).add(skill);

        } else {
            List<Skill> container = new ArrayList<>();
            container.add(skill);
            skillTable.put(owner, container);
        }
    }

    /**
     * unregister one skill from SkillManager
     * @param owner
     * @param skill
     */
    public static void unregister(SBComponent owner, Skill skill) {
        if (skillTable.containsKey(owner)) {
            boolean removeExist = skillTable.get(owner).remove(skill);
            if (!removeExist) {
                Bukkit.broadcastMessage("NO SKILL TO REMOVE");

            } else {
                Bukkit.broadcastMessage("REMOVE SUCCESSFUL");
            }
        } else {
            Bukkit.broadcastMessage("NO ENTITY FOUND");
        }
    }

    /**
     * Check whether the skill is cancelled
     * @param skill
     * @return
     */
    public static boolean isCancelled(Skill skill) {
        return skill.getSequenceRunner().isCancelled();
    }

    /**
     * Cancel one specific skill
     *
     */
    public static void cancel(Skill skill) {
        // TODO: 27/6/2566 stop at skill then skill will stop all sequence machines
        skill.getSequenceRunner().stop();

    }

    /**
     * Cancel All skill that belong to this owner
     * @param target the target that want to cancel skill
     * */
    public static void cancelAll(SBComponent target) {
        if (skillTable.containsKey(target)) {
            skillTable.get(target).forEach(s -> s.getSequenceRunner().stop());
        }
    }

    /**
     * Cancel All skill of specific owner that running the specific skill;
     * @param owner the target that want to cancel skill
     * @param skillName the skill that want to cancel (with name)
     */
    public static void cancel(SBComponent owner, String skillName) {
        if (skillTable.containsKey(owner)) {
            for (Skill sm : skillTable.get(owner)) {
                if (sm.getName().equals(skillName))
                    sm.getSequenceRunner().stop();
            }
        }
    }

    /**
     * Get all skill in the config
     * @return
     */
    public static List<String> getAllSkills() {
        PluginConfiguration skillList = SkillBuilder.configurationDatabase.get("SkillConfig");
        return new ArrayList<>(skillList.getKeys(false));
    }
}

