/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.util.States;

import java.io.InputStream;
import java.util.function.Predicate;

/**
 * Create an output reader with a substitute value as final result if the awaiting one doesn't exist.
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
