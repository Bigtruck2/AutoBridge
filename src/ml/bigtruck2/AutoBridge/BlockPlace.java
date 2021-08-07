package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

public class BlockPlace implements Listener {
    private final AutoBridge autoBridge;
    public BlockPlace(AutoBridge autoBridge){
        this.autoBridge = autoBridge;
    }
    @EventHandler
    public void blockPlace(BlockPlaceEvent e){
        if(e.getBlockPlaced().getType() == Material.SKULL){
            if (e.getItemInHand().getDurability() == 2||e.getItemInHand().getDurability() == 4) {
                if (e.getBlock().getRelative(BlockFace.DOWN).equals(e.getBlockAgainst())) {

                    Player player = e.getPlayer();
                    e.setCancelled(true);


                    Location location = e.getBlock().getLocation();
                    location.setY(location.getY() - 1.35);
                    Location loc = player.getLocation();
                    loc.setY(location.getY());

                    location.setX(location.getX() + .5);
                    location.setZ(location.getZ() + .5);
                    Vector vector = location.toVector().subtract(loc.toVector()).normalize();
                    vector.multiply(autoBridge.getConfig().getDouble("speed"));

                    NmsArmorStand nmsArmorStand = new NmsArmorStand(player);
                    nmsArmorStand.spawn(e.getBlock().getLocation(), vector, e.getPlayer());
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        nmsArmorStand.show(player1, e.getItemInHand().getDurability());
                    }
                    Location armLoc = nmsArmorStand.getEntLoc();
                    armLoc.setY(nmsArmorStand.getEntLoc().getY()-1);
                    if(!nmsArmorStand.getEntLoc().add(vector.clone().multiply(3)).getBlock().getType().equals(Material.AIR) || !armLoc.add(vector.clone().multiply(3)).getBlock().getType().equals(Material.AIR)){
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            nmsArmorStand.remove(player1, nmsArmorStand.getEntId());
                        }
                        return;
                    }
                    BridgeRunnable bridgeRunnable = new BridgeRunnable();
                    if (e.getItemInHand().getDurability() == 2) {
                        bridgeRunnable.runnable(nmsArmorStand, player, vector, autoBridge, 8);
                    }else if(e.getItemInHand().getDurability() == 4){
                        bridgeRunnable.runnable(nmsArmorStand, player, vector, autoBridge, 16);
                    }
                    if(player.getItemInHand().getAmount() > 1) {
                        player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                    }else {
                        player.getInventory().remove(player.getItemInHand());
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }
        }
    }


