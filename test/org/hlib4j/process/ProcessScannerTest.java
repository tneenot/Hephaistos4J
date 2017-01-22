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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Unit tests for {@link ProcessScanner} class.
 */
public class ProcessScannerTest
{
  private ProcessBuilder processBuilder;

  @Before
  public void setUp()
  {
    this.processBuilder = new ProcessBuilder("ls", "-l", "/");
  }

  @Test
  public void test_getOutputResultAsString_Default_NullValue()
  {
    ProcessScanner process_scanner = new ProcessScanner(this.processBuilder, "");
    Assert.assertNull(process_scanner.getOutputResultAsString());
  }

  @Test
  public void test_getOutputResultAsString_AfterRunningWithValidFilter_NoNullValue() throws InterruptedException
  {
    ProcessScanner process_scanner = new ProcessScanner(this.processBuilder, "r");
    process_scanner.start();
    process_scanner.join();

    Assert.assertNotNull(process_scanner.getOutputResultAsString());
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullString_AwaitingException() throws InterruptedException
  {
    new ProcessScanner(this.processBuilder, (String) null);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullStringAndBoolean_AwaitingException()
  {
    new ProcessScanner(this.processBuilder, (String) null, false);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullPredicateFilter_AwaitingException()
  {
    new ProcessScanner(this.processBuilder, (Predicate<String>) null);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullPredicateFilterAndBoolean_AwaitingException()
  {
    new ProcessScanner(this.processBuilder, (Predicate<String>) null, false);
  }

  @Test
  public void test_getOutputResultAsString_AfterRunningWithEmptyFilter_NoNullValue() throws InterruptedException
  {
    ProcessScanner process_scanner = new ProcessScanner(this.processBuilder, "");
    process_scanner.run();
    process_scanner.join();

    Assert.assertNotNull(process_scanner.getOutputResultAsString());
  }
}
