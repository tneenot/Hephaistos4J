/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.util.States;

import java.util.TimerTask;

/**
 * Run a process or a task after a given amount of time. This feature allows to run a task later. The task must be a
 * {@link Runnable} command type.
 */
public class ProcessFuture extends TimerTask implements Runnable
{
  private final Runnable runnableTask;
  private final long runAfterThisTimeDuration;


  /**
   * Builds an instance of a Runnable task
   *
   * @param runnableTask             Runnable task to run
   * @param runAfterThisTimeDuration Long time delay after which the runnable task will be running.
   */
  public ProcessFuture(Runnable runnableTask, long runAfterThisTimeDuration)
  {
    this.runnableTask = States.validate(runnableTask);
    this.runAfterThisTimeDuration = (runAfterThisTimeDuration < 0 ? 0 : runAfterThisTimeDuration);
  }

  /**
   * Gets the runnable task associated with this instance
   *
   * @return Runnable task associated with this instance
   */
  public Runnable getRunnableTask()
  {
    return runnableTask;
  }


  /**
   * Waits during the specified long time and run the defined task.
   */
  @Override
  public void run()
  {
    try
    {
      Thread.sleep(runAfterThisTimeDuration);
    } catch (InterruptedException e)
    {
      // Do nothing
    }
    this.runnableTask.run();
  }
}
