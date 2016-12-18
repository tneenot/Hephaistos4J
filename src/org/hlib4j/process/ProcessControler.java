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
 * Created by TiobenNeenot on 18/12/2016.
 */
public class ProcessControler
{
  private final ProcessBuilder processBuilder;
  private final Counter counterDelay;
  private ProcessResult processResult;

  public ProcessControler(ProcessBuilder processBuilder, Counter counterDelay)
  {
    this.processBuilder = processBuilder;
    this.counterDelay = counterDelay;
  }

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

  public String getEffectiveResult()
  {
    if (null != processResult)
    {
      return processResult.getOutputResultAsString();
    }

    return null;
  }
}
