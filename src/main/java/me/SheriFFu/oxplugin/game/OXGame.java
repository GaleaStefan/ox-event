package me.SheriFFu.oxplugin.game;

import me.SheriFFu.oxplugin.Main;
import me.SheriFFu.oxplugin.arena.Region;
import me.SheriFFu.oxplugin.questions.Question;
import me.SheriFFu.oxplugin.questions.QuestionsFileReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OXGame {
    private final Main plugin;

    private final Player host;

    public boolean started;
    public boolean locked;
    public boolean randomQuestions = true;

    public int rounds = 10;
    public int currentRound;
    public int roundTimer = 15;

    private List<Question> questions;
    public Question currentQuestion;
    private final String questionFormat;

    public ItemStack[] reward;

    private Region xRegion;
    private Region oRegion;
    private Region neutralRegion;

    private List<Player> players;



    public OXGame(Main plugin, Player host) {
        this.plugin = plugin;
        this.host = host;
        this.locked = true;
        this.started = false;

        currentRound = 0;
        players = new ArrayList<>();

        reward = null;

        List<String> questionLines = (List<String>) plugin.getConfig().getList("messages.question");
        String temp = "";

        for(String line : questionLines) {
            temp += line + "\n";
        }

        questionFormat = temp;

        Bukkit.getConsoleSender().sendMessage("[DEBUG]" + temp);
    }

    public void saveGameSettings(int rounds, boolean random, int timer) {
        this.rounds = rounds;
        this.randomQuestions = random;
        this.roundTimer = timer;
    }
    public void saveRoundRewards(ItemStack[] rewards) {
        this.reward = rewards;
    }

    public Player getHost() {
        return host;
    }

    public List<Player> getPlayers() { return players; }
    public void addPlayer(Player player) {players.add(player);}
    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void setLocked(boolean locked) { this.locked = locked; }
    public boolean isLocked() { return locked; }

    private void stopOXEvent() {
        plugin.closeOXEvent();
    }

    private void startNextRound() {
        if(currentRound == rounds) {
            stopOXEvent();
            return;
        }

        if(randomQuestions) {
            askQuestion();
        }
        else {
            currentQuestion = null;
        }
    }

    private void giveReward(Player player) {
        for(ItemStack itemStack : reward) {
            player.getInventory().addItem(itemStack);
        }
    }

    private void getRightPlayers() {
        Region correctRegion = currentQuestion.correct.toLowerCase().equals("x") ? xRegion : oRegion;
        Location center = neutralRegion.getRegionCenter();

        boolean[] remove = new boolean[players.size()];
        for(int i = 0; i < players.size(); i++) {
            remove[i] = false;
        }

        for(Player player : players) {
            if(correctRegion.isLocationInRegion(player.getLocation())) {
                giveReward(player);
                player.teleport(center);
            }
            else {
                remove[players.indexOf(player)] = true;
                Bukkit.dispatchCommand(player, "spawn");
                player.sendMessage((String) plugin.getConfig().get("messages.notifications.wronganswer"));
                host.sendMessage(plugin.getConfig().getString("messages.notifications.wronganswerhost").replace("%player%", player.getDisplayName()));
            }
        }

        for(int i = 0; i < players.size(); i++) {
            if(remove[i]) {
                players.remove(i);
            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                if(randomQuestions) {
                    host.sendMessage((String) plugin.getConfig().get("message.notifications.nextround"));

                    for (Player player : players) {
                        player.sendMessage((String) plugin.getConfig().get("message.notifications.nextround"));
                    }
                }

                startNextRound();
            }
        }.runTaskLater(plugin, 20 * 5);
    }

    public void askQuestion(Question question) {
        currentRound++;

        currentQuestion = question;

        String message = questionFormat.replace("%question%", question.text);

        for(Player player : players) {
            player.sendMessage(message);
        }
        host.sendMessage(message);

        Bukkit.getConsoleSender().sendMessage("[DEBUG]" + message);
        Bukkit.getConsoleSender().sendMessage("[DEBUG]" + question.text);

        new BukkitRunnable() {

            @Override
            public void run() {
                getRightPlayers();
            }
        }.runTaskLater(plugin, 20 * roundTimer);
    }

    private void askQuestion() {
        Random randomizer = new Random();
        int index = randomizer.nextInt(questions.size() - 1);

        askQuestion(questions.get(index));
        questions.remove(index);
    }

    public void startGame() {
        host.sendMessage((String) plugin.getConfig().get("messages.notifications.gamestart"));
        started = true;
        locked = true;

        xRegion = new Region("x", plugin.getConfig());
        oRegion = new Region("o", plugin.getConfig());
        neutralRegion = new Region("neutral", plugin.getConfig());

        for(Player player : players) {
            player.sendMessage((String) plugin.getConfig().get("messages.notifications.gamestart"));
        }

        if(randomQuestions) {
            questions = QuestionsFileReader.readQuestions(plugin.questionsConfig);

            if(questions.size() < rounds) {
                rounds = questions.size();
                host.sendMessage((String) plugin.getConfig().get("messages.notifications.roundchange"));
            }

            askQuestion();
        }
    }
}
