/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.util.States;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit tests for {@link ProcessOutputReader}.
 */
public class ProcessOutputReaderTest
{
  private Process testProcess;

  @Test
  public void test_Constructor_WithInputStream_NoError() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.14").start();
    new ProcessOutputReader(testProcess.getInputStream());
  }

  @Test
  public void test_Constructor_WithInputStreamAndFilter_NoError() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.15").start();
    new ProcessOutputReader(testProcess.getInputStream(), "unreachable");
  }

  @Test
  public void test_Constructor_WithInputStreamAndNullFilter_NoError() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.16").start();
    new ProcessOutputReader(testProcess.getInputStream(), (String) null);
  }

  @Test
  public void test_Constructor_WithInputStreamAndFilterAndBoolean_NoError() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.17").start();
    new ProcessOutputReader(testProcess.getInputStream(), "", false);
  }

  @Test
  public void test_getOutputResult_Default_EmptyString() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.18").start();
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getInputStream());

    Assert.assertTrue(States.isNullOrEmpty(process_output_reader.getOutputResult()));
  }

  @Test
  public void test_getOutputResult_RunAndGetResult_StringNotEmpty() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.19").start();
    ProcessOutputReader process_output_reader = new ProcessOutputReader(testProcess.getErrorStream());
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
  public void test_getOutputResult_RunWithValidFilter_StringContainsFilter() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.20").start();
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
  public void test_getOutputResult_RunWithValidFilterAndOnceOccurrence_StringContainsFilter() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.21").start();
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
  public void test_getOutputResult_WithValidFilterAndSeveralOccurrences_ResultGetsSeveralOccurrences() throws IOException
  {
    testProcess = new ProcessBuilder("ping", "-r", "10.10.10.22").start();
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
  public void test_getOutputResult_WithValidFilterAndOnceOccurrence_ResultContainOnceOccurrence() throws IOException
  {
    ProcessBuilder process_test = new ProcessBuilder("ping", "-r", "10.10.10.23");
    ProcessOutputReader process_output_reader = new ProcessOutputReader(process_test.start().getErrorStream(), "unreachable",
      true);
    process_output_reader.start();
    try
    {
      process_output_reader.join(5000);
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
      testProcess = null;
    }
  }
}
