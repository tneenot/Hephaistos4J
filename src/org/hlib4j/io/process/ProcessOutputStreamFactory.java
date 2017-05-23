/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

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
