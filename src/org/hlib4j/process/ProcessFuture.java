package org.hlib4j.process;

import org.hlib4j.time.TimeFlow;
import org.hlib4j.util.States;

/**
 * Run a process or a task after a given amount of time.
 */
public class ProcessFuture implements Runnable
{
  private final Runnable runnableTask;
  private final long afterLongTime;

  /**
   * Builds an instance of a Runnable task
   *
   * @param runnableTask  Runnable task to run
   * @param afterLongTime Long time delay after which the runnable task will be running.
   */
  public ProcessFuture(Runnable runnableTask, long afterLongTime)
  {
    this.runnableTask = States.validate(runnableTask);
    this.afterLongTime = afterLongTime;
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


  @Override
  public void run()
  {
    TimeFlow waiting_counter = new TimeFlow();
    waiting_counter.begin();
    waiting_counter.end();
    while (waiting_counter.getTimeFlow() < this.afterLongTime)
    {
      waiting_counter.end();
    }

    this.runnableTask.run();
  }
}
