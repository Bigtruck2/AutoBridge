package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoBridge extends JavaPlugin {
    @Override
    public void onEnable() {
        Config config = new Config(this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(this),this);
    }

    @Override
    public void onDisable() {

    }
}
