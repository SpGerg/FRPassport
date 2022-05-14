package main.spgerg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.clip.placeholderapi.PlaceholderAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private Main plugin;

    private PassInventory passInventory;

    private File configFile;

    private File passFile;

    public List<String> pass_list = new ArrayList<String>();

    public String pass_dialog_name;

    public String pass_name;

    public String give_pass_up;

    public String give_pass_down;

    public String deny_give_pass;

    public String nothing_give;

    public Config(Main plugin){
        this.plugin = plugin;
        this.passInventory = plugin.GetPassInventory();

        configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        passFile = new File(plugin.getDataFolder() + File.separator + "passports.yml");

        if(!configFile.exists()){
            ConfigReload();
        }

        Init();
    }

    private void Init(){
        FileConfiguration file = YamlConfiguration.loadConfiguration(configFile);

        for(String s : file.getStringList("pass")){
            pass_list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        pass_dialog_name = ChatColor.translateAlternateColorCodes('&', file.getString("pass_dialog_name"));
        pass_name = ChatColor.translateAlternateColorCodes('&', file.getString("pass_name"));
        give_pass_up = ChatColor.translateAlternateColorCodes('&', file.getString("give_pass_up"));
        give_pass_down = ChatColor.translateAlternateColorCodes('&', file.getString("give_pass_down"));
        deny_give_pass = ChatColor.translateAlternateColorCodes('&', file.getString("deny_give_pass"));
        nothing_give = ChatColor.translateAlternateColorCodes('&', file.getString("nothing_give"));
    }

    public void CreatePassport(Passport passport){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        List<String> pass = new ArrayList<String>();

        pass.add(passport.name);
        pass.add(passport.surname);
        pass.add(passport.work);
        pass.add(String.valueOf(passport.old));
        pass.add(String.valueOf(passport.female));
        pass.add(passport.player.getName());

        file.set("passports." + passport.player.getName(), pass);
        file.set("invites." + passport.player.getName(), false);
        file.set("who_invites." + passport.player.getName(), "");

        try{
            file.save(passFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Passport GetPassport(Player player){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        Passport passport = new Passport();

        passport.name = file.getStringList("passports." + player.getName()).get(0);
        passport.surname = file.getStringList("passports." + player.getName()).get(1);
        passport.work = file.getStringList("passports." + player.getName()).get(2);
        passport.old = file.getStringList("passports." + player.getName()).get(3);
        passport.female = file.getStringList("passports." + player.getName()).get(4);

        return passport;
    }

    public void Invite(Player inviter, Player to_invite){
        String s = give_pass_up.replace("%player%", inviter.getName());

        to_invite.sendTitle(s, give_pass_down);

        SetInvite(to_invite, true);
        SetWhoInvite(to_invite.getName(), inviter.getName());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Deny(to_invite);
            }
        }, 200);
    }

    private void SetWhoInvite(String to_invite, String invite){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        file.set("who_invites." + invite, to_invite);

        try{
            file.save(passFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetInvite(Player invite, boolean status){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        file.set("invites." + invite.getName(), status);

        try{
            file.save(passFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String GetWhoInvite(String who){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        return file.getString("who_invites." + who);
    }

    private boolean isInvite(Player checker){
        FileConfiguration file = YamlConfiguration.loadConfiguration(passFile);

        return file.getBoolean("invites."+checker.getName());
    }

    public void Accept(Player accepter){
        if(isInvite(accepter)){
            Player inviter = Bukkit.getPlayer(GetWhoInvite(accepter.getName()));

            passInventory.Open(inviter, PlaceholderAPI.setPlaceholders(accepter, pass_list));

            SetInvite(accepter, false);
            SetWhoInvite("", accepter.getName());
        }
        else{
            accepter.sendMessage(nothing_give);
        }
    }

    public void Deny(Player decliner){
        String s = deny_give_pass.replace("%player%", decliner.getName());

        if(isInvite(decliner)){
            Player inviter = Bukkit.getPlayer(GetWhoInvite(decliner.getName()));

            SetInvite(decliner, false);
            SetWhoInvite("", decliner.getName());

            inviter.sendTitle(s, "");
        }
        else{
            decliner.sendMessage(nothing_give);
        }
    }

    private void ConfigReload(){
        plugin.getLogger().info("Config file not found!");
        plugin.getLogger().info("Creating new config file...");

        plugin.saveDefaultConfig();

        plugin.getLogger().info("Config file created!");
    }

}
