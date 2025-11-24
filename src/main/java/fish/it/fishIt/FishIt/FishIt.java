package fish.it.fishIt;

import fish.it.fishIt.api.EventsAPI;
import fish.it.fishIt.api.FishAPI;
import fish.it.fishIt.commands.*;
import fish.it.fishIt.database.Database;
import fish.it.fishIt.database.MySQL;
import fish.it.fishIt.database.SQLite;
import fish.it.fishIt.listeners.*;
import fish.it.fishIt.managers.*;
import fish.it.fishIt.system.BaitSystem;
import fish.it.fishIt.system.FishSystem;
import fish.it.fishIt.system.LevelSystem;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class FishIt extends JavaPlugin {
    
    private ConfigManager configManager;
    private Database database;
    private NamespacedKey key;
    private FishManager fishManager;
    private RodManager rodManager;
    private BaitManager baitManager;
    private PlayerDataManager playerDataManager;
    private EconomyManager economyManager;
    private RegionManager regionManager;
    private ShopManager shopManager;
    private QuestManager questManager;
    private CollectionManager collectionManager;
    private GUIManager guiManager;
    private LevelSystem levelSystem;
    private BaitSystem baitSystem;
    private FishSystem fishSystem;
    private FishAPI fishAPI;
    private EventsAPI eventsAPI;

    @Override
    public void onEnable() {
        getLogger().info("Memulai inisialisasi FishIt!...");
        configManager = new ConfigManager(this);
        key = new NamespacedKey(this, "fishit_data");
        setupDatabase();
        getLogger().info("Memuat managers...");
        fishManager = new FishManager(this);
        rodManager = new RodManager(this);
        baitManager = new BaitManager(this);
        playerDataManager = new PlayerDataManager(this);
        economyManager = new EconomyManager(this);
        regionManager = new RegionManager(this);
        shopManager = new ShopManager(this);
        questManager = new QuestManager(this);
        collectionManager = new CollectionManager(this);
        guiManager = new GUIManager(this);
        getLogger().info("Memuat sistem logika...");
        levelSystem = new LevelSystem(this);
        baitSystem = new BaitSystem(this);
        fishSystem = new FishSystem(this);

        getLogger().info("Menyiapkan API...");
        fishAPI = new FishAPI(this);
        eventsAPI = new EventsAPI(this);

        getLogger().info("Mendaftarkan events dan commands...");
        registerCommands();
        registerListeners();

        getLogger().info("FishIt! telah diaktifkan dengan sukses!");
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            getLogger().info("Menyimpan data semua pemain...");
            playerDataManager.saveAllPlayers();
        }

        if (database != null) {
            getLogger().info("Menutup koneksi database...");
            database.close();
        }

        getLogger().info("FishIt! telah dimatikan. Sampai jumpa!");
    }

    private void setupDatabase() {
        String dbType = configManager.getConfig("config").getString("database.type", "sqlite");
        if (dbType.equalsIgnoreCase("mysql")) {
            database = new MySQL(this);
        } else {
            database = new SQLite(this);
        }
        database.createTables();
    }

    private void registerCommands() {
        getCommand("fishit").setExecutor(new FishItCommand(this));
        getCommand("sellfish").setExecutor(new SellCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("fishitadmin").setExecutor(new AdminCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new FishingListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new BaitUseListener(this), this);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public FishManager getFishManager() {
        return fishManager;
    }

    public RodManager getRodManager() {
        return rodManager;
    }

    public BaitManager getBaitManager() {
        return baitManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public LevelSystem getLevelSystem() {
        return levelSystem;
    }

    public BaitSystem getBaitSystem() {
        return baitSystem;
    }

    public FishSystem getFishSystem() {
        return fishSystem;
    }

    public FishAPI getFishAPI() {
        return fishAPI;
    }

    public EventsAPI getEventsAPI() {
        return eventsAPI;
    }
}