package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class ConfigGameCommand extends SubCommand{

    public ConfigGameCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player player, String[] arguments) {
        if(plugin.oxGame == null) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.nogame"));
            return;
        }

        if(plugin.oxGame.started) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.alreadyrunning"));
            return;
        }
        plugin.enableGameEditor(player);
    }
}
