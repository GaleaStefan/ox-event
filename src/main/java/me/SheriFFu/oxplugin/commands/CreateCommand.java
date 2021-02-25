package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class CreateCommand extends SubCommand {
    public CreateCommand(Main plugin) {
        super(plugin);
    }

    public void execute(Player sender, String arguments[]) {
        if(plugin.oxGame != null) {
            sender.sendMessage((String) plugin.getConfig().get("messages.errors.alreadyrunning"));
            return;
        }

        boolean xConfigurated = plugin.getConfig().getBoolean("arena.x.configured");
        boolean oConfigurated = plugin.getConfig().getBoolean("arena.o.configured");
        boolean neutral = plugin.getConfig().getBoolean("arena.neutral.configured");

        if(!xConfigurated || !oConfigurated || !neutral) {
            sender.sendMessage((String) plugin.getConfig().get("messages.errors.noarenas"));
            return;
        }

        sender.sendMessage((String) plugin.getConfig().get("messages.notifications.gamecreate"));
        plugin.createOXEvent(sender);
    }
}
