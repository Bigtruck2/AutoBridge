package ml.bigtruck2.AutoBridge;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;

public class ItemPickupListener implements Listener {
    private AutoBridge autoBridge;
    public ItemPickupListener(AutoBridge autoBridge){
        this.autoBridge = autoBridge;
    }
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        // Get the player who picked up the item
        // Get the item that was picked up
        ItemStack item = event.getItem().getItemStack();
        formatter(item);
        // Log the pickup event

        // Example of additional logic
        // You could add custom logic here, e.g., modify item properties, give bonuses, etc.
    }
    public void formatter(ItemStack item){
        if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier1.unformatted"))) {
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(autoBridge.getConfig().getString("name-tier1.formatted"));
            im.setLore(NBTUtil.replace(autoBridge,autoBridge.getConfig().getInt("Blocks.tier1")));
            item.setItemMeta(im);
            NBTUtil.setHeadSkin(item, autoBridge.getConfig().getString("skins.default-tier1"));
            NBTUtil.setCustomTag(item,"blocks", autoBridge.getConfig().getInt("Blocks.tier1"));
            NBTUtil.setCustomTag(item,"tier", 1);

        }else if(item.getType().equals(Material.SKULL_ITEM) && item.getItemMeta().getDisplayName().equals(autoBridge.getConfig().getString("name-tier2.unformatted"))){
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(autoBridge.getConfig().getString("name-tier2.formatted"));
            im.setLore(NBTUtil.replace(autoBridge,autoBridge.getConfig().getInt("Blocks.tier2")));
            item.setItemMeta(im);
            NBTUtil.setHeadSkin(item, autoBridge.getConfig().getString("skins.default-tier2"));
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
            if (item != null && item.getType() != null && item.getAmount() != 0) {
                // Log or handle the item taken from the chest
                formatter(item);
                net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);

            }
    }



    public static ItemStack createCustomHead(String base64) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        NBTUtil.setHeadSkin(head, base64);
        return head;
    }

}
