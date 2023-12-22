package better.defusal;

import better.defusal.commands.BetterDefusalCommand;
import better.defusal.listener.InteractListener;
import better.defusal.listener.ReloadListener;
import com.imjustdoom.cmdinstruction.CMDInstruction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BetterDefusal extends JavaPlugin {

    public final static List<String> MATERIALS = new ArrayList<>();
    public final static String VERSION = "1.0.0";

    private static BetterDefusal instance;

    private boolean allBlocks, durabilityUsed;
    private Material defusalItem;

    /**
     * Generate a list of possible items to use as the "wire cutter".
     */
    static {
        MATERIALS.add("*");
        for (Material material : Material.values()) if (material.isItem()) MATERIALS.add(material.name().toLowerCase());
    }

    /*
     * Getters.
     */

    /**
     * Return the material used as the wire cutters.
     */
    public Material getDefusalItem() {
        return defusalItem;
    }

    /**
     * Return the plugin's current instance.
     */
    public static BetterDefusal getInstance() {
        return instance;
    }

    /**
     * Return whether falling blocks can also be "defused".
     */
    public boolean isAllBlocks() {
        return allBlocks;
    }

    /**
     * Return whether defusing uses item durability
     */
    public boolean isDurabilityUsed() {
        return durabilityUsed;
    }

    /*
     * Methods.
     */

    @Override
    public void onEnable() {
        instance = this;
        new Metrics(this, 20535);

        CMDInstruction.registerCommands(this, new BetterDefusalCommand());

        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        if (Bukkit.getPluginManager().getPlugin("BetterReload") != null)
            Bukkit.getPluginManager().registerEvents(new ReloadListener(), this);

        reload();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    /**
     * Read the config file.
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        allBlocks = getConfig().getBoolean("all-blocks");
        String material = getConfig().getString("defusal-item");
        if (!MATERIALS.contains(material.toLowerCase())) {
            getLogger().info("Unknown material " + material + "! Defaulting to shears.");
            setDefusalItem(Material.SHEARS);
        } else defusalItem = Material.getMaterial(material.toUpperCase());
        durabilityUsed = getConfig().getBoolean("use-durability");
    }

    /**
     * Set what item should be used as wire cutters and update the config file.
     */
    public void setDefusalItem(Material defusalItem) {
        this.defusalItem = defusalItem;
        getConfig().set("defusal-item", defusalItem == null ? "*" : defusalItem.name().toLowerCase());
        saveConfig();
    }
}
