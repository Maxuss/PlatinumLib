package space.maxus.plib.model;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.action.ClickContext;
import space.maxus.plib.action.TypedEventResult;
import space.maxus.plib.effects.ApplicableEffect;
import space.maxus.plib.exceptions.NBTModificationException;
import space.maxus.plib.exceptions.Provoker;
import space.maxus.plib.registry.Identifier;
import space.maxus.plib.registry.Registry;
import space.maxus.plib.settings.FoodSettings;
import space.maxus.plib.settings.ItemSettings;
import space.maxus.plib.settings.Rarity;
import space.maxus.plib.utils.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Class, representing a mod-like item. Can be overriden, as well as some of it's methods, to give the desired item behaviour
 */
public class ItemModel {
    static final HashMap<UUID, ItemStack> _remainders = new HashMap<>();

    /**
     * Gets item model from default stack
     * @param stack stack to be searched for model identifier
     * @return found item model, or null if item does not have platinum_data/model nbt tag
     */
    public static ItemModel of(ItemStack stack) {
        var nms = CraftItemStack.asNMSCopy(stack);
        if(nms.tag == null)
            return null;
        if(!nms.tag.contains("platinum_data"))
            return null;
        var dataTag = (CompoundTag) nms.tag.get("platinum_data");
        assert dataTag != null;
        var id = Identifier.parse(dataTag.getString("model"));
        return Registry.find(Registry.ITEM, id);
    }

    /**
     * Food properties of item
     */
    @Getter
    private final FoodSettings foodSettings;
    /**
     * Custom model id of item
     */
    @Getter
    private final int customModelData;

    /**
     * Remainder in crafting grid after crafting item
     */
    @Getter
    private final ItemStack craftingRemainder;

    /**
     * Max allowed stack size of item
     */
    @Getter
    private final int maxStackSize;
    /**
     * Rarity of item
     */
    @Getter
    private final Rarity rarity;
    /**
     * Material of item
     */
    @Getter
    private final Material type;

    @NotNull
    private final ItemStack defaultStack;

    /**
     * Default item stack of this item, ready for user modifications.
     * <br/>
     * Note, that it is cloned, so theres no way to modify default generated stack by itself
     */
    public final @NotNull ItemStack getDefaultStack() {
        return defaultStack.clone();
    }

    /**
     * Creates a new item model with provided settings
     *
     * @param settings item settings to use
     */
    public ItemModel(ItemSettings settings, Identifier id) {
        this.foodSettings = settings.getFoodSettings();
        this.customModelData = settings.getCustomModelData();
        this.craftingRemainder = settings.getCraftingRemainder();
        this.maxStackSize = Math.max(settings.getMaxStackSize(), 1);
        this.rarity = settings.getRarity();
        this.type = settings.getType();

        var stack = new ItemStack(type);
        // nbt modifications
        var nmsStack = CraftItemStack.asNMSCopy(stack);
        if(nmsStack.tag == null)
            Provoker.provoke(new NBTModificationException("Error modifying stack nbt! The main compound tag was null!"));
        var nbt = nmsStack.tag.copy();

        // food nbt
        var food = new CompoundTag();
        food.putInt("hunger", foodSettings.getHunger());
        food.putInt("saturation", foodSettings.getSaturation());
        food.putBoolean("snack", foodSettings.isSnack());
        food.putBoolean("alwaysEdible", foodSettings.isAlwaysEdible());
        var effectList = new ListTag();
        for(var e: foodSettings.getEffects()) {
            effectList.add(StringTag.valueOf(e.toString()));
        }
        food.put("effects", effectList);

        var dataComp = new CompoundTag();
        dataComp.putString("model", id.toString());
        dataComp.put("food", food);
        if(maxStackSize != type.getMaxStackSize()) {
            dataComp.putInt("maxSize", maxStackSize);
        }

        if(craftingRemainder.getType() != Material.AIR) {
            var cid = UUID.randomUUID();
            _remainders.put(cid, craftingRemainder);
            dataComp.putUUID("remainder", cid);
        }

        nbt.put("platinum_data", dataComp);

        nmsStack.tag = nbt;

        // reflection max stack size modifications
        if(maxStackSize != type.getMaxStackSize()) {
            var item = nmsStack.getItem();
            Field fMaxStackSize = null;
            try {
                fMaxStackSize = item.getClass().getField("maxStackSize");
            } catch (NoSuchFieldException e) {
                Provoker.provoke(e);
            }

            fMaxStackSize.setAccessible(true);
            if(maxStackSize > 64) {
                PlatinumLib.logger().warning("Registering an item model with max stack size over 64! This might cause client errors!");
                if(maxStackSize > 127) {
                    Provoker.provoke(new IllegalArgumentException("Item max stack size is over 127!"));
                }
            }
            try {
                fMaxStackSize.set(item, maxStackSize);
            } catch (IllegalAccessException e) {
                Provoker.provoke(e);
            }
        }

        // more modifications
        stack = CraftItemStack.asBukkitCopy(nmsStack);
        var meta = stack.getItemMeta();
        meta.setCustomModelData(this.customModelData);
        var comp = Component.translatable("item."+id.getNamespace()+"."+id.getPath(), rarity.getColor());
        meta.displayName(comp);
        stack.setItemMeta(meta);
        List<Component> modifiableList = List.of();
        appendTooltip(stack, modifiableList);
        var meta1 = stack.getItemMeta();
        meta1.lore(modifiableList);
        stack.setItemMeta(meta1);

        // finish by setting default stack
        defaultStack = stack;
    }

    /**
     * Called when a tooltip is appended to item instance for first time
     *
     * @param stack    the default stack to which the tooltip will be appended. Note, that you should not add the tooltip to it yourself
     * @param tooltips the tooltips to be added. Modify this list to change the item tooltips
     */
    public void appendTooltip(ItemStack stack, List<Component> tooltips) {

    }

    /**
     * Called when item is used by player
     *
     * @param player player that used the item
     * @param used   the used item stack
     * @param ctx    click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, ClickContext ctx) {
        return TypedEventResult.success(used);
    }

    /**
     * Called when item is used by player on block
     *
     * @param player player that used the item
     * @param used   the used item stack
     * @param block  block on which the item was used
     * @param ctx    click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, Block block, ClickContext ctx) {
        return TypedEventResult.success(used);
    }

    /**
     * Called when item is used by player on entity
     *
     * @param player player that used the item
     * @param used   the used item stack
     * @param target target entity on which the item was used
     * @param ctx    click context
     * @return result of the event
     */
    public TypedEventResult<ItemStack> use(Player player, ItemStack used, Entity target, ClickContext ctx) {
        return TypedEventResult.success(used);
    }
}
