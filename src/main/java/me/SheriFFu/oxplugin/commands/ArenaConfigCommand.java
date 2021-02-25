package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.entity.Player;

public class ArenaConfigCommand extends SubCommand {
    public ArenaConfigCommand(Main plugin) {super(plugin);}

    @Override
    public void execute(Player sender, String[] arguments) {
        String arena;

        if(arguments != null && arguments.length > 1) {
            arena = arguments[1];
        }
        else
        {
            sender.sendMessage((String) plugin.getConfig().get("messages.usage.arenaconfig"));
            return;
        }

        if(plugin.oxGame != null) {
            sender.sendMessage((String) plugin.getConfig().get("messages.errors.editrun"));
            return;
        }

        if(plugin.arenaEditor != null) {
            sender.sendMessage((String) plugin.getConfig().get("messages.errors.alreadyediting"));
            return;
        }

        arena = arena.toLowerCase();

        if(!arena.equals("x") && !arena.equals("o") && !arena.equals("neutral")) {
            sender.sendMessage((String) plugin.getConfig().get("messages.usage.arenaconfig"));
            return;
        }

        plugin.enableArenaEditing(sender, arena);
    }
}
