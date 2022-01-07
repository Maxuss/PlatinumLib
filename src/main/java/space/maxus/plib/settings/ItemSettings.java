package space.maxus.plib.settings;

import lombok.Getter;
import org.bukkit.Material;
import space.maxus.plib.textures.ModelRegistry;

/**
 * Settings for item creation
 */
public class ItemSettings {
    @Getter
    private final int maxStackSize = 0;

    private ItemSettings() {
    }

    public static class Builder {
        private int maxStackSize = 1;
        private Material bukkitMaterial = Material.AIR;
        private int customModelData = ModelRegistry.allocate();

        public Builder maxStackSize(int stackSize) {
            maxStackSize = stackSize;
            return this;
        }

        public Builder baseMaterial(Material mat) {
            bukkitMaterial = mat;
            return this;
        }

        public Builder modelId(int modelId) {
            ModelRegistry.free(customModelData);
            customModelData = ModelRegistry.prepare(modelId);
            return this;
        }

    }
}
