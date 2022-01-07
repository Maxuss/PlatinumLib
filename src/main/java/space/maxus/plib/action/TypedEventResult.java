package space.maxus.plib.action;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypedEventResult<T> extends EventResult {
    /**
     * Value of event result
     */
    @Nullable
    @Getter
    @Setter
    private T typedValue;

    protected TypedEventResult(@Nullable T value, boolean success) {
        super(success);
        this.typedValue = value;
    }

    /**
     * Marks that the event succeeded
     *
     * @param value value to be stored inside result
     */
    public static <V> TypedEventResult<V> success(@NotNull V value) {
        return new TypedEventResult<>(value, true);
    }

    /**
     * Marks that the event failed and should be cancelled
     *
     * @param value value to be stored inside result. May be null.
     */
    public static <V> TypedEventResult<V> fail(@Nullable V value) {
        return new TypedEventResult<>(value, false);
    }
}
