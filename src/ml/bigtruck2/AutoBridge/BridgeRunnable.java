package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BridgeRunnable {

    private int blocks;
    public void runnable(NmsArmorStand nmsArmorStand, Player player, Vector vector, AutoBridge autoBridge, int blockNum){
        blocks = blockNum;
        new BukkitRunnable() {
            @Override
            public void run() {
                Location armLoc = nmsArmorStand.getEntLoc();
                armLoc.setY(nmsArmorStand.getEntLoc().getY()-1);
                if(blocks <= 0){
                    cancel();
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.remove(player1, nmsArmorStand.getEntId());
                    }
                }else if(armLoc.getBlock().getType() == Material.AIR){
                    placeBlock(armLoc.getBlock());
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.move(player1, vector);
                    }
                    blocks -= 1;
                }else {
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.move(player1, vector);
                    }
                }

                if(!nmsArmorStand.getEntLoc().add(vector.clone().normalize().multiply(1.5)).getBlock().getType().equals(Material.AIR) || !armLoc.add(vector.clone().normalize().multiply(1.5)).getBlock().getType().equals(Material.AIR)){
                    blocks = 0;
                    cancel();
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.remove(player1, nmsArmorStand.getEntId());
                    }
                }
            }
        }.runTaskTimer(autoBridge,0,1);
    }
    public void placeBlock(Block block){
        block.setType(Material.STAINED_CLAY);
    }
}
