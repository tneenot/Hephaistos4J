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

import org.hlib4j.util.States;

import java.io.IOException;
import java.util.Scanner;

/**
 * Convenient class to get the first string output result for the process builder. Once the standard output or error of the
 * underlying process builder returns the awaiting result, the {@link #getOutputResultAsString()} will return the
 * first line that's verifying this filter. Otherwise, the <code>ProcessScanner</code> will be activated until the
 * end of the underlying process, or if it receives a thread interrupted signal.
 */
public class ProcessScanner extends Thread
{
  private final String filterResult;
  private final ProcessBuilder processBuilder;
  private String outputResultAsString;
  private Process associatedProcess;

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder The process builder associated with this instance.
   * @param filterResult   The process filter that is awaiting.
   */
  public ProcessScanner(ProcessBuilder processBuilder, String filterResult)
  {
    this.processBuilder = processBuilder;
    this.filterResult = States.validateNotNullOnly(filterResult);
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
    final String[] scanner_line = {""};
    try
    {
      associatedProcess = this.processBuilder.start();
      Thread output_capture = new Thread(() ->
      {
        Scanner stream_scanner = new Scanner(associatedProcess.getInputStream());

        while (!scanner_line[0].contains(filterResult) && stream_scanner.hasNextLine())
        {
          scanner_line[0] = stream_scanner.nextLine();
        }
        stream_scanner.close();
      });

      Thread error_capture = new Thread(() ->
      {
        Scanner stream_scanner = new Scanner(associatedProcess.getErrorStream());

        while (!scanner_line[0].contains(filterResult) && stream_scanner.hasNextLine())
        {
          scanner_line[0] = stream_scanner.nextLine();
        }
        stream_scanner.close();
      });

      output_capture.start();
      error_capture.start();
      output_capture.join();
      error_capture.join();

    } catch (IOException e)
    {
      e.printStackTrace();
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    } finally
    {
      if (scanner_line[0].contains(filterResult))
      {
        this.outputResultAsString = scanner_line[0];
      }

      interrupt();
    }
  }

  @Override
  public void interrupt()
  {
    if (null != associatedProcess)
    {
      associatedProcess.destroyForcibly();
      associatedProcess = null;
    }
    super.interrupt();
  }
}
