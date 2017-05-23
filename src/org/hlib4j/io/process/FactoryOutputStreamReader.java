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
 * Defines a factory output stream reader concept. This interface allows to specify specific output stream reader for specific usages.
 */
public interface FactoryOutputStreamReader
{
  /**
   * Creates an instance of stream readers for the given input stream
   *
   * @param inputStream           Stream to read
   * @param filter                Filter rule to apply
   * @param stopOnFirstOccurrence Stop on first occurrence find from the input stream if <code>true</code>. Otherwise takes all occurrences.
   * @return An instance of {@link ProcessOutputReader}.
   */
  ProcessOutputReader makeOutputStream(InputStream inputStream, Rule<String> filter, boolean stopOnFirstOccurrence);
}
