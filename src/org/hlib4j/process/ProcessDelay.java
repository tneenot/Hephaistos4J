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
 * ProcessDelay allows to run an external command during a delay. Call {@link #run()} to run the
 * underlying process. While the awaiting result was found, the underlying process is
 * stopping immediately. If the result is not found immediately, the task will be stopping according to counter delay
 * gives as constructor argument. <br><br>
 *
 * The process output filtering is controlling by an instance of {@link ProcessScanner}.
 *
 * @see ProcessScanner
 */
public class ProcessDelay
{
  private final ProcessScanner processScanner;
  private final Counter counterDelay;

  /**
   * Builds an instance of the process delay for the process scanner. The {@link Counter} class is using to
   * specify the way of the timeout will be managed.
   *
   * @param processScanner Process scanner that will run the underlying command.
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
   * @return The current instance to allow some chaining calls.
   * @throws IOException If the underlying process occurs an IOException.
   */
  public ProcessDelay run() throws IOException
  {
    this.processScanner.start();

    while (States.isNullOrEmpty(this.processScanner.getOutputResultAsString()) && this.counterDelay.isValid())
    {
      try
      {
        this.processScanner.join(counterDelay.getCounterStep());
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }

        this.counterDelay.increment();
    }

    return this;
  }


  /**
   * Gets the process scanner associated with this process delay.
   * @return The process scanner associated with this process delay.
   */
  public ProcessScanner getProcessScanner()
  {
    return this.processScanner;
  }
}
