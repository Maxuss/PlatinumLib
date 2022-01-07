package space.maxus.plib.exceptions;

import lombok.SneakyThrows;

public class Provoker {
    /**
     * Provokes assertion error
     */
    @SneakyThrows
    public static void provoke() {
        throw new AssertionError("An exception was provoked!");
    }

    /**
     * Provokes a provided error
     * @param e error to be provoked
     */
    @SneakyThrows
    public static void provoke(Exception e) {
        throw e;
    }
}
