package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class GameStartCommand extends SubCommand {

    public GameStartCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player player, String arguments[]) {
        if(plugin.oxGame == null) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.nogame"));
            return;
        }

        if(plugin.oxGame.started) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.alreadystared"));
            return;
        }

        if(plugin.oxGame.getPlayers() == null || plugin.oxGame.getPlayers().size() == 0) {
            player.sendMessage((String) plugin.getConfig().get("messages.errors.noplayers"));
            return;
        }

        player.sendMessage((String) plugin.getConfig().get("messages.notifications.gamestart"));
        plugin.startOXGame();
    }
}
