package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    public ReloadCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String[] arguments) {
        plugin.reloadPlugin(player);
    }
}
