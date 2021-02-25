package me.SheriFFu.oxplugin.configuration;

import me.SheriFFu.oxplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GameConfigInventory {
    public Inventory configInventory;
    private final Main plugin;

    public GameConfigInventory(Main plugin) {
        this.plugin = plugin;

        configInventory = Bukkit.createInventory(null, 27, "Configurare Eveniment");
        addConfigItems();
    }

    private void addConfigItems() {
        ItemStack plus = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta plusMeta = plus.getItemMeta();
        plusMeta.setDisplayName("§r§aAdauga");
        plus.setItemMeta(plusMeta);

        ItemStack minus = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta minusMeta = plus.getItemMeta();
        minusMeta.setDisplayName("§r§cScade");
        minus.setItemMeta(minusMeta);

        for(int i = 0; i < 9; i++) {
            configInventory.setItem(i, plus);
            configInventory.setItem(i + 18, minus);
        }

        ItemStack rounds = new ItemStack(Material.BELL, plugin.oxGame.rounds);
        ItemMeta roundsMeta = rounds.getItemMeta();
        roundsMeta.setDisplayName("§rNumar runde");
        roundsMeta.setLore(Arrays.asList("§r§aVerde +", "§r§cRosu -"));
        rounds.setItemMeta(roundsMeta);

        int randomCount = plugin.oxGame.randomQuestions == true ? 1 : 2;
        ItemStack random = new ItemStack(Material.REPEATER, randomCount);
        ItemMeta randomMeta = random.getItemMeta();
        randomMeta.setDisplayName("§rIntrebari aleatorii");
        randomMeta.setLore(Arrays.asList("§r§aVerde +", "§r§cRosu -", "§r§a1 - Da §c2 - Nu"));
        random.setItemMeta(randomMeta);

        ItemStack answerTimer = new ItemStack(Material.CLOCK, plugin.oxGame.roundTimer);
        ItemMeta answerMeta = answerTimer.getItemMeta();
        answerMeta.setDisplayName("§rTimp alegere raspuns(secunde)");
        answerMeta.setLore(Arrays.asList("§r§aVerde +", "§r§cRosu -"));
        answerTimer.setItemMeta(answerMeta);

        ItemStack reward = new ItemStack(Material.CHEST, 1);
        ItemMeta rewardMeta = reward.getItemMeta();
        rewardMeta.setDisplayName("§rPremii");
        rewardMeta.setLore(Arrays.asList("§r§aApasa pentru a modifica premiile"));
        reward.setItemMeta(rewardMeta);

        ItemStack nothing = new ItemStack(Material.BARRIER, 1);
        ItemMeta nothingMeta = nothing.getItemMeta();
        nothingMeta.setDisplayName("§r§aSlot liber");
        nothing.setItemMeta(nothingMeta);

        for(int i = 13; i <= 17; i++)
        {
            configInventory.setItem(i, nothing);
        }

        configInventory.setItem(9, rounds);
        configInventory.setItem(10, random);
        configInventory.setItem(11, answerTimer);
        configInventory.setItem(12, reward);
    }
}
