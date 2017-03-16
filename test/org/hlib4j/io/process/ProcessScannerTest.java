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

package org.hlib4j.io.process;

import org.hlib4j.util.States;
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
    Assert.assertTrue(States.isNullOrEmpty(process_scanner.getStandardOutput()));
  }

  @Test
  public void test_getExitValue_WithValidCommand_Zero()
  {
    ProcessScanner process_scanner = new ProcessScanner(this.processBuilder, s -> true);

    process_scanner.run();
    Assert.assertEquals(0, process_scanner.getExitValue());
  }

  @Test
  public void test_getExitValue_WithInvalidCommand_GreaterThanZero()
  {
    ProcessScanner process_scanner = new ProcessScanner(new ProcessBuilder("ls", "-l", "/foo"), s -> true);
    process_scanner.run();
    Assert.assertTrue(process_scanner.getExitValue() > 0);
  }

  @Test
  public void test_getOutputResultAsString_AfterRunningWithValidFilter_NoNullValue() throws InterruptedException
  {
    ProcessBuilder ping_process = new ProcessBuilder("ping", "-r", "10.10.10.24");
    ProcessScanner process_scanner = new ProcessScanner(ping_process, "");
    process_scanner.start();
    process_scanner.join(5000);

    Assert.assertNotNull(process_scanner.getErrorOutput());
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullStringFilter_AwaitingException() throws InterruptedException
  {
    new ProcessScanner(this.processBuilder, (String) null);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullStringFilterAndBoolean_AwaitingException()
  {
    new ProcessScanner(this.processBuilder, (String) null, false);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullRuleFilter_AwaitingException()
  {
    new ProcessScanner(this.processBuilder, (Predicate<String>) null);
  }

  @Test(expected = AssertionError.class)
  public void test_Constructor_NullRuleFilterAndBoolean_AwaitingResult()
  {
    new ProcessScanner(this.processBuilder, (Predicate<String>) null, false);
  }

  @Test
  public void test_getOutputResultAsString_AfterRunningWithEmptyFilter_NoNullValue() throws InterruptedException
  {
    ProcessScanner process_scanner = new ProcessScanner(this.processBuilder, "");
    process_scanner.run();

    Assert.assertNotNull(process_scanner.getErrorOutput());
  }

  @Test
  public void test_getOutputResultAsString_WithSubstituteValue_SubstituteValueReturned()
  {
    ProcessScanner process_scanner = new ProcessScanner(new ProcessBuilder("ls", "-l", "/"), element -> element
      .contains("r"), false, new ProcessOutputReaderSubstituteFactory(element -> null != element && !element.contains("booboo"), "foo"));

    process_scanner.run();

    Assert.assertTrue(process_scanner.getErrorOutput().getOutputResult().contains("foo"));
  }

  @Test
  public void test_getOutputResultAsString_WithSubstituteValueButRealValueFound_RealValueReturned() throws InterruptedException
  {
    ProcessScanner process_scanner = new ProcessScanner(new ProcessBuilder("ping", "-r", "10.10.10.241"), element ->
      element.contains("unreachable"), false, new ProcessOutputReaderSubstituteFactory(element -> null != element && !element.contains("send"), "foo"));
    process_scanner.start();
    process_scanner.join(5000);

    Assert.assertTrue((!process_scanner.getErrorOutput().getOutputResult().contains("foo") && process_scanner
      .getErrorOutput().getOutputResult().contains
        ("unreachable")));
  }
}
