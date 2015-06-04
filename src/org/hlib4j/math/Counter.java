package org.hlib4j.math;

/**
 * Class that's allowing a counter according to limits defined with its constructors.
 *
 * @author Tioben Neenot
 */
public class Counter extends Range<Integer> {

    private int counterStep = 1;

    private boolean isValidCounter;

    /**
     * Builds an instance of the Counter by defining the counter limits and its specific default value.
     *
     * @param lowLimit     Low limit for this counter.
     * @param highLimit    High limit for this counter.
     * @param defaultValue Default value for this counter.
     * @throws RangeException If counter is not valid due to its parameters.
     */
    public Counter(Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException {
        super(LimitType.CLOSE_OPEN, lowLimit, highLimit, defaultValue);

        this.isValidCounter = true;
    }

    /**
     * Builds an instance of the Counter by defining the counter limits.
     *
     * @param lowLimit  Low limit for this counter.
     * @param highLimit High limit for this Counter.
     * @throws RangeException If counter parameters are not valid
     */
    public Counter(Integer lowLimit, Integer highLimit) throws RangeException {
        this(lowLimit, highLimit, lowLimit);
    }

    /**
     * Increments the counter internal value by step value defined with {@link #setCounterStep(int)}.
     *
     * @return The new value after increment.
     */
    public int increment() {
        return incrementByStep(this.counterStep);
    }

    /**
     * Increments the counter internal value by the specific step. This step can not be correspond to the last one
     * defined with {@link #setCounterStep(int)}.
     *
     * @param step Incrementation step value.
     * @return The new value after increment.
     */
    public int incrementByStep(int step) {
        return computeNewValue(this.getCurrentValue() + step);
    }

    private int computeNewValue(int currentValue) {
        try {
            this.setCurrentValue(currentValue);
        } catch (RangeException e) {
            // No value available.
            this.isValidCounter = false;
        }
        return this.getCurrentValue();
    }

    /**
     * Decrements the counter internal value by step value defined with {@link #setCounterStep(int)}.
     *
     * @return The new value after decrement.
     */
    public int decrement() {
        return decrementByStep(1);
    }

    /**
     * Decrements the counter internal value by the specific step. This step can not be correspond to the last one
     * definied with {@link #setCounterStep(int)}.
     *
     * @param step Decrementing step value.
     * @return The new value after decrement.
     */
    public int decrementByStep(int step) {
        return computeNewValue(this.getCurrentValue() - step);
    }

    /**
     * Gets the value of the counter step.
     *
     * @return Value of the counter step.
     */
    public int getCounterStep() {
        return counterStep;
    }

    /**
     * Defines the counter step for this counter.
     *
     * @param counterStep Counter step value (can be negative).
     * @throws RangeException If the counterStep value overload one of the counter limits.
     */
    public void setCounterStep(int counterStep) throws RangeException {
        int _hypothetical_value = this.getCurrentValue() + counterStep;

        if (_hypothetical_value >= this.getUpperLimitValue() || _hypothetical_value < this.getLowerLimitValue()) {
            throw new RangeException("Impossible to set counter step value that overload counter limits");
        }

        this.counterStep = counterStep;
    }

    /**
     * Gets the counter validity status.
     *
     * @return <code>true</code> means the counter can be incremented or decremented again. <code>False</code> means {@link #increment()
     * }, {@link #incrementByStep(int)}, {@link #decrement()} or {@link #decrementByStep(int)} don't change the current value, since this
     * counter is not valid again.
     */
    public boolean isValid() {
        return this.isValidCounter;
    }

    @Override
    public void setCurrentValue(Integer currentValue) throws RangeException {
        this.isValidCounter = false;
        super.setCurrentValue(currentValue);
        this.isValidCounter = true;
    }
}
