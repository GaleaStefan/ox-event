package me.SheriFFu.oxplugin.commands;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class BaseCommand implements CommandExecutor {
    private final Main plugin;

    private HashMap<String, String> subcommands;
    
    public BaseCommand(Main plugin) {
        this.plugin = plugin;

        String path = "me.SheriFFu.oxplugin.commands.";
        subcommands = new HashMap<String, String>();
        subcommands.put("help", path + "HelpCommand");
        subcommands.put("arenaconfig", path + "ArenaConfigCommand");
        subcommands.put("reload", path + "ReloadCommand");
        subcommands.put("join", path + "JoinCommand");
        subcommands.put("quit", path + "QuitCommand");
        subcommands.put("kick", path + "KickCommand");
        subcommands.put("create", path + "CreateCommand");
        subcommands.put("close", path + "CloseCommand");
        subcommands.put("state", path + "StateCommand");
        subcommands.put("start", path + "GameStartCommand");
        subcommands.put("gameconfig", path + "ConfigGameCommand");
        subcommands.put("askquestion", path + "AskQuestionCommand");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String arguments[]) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("messages.errors.noconsole"));
            return true;
        }

        if(arguments.length == 0 || subcommands.get(arguments[0].toLowerCase()) == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.usage.base"));
            return true;
        }

        if(!sender.hasPermission("ox.command." + arguments[0].toLowerCase())) {
            sender.sendMessage(plugin.getConfig().getString("messages.errors.noperm"));
            return true;
        }

        try {
            Class cmdClass = Class.forName(subcommands.get(arguments[0].toLowerCase()));
            Class classArgs[] = new Class[1];
            classArgs[0] = Main.class;

            SubCommand subCommand = (SubCommand) cmdClass.getDeclaredConstructor(classArgs).newInstance(plugin);

            if(arguments.length == 0) {
                arguments = null;
            }

            subCommand.execute((Player)sender, arguments);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}
