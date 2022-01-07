package space.maxus.plib.model;

import net.kyori.adventure.text.Component;
import net.minecraft.world.item.Item;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.maxus.plib.action.ClickContext;
import space.maxus.plib.action.TypedEventResult;
import space.maxus.plib.settings.ItemSettings;

import java.util.List;

public class ItemModel {

    public ItemModel(ItemSettings settings) {

    }

    /**
     * Called when a tooltip is appended to item instance for first time
     * @param stack the default stack to which the tooltip will be appended. Note, that you should not add the tooltip to it yourself
     * @param tooltips the tooltips to be added. Modify this list to change the item tooltips
     */
    public void appendTooltip(ItemStack stack, List<Component> tooltips) {

    }

    /**
     * Called when item is used by player
     * @param player player that used the item
     * @param used the used item stack
     * @param ctx click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, ClickContext ctx) {
        return TypedEventResult.success(used);
    }

    /**
     * Called when item is used by player on block
     * @param player player that used the item
     * @param used the used item stack
     * @param block block on which the item was used
     * @param ctx click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, Block block, ClickContext ctx) {
        return TypedEventResult.success(used);
    }

    /**
     * Called when item is used by player on entity
     * @param player player that used the item
     * @param used the used item stack
     * @param target target entity on which the item was used
     * @param ctx click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, Entity target, ClickContext ctx) {
        return TypedEventResult.success(used);
    }
}
