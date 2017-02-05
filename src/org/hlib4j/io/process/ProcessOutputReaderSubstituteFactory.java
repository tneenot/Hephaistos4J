package org.hlib4j.io.process;

import org.hlib4j.concept.Rule;
import org.hlib4j.util.States;

import java.io.InputStream;

/**
 * Create an output reader with a substitute value as final result if the awaiting one hadn't got.
 */
public class ProcessOutputReaderSubstituteFactory implements FactoryOutputStreamReader
{
  private final Rule<String> substituteRule;
  private final String substituteValue;

  /**
   * Builds an instance of factory with the substitute value to used to if the awaiting filter value hadn't got.
   *
   * @param substituteRule Substitute rule to apply for this factory..
   * @param substituteValue Substitute value to used. Must be not <code>null</code>.
   */
  public ProcessOutputReaderSubstituteFactory(Rule<String> substituteRule, String substituteValue)
  {
    this.substituteRule = States.validate(substituteRule);
    this.substituteValue = States.validate(substituteValue);
  }

  @Override
  public ProcessOutputReader makeOutputStream(final InputStream inputStream, Rule<String> filter, boolean stopOnFirstOccurrence)
  {
    return new ProcessOutputReader(inputStream, filter, stopOnFirstOccurrence)
    {
      @Override
      public String getOutputResult()
      {
        String output_result = super.getOutputResult();
        if (substituteRule.accept(output_result))
        {
          return substituteValue;
        }

        return output_result;
      }
    };
  }
}
