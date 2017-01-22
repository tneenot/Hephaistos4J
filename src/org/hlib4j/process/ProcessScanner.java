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
  private String outputResultAsString;
  private Process associatedProcess;

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
  }

  /**
   * The output result that's verifying the filter result.
   *
   * @return The output that's verifying the filter result or <code>null</code>.
   */
  public synchronized String getOutputResultAsString()
  {
    return outputResultAsString;
  }

  @Override
  public void run()
  {
    ProcessOutputReader output_capture = null;
    ProcessOutputReader error_capture = null;
    try
    {
      associatedProcess = this.processBuilder.start();
      output_capture = new ProcessOutputReader(associatedProcess.getInputStream(), filterResult, firstInstanceOnly);
      error_capture = new ProcessOutputReader(associatedProcess.getErrorStream(), filterResult, firstInstanceOnly);

      output_capture.start();
      error_capture.start();

      try
      {
        output_capture.join();
      } catch (InterruptedException e)
      {
        // Do nothing
      }

      try
      {
        error_capture.join();
      } catch (InterruptedException e)
      {
        // Do nothing
      }

    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      synchronized (this)
      {
        if (filterResult.accept(output_capture.getOutputResult()))
        {
          this.outputResultAsString = output_capture.getOutputResult();
        }

        if (null != this.outputResultAsString)
        {
          this.outputResultAsString += "-" + error_capture.getOutputResult();
        } else
        {
          this.outputResultAsString = error_capture.getOutputResult();
        }
      }

      interrupt();
    }
  }

  @Override
  public void interrupt()
  {
    if (null != associatedProcess)
    {
      associatedProcess.destroy();
      associatedProcess = null;
    }
    super.interrupt();
  }
}
