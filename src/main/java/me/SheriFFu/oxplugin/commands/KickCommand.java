package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KickCommand extends SubCommand{

    public KickCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player source, String arguments[]) {
        if(arguments == null || arguments.length < 2) {
            source.sendMessage((String)plugin.getConfig().get("messages.usage.kick"));
            return;
        }

        String playerName = arguments[1];

        if(plugin.oxGame != null) {
            source.sendMessage((String)plugin.getConfig().get("messages.errors.nogame"));
            return;
        }
        if(plugin.oxGame.getHost().getName().equals(playerName)) {
            source.sendMessage((String)plugin.getConfig().get("messages.errors.sourcehost"));
            return;
        }

        Player target = plugin.getServer().getPlayer(playerName);
        if(target == null) {
            source.sendMessage((String)plugin.getConfig().get("messages.errors.inexistentplayer"));
            return;
        }

        if(!plugin.oxGame.getPlayers().contains(target)) {
            source.sendMessage((String)plugin.getConfig().get("messages.errors.playernotjoined"));
            return;
        }

        plugin.oxGame.removePlayer(target);
        Bukkit.dispatchCommand(target, "spawn");
    }
}
