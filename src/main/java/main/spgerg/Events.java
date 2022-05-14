package main.spgerg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Events implements Listener{

    private PassInventory passInventory;

    public Events(PassInventory passInventory){
        this.passInventory = passInventory;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e){
        if(passInventory.GetPassInventory().getItem(2).equals(e.getClickedInventory().getItem(2))){
            e.setCancelled(true);
        }
    }
}
