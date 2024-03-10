package nameerror.skillbuilder;

import nameerror.skillbuilder.Commands.CommandManager;
import nameerror.skillbuilder.Configuration.PluginConfiguration;
import nameerror.skillbuilder.EventListener.DisableParticleDamage;
import nameerror.skillbuilder.EventListener.SkillListener;
import nameerror.skillbuilder.Fundamental.ObjectManagement.FieldManager;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import nameerror.skillbuilder.Testing.Test;
import nameerror.skillbuilder.Utils.DevTools.StatusEffectChecker;
import nameerror.skillbuilder.Utils.EventDispatcher;
import nameerror.skillbuilder.Utils.EventLab;
import nameerror.skillbuilder.Utils.MovementTrackingHandler;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl.PlayerControl;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SkillBuilder extends JavaPlugin {

    private boolean developMode = true;

    private static SkillBuilder plugin;
    public static HashMap<String, PluginConfiguration> configurationDatabase = new HashMap<>();

    public static String consolePrefix = ChatColor.YELLOW + "[Announce] : " + ChatColor.RESET;

    @Override
    public void onEnable() {
        // Plugin startup logic

        enableMessage();
        registerEvent();
        registerHandler();
        registerConfig();
        registerCommand();

        if (this.developMode) {
            Test.registerTest();
        }

        plugin = this;

        getServer().getConsoleSender().sendMessage(consolePrefix + ChatColor.GREEN + "Load Success..");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(consolePrefix + ChatColor.of("#FFFF8B") + "Preparing shutdown");
        getServer().getConsoleSender().sendMessage(consolePrefix + ChatColor.of("#FFFF8B") + "SkillBuilder ready to deactivate, Bye.");
    }

    public static SkillBuilder getPlugin() { return plugin; }

    private void enableMessage() {
        getServer().getConsoleSender().sendMessage("\n");
        getServer().getConsoleSender().sendMessage(consolePrefix + ChatColor.YELLOW + "SkillBuilder Enabled!");
        getServer().getConsoleSender().sendMessage(consolePrefix + ChatColor.YELLOW + "Loading all skill to the universe");
    }

    private void registerConfig() {
//        getConfig().options().copyDefaults();
//        saveDefaultConfig();
        // add file config here!
        Map<String, String> allPath = Stream.of(new String[][] {
                        {"SkillConfig", "Skill\\Skill.yml"},
                        {"ObjectConfig", "Skill\\Object.yml"},
                        {"SettingsConfig", "Settings\\SkillSettings.yml"},
                }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::<String, String> unmodifiableMap));

        for (Map.Entry<String, String> path : allPath.entrySet()) {
            PluginConfiguration config = new PluginConfiguration(new File(Bukkit.getServer().getPluginManager()
                    .getPlugin("SkillBuilder").getDataFolder(), path.getValue()));
            config.getConfig().options().copyDefaults(true);
            config.save();
            configurationDatabase.put(path.getKey(), config);
        }

    }

    private void registerCommand() {
        getCommand("skillbuilder").setExecutor(new CommandManager());
    }

    private void registerEvent() {
        PluginManager pm = getServer().getPluginManager();
        Bukkit.getServer().getPluginManager().registerEvents(new SkillListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EventDispatcher(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerControl(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DisableParticleDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new StatusEffectChecker(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EventLab(), this);
    }

    private void registerHandler() {
        getServer().getScheduler().runTaskTimer(this, MovementTrackingHandler::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, FieldManager::update, 0, 1);
        getServer().getScheduler().runTaskTimer(this, StatusEffectManager::update, 0, 1);
    }

    public PluginConfiguration getConfigDatabase(String name) {
        return configurationDatabase.get(name);
    }
}
