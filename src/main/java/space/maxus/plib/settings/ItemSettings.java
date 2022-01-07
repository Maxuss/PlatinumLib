package space.maxus.plib.settings;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.maxus.plib.textures.ModelRegistry;

/**
 * Settings for item creation
 */
public class ItemSettings {
    /**
     * Max stack size of item
     */
    @Getter
    private int maxStackSize;

    /**
     * Material type of item
     */
    @Getter
    private Material type;

    /**
     * Custom model id of item, used for texturing
     */
    @Getter
    private int customModelData;

    /**
     * Food properties of item
     */
    @Getter
    private FoodSettings foodSettings;

    /**
     * Rarity of item
     */
    @Getter
    private Rarity rarity;

    /**
     * Whether the item is fire and cactus resistant
     */
    @Getter
    private boolean fireResistant;

    /**
     * The remainder in crafting grid after crafting with this item
     */
    @Getter
    private ItemStack craftingRemainder;

    private ItemSettings() {
    }

    /**
     * Builder used for building item configuration settings
     */
    public static class Builder {
        private int maxStackSize = 64;
        private Material bukkitMaterial = Material.AIR;
        private int customModelData = ModelRegistry.allocate();
        private FoodSettings foodSettings = null;
        private Rarity rarity = Rarity.COMMON;
        private boolean fireResistant = false;
        private ItemStack craftingRemainder = new ItemStack(Material.AIR);

        /**
         * Sets max stack size of item.<br/>
         * NOTE: it is not recommended to set max stack size to over 64, it might break client!
         * @param stackSize max stack size of item
         */
        public Builder maxStackSize(int stackSize) {
            this.maxStackSize = stackSize;
            return this;
        }

        /**
         * Sets the base bukkit material of item.<br/>
         * NOTE: the {@link FoodSettings} will override it:<br/>
         * If item is snack, the item type will be set to {@link Material#DRIED_KELP}.<br/>
         * Otherwise the item type will be set to {@link Material#COOKED_BEEF}.
         * @param mat base item material
         */
        public Builder baseMaterial(Material mat) {
            this.bukkitMaterial = mat;
            return this;
        }

        /**
         * Sets the model id of the item.<br/>
         * It is not recommended to do, because it shifts {@link ModelRegistry}'s cursor,<br/>
         * possibly causing registry overwrites. Do this on your own risk!
         * @param modelId new model id
         */
        public Builder modelId(int modelId) {
            ModelRegistry.free(this.customModelData);
            this.customModelData = ModelRegistry.prepare(modelId);
            return this;
        }

        /**
         * Configures the food settings of the item.<br/>
         * It will override item type! See {@link ItemSettings.Builder#baseMaterial(Material)} for more info.
         * @param food food settings to be applied to the item
         */
        public Builder food(FoodSettings food) {
            this.foodSettings = food;
            return this;
        }

        /**
         * Sets the rarity of the item
         * @param rarity the new rarity of item
         */
        public Builder rarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        /**
         * Makes the item fire and cactus resistant, like netherite
         */
        public Builder fireResistant() {
            this.fireResistant = true;
            return this;
        }

        /**
         * Sets the remainder after crafting with this item
         * @param remainder the new crafting remainder
         */
        public Builder craftingRemainder(ItemStack remainder) {
            this.craftingRemainder = remainder;
            return this;
        }

        /**
         * Builds the settings from provided configurations
         */
        public ItemSettings build() {
            var settings = new ItemSettings();
            settings.maxStackSize = this.maxStackSize;
            settings.type = this.bukkitMaterial;
            settings.customModelData = this.customModelData;
            settings.foodSettings = this.foodSettings;
            settings.rarity = this.rarity;
            settings.fireResistant = this.fireResistant;
            settings.craftingRemainder = this.craftingRemainder;

            return settings;
        }
    }
}
