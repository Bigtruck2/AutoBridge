package ml.bigtruck2.AutoBridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;


public class FireBurnStop implements Listener {
    @EventHandler
    public void fireBurnEvent(BlockBurnEvent e){
        e.setCancelled(true);
    }
}
