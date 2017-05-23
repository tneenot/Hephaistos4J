/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.util;

/**
 * This interface defines a progress step concept. A progress step is a concept for which each progress value is computing
 * according to a current step and the step for its successors if they exist. This progress definition allows to run a chain
 * of progress computation. A progress step allows to avoid to get progress value that's coming back to zero while a new
 * step is beginning with its initial value to 0.
 */
public interface ProgressStep
{

  /**
   * Gets the successor for this progress step.
   *
   * @return Successor of this progress step definition, or <code>null</code> if not exist.
   */
  ProgressStep getSuccessor();

  /**
   * Defines a successor to this progress step.
   *
   * @param successor Successor for this progress step, or <code>null</code> to break the chain of processes.
   */
  void setSuccessor(ProgressStep successor);

  /**
   * Returns the progress value for this step.
   *
   * @return Progress value for this step. The progress value must be computing by the implementation according to the current
   * step of this instance, and the current step of its successors.
   */
  double getProgress();

  /**
   * Defines the current progress value for this step.
   *
   * @param progress Current progress value.
   */
  void setProgress(double progress);

  /**
   * Go to the next step. This feature fix the progress value to 0, to begin a next step if next step is existing.
   *
   * @return <code>true</code> if next step is existing, <code>false</code> otherwise.
   */
  boolean nextStep();

  /**
   * Go to the previous step. This feature fix the progress value to 0, to begin the previous step if previous step is existing.
   *
   * @return <code>true</code> if previous step is existing, <code>false</code> otherwise.
   */
  boolean previousStep();

  /**
   * Returns the current step value.
   *
   * @return Current step value.
   */
  int getCurrentStep();
}
