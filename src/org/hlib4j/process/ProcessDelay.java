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

import java.io.IOException;
import java.util.concurrent.*;

/**
 * ProcessDelay allows to proceed an external command during a delay. Call {@link #proceed()} to proceed the
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
   * Run the task of the underlying process and stop on the awaiting result. That means this task will stop if its
   * output contains the awaiting filter result.
   *
   * @return The current instance to allow some chaining calls.
   * @throws IOException If the underlying process occurs an IOException.
   */
  public ProcessDelay proceed() throws IOException
  {
    ExecutorService scanner_executor = Executors.newSingleThreadExecutor();

    FutureTask<ProcessScanner> scanner_task = new FutureTask<ProcessScanner>(new Runnable()
    {
      @Override
      public void run()
      {
        ExecutorService second_service = Executors.newSingleThreadExecutor();
        FutureTask<ProcessScanner> second_task = new FutureTask<>(new Runnable()
        {
          @Override
          public void run()
          {
            processScanner.run();
          }
        }, null);

        do
        {
          second_service.execute(second_task);
          try
          {
            second_task.get(counterDelay.getCounterStep(), TimeUnit.MILLISECONDS);
          } catch (InterruptedException | ExecutionException | TimeoutException e)
          {
            // Do nothing
          }

          counterDelay.increment();
        }
        while (processScanner.getOutputResultAsString() == null && counterDelay.isValid());
      }
    }, null);

    try
    {
      scanner_executor.execute(scanner_task);
      try
      {
        scanner_task.get(counterDelay.getUpperLimitValue(), TimeUnit.MILLISECONDS);
      } catch (InterruptedException | ExecutionException | TimeoutException e)
      {
        // Do nothing
      }
    } finally
    {
      scanner_task.cancel(true);
      scanner_executor.shutdownNow();
      processScanner.interrupt();
    }

    return this;
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
    try
    {
      this.proceed();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
