package org.hlib4j.math;

/**
 * This exception represents an exception during Range limits or current value definition for a {@link Range} class occurrence.
 */
public class RangeException extends Exception {
    public RangeException() {
        super();
    }

    public RangeException(String message) {
        super(message);
    }

    public RangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RangeException(Throwable cause) {
        super(cause);
    }

    protected RangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
