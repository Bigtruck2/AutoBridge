package ml.bigtruck2.AutoBridge;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemPickupListener implements Listener {
    private AutoBridge autoBridge;
    public ItemPickupListener(AutoBridge autoBridge){
        this.autoBridge = autoBridge;
    }
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        // Get the player who picked up the item
        Player player = event.getPlayer();
        // Get the item that was picked up
        ItemStack item = event.getItem().getItemStack();

        // Log the pickup event
        if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier1.unformatted"))) {
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(autoBridge.getConfig().getString(""));
            im.setLore(autoBridge.getConfig().getStringList("lore"));
            item.setItemMeta(im);
        }else if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier2.unformatted"))){
            ItemMeta im = item.getItemMeta();
            im.setLore(autoBridge.getConfig().getStringList("lore"));
            item.setItemMeta(im);
        }
        // Example of additional logic
        // You could add custom logic here, e.g., modify item properties, give bonuses, etc.
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        // Check if the clicked inventory is a chest or other container
            // Get the player who clicked the inventory
            Player player = (Player) event.getWhoClicked();

            // Get the item being taken from the inventory

            // Check if the item is not null and has a type
            if (item != null && item.getType() != null && item.getAmount() != 0) {
                // Log or handle the item taken from the chest
                player.sendMessage("You took: " + item.getType().name() + " with amount: " + item.getAmount() + " from a chest.");
            }
    }
}
