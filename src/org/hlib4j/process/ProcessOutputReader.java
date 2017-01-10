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

import org.hlib4j.math.Counter;
import org.hlib4j.math.RangeException;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Creates a process output reading. This class is a thread type to read an output stream from a process. It's
 * possible to define if only the first instance must be taking account.
 */
public class ProcessOutputReader extends Thread
{
  private final InputStream inputStream;
  private final StringBuffer stringBuffer;
  private final String filter;
  private Counter occurrenceCounter;

  /**
   * Builds an instance of ProcessOutputStream with no filter and takes all string occurrences.
   *
   * @param inputStream InputStream to read
   */
  public ProcessOutputReader(InputStream inputStream)
  {
    this(inputStream, null);
  }

  /**
   * Builds an instance of ProcessOutputStream with a defined filter and takes all string occurrences.
   *
   * @param inputStream InputStream to read
   * @param filter      Filter to take account for the final result. If <code>null</code> takes all string occurrences.
   */
  public ProcessOutputReader(InputStream inputStream, String filter)
  {
    this(inputStream, filter, false);
  }

  /**
   * Builds an instance of ProcessOutputStream with a defined filter and takes the first occurrence only if
   * <code>stopOnFirstOccurence</code> is <code>true</code>.
   *
   * @param inputStream           InputStream to read
   * @param filter                Filter to take account for the final result. If <code>null</code> takes all string occurrences.
   * @param stopOnFirstOccurrence If <code>true</code> takes only the first occurrence that's corresponding to
   *                              <code>filter</code>. If <code>filter</code> is <code>null</code>, it will take only
   *                              the first string.
   */
  public ProcessOutputReader(InputStream inputStream, String filter, boolean stopOnFirstOccurrence)
  {
    this.inputStream = inputStream;
    this.stringBuffer = new StringBuffer();
    this.filter = filter;
    try
    {
      if (stopOnFirstOccurrence)
      {
        occurrenceCounter = new Counter(0, 1, 0);
      } else
      {
        occurrenceCounter = new Counter(0, Integer.MAX_VALUE, 0);
      }
    } catch (RangeException e)
    {
      // Do nothing
    }
  }

  /**
   * Gets the output result.
   * @return The output result string or an empty string.
   */
  public String getOutputResult()
  {
    return stringBuffer.toString();
  }

  @Override
  public void run()
  {
    Scanner text_reader = new Scanner(this.inputStream);
    while (occurrenceCounter.isValid() && text_reader.hasNextLine())
    {
      synchronized (stringBuffer)
      {
        if (null == filter)
        {
          stringBuffer.append(text_reader.nextLine()).append('\n');
          occurrenceCounter.increment();
        } else
        {
          String a_text = text_reader.nextLine();
          if (a_text.contains(filter))
          {
            stringBuffer.append(a_text).append('\n');
            occurrenceCounter.increment();
          }
        }
      }
    }

    text_reader.close();
  }
}
