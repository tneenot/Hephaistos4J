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
  private final String initialState;

  /**
   * Builds an instance of factory with the substitute value to used to if the awaiting filter value hadn't got.
   *
   * @param initialValue Initial value that will be used as reference to decide if substitute value must be setting or not.
   * @param substituteValue Substitute value to used. Must be not <code>null</code>.
   */
  public ProcessOutputReaderSubstituteFactory(String initialValue, String substituteValue)
  {
    this.initialState = States.validateNotNullOnly(initialValue);
    this.substituteValue = States.validateNotNullOnly(substituteValue);
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
        if (null != output_result && !output_result.contains(initialState))
        {
          return substituteValue;
        }

        return output_result;
      }
    };
  }
}
