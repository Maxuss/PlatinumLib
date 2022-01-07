package space.maxus.plib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class ComponentUtils {
    /**
     * Serializes component to legacy section string
     *
     * @param component component to be serialized
     * @return String representation of current component, ready for modifications
     */
    public static @NotNull String serializeComponent(@NotNull Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    /**
     * Deserializes current string into component
     *
     * @param deserializable String to be serialized into component
     * @return Deserialized {@link net.kyori.adventure.text.Component }
     */
    public static @NotNull Component deserializeComponent(@NotNull String deserializable) {
        return LegacyComponentSerializer.legacySection().deserialize(deserializable);
    }

    /**
     * Deserializes current string into component with ampersand (&) being the style identifier
     *
     * @param deserializable String to be serialized into component
     * @return Deserialized {@link net.kyori.adventure.text.Component }
     */
    public static @NotNull Component deserializeComponentAmpersand(@NotNull String deserializable) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(deserializable);
    }
}
