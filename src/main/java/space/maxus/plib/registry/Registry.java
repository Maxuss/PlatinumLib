package space.maxus.plib.registry;

import space.maxus.plib.model.ItemModel;

import java.util.HashMap;
import java.util.List;

/**
 * A registry, that allows for registering custom elements inside
 *
 * @param <T> type of item stored in registry
 */
public sealed interface Registry<T> permits SimpleRegistry {
    Registry<ItemModel> ITEM = new ItemRegistry();

    static <V> V register(Registry<V> registry, Identifier id, V value) {
        registry.performRegistration(id, value);
        return value;
    }

    static <V> V find(Registry<V> registry, Identifier id) {
        return registry.find(id);
    }

    static List<Identifier> entries(Registry<?> registry) {
        return registry.identifiers();
    }

    T find(Identifier id);

    List<Identifier> identifiers();

    void performRegistration(Identifier id, T value);
}