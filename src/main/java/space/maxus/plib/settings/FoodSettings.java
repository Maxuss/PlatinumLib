package space.maxus.plib.settings;

import lombok.Getter;
import space.maxus.plib.effects.ApplicableEffect;

import java.util.List;

public class FoodSettings {
    /**
     * Hunger restoration amount of food
     */
    @Getter
    private int hunger;
    /**
     * Saturation restoration amount of food
     */
    @Getter
    private int saturation;
    /**
     * Is food a snack? (can be eaten very fast)
     */
    @Getter
    private boolean snack;
    /**
     * Is item always edible?
     */
    @Getter
    private boolean alwaysEdible;
    /**
     * Effects applied on
     */
    @Getter
    private List<ApplicableEffect> effects;

    private FoodSettings() {
    }

    /**
     * @return a builder to build food properties
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for item food settings
     */
    public static class Builder {
        private int hunger = 0;
        private int saturation = 0;
        private boolean snack = false;
        private boolean canAlwaysEat = false;
        private List<ApplicableEffect> effects = List.of();

        /**
         * Sets the amount of hunger the food will restore
         *
         * @param hunger amount of hunger to restore
         */
        public Builder hunger(int hunger) {
            this.hunger = hunger;
            return this;
        }

        /**
         * Sets the amount of saturation the food will restore
         *
         * @param saturation amount of saturation to restore
         */
        public Builder saturation(int saturation) {
            this.saturation = saturation;
            return this;
        }

        /**
         * Sets the food to be snack (can be eaten very fast)
         */
        public Builder snack() {
            this.snack = true;
            return this;
        }

        /**
         * Makes the food always edible, even when full
         */
        public Builder canAlwaysEat() {
            this.canAlwaysEat = true;
            return this;
        }

        /**
         * Adds custom effects to be applied when the food is eaten
         *
         * @param effects effects to be applied
         */
        public Builder effects(ApplicableEffect... effects) {
            this.effects = List.of(effects);
            return this;
        }

        /**
         * Builds the food properties
         */
        public FoodSettings build() {
            var settings = new FoodSettings();
            settings.hunger = this.hunger;
            settings.saturation = this.saturation;
            settings.snack = this.snack;
            settings.alwaysEdible = this.canAlwaysEat;
            settings.effects = this.effects;
            return settings;
        }
    }
}
