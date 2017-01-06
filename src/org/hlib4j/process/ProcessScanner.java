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

import org.hlib4j.util.States;

import java.io.IOException;
import java.util.Scanner;

/**
 * Convenient class to get the first string output result for the process builder. Once the standard output of the
 * underlying process builder returns the awaiting result, the {@link #getOutputResultAsString()} will return the
 * first line that's verifying this filter. Otherwise, the <code>ProcessScanner</code> will be activated until the
 * end of the underlying process.
 * <p>
 * <h3>ProcessBuilder rule to respect or limit of implementation</h3>
 * To avoid situation where some output can be missed or not capture by the <code>ProcessScanner</code> it's recommanded
 * to not call the {@link ProcessBuilder#start()} method. This method will be calling by the <code>ProcessScanner</code>
 * itself. If the process builder is starting before to call the {@link #run()} method, it will be re-running again. The
 * awaiting result can be inconsistent for the underlying process and the awaiting result !
 */
public class ProcessScanner extends Thread implements Cloneable
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

  @Override
  protected ProcessScanner clone()
  {
    interrupt();

    return new ProcessScanner(this.processBuilder, this.filterResult);
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
    try
    {
      associatedProcess = this.processBuilder.start();
      Scanner stream_scanner = new Scanner(associatedProcess.getInputStream());

      while (this.outputResultAsString == null && stream_scanner.hasNextLine())
      {
        String scanner_line = stream_scanner.nextLine();

        if (scanner_line.contains(this.filterResult))
        {
          synchronized (this)
          {
            this.outputResultAsString = scanner_line;
          }
        }
      }
      stream_scanner.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      interrupt();
    }
  }

  public ProcessBuilder getProcessBuilder()
  {
    return this.processBuilder;
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
