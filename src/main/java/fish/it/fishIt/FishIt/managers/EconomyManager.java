package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    private final FishIt plugin;
    private Economy economy;

    public EconomyManager(FishIt plugin) {
        this.plugin = plugin;
        setupEconomy();
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public boolean hasEconomy() {
        return economy != null;
    }

    public void depositPlayer(org.bukkit.OfflinePlayer player, double amount) {
        if (hasEconomy()) {
            economy.depositPlayer(player, amount);
        }
    }

    public void withdrawPlayer(org.bukkit.OfflinePlayer player, double amount) {
        if (hasEconomy()) {
            economy.withdrawPlayer(player, amount);
        }
    }

    public double getBalance(org.bukkit.OfflinePlayer player) {
        if (hasEconomy()) {
            return economy.getBalance(player);
        }
        return 0;
    }

    public String format(double amount) {
        if (hasEconomy()) {
            return economy.format(amount);
        }
        return String.valueOf(amount);
    }
}