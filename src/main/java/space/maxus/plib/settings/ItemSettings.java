package space.maxus.plib.settings;

import lombok.Getter;

/**
 * Settings for item creation
 */
public class ItemSettings {
    @Getter
    private final int maxStackSize = 0;

    private ItemSettings() {
        // Item.Properties
    }
}
