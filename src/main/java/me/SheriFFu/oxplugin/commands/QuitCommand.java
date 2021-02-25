package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QuitCommand extends SubCommand {

    public QuitCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player player) {
        if(plugin.oxGame == null) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.nogame"));
            return;
        }

        if(!plugin.oxGame.getPlayers().contains(player)) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.notjoined"));
            return;
        }

        plugin.oxGame.removePlayer(player);
        Bukkit.dispatchCommand(player, "spawn");

        String message = plugin.getConfig().getString("messages.notifications.playerquit");
        message = message.replace("%player%", player.getDisplayName());
        plugin.oxGame.getHost().sendMessage(message);
    }
}
