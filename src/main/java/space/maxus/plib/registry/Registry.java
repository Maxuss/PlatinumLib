package space.maxus.plib.registry;

/**
 * A registry, that allows for registering custom elements inside
 * @param <T> type of item stored in registry
 */
public sealed interface Registry<T> permits ItemRegistry {
    static <V> void register(Registry<V> registry, V value) {

    }
}