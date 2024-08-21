package ml.bigtruck2.AutoBridge;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class AutoBridge extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        Config config = new Config(this);
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlace(this),this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
        this.getCommand("bridge").setExecutor(new CommandBridge(this));
        createCustomConfig();
    }

    @Override
    public void onDisable() {

    }
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
    public void saveCustomConfig() throws IOException {
       customConfig.save(customConfigFile);
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "skins.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("skins.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        /* User Edit:
            Instead of the above Try/Catch, you can also use
            YamlConfiguration.loadConfiguration(customConfigFile)
        */
    }
}
