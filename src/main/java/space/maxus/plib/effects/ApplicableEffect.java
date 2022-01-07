package space.maxus.plib.effects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Effect;
import space.maxus.plib.model.EffectModel;

/**
 * An effect that can be applied to player or any other entity
 */
public class ApplicableEffect {
    /**
     * Chance for effect to apply. 1.00f by default, which means 100%
     */
    @Getter
    @Setter
    private float applyChance = 1.00f;

    /**
     * The potion effect that will be applied
     */
    @Getter
    private Effect potionEffect;

    /**
     * Duration of the effect in ticks
     */
    @Getter
    @Setter
    private long duration;

    private ApplicableEffect() {
    }

    /**
     * Constructs a vanilla-based effect
     *
     * @param effect effect to be used
     * @param ticks  duration in ticks for effect to last
     */
    public static ApplicableEffect vanilla(Effect effect, long ticks) {
        var ef = new ApplicableEffect();
        ef.potionEffect = effect;
        ef.duration = ticks;
        return ef;
    }

    /**
     * Constructs a custom-based effect
     *
     * @param effect effect to be used
     * @param ticks  duration in ticks for event to last
     */
    public static ApplicableEffect custom(EffectModel effect, long ticks) {
        throw new UnsupportedOperationException("Custom effects are not yet supported!");
    }
}
