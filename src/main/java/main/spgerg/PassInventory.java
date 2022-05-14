package main.spgerg;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PassInventory {

    private Main plugin;

    private Config config;

    private Inventory minventory;

    public PassInventory(Main plugin){
        this.plugin = plugin;
        config = new Config(plugin);

        List<String> user = config.pass_list;

        Inventory inventory = plugin.getServer().createInventory(null, 9, config.pass_dialog_name);;

        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(config.pass_name);
        itemMeta.setLore(user);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack);

        inventory.setItem(0, empty);
        inventory.setItem(1, empty);
        inventory.setItem(2, empty);
        inventory.setItem(3, empty);

        inventory.setItem(5, empty);
        inventory.setItem(6, empty);
        inventory.setItem(7, empty);
        inventory.setItem(8, empty);

        minventory = inventory;
    }

    public void Open(Player player, List<String> lore){
        Inventory inventory = plugin.getServer().createInventory(null, 9, config.pass_dialog_name);;

        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(config.pass_name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(4, itemStack);

        inventory.setItem(0, empty);
        inventory.setItem(1, empty);
        inventory.setItem(2, empty);
        inventory.setItem(3, empty);

        inventory.setItem(5, empty);
        inventory.setItem(6, empty);
        inventory.setItem(7, empty);
        inventory.setItem(8, empty);

        player.openInventory(inventory);
    }

    public Inventory GetPassInventory(){
        return minventory;
    }
}
