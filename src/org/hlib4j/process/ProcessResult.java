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

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by TiobenNeenot on 18/12/2016.
 */
public class ProcessResult extends Thread
{
  private final String filterResult;
  private final ProcessBuilder processBuilder;
  private String outputResultAsString;

  public ProcessResult(ProcessBuilder processBuilder, String filterResult)
  {
    this.processBuilder = processBuilder;
    this.filterResult = filterResult;
  }

  public synchronized String getOutputResultAsString()
  {
    return outputResultAsString;
  }

  @Override
  public void run()
  {
    try
    {
      Scanner stream_scanner = new Scanner(this.processBuilder.start().getInputStream());

      // while ( (outputResultAsString = stream_scanner.findInLine(this.filterResult)) == null)
      while (this.outputResultAsString == null && stream_scanner.hasNextLine())
      {
        String line = stream_scanner.nextLine();
        if (line.contains(this.filterResult))
        {
          synchronized (this)
          {
            this.outputResultAsString = line;
          }
        }
      }

      stream_scanner.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }


}
