package space.maxus.plib.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.maxus.plib.model.ItemModel;
import space.maxus.plib.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackSizeMaintainer implements Listener {
    private void println(String str) {
        System.out.println(str);
    }
    @EventHandler
    public void onPickup(final @NotNull EntityPickupItemEvent e) {
        println("EVENT CALLED");
        if(!(e.getEntity() instanceof Player player))
            return;

        var item = e.getItem().getItemStack();
//        var model = ItemModel.of(item);
//        if(model == null)
//            return;
        e.setCancelled(true);
        var maxSize = 10; // model.getMaxStackSize();
        var inv = player.getInventory();
        var slots = Arrays.stream(inv.getStorageContents()).filter(stack -> stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0).toList().size();
        println("SLOTS "+slots);
        if(slots == 0) {
            return;
        }
        var distributed = Utils.distributeItemsEvenly(maxSize, item);
        println("DISTRIBUTED SIZE "+distributed.size());
        var drop = new ArrayList<ItemStack>();
        if(distributed.size() > slots) {
            println("DISTRIBUTED SIZE IS MORE");
            var neededToDrop = distributed.size() - slots;
            int pos = distributed.size() - 1;
            for (int i = 0; i < neededToDrop; i++) {
                if(pos < 0)
                    break;
                var popped = distributed.remove(pos);
                drop.add(popped);
                pos--;
                println("POPPING! POS "+pos);
            }
        }
        println("REQUIRED TO DROP "+drop);
        for(var ditem: drop) {
            player.getWorld().dropItem(player.getLocation(), ditem);
            println("DROPPING");
        }

        for(var left: distributed) {
            println("GIVING ITEM");
            var nextFree = player.getInventory().firstEmpty();
            if(nextFree <= -1) {
                player.getWorld().dropItem(player.getLocation(), left);
                continue;
            }
            var contents = player.getInventory().getStorageContents();
            contents[nextFree] = left;
            player.getInventory().setStorageContents(contents);
        }

        e.getItem().remove();
    }
}
