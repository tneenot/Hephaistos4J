/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2016 Tioben Neenot
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
import org.hlib4j.time.TimeFlow;
import org.hlib4j.util.States;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class ProcessFilterTest
{
  private ProcessFilter processFilter;
  private Counter internalCounter;

  @Before
  public void setUp() throws Exception
  {
    internalCounter = new Counter(0, 5000);
    internalCounter.setCounterStep(1000);

    processFilter = new ProcessFilter(new ProcessBuilder("/sbin/ping", "10.10.10.10"),
      internalCounter);
  }

  @Test
  public void test_runForFilterAsString_TaskFail_AwaitingResultIsFalse() throws Exception
  {
    Assert.assertFalse(processFilter.runForFilterAsString("boo"));
  }

  @Test
  public void test_runForFilterAsString_TaskOk_AwaitingResultIsTrue() throws IOException
  {
    Assert.assertTrue(createValidTask().runForFilterAsString("r"));
  }

  @Test
  public void test_getEffectiveResult_DefaultResult_ValueIsNull() throws Exception
  {
    Assert.assertNull(processFilter.getEffectiveResult());
  }

  @Test
  public void test_getEffectiveResult_ResultAfterValidProcess_ResultAwaitingValid() throws IOException
  {
    ProcessFilter process_controler = createValidTask();
    process_controler.runForFilterAsString("r");

    Assert.assertFalse(States.isNullOrEmpty(process_controler.getEffectiveResult()));
  }

  private ProcessFilter createValidTask()
  {
    return new ProcessFilter(new ProcessBuilder("/bin/ls", "-l", "/"),
      internalCounter);
  }

  @Test
  public void test_runForFilterAsString_TaskFail_TimeRunningOutOfTime() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    processFilter.runForFilterAsString("boo");
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() >= internalCounter.getUpperLimitValue());
  }

  @Test
  public void test_runForFilterAsString_ValidTask_NoTimeOut() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    createValidTask().runForFilterAsString("r");
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() < internalCounter.getUpperLimitValue());
  }

}