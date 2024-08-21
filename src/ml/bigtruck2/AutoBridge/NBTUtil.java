package ml.bigtruck2.AutoBridge;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sun.org.apache.xerces.internal.xs.StringList;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NBTUtil {
    public static void setCustomTag(ItemStack item, String tagName, int tagValue) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        tag.setInt(tagName, tagValue);
        nmsItem.setTag(tag);
        item.setItemMeta(CraftItemStack.asBukkitCopy(nmsItem).getItemMeta());
    }

    public static int getCustomTag(ItemStack item, String tagName) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem.hasTag()) {
            NBTTagCompound tag = nmsItem.getTag();
            return tag.hasKey(tagName) ? tag.getInt(tagName) : 0;
        }
        return 0;
    }
    public static void setHeadSkin(ItemStack head, String base64) {
        if (head.getType() != Material.SKULL_ITEM) {
            throw new IllegalArgumentException("ItemStack must be a skull.");
        }

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta == null) {
            throw new IllegalStateException("ItemMeta cannot be null.");
        }

        try {
            // Set the texture using reflection
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", base64));
            profileField.set(meta, profile);
            head.setItemMeta(meta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<String> replace(AutoBridge autoBridge, int blocks){
        List<String> list = new ArrayList<>();
        for(String lore: autoBridge.getConfig().getStringList("lore")) {
            list.add(lore.replaceAll("<blocks>", String.valueOf(blocks)));
        }
        return list;
    }
}
