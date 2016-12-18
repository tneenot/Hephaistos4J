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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ProcessControler allows to run an external command during an amount of times or for an awaiting result.
 * This release takes account only standard output. The process controler stops the process command while the
 * awaiting result is reach or if the time wait upper limit is reach.
 */
public class ProcessControler
{
  private final ProcessBuilder processBuilder;
  private final Counter counterDelay;
  private ProcessResult processResult;

  /**
   * Builds an instance of the process controler for the process builder. The {@link Counter} class is using to
   * specify the way of the timeout will be managed.
   *
   * @param processBuilder ProcessBuilder that will run the underlying command.
   * @param counterDelay   The counter delay for this controler.
   */
  public ProcessControler(ProcessBuilder processBuilder, Counter counterDelay)
  {
    this.processBuilder = processBuilder;
    this.counterDelay = counterDelay;
  }

  /**
   * Run the task of the underlying process and stop on the awaiting result. That means this task will stop if its
   * output contains the awaiting filter result.
   * @param awaitingFilterResult The awaiting filter result to reach to stop command as valid.
   * @return <code>true</code> if the command was reached with the awaiting result, <code>false</code> otherwise.
   * @throws IOException If the underlying process occurs an IOException.
   */
  public boolean runTaskWithAwaitingResult(String awaitingFilterResult) throws IOException
  {
    AtomicBoolean is_start_again = new AtomicBoolean(true);

    do
    {
      processResult = new ProcessResult(this.processBuilder, awaitingFilterResult);
      processResult.start();

      if (null == processResult.getOutputResultAsString())
      {
        Thread waiting_thread = new Thread()
        {

          @Override
          public void run()
          {
            try
            {
              sleep(counterDelay.getCounterStep());
            } catch (InterruptedException e)
            {
              e.printStackTrace();
            }
          }
        };

        waiting_thread.start();
        while (waiting_thread.isAlive())
        {
        }

        this.counterDelay.increment();
        if (null != processResult.getOutputResultAsString())
        {
          is_start_again.set(!processResult.getOutputResultAsString().contains(awaitingFilterResult));
        }
      }
    } while (this.counterDelay.isValid() && is_start_again.get() == true);


    return !States.isNullOrEmpty(getEffectiveResult());
  }

  /**
   * Gets the effective result of the underlying process. Can be <code>null</code>.
   * @return The first line that's verifying the awaiting filer result.
   */
  public String getEffectiveResult()
  {
    if (null != processResult)
    {
      return processResult.getOutputResultAsString();
    }

    return null;
  }
}
