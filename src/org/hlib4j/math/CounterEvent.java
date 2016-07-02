package org.hlib4j.math;

/**
 * Defines the method to receive an event while the counter value was changed.
 */
public interface CounterEvent {

    /**
     * Method to implements to receive the new value of the counter.
     *
     * @param counterValue New value of the counter.
     */
    void counterValueUpdatedTo(int counterValue);
}
