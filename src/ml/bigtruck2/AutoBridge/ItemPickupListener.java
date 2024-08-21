package ml.bigtruck2.AutoBridge;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemPickupListener implements Listener {
    private final AutoBridge autoBridge;
    public ItemPickupListener(AutoBridge autoBridge){
        this.autoBridge = autoBridge;
    }
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        // Get the player who picked up the item
        // Get the item that was picked up
        ItemStack item = event.getItem().getItemStack();
        formatter(item, event.getPlayer());
        // Log the pickup event

        // Example of additional logic
        // You could add custom logic here, e.g., modify item properties, give bonuses, etc.
    }
    public void formatter(ItemStack item, Player player){
        if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier1.unformatted"))) {
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(autoBridge.getConfig().getString("name-tier1.formatted"));
            im.setLore(NBTUtil.replace(autoBridge,autoBridge.getConfig().getInt("Blocks.tier1")));
            item.setItemMeta(im);

            NBTUtil.setHeadSkin(item, player,1,autoBridge);
            NBTUtil.setCustomTag(item,"blocks", autoBridge.getConfig().getInt("Blocks.tier1"));
            NBTUtil.setCustomTag(item,"tier", 1);

        }else if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier2.unformatted"))){
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(autoBridge.getConfig().getString("name-tier2.formatted"));
            im.setLore(NBTUtil.replace(autoBridge,autoBridge.getConfig().getInt("Blocks.tier2")));
            item.setItemMeta(im);
            NBTUtil.setHeadSkin(item, player,2,autoBridge);
            NBTUtil.setCustomTag(item,"blocks", autoBridge.getConfig().getInt("Blocks.tier2"));
            NBTUtil.setCustomTag(item,"tier", 2);

        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        // Check if the clicked inventory is a chest or other container
            // Get the player who clicked the inventory

            // Get the item being taken from the inventory

            // Check if the item is not null and has a type
            if (item != null && item.getType() != null && item.getAmount() != 0 && event.getWhoClicked().getType().equals(EntityType.PLAYER)) {
                // Log or handle the item taken from the chest
                formatter(item, (Player) event.getWhoClicked());
            }
    }





}
