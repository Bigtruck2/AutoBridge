package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Skull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BridgeRunnable {

    private int blocks;
    public void runnable(NmsArmorStand nmsArmorStand, Player player, Vector vector, AutoBridge autoBridge, int blockNum, int tier){
        blocks = blockNum;
        new BukkitRunnable() {
            @Override
            public void run() {
                Location armLoc = nmsArmorStand.getEntLoc();
                armLoc.setY(nmsArmorStand.getEntLoc().getY()-1);
                if(blocks <= 0){
                    cancel();
                    player.playSound(nmsArmorStand.getEntLoc(), Sound.valueOf(autoBridge.getConfig().getString("sounds.bridger-finish")),1,1);
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.remove(player1, nmsArmorStand.getEntId());
                    }
                }else if(armLoc.getBlock().getType() == Material.AIR){
                    placeBlock(armLoc.getBlock(),player,nmsArmorStand,autoBridge);
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.move(player1, vector);
                    }
                    nmsArmorStand.serverMove(vector);
                    blocks -= 1;
                }else if(armLoc.getBlock().getType() == Material.valueOf(autoBridge.getConfig().getString("Blocks.block"))){
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.move(player1, vector);
                    }
                    nmsArmorStand.serverMove(vector);
                }else {
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.move(player1, vector);
                    }
                    nmsArmorStand.serverMove(vector);
                }

                if(!nmsArmorStand.getEntLoc().add(vector.clone().normalize().multiply(1.35)).getBlock().getType().equals(Material.AIR) || !armLoc.add(vector.clone().normalize().multiply(1.35)).getBlock().getType().equals(Material.AIR)){
                    if(blocks!=0) {
                        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1);
                        itemStack.setDurability((short) 3);
                        NBTUtil.setHeadSkin(itemStack, player, tier, autoBridge);
                        NBTUtil.setCustomTag(itemStack, "blocks", blocks);
                        NBTUtil.setCustomTag(itemStack, "tier", tier);
                        ItemMeta im = itemStack.getItemMeta();
                        im.setDisplayName(autoBridge.getConfig().getString("name-tier" + tier + ".formatted"));
                        im.setLore(NBTUtil.replace(autoBridge, blocks));
                        itemStack.setItemMeta(im);
                        player.getInventory().addItem(itemStack);
                    }
                    blocks = 0;
                    cancel();
                    player.sendMessage(autoBridge.getConfig().getString("Blocks.return"));
                    player.playSound(nmsArmorStand.getEntLoc(), Sound.valueOf(autoBridge.getConfig().getString("sounds.bridger-finish")),1,1);

                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.remove(player1, nmsArmorStand.getEntId());
                    }
                }
            }
        }.runTaskTimer(autoBridge,0,1);
    }
    public void placeBlock(Block block, Player player, NmsArmorStand nmsArmorStand, AutoBridge autoBridge){
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            nmsArmorStand.name(player1, blocks);
        }

        player.playSound(nmsArmorStand.getEntLoc(), Sound.valueOf(autoBridge.getConfig().getString("sounds.bridger-move")),1,1);
        block.setType(Material.valueOf(autoBridge.getConfig().getString("Blocks.block")));
    }
}
