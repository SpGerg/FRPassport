package main.spgerg;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;

public final class Main extends JavaPlugin {

    private PassInventory passInventory= new PassInventory(this);

    private Config config = new Config(this);

    @Override
    public void onEnable() {
        getServer().getPluginCommand("pass").setExecutor(new Commands(config, passInventory));
        getServer().getPluginManager().registerEvents(new Events(passInventory), this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new Placeholders(config).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PassInventory GetPassInventory(){
        return passInventory;
    }
}
