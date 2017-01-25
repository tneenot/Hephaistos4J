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

import org.hlib4j.math.Counter;

import java.util.concurrent.*;

/**
 * ProcessDelay allows to proceed an external command during a delay. Call {@link #run()} to proceed the
 * underlying process. While the awaiting result was found, the underlying process is
 * stopping immediately. If the result is not found immediately, the task will be stopping according to counter delay
 * gives as constructor argument. <br><br>
 * <p>
 * The process output filtering is controlling by an instance of {@link ProcessScanner}.
 *
 * @see ProcessScanner
 */
public class ProcessDelay implements Runnable
{
  private final ProcessScanner processScanner;
  private final Counter counterDelay;
  private FutureTask<ProcessScanner> futureTask;
  private ExecutorService executorService;

  /**
   * Builds an instance of the process delay for the process scanner. The {@link Counter} class is using to
   * specify the way of the timeout will be managed.
   *
   * @param processScanner Process scanner that will proceed the underlying command.
   * @param counterDelay   The counter delay for this controller.
   */
  public ProcessDelay(ProcessScanner processScanner, Counter counterDelay)
  {
    this.processScanner = processScanner;
    this.counterDelay = counterDelay;
  }


  /**
   * Gets the process scanner associated with this process delay.
   *
   * @return The process scanner associated with this process delay.
   */
  public synchronized ProcessScanner getProcessScanner()
  {
    return this.processScanner;
  }

  @Override
  public void run()
  {
    executorService = Executors.newSingleThreadExecutor();

    futureTask = new FutureTask<>(new Runnable()
    {
      @Override
      public void run()
      {
        do
        {
          processScanner.run();

          if (processScanner.getOutputResultAsString() == null && counterDelay.isValid())
          {
            counterDelay.increment();
            try
            {
              Thread.sleep(counterDelay.getCounterStep());
            } catch (InterruptedException e)
            {
              // Do nothing
            }
          }
        }
        while (processScanner.getOutputResultAsString() == null && counterDelay.isValid());
      }
    }, null);

    executorService.execute(futureTask);
    try
    {
      futureTask.get(counterDelay.getUpperLimitValue(), TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException | CancellationException e)
    {
      // Do nothing
    }
  }

  /**
   * Stop all processes and threads associated with this instance.
   */
  public void interrupt()
  {
    if (null != processScanner)
    {
      processScanner.interrupt();
    }

    if (null != futureTask && futureTask.isDone() == false && futureTask.isCancelled() == false)
    {
      futureTask.cancel(true);
    }

    if (null != executorService)
    {
      executorService.shutdownNow();
    }
  }
}
