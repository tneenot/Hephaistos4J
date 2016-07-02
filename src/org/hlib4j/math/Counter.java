/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2016 Tioben Neenot
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 51
 *  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.hlib4j.math;


/**
 * Class that's allowing a counter according to limits defined with its constructors. Limits management are defined with {@link DefinitionDomain.LimitType#CLOSE_OPEN},
 * that's meaning the counter is managing as zero based values. So, the low limit is
 * taking account and not the upper limit. While the one of the <code>increment...(...)</code> or <code>decrement...(...)</code> like
 * methods reached the upper or lower limit, {@link #isValid()} return <code>false</code>, and the new call to these increment/decrement
 * methods don't update any value. To reinitialize the counter to its original default value, used {@link #rearm()} method. You can set a
 * specific value thanks to {@link #setCurrentValue(Integer)} method. If the new value is not allowing due to counter limit, the {@link
 * #isValid()} return <code>false</code>.
 *
 * @author Tioben Neenot
 */
public class Counter extends Range<Integer> {

    private int counterStep = 1;
    private int initialValue = -1;

    private boolean isValidCounter;

    private CounterEvent counterEvent;

    /**
     * Builds an instance of the Counter by defining the counter limits and its specific default value.
     *
     * @param lowLimit     Low limit for this counter.
     * @param highLimit    High limit for this counter.
     * @param defaultValue Default value for this counter.
     * @throws RangeException If counter is not valid due to its parameters.
     */
    public Counter(Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException {
        this(LimitType.CLOSE_OPEN, lowLimit, highLimit, defaultValue);
    }

    /**
     * Builds an instance of the Counter by defining specific limit and specific value.
     *
     * @param limitType    Limit type for this counter
     * @param lowLimit     Low limit for this counter.
     * @param highLimit    High limit for this counter.
     * @param defaultValue Default value for this counter.
     * @throws RangeException If counter is not valid due to its parameters.
     */
    public Counter(LimitType limitType, Integer lowLimit, Integer highLimit, Integer defaultValue) throws RangeException {
        super(limitType, lowLimit, highLimit, defaultValue);
        this.isValidCounter = true;
        this.initialValue = defaultValue;
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

        int counter_value = computeNewValue(this.getCurrentValue() + step);

        fireCounterEvent(counter_value);

        return counter_value;
    }

    private void fireCounterEvent(final int counterValue) {

        if (this.counterEvent != null) {
            this.counterEvent.counterValueUpdatedTo(counterValue);
        }
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
        return decrementByStep(this.counterStep);
    }

    /**
     * Decrements the counter internal value by the specific step. This step can not be correspond to the last one
     * defined with {@link #setCounterStep(int)}.
     *
     * @param step Decrementing step value.
     * @return The new value after decrement.
     */
    public int decrementByStep(int step) {
        int counter_value = computeNewValue(this.getCurrentValue() - step);

        fireCounterEvent(counter_value);

        return counter_value;
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
            throw new RangeException("CounterStep value overloads counter limits");
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

    /**
     * Fix the original default value for this counter to allow to use it again.
     *
     * @return <code>true</code> if counter has been rearm with its default initial value.
     */
    public boolean rearm() {
        try {
            setCurrentValue(this.initialValue);
        } catch (RangeException e) {
            // No other action
        }

        return this.isValid();
    }

    /**
     * Sets a counter event that will be called, while the counter value will be updated.
     *
     * @param counterEvent CounterEvent attached with this counter.
     */
    public void setCounterEvent(CounterEvent counterEvent) {
        this.counterEvent = counterEvent;
    }
}
