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

import org.hlib4j.concept.Rule;
import org.hlib4j.util.States;

import java.io.IOException;

/**
 * Convenient class to get the first string output result for the process builder. Once the standard output or error of the
 * underlying process builder returns the awaiting result, the {@link #getOutputResultAsString()} will return the
 * first line that's verifying this filter. Otherwise, the <code>ProcessScanner</code> will be activated until the
 * end of the underlying process, or if it receives a thread interrupted signal.
 */
public class ProcessScanner extends Thread
{
  private final Rule<String> filterResult;
  private final ProcessBuilder processBuilder;
  private final boolean firstInstanceOnly;
  private int exitValue;
  private ProcessOutputReader outputCapture;
  private ProcessOutputReader errorCapture;

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter. Takes on first
   * occurrence or not according to <code>firstInstanceOnly</code> value.
   *
   * @param processBuilder    Process builder associated with this instance.
   * @param filterResult      The process filter that is awaiting.
   * @param firstInstanceOnly Takes only first instance if <code>true</code>, or all instances if <code>false</code>.
   */
  public ProcessScanner(ProcessBuilder processBuilder, final String filterResult, boolean firstInstanceOnly)
  {
    this(processBuilder, new Rule<String>()
      {

        /**
         * Verifies if the element is valid according to the rule.
         *
         * @param element Element to control by the rule.
         * @return <code>true</code> if the rule implementation determines if the
         * element is valid, <code>false</code> otherwise.
         */
        @Override
        public boolean accept(String element)
        {
          return element.contains(filterResult);
        }
      },
      firstInstanceOnly);
    States.validateNotNullOnly(filterResult);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder The process builder associated with this instance.
   * @param filterResult   The process filter that is awaiting.
   */
  public ProcessScanner(ProcessBuilder processBuilder, String filterResult)
  {
    this(processBuilder, States.validateNotNullOnly(filterResult), false);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the predicate filter.
   *
   * @param processBuilder The process builder associated with this instance.
   * @param filter         The predicate filter that will control the final result
   */
  public ProcessScanner(ProcessBuilder processBuilder, Rule<String> filter)
  {
    this(processBuilder, filter, false);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder    The process builder associated with this instance.
   * @param filter            The predicate filter that will control the final result.
   * @param firstInstanceOnly Stop on first instance if <code>true</code>, <code>false</code> otherwise.
   */
  public ProcessScanner(ProcessBuilder processBuilder, Rule<String> filter, boolean firstInstanceOnly)
  {
    this.processBuilder = processBuilder;
    this.filterResult = States.validateNotNullOnly(filter);
    this.firstInstanceOnly = firstInstanceOnly;
    this.exitValue = -1;
  }

  /**
   * The output result that's verifying the filter result.
   *
   * @return The output that's verifying the filter result or <code>null</code>.
   */
  public synchronized String getOutputResultAsString()
  {
    String final_output_result = null;
    if (null != outputCapture)
    {
      String output_result = outputCapture.getOutputResult();
      if (null != output_result && filterResult.accept(output_result))
      {
        final_output_result = output_result;
      }
    }

    if (null != errorCapture)
    {
      if (null != final_output_result)
      {
        final_output_result += "-" + errorCapture.getOutputResult();
      } else
      {
        final_output_result = errorCapture.getOutputResult();
      }
    }

    return final_output_result;
  }

  @Override
  public void run()
  {
    Process associated_process = null;
    try
    {
      associated_process = this.processBuilder.start();
      outputCapture = new ProcessOutputReader(associated_process.getInputStream(), filterResult, firstInstanceOnly);
      errorCapture = new ProcessOutputReader(associated_process.getErrorStream(), filterResult, firstInstanceOnly);

      outputCapture.start();
      errorCapture.start();

      try
      {
        this.exitValue = associated_process.waitFor();
      } catch (InterruptedException e)
      {
        // Do nothing
      }

    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      if (null != associated_process)
      {
        associated_process.destroy();
      }
    }
  }

  @Override
  public void interrupt()
  {
    if (this.exitValue == -1)
    {
      this.exitValue = getOutputResultAsString() == null ? -1 : 0;
    }
    super.interrupt();
  }

  /**
   * Gets the exit value from the underlying associated process.
   *
   * @return The exit value from the underlying process. <code>-1</code> if the exit value hasn't been evaluated.
   */
  public int getExitValue()
  {
    return exitValue;
  }
}
