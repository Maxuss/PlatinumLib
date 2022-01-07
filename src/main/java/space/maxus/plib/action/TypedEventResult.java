package space.maxus.plib.action;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypedEventResult<T> extends EventResult {
    @Nullable
    private T typedValue;

    /**
     * Gets the value of event result
     */
    public @Nullable T getTypedValue() {
        return typedValue;
    }

    /**
     * Sets the value of event result
     */
    public void setTypedValue(@Nullable T typedValue) {
        this.typedValue = typedValue;
    }

    protected TypedEventResult(@Nullable T value, boolean success) {
        super(success);
        this.typedValue = value;
    }

    /**
     * Marks that the event succeeded
     * @param value value to be stored inside result
     */
    public static <V> TypedEventResult<V> success(@NotNull V value) {
        return new TypedEventResult<>(value, true);
    }

    /**
     * Marks that the event failed and should be cancelled
     * @param value value to be stored inside result. May be null.
     */
    public static <V> TypedEventResult<V> fail(@Nullable V value) {
        return new TypedEventResult<>(value, false);
    }
}
