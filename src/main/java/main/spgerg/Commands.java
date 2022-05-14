package main.spgerg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Commands implements CommandExecutor {

    private Config config;
    private PassInventory passInventory;

    public Commands(Config config, PassInventory passInventory){
        this.passInventory = passInventory;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(sender instanceof Player){
            if(command.getName().equals("pass")){
                if(args.length == 0){
                    passInventory.Open(p, PlaceholderAPI.setPlaceholders(p, config.pass_list));
                }
                if(args.length == 1){
                    if(args[0].equals("accept")){
                        config.Accept(p);
                    }
                    else if(args[0].equals("deny")){
                        config.Deny(p);
                    }
                    else{
                        config.Invite(p, Bukkit.getPlayer(args[0]));
                    }
                }
                if(args.length >= 5){
                    if(args[0].equals("create")){
                        Passport passport = new Passport();
                        passport.name = args[1];
                        passport.surname = args[2];
                        passport.work = args[3];
                        passport.old = args[4];
                        passport.female = args[5];
                        passport.player = p;

                        config.CreatePassport(passport);
                    }
                }
            }
        }

        return false;
    }
}
