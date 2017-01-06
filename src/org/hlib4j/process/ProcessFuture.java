/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
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

package org.hlib4j.process;

import org.hlib4j.util.States;

import java.util.concurrent.*;

/**
 * Run a process or a task after a given amount of time. This feature allows to run a task later. The task must be a
 * {@link Runnable} command type.
 */
public class ProcessFuture implements Runnable
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
    this.runAfterThisTimeDuration = runAfterThisTimeDuration;
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
    ExecutorService waiting_executor = Executors.newSingleThreadExecutor();
    FutureTask<Void> waiting_task = new FutureTask<Void>(new Runnable()
    {
      @Override
      public void run()
      {
        while (true)
        {

        }
      }
    }, null);

    waiting_executor.execute(waiting_task);
    try
    {
      waiting_task.get(this.runAfterThisTimeDuration, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e)
    {
      // Do nothing
    }

    this.runnableTask.run();
  }
}
