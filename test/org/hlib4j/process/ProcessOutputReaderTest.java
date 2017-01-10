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

import org.hlib4j.util.States;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit tests for {@link ProcessOutputReader}.
 */
public class ProcessOutputReaderTest
{
  private Process testProcess = null;

  @Before
  public void setUp() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.10").start();
  }

  @Test
  public void test_Constructor_WithInputStream_NoError()
  {
    new ProcessOutputReader(testProcess.getInputStream());
  }

  @Test
  public void test_Constructor_WithInputStreamAndFilter_NoError()
  {
    new ProcessOutputReader(testProcess.getInputStream(), "unreachable");
  }

  @Test
  public void test_Constructor_WithInputStreamAndNullFilter_NoError()
  {
    new ProcessOutputReader(testProcess.getInputStream(), null);
  }

  @Test
  public void test_Constructor_WithInputStreamAndFilterAndBoolean_NoError()
  {
    new ProcessOutputReader(testProcess.getInputStream(), "", false);
  }

  @Test
  public void test_getOutputResult_Default_EmptyString()
  {
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getInputStream());

    Assert.assertTrue(States.isNullOrEmpty(process_output_reader.getOutputResult()));
  }

  @Test
  public void test_getOutputResult_RunAndGetResult_StringNotEmpty()
  {
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getInputStream());
    process_output_reader.start();
    try
    {
      process_output_reader.join(5000);
    } catch (InterruptedException e)
    {
      // Do nothing
    }

    Assert.assertFalse(States.isNullOrEmpty(process_output_reader.getOutputResult()));
  }

  @Test
  public void test_getOutputResult_RunWithValidFilter_StringContainsFilter()
  {
    ProcessOutputReader process_output_stream = new ProcessOutputReader(testProcess.getErrorStream(), "unreachable");
    process_output_stream.start();
    try
    {
      process_output_stream.join(5000);
    } catch (InterruptedException e)
    {
      // Do nothing
    }

    Assert.assertTrue(process_output_stream.getOutputResult().contains("unreachable"));
  }

  @Test
  public void test_getOutputResult_RunWithValidFilterAndOnceOccurrence_StringContainsFilter()
  {
    ProcessOutputReader process_output_filter = new ProcessOutputReader(testProcess.getErrorStream(), "unreachable",
      true);
    process_output_filter.start();
    try
    {
      process_output_filter.join(5000);
    } catch (InterruptedException e)
    {
      // Do nothing
    }

    Assert.assertTrue(process_output_filter.getOutputResult().contains("unreachable"));
  }

  @Test
  public void test_getOutputResult_WithValidFilterAndSeveralOccurrences_ResultGetsSeveralOccurrences()
  {
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getErrorStream(), "unreachable");
    process_output_reader.start();
    try
    {
      process_output_reader.join(5000);
    } catch (InterruptedException e)
    {
      // Do nothing
    }

    String[] results = process_output_reader.getOutputResult().split("\\n");
    Assert.assertFalse(States.isNullOrEmptyArray(results));
  }

  @Test
  public void test_getOutputResult_WithValidFilterAndOnceOccurrence_ResultContainOnceOccurrence()
  {
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getInputStream(), "10.10.10.10",
      true);
    process_output_reader.start();
    try
    {
      process_output_reader.join();
    } catch (InterruptedException e)
    {
      // Do nothing
    }

    String[] results = process_output_reader.getOutputResult().split("\\n");
    Assert.assertEquals(1, results.length);
  }

  @After
  public void tearDown()
  {
    if (null != testProcess)
    {
      testProcess.destroy();
    }
  }
}
