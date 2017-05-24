/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.concept.Rule;
import org.hlib4j.math.Counter;
import org.hlib4j.util.States;

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
  private final Rule<ProcessScanner> validateRule;
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
    this(processScanner, counterDelay, new Rule<ProcessScanner>()
    {
      @Override
      public boolean accept(ProcessScanner processScanner)
      {
        return (!States.isNullOrEmpty(processScanner.getStandardOutput().getOutputResult()) || !States.isNullOrEmpty(processScanner
          .getErrorOutput().getOutputResult()));
      }
    });
  }

  /**
   * Builds an instance of the process delay for the process scanner. The {@link Counter} class is using to
   * specify the way of the timeout will be managed.
   *
   * @param processScanner Process scanner that will proceed the underlying command.
   * @param counterDelay   The counter delay for this controller.
   * @param validateRule   Rule to use to validate the {@link ProcessScanner} command result. This constructor is using while it's necessary to get specific validation rule for the underlying command.
   */
  public ProcessDelay(ProcessScanner processScanner, Counter counterDelay, Rule<ProcessScanner> validateRule)
  {
    this.processScanner = processScanner;
    this.counterDelay = counterDelay;
    this.validateRule = States.validate(validateRule);
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

          if (!validateRule.accept(processScanner) && counterDelay.isValid())
          {
            counterDelay.increment();
            try
            {
              Thread.sleep(counterDelay.getCounterStep());
            } catch (InterruptedException e)
            {
              // Do nothing
            }
          } else
          {
            counterDelay.invalidate();
          }
        }
        while (counterDelay.isValid());
      }
    }, null);

    executorService.execute(futureTask);
    try
    {
      futureTask.get(counterDelay.getUpperLimitValue(), TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException | CancellationException e)
    {
      // Do nothing
    } finally
    {
      interrupt();
    }
  }

  /**
   * Stop all processes and threads associated with this instance.
   */
  public void interrupt()
  {
    this.counterDelay.invalidate();

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
