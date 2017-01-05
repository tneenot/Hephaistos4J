package org.hlib4j.process;

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
    TimeFlow duration = new TimeFlow();
    ProcessFuture process_future = new ProcessFuture(new Runnable()
    {
      @Override
      public void run()
      {

      }
    }, 5000);

    duration.begin();
    process_future.run();
    duration.end();

    Assert.assertTrue(duration.getTimeFlow() >= 5000);
  }

  @Test
  public void test_run_RunTaskImmediatly_ValidDelay()
  {
    TimeFlow duration = new TimeFlow();
    ProcessFuture process_future = new ProcessFuture(new Runnable()
    {
      @Override
      public void run()
      {

      }
    }, 0);

    duration.begin();
    process_future.run();
    duration.end();

    Assert.assertTrue(duration.getTimeFlow() >= 0 && duration.getTimeFlow() <= 50);
  }

  @Test
  public void test_run_RunTaskWithNegativeDelay_TaskRunImmediatly()
  {
    TimeFlow duration = new TimeFlow();
    ProcessFuture process_future = new ProcessFuture(new Runnable()
    {
      @Override
      public void run()
      {

      }
    }, -1);

    duration.begin();
    process_future.run();
    duration.end();

    Assert.assertTrue(duration.getTimeFlow() >= 0 && duration.getTimeFlow() <= 50);
  }
}
