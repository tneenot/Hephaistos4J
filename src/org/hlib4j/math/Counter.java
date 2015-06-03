package org.hlib4j.math;

/**
 * Class to perform a counter according to limits defined thru constructors.
 *
 * @author Tioben Neenot
 */
public class Counter extends Range<Integer> {

    private int counterStep = 1;

    /**
     * Builds an instance of the Counter by defining the counter limits and its specific default value.
     *
     * @param lowLimit     Low limit for this counter.
     * @param highLimit    High limit for this counter.
     * @param defaultValue Default value for this counter.
     * @throws RangeException If counter is not valid due to its parameters.
     */
    public Counter(Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException {
        super(LimitType.BOTH_CLOSE, lowLimit, highLimit, defaultValue);
    }

    /**
     * Builds an instance of the Counter by defining the counter limits.
     *
     * @param lowLimit  Low limit for this counter.
     * @param highLimit High limit for this Counter.
     * @throws RangeException If counter parameters are not valid
     */
    public Counter(Integer lowLimit, Integer highLimit) throws RangeException {
        super(LimitType.BOTH_CLOSE, lowLimit, highLimit);
    }

    /**
     * Increments the counter internal value by step value defined with {@link #setCounterStep(int)}.
     *
     * @return The new value after increment.
     * @throws RangeException If the value is reaching the counter upper limit.
     */
    public int increment() throws RangeException {
        return incrementByStep(this.counterStep);
    }

    /**
     * Increments the counter internal value by the specific step. This step can not be correspond to the last one
     * defined with {@link #setCounterStep(int)}.
     *
     * @param step Incrementation step value.
     * @return The new value after increment.
     * @throws RangeException If the value is reaching the counter upper limit.
     */
    public int incrementByStep(int step) throws RangeException {
        this.setCurrentValue(this.getCurrentValue() + step);

        return this.getCurrentValue();
    }

    /**
     * Decrements the counter internal value by step value defined with {@link #setCounterStep(int)}.
     *
     * @return The new value after decrement.
     * @throws RangeException If the value is reaching the counter lower limit.
     */
    public int decrement() throws RangeException {
        return decrementByStep(1);
    }

    /**
     * Decrements the counter internal value by the specific step. This step can not be correspond to the last one
     * definied with {@link #setCounterStep(int)}.
     *
     * @param step Decrementation step value.
     * @return The new value after decrement.
     * @throws RangeException If the value is reaching the counter low limit.
     */
    public int decrementByStep(int step) throws RangeException {
        this.setCurrentValue(this.getCurrentValue() - step);

        return this.getCurrentValue();
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
        int _hypothetic_value = this.getCurrentValue() + counterStep;

        if (_hypothetic_value >= this.getUpperLimitValue() || _hypothetic_value <= this.getLowerLimitValue()) {
            throw new RangeException("Impossible to set counter step value that overload counter limits");
        }

        this.counterStep = counterStep;
    }
}
