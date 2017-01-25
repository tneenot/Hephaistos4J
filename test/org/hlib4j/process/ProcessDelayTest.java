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
import org.hlib4j.util.States;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class ProcessDelayTest
{
  private Counter internalCounter;

  @Before
  public void setUp() throws Exception
  {
    internalCounter = new Counter(0, 5000);
    internalCounter.setCounterStep(1000);
  }

  private ProcessDelay createValidTask()
  {
    return new ProcessDelay(new ProcessScanner(new ProcessBuilder("ls", "-l", "/"), "r"), internalCounter);
  }

  @Test
  public void test_run_TaskFail_TimeRunningOutOfTime() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    ProcessDelay process_delay = new ProcessDelay(new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.10"), "foo"),
      internalCounter);
    process_delay.run();
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() >= internalCounter.getUpperLimitValue());
  }

  @Test
  public void test_run_ValidTask_NoTimeOut() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    createValidTask().run();
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() < internalCounter.getUpperLimitValue());
  }

  @Test
  public void test_getProcessScanner_ValidTask_ValidResult() throws IOException
  {
    ProcessDelay process_delay = createValidTask();
    process_delay.run();
    Assert.assertFalse(States.isNullOrEmpty(process_delay.getProcessScanner().getOutputResultAsString()));
  }

  @Test
  public void test_getProcessScanner_ValidTaskIntoThread_ValidResult() throws InterruptedException
  {
    ProcessDelay process_delay = createValidTask();
    Thread start_thread = new Thread(process_delay);
    start_thread.start();
    start_thread.join();

    Assert.assertFalse(States.isNullOrEmpty(process_delay.getProcessScanner().getOutputResultAsString()));
  }

  @Test
  public void test_getProcessScanner_InvalidTaskIntoThread_NullResult() throws InterruptedException
  {
    ProcessDelay process_delay = new ProcessDelay(new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.11"), "foo"),
      internalCounter);
    Thread start_thread = new Thread(process_delay);
    start_thread.start();
    start_thread.join();

    Assert.assertTrue(States.isNullOrEmpty(process_delay.getProcessScanner().getOutputResultAsString()));
  }

  @Test
  public void test_getProcessScanner_RunTaskInBackground_ControlIfTaskWasRunningSeveralTimes() throws InterruptedException, RangeException
  {
    // Setup
    ProcessDelay process_delay = new ProcessDelay(new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.12"),
      "unreachable"), internalCounter);

    // SUT
    Thread start_thread = new Thread(process_delay);
    start_thread.start();
    start_thread.join();

    // Result
    String string_result = process_delay.getProcessScanner().getOutputResultAsString();
    Assert.assertTrue(string_result.contains("unreachable"));
  }

  @Test
  public void test_interrupt_RunTaskInBackgroundAndInterruptIt_NoError()
  {
    ProcessDelay process_delay = new ProcessDelay(new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.13"),
      "unreachable"), internalCounter);

    // SUT
    Thread start_thread = new Thread(process_delay);
    start_thread.start();

    try
    {
      Thread.sleep(1000);
    } catch (InterruptedException e)
    {
      // No error
    }
    process_delay.interrupt();
  }

  @Test
  public void test_interrupt_RunTaskInBackgroundAndInterruptTwice_NoError()
  {
    ProcessDelay process_delay = new ProcessDelay(new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.133"),
      "unreachable"), internalCounter);

    // SUT
    Thread start_thread = new Thread(process_delay);
    start_thread.start();

    try
    {
      Thread.sleep(1000);
    } catch (InterruptedException e)
    {
      // No error
    }
    process_delay.interrupt();
    process_delay.interrupt();
  }

}