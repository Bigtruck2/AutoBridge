package ml.bigtruck2.AutoBridge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CommandBridge implements CommandExecutor {
    AutoBridge autoBridge;
    public CommandBridge(AutoBridge autoBridge){
        this.autoBridge = autoBridge;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equals("bridge")){
            if(Objects.equals(strings[0], "1")){
                autoBridge.getCustomConfig().set(strings[2]+".tier1",autoBridge.getConfig().getString("skins."+strings[1]));

                try {
                    autoBridge.saveCustomConfig();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }else if(Objects.equals(strings[0], "2")){
                autoBridge.getCustomConfig().set(strings[2]+".tier2",autoBridge.getConfig().getString("skins."+strings[1]));
                try {
                    autoBridge.saveCustomConfig();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        return false;
    }
}
