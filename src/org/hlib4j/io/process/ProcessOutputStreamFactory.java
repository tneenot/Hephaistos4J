/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import java.io.InputStream;
import java.util.function.Predicate;

/**
 * Factory class to create the output stream readers for the given process..
 */
public class ProcessOutputStreamFactory implements FactoryOutputStreamReader
{
  @Override
  public ProcessOutputReader makeOutputStream(InputStream inputStream, Predicate<String> filter, boolean
    stopOnFirstOccurrence)
  {
    return new ProcessOutputReader(inputStream, filter, stopOnFirstOccurrence);
  }
}
