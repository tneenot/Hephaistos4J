/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;
/**
 * Defines a basis operation for running a cleaning process.
 *
 * @author Tioben Neenot
 */
public interface Cleaner
{

  /**
   * Perform the cleaning process.
   *
   * @return The number of elements that had been cleaned.
   */
  int clean();
}
