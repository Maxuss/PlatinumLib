package space.maxus.plib.action;

/**
 * Represents the result of the event
 */
public class EventResult {
    private final boolean success;

    /**
     * Whether the event was successful
     */
    public boolean isSuccessful() {
        return success;
    }

    protected EventResult(boolean success) {
        this.success = success;
    }

    /**
     * Marks that the event succeeded
     */
    public static EventResult success() {
        return new EventResult(true);
    }

    /**
     * Marks that the event failed, and should be cancelled
     */
    public static EventResult fail() {
        return new EventResult(false);
    }
}
