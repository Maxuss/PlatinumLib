package space.maxus.plib.textures;

import io.netty.util.internal.UnstableApi;
import lombok.Getter;
import space.maxus.plib.model.ItemModel;
import space.maxus.plib.utils.AllocatedSpace;

import java.util.HashMap;

/**
 * Registry in which models are stored
 */
public class ModelRegistry {
    /**
     * The constant offset of model data registry position
     */
    public static final int CMD_POS_OFFSET = 0x10000; // 65536, just a fancy number i chose. also should not conflict with other plugins
    /**
     * Amount for registry to be pushed, when allocation occurs
     */
    public static final int PUSH_AMOUNT = 1;

    private static int cursor = CMD_POS_OFFSET;

    @Getter
    private static final HashMap<Integer, AllocatedSpace<ItemModel>> registrants = new HashMap<>();

    /**
     * Allocates space for model in registry
     * @return index position of allocation
     */
    public static int allocate() {
        registrants.put(cursor, AllocatedSpace.empty());
        cursor += PUSH_AMOUNT;
        return cursor - PUSH_AMOUNT;
    }

    /**
     * Allocates space for model in registry and puts the model here
     * @param value value to be put inside the registry
     * @return index position of allocation
     */
    public static int allocate(ItemModel value) {
        registrants.put(cursor, AllocatedSpace.filled(value));
        cursor += PUSH_AMOUNT;
        return cursor - PUSH_AMOUNT;
    }

    /**
     * Allocates space at specific index position and shifts the cursor to current position.<br/>
     * Note, that this method is unstable, and it can break plugin system because of cursor shift.
     * @param position position which should be prepared
     * @return the position index
     */
    @UnstableApi
    public static int prepare(int position) {
        cursor = position;
        return allocate();
    }

    /**
     * Removed the value at provided index of the cell and returns it
     * @param at index of the cell, memory of which to be freed
     * @return popped value from the registry
     */
    public static AllocatedSpace<ItemModel> free(int at) {
        var value = registrants.get(at);
        registrants.remove(at);
        return value;
    }

    /**
     * Fills the space in registry with provided value
     * @param at position to be filled
     * @param value value to be put
     */
    public static void fill(int at, ItemModel value) {
        registrants.put(at, AllocatedSpace.filled(value));
    }

    /**
     * Gets the value at provided cell
     * @param at index of allocated space
     * @return model at the cell
     */
    public static ItemModel get(int at) {
        return registrants.get(at).getReserved();
    }
}
