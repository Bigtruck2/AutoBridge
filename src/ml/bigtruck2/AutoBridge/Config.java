package ml.bigtruck2.AutoBridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Config {
    private AutoBridge plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public Config(AutoBridge plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(),"config.yml");
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream inputStream = this.plugin.getResource("config.yml");
        if(inputStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getConfig(){
        if(this.dataConfig == null){
            reloadConfig();
        }
        return this.dataConfig;
    }
    public void saveConfig(){
        if(this.dataConfig == null || this.configFile == null){
            return;
        }
        try {
            this.getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't save config file"+ this.configFile,e);
        }
    }
    public void saveDefaultConfig(){
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        }
        if(!this.configFile.exists()){
            this.plugin.saveResource("config.yml",false);
        }
    }

}

