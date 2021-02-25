package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {
    public JoinCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player player, String arguments[]) {
        if(plugin.oxGame == null) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.nogame"));
            return;
        }

        if(plugin.oxGame.getPlayers().contains(player)) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.alreadyjoined"));
            return;
        }

        if(plugin.oxGame.isLocked()) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.gamelocked"));
            return;
        }

        if(plugin.oxGame.getHost().equals(player)) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.gamelocked"));
            return;
        }

        player.sendMessage((String) plugin.getConfig().get("messages.notifications.joinevent"));
        plugin.oxGame.addPlayer(player);

        String message = plugin.getConfig().getString("messages.notifications.playerjoin");
        message = message.replace("%player%", player.getDisplayName());
        plugin.oxGame.getHost().sendMessage(message);
    }
}
