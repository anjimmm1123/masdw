package fish.it.fishIt.listeners;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.BaitData;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BaitUseListener implements Listener {

    private final FishIt plugin;

    public BaitUseListener(FishIt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack baitItem = event.getItem();
        ItemStack rodItem = player.getInventory().getItemInMainHand();

        if (rodItem.getType() != Material.FISHING_ROD || baitItem == null || baitItem.getType() == Material.AIR) {
            return;
        }

        String baitId = baitItem.getItemMeta().getPersistentDataContainer().get(plugin.getKey("bait_id"), PersistentDataType.STRING);
        if (baitId == null) {
            return;
        }

        BaitData baitData = plugin.getBaitManager().getBait(baitId);
        if (baitData == null) {
            return;
        }

        if (plugin.getBaitSystem().applyBait(rodItem, baitData)) {
            if (baitItem.getAmount() > 1) {
                baitItem.setAmount(baitItem.getAmount() - 1);
            } else {
                player.getInventory().setItemInOffHand(null);
            }
            player.sendMessage(MessageUtils.colorize("&aBerhasil menerapkan " + baitData.getDisplayName() + "&a!"));
            event.setCancelled(true);
        } else {
            player.sendMessage(MessageUtils.colorize("&cUmpan ini tidak bisa diterapkan pada pancing ini."));
        }
    }
}