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
import org.hlib4j.util.States;

import java.io.IOException;

/**
 * ProcessFilter allows to run an external command during an amount of times or for an awaiting result specifies
 * with {@link #runForFilterAsString(String)}. While the awaiting result was found, the underlying process is
 * stopping.
 * This release takes account only standard output. The <code>ProcessFilter</code> stops the process command while the
 * awaiting result was reached or if the time wait upper limit was reached.
 */
public class ProcessFilter
{
  private final ProcessBuilder processBuilder;
  private final Counter counterDelay;
  private String effectiveResult;

  /**
   * Builds an instance of the process controler for the process builder. The {@link Counter} class is using to
   * specify the way of the timeout will be managed.
   *
   * @param processBuilder ProcessBuilder that will run the underlying command.
   * @param counterDelay   The counter delay for this controler.
   */
  public ProcessFilter(ProcessBuilder processBuilder, Counter counterDelay)
  {
    this.processBuilder = processBuilder;
    this.counterDelay = counterDelay;
  }

  /**
   * Run the task of the underlying process and stop on the awaiting result. That means this task will stop if its
   * output contains the awaiting filter result.
   * @param filterAsString The awaiting filter result to reach to stop command as valid.
   * @return <code>true</code> if the command was reached with the awaiting result, <code>false</code> otherwise. If
   * <code>true</code>, {@link #getEffectiveResult()} return the effective result that's corresponding to the filter.
   * @throws IOException If the underlying process occurs an IOException.
   */
  public boolean runForFilterAsString(String filterAsString) throws IOException
  {

    ProcessScanner process_result = new ProcessScanner(this.processBuilder, filterAsString);
    process_result.start();

    while (States.isNullOrEmpty(process_result.getOutputResultAsString()) && this.counterDelay.isValid())
    {
      try
      {
        process_result.join(counterDelay.getCounterStep());
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }

        this.counterDelay.increment();
    }

    this.effectiveResult = process_result.getOutputResultAsString();
    return !States.isNullOrEmpty(this.effectiveResult);
  }

  /**
   * Gets the effective result of the underlying process. Can be <code>null</code>.
   * @return The first line that's verifying the awaiting filer result.
   */
  public String getEffectiveResult()
  {
    return this.effectiveResult;
  }
}
