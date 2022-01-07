package space.maxus.plib.settings;

import net.minecraft.world.item.Item;

/**
 * Settings for item creation
 */
public class ItemSettings {
    private final int maxStackSize = 0;


    private ItemSettings() {
        // Item.Properties
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

    }
}
