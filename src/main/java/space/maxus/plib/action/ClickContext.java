package space.maxus.plib.action;

/**
 * Represents a context for click event
 * @param click type of click made
 * @param shiftHeld whether the player held shift during click
 */
public record ClickContext(Click click, boolean shiftHeld) { }
