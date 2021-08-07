package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoBridge extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(this),this);
        Bukkit.getServer().getPluginManager().registerEvents(new FireBurnStop(),this);
    }

    @Override
    public void onDisable() {

    }
}
