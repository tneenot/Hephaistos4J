/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.util;


import org.hlib4j.math.Counter;
import org.hlib4j.math.DefinitionDomain;
import org.hlib4j.math.RangeException;

/**
 * The relative progress is a class that computes a progress value according to the current step and step of its successors.
 * To compute a relative progress, the maximum steps must be defined with the {@link RelativeProgressStep#RelativeProgressStep(int)} constructor.
 * Afterwards, while a value is defining with {@link #setProgress(double)}, {@link #getProgress()} is computing according to the current
 * step incremented or decremented by {@link #nextStep()} or {@link #previousStep()}. If successor exits, the current value is computing by the successor first
 * (relatively to its own current step) and by the current instance relatively its current step too. <br><br>
 * <p>
 * While a next step or a previous step is calling, the internal progress value is setting to 0, to begin the awaiting step if it exists.
 * If the step doesn't exist, the internal progress value is not updated to 0. The current step value can be obtained with {@link #getCurrentStep()}.
 */
public class RelativeProgressStep implements ProgressStep
{

  private final Counter counter;
  private ProgressStep successor;
  private double progressValue;

  /**
   * Builds an instance of RelativeProgressStep with the maximum step for this progress.
   *
   * @param maxSteps Maximum step for this instance.
   * @throws RangeException If maximum steps is zero.
   */
  public RelativeProgressStep(int maxSteps) throws RangeException
  {
    this.counter = new Counter(DefinitionDomain.LimitType.BOTH_CLOSE, 1, maxSteps, 1);
    this.successor = null;
  }

  @Override
  public ProgressStep getSuccessor()
  {
    return successor;
  }

  @Override
  public void setSuccessor(ProgressStep successor)
  {
    this.successor = successor;
  }

  @Override
  public double getProgress()
  {
    if (successor != null)
    {
      this.progressValue = successor.getProgress();
    }

    return (((this.counter.getCurrentValue() - 1) + this.progressValue) / this.counter.getUpperLimitValue());
  }

  @Override
  public void setProgress(double progress)
  {
    if (successor != null)
    {
      throw new IllegalArgumentException("You can't modify a value while a successor is existing");
    }

    this.progressValue = progress;
  }

  @Override
  public boolean nextStep()
  {
    this.counter.increment();

    initializeProgressValueAccordingToCounterValidStatus();

    return this.counter.isValid();
  }

  private void initializeProgressValueAccordingToCounterValidStatus()
  {
    if (this.counter.isValid())
    {
      this.progressValue = 0;
    }
  }

  @Override
  public boolean previousStep()
  {
    this.counter.decrement();

    initializeProgressValueAccordingToCounterValidStatus();

    return this.counter.isValid();
  }

  /**
   * Returns the current step value.
   *
   * @return Current step value.
   */
  @Override
  public int getCurrentStep()
  {
    return this.counter.getCurrentValue();
  }

}
