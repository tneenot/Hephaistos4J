package org.hlib4j.process;

import org.hlib4j.concept.Rule;
import org.hlib4j.util.States;

import java.io.InputStream;

/**
 * Create an output reader with a substitute value as final result if the awaiting one hadn't got.
 */
public class ProcessOutputReaderSubstituteFactory implements FactoryOutputStreamReader
{
  private final String substituteValue;

  /**
   * Builds an instance of factory with the substitute value to used to if the awaiting filter value hadn't got.
   *
   * @param substituteValue Subtitute value to used. Must be not <code>null</code>.
   */
  public ProcessOutputReaderSubstituteFactory(String substituteValue)
  {
    this.substituteValue = States.validateNotNullOnly(substituteValue);
  }

  @Override
  public ProcessOutputReader makeOutputStream(InputStream inputStream, Rule<String> filter, boolean stopOnFirstOccurrence)
  {
    return new ProcessOutputReader(inputStream, filter, stopOnFirstOccurrence)
    {
      @Override
      public String getOutputResult()
      {
        String output_result = super.getOutputResult();
        if (output_result == null)
        {
          return substituteValue;
        }

        return output_result;
      }
    };
  }
}
