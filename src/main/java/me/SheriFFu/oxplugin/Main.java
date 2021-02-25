package me.SheriFFu.oxplugin;

import me.SheriFFu.oxplugin.arena.ArenaEditor;
import me.SheriFFu.oxplugin.commands.BaseCommand;
import me.SheriFFu.oxplugin.commands.TabCompleteCommand;
import me.SheriFFu.oxplugin.configuration.GameConfigurationHandler;
import me.SheriFFu.oxplugin.game.OXGame;
import me.SheriFFu.oxplugin.listeners.ArenaConfigListener;
import me.SheriFFu.oxplugin.listeners.InventoryInteractionListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    public FileConfiguration questionsConfig;
    public FileConfiguration logsConfig;

    public OXGame oxGame;
    public ArenaEditor arenaEditor;
    public GameConfigurationHandler configHandler;

    private ArenaConfigListener editorListener;
    private InventoryInteractionListener inventoryListener;

    @Override
    public void onEnable() {
        // File Configurations
        questionsConfig = loadQuestionsFile();
        logsConfig = loadLogsFile();

        // Commands registering
        this.getCommand("ox").setExecutor(new BaseCommand(this));
        this.getCommand("ox").setTabCompleter(new TabCompleteCommand());

        // Variables initialization
        oxGame = null;
        arenaEditor = null;
        configHandler = null;
    }

    @Override
    public void onDisable() {
        try{
            questionsConfig.save("questions.yml");
            logsConfig.save("logs.yml");

            HandlerList.unregisterAll(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadPlugin(Player player) {
        if(arenaEditor != null) {
            disableArenaEditing();
        }

        if(configHandler != null) {
            disableGameEditor();
        }

        if(oxGame != null) {
            closeOXEvent();
        }

        try{
            questionsConfig.save("questions.yml");
            logsConfig.save("logs.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        questionsConfig = loadQuestionsFile();
        logsConfig = loadLogsFile();

        oxGame = null;
        arenaEditor = null;
        configHandler = null;

        player.sendMessage(getConfig().getString("messages.notifications.reload"));
    }

    public void createOXEvent(Player host) {
        oxGame = new OXGame(this, host);
        arenaEditor = null;
        configHandler = null;
    }
    public void closeOXEvent() {
        oxGame = null;
    }

    public void enableArenaEditing(Player handler, String arena) {
        arenaEditor = new ArenaEditor(this, handler, arena);
        editorListener = new ArenaConfigListener(this);
        getServer().getPluginManager().registerEvents(editorListener, this);
    }
    public void disableArenaEditing() {
        arenaEditor = null;
        //PlayerInteractEvent.getHandlerList().unregister(editorListener);
        HandlerList.unregisterAll(editorListener);

        editorListener = null;
    }

    public void enableGameEditor(Player source) {
        if(configHandler != null) {
            source.openInventory(configHandler.config.configInventory);
        }
        else {
            configHandler = new GameConfigurationHandler(this, source);
            inventoryListener = new InventoryInteractionListener(this);
            getServer().getPluginManager().registerEvents(inventoryListener, this);
        }
    }
    public void disableGameEditor() {
        configHandler = null;
        //InventoryCloseEvent.getHandlerList().unregister(inventoryListener);
        HandlerList.unregisterAll(inventoryListener);

        inventoryListener = null;
    }

    public void startOXGame() {
        if(configHandler != null) {
            disableGameEditor();
        }

        if(arenaEditor != null) {
            disableArenaEditing();
        }

        oxGame.startGame();
    }

    FileConfiguration loadQuestionsFile() {
        File questionsFile = new File(getDataFolder(), "questions.yml");

        if(!questionsFile.exists()) {
            questionsFile.getParentFile().mkdirs();
            saveResource("questions.yml", false);
        }

        FileConfiguration fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(questionsFile);
            return fileConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
    FileConfiguration loadLogsFile() {
        File logsFile = new File(getDataFolder(), "logs.yml");

        if(!logsFile.exists()) {
            logsFile.getParentFile().mkdirs();
            saveResource("logs.yml", false);
        }

        FileConfiguration fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(logsFile);
            return fileConfiguration;
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
