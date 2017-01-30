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

import java.io.InputStream;
import java.util.function.Predicate;

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
  ProcessOutputReader makeOutputStream(InputStream inputStream, Predicate<String> filter, boolean stopOnFirstOccurrence);
}
