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


public class ProcessControlerTest
{
  private ProcessControler processControler;
  private Counter internalCounter;

  @Before
  public void setUp() throws Exception
  {
    internalCounter = new Counter(0, 5000);
    internalCounter.setCounterStep(1000);

    processControler = new ProcessControler(new ProcessBuilder("/sbin/ping", "10.10.10.10"),
      internalCounter);
  }

  @Test
  public void test_runTaskWithAwaitingResult_TaskFail_AwaitingResultIsFalse() throws Exception
  {
    Assert.assertFalse(processControler.runTaskWithAwaitingResult("boo"));
  }

  @Test
  public void test_runTaskWithAwaitingResult_TaskOk_AwaitingResultIsTrue() throws IOException
  {
    Assert.assertTrue(createValidTask().runTaskWithAwaitingResult("r"));
  }

  @Test
  public void test_getEffectiveResult_DefaultResult_ValueIsNull() throws Exception
  {
    Assert.assertNull(processControler.getEffectiveResult());
  }

  @Test
  public void test_getEffectiveResult_ResultAfterValidProcess_ResultAwaitingValid() throws IOException
  {
    ProcessControler process_controler = createValidTask();
    process_controler.runTaskWithAwaitingResult("r");

    Assert.assertFalse(States.isNullOrEmpty(process_controler.getEffectiveResult()));
  }

  private ProcessControler createValidTask()
  {
    return new ProcessControler(new ProcessBuilder("/bin/ls", "-l", "/"),
      internalCounter);
  }

  @Test
  public void test_runTaskWithAwaitingResult_TaskFail_TimeRunningOutOfTime() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    processControler.runTaskWithAwaitingResult("boo");
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() >= internalCounter.getUpperLimitValue());
  }

  @Test
  public void test_runTaskWithAwaitingResult_ValidTask_NoTimeOut() throws IOException
  {
    TimeFlow time_flow = new TimeFlow();
    time_flow.begin();
    createValidTask().runTaskWithAwaitingResult("r");
    time_flow.end();

    Assert.assertTrue(time_flow.getTimeFlow() < internalCounter.getUpperLimitValue());
  }

}