package main.spgerg;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private Config config;

    public Placeholders(Config config) {
        this.config = config;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (identifier.equals("name")) {
            return config.GetPassport(p).name;
        }
        if (identifier.equals("surname")) {
            return config.GetPassport(p).surname;
        }
        if (identifier.equals("work")) {
            return config.GetPassport(p).work;
        }
        if (identifier.equals("old")) {
            return config.GetPassport(p).old;
        }
        if (identifier.equals("male")) {
            return config.GetPassport(p).female;
        }
        return null;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params){
        if(player != null & player.isOnline()){
            return onPlaceholderRequest(player.getPlayer(), params);
        }

        return null;
    }


    @Override
    public @NotNull String getIdentifier() {
        return "pass";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SpGerg";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }
}
