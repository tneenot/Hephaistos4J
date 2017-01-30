package org.hlib4j.process;

import org.hlib4j.concept.Rule;

import java.io.InputStream;

/**
 * Factory class to create the output stream readers for the given process..
 */
public class ProcessOutputStreamFactory implements FactoryOutputStreamReader
{
  @Override
  public ProcessOutputReader makeOutputStream(InputStream inputStream, Rule<String> filter, boolean stopOnFirstOccurrence)
  {
    return new ProcessOutputReader(inputStream, filter, stopOnFirstOccurrence);
  }
}
