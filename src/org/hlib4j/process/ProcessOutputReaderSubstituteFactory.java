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

import java.io.InputStream;
import java.util.function.Predicate;

/**
 * Create an output reader with a substitute value as final result if the awaiting one hadn't got.
 */
public class ProcessOutputReaderSubstituteFactory implements FactoryOutputStreamReader
{
  private final Predicate<String> substituteRule;
  private final String substituteValue;

  /**
   * Builds an instance of factory with the substitute value to used to if the awaiting filter value hadn't got.
   *
   * @param substituteRule  Substitute rule to apply for this factory..
   * @param substituteValue Substitute value to used. Must be not <code>null</code>.
   */
  public ProcessOutputReaderSubstituteFactory(Predicate<String> substituteRule, String substituteValue)
  {
    this.substituteRule = States.validate(substituteRule);
    this.substituteValue = States.validate(substituteValue);
  }

  @Override
  public ProcessOutputReader makeOutputStream(final InputStream inputStream, Predicate<String> filter, boolean
    stopOnFirstOccurrence)
  {
    return new ProcessOutputReader(inputStream, filter, stopOnFirstOccurrence)
    {
      @Override
      public String getOutputResult()
      {
        String output_result = super.getOutputResult();
        if (substituteRule.test(output_result))
        {
          return substituteValue;
        }

        return output_result;
      }
    };
  }
}
