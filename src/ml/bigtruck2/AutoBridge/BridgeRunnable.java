package ml.bigtruck2.AutoBridge;

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
                    nmsArmorStand.remove(player, nmsArmorStand.getEntId());
                }else if(armLoc.getBlock().getType() == Material.AIR){
                    placeBlock(armLoc.getBlock());
                    nmsArmorStand.move(player,vector);
                    blocks -= 1;
                }else {
                    nmsArmorStand.move(player,vector);
                }

                if(!nmsArmorStand.getEntLoc().add(vector.clone().multiply(3)).getBlock().getType().equals(Material.AIR) || !armLoc.add(vector.clone().multiply(3)).getBlock().getType().equals(Material.AIR)){
                    System.out.println("crashed");
                    blocks = 0;
                    cancel();
                    nmsArmorStand.remove(player, nmsArmorStand.getEntId());
                }
            }
        }.runTaskTimer(autoBridge,0,1);
    }
    public void placeBlock(Block block){
        block.setType(Material.STAINED_CLAY);
    }
}
