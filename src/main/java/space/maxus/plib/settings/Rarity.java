package space.maxus.plib.settings;


import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Vanilla item rarity system
 */
public enum Rarity {
    /**
     * Default rarity for all items
     */
    COMMON(NamedTextColor.WHITE),
    /**
     * More rare items, like elytras, totems of undying, etc.
     */
    UNCOMMON(NamedTextColor.YELLOW),
    /**
     * Rare items, like music discs, end crystals, beacons, etc.
     */
    RARE(NamedTextColor.AQUA),
    /**
     * Either super rare items, like dragon egg, or<br/>
     * creative-only items, like command block, structure block, etc.
     */
    EPIC(NamedTextColor.LIGHT_PURPLE)

    ;

    /**
     * The color formatting of the rarity
     */
    @Getter
    private final NamedTextColor color;

    Rarity(NamedTextColor color) {
        this.color = color;
    }
}
