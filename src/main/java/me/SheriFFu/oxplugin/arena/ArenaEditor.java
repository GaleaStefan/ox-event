package me.SheriFFu.oxplugin.arena;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class ArenaEditor {
    private final Main plugin;
    public Player editorPlayer;
    private final String arena;
    public Region region;

    public ArenaEditor(Main plugin, Player player, String arena) {
        this.plugin = plugin;
        this.arena = arena;
        this.editorPlayer = player;
        this.region = new Region();
    }

    public void saveRegionToConfig() {
        String path = "arena." + arena + ".";

        double x = region.corner1.getX();
        double y = region.corner1.getY();
        double z = region.corner1.getZ();
        //String worldName = region.corner1.getWorld().getName();

        plugin.getConfig().set(path + "corner1.x", x);
        plugin.getConfig().set(path + "corner1.y", y);
        plugin.getConfig().set(path + "corner1.z", z);
        //plugin.getConfig().set(path + "corner1.world", worldName);

        x = region.corner2.getX();
        y = region.corner2.getY();
        z = region.corner2.getZ();
        //worldName = region.corner2.getWorld().getName();

        plugin.getConfig().set(path + "corner2.x", x);
        plugin.getConfig().set(path + "corner2.y", y);
        plugin.getConfig().set(path + "corner2.z", z);
        //plugin.getConfig().set(path + "corner2.world", worldName);

        plugin.getConfig().set(path + "configured", true);
        plugin.getConfig().set("arena.world", region.corner1.getWorld().getName());

        plugin.saveConfig();
    }
}
