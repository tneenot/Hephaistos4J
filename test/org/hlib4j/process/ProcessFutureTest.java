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
import org.hlib4j.math.RangeException;
import org.hlib4j.time.TimeFlow;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ProcessFuture}
 */
public class ProcessFutureTest
{

  @Test
  public void test_run_RunTaskAfterDelay_AwaitingDelayValid()
  {
    TimeFlow duration = createTaskWithDurationAndRunIt(5000);

    Assert.assertTrue(duration.getTimeFlow() >= 5000);
  }

  private TimeFlow createTaskWithDurationAndRunIt(int durationValue)
  {
    ProcessFuture process_future = new ProcessFuture(new FutureTaskForTest(), durationValue);
    process_future.run();
    return ((FutureTaskForTest) process_future.getRunnableTask()).getTimeFlow();
  }

  @Test
  public void test_run_RunTaskImmediatly_ValidDelay()
  {
    TimeFlow duration = createTaskWithDurationAndRunIt(0);

    Assert.assertTrue(duration.getTimeFlow() >= 0 && duration.getTimeFlow() <= 50);
  }

  @Test
  public void test_run_RunTaskWithNegativeDelay_TaskRunImmediatly()
  {
    TimeFlow duration = createTaskWithDurationAndRunIt(-1);

    Assert.assertTrue(duration.getTimeFlow() >= 0 && duration.getTimeFlow() <= 50);
  }


  @Test
  public void test_getRunnable_VerifyIfNotNull_NotNull()
  {
    ProcessFuture process_future = new ProcessFuture(new Runnable()
    {
      @Override
      public void run()
      {

      }
    }, 5000);

    Assert.assertNotNull(process_future.getRunnableTask());
  }

  @Test
  public void test_run_TrueRunnableTask_TaskRunAfterDelay() throws RangeException
  {
    Counter counter_delay = new Counter(0, 5000);
    counter_delay.setCounterStep(50);
    ProcessFuture process_future = new ProcessFuture(new ProcessDelay(new ProcessScanner(new ProcessBuilder("ls",
      "-l", "/"), "r"), counter_delay), counter_delay.getUpperLimitValue());

    TimeFlow duration = new TimeFlow();
    duration.begin();
    process_future.run();
    duration.end();

    Assert.assertTrue(duration.getTimeFlow() >= 5000);
  }

  @Test
  public void test_run_TrueRunnableTask_TaskRunWithValidValue() throws RangeException
  {
    Counter counter_delay = new Counter(0, 15000);
    counter_delay.setCounterStep(50);
    ProcessFuture process_future = new ProcessFuture(new ProcessDelay(new ProcessScanner(new ProcessBuilder("ls", "-l", "/"), "r"), counter_delay), 5000);
    process_future.run();

    Assert.assertNotNull(((ProcessDelay) process_future.getRunnableTask()).getProcessScanner().getOutputResultAsString());
  }
}

class FutureTaskForTest implements Runnable
{

  private final TimeFlow timeFlow;

  FutureTaskForTest()
  {
    this.timeFlow = new TimeFlow();
    this.timeFlow.begin();
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used
   * to create a thread, starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may
   * take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run()
  {
    this.timeFlow.end();
    System.out.println("Task was ran");
  }

  TimeFlow getTimeFlow()
  {
    return this.timeFlow;
  }
}
