/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.util.States;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * Convenient class to get the first string output result for the process builder. Once the standard output or error of the
 * underlying process builder returns the awaiting result, the {@link #getStandardOutput()}} or the {@link #getErrorOutput()} will
 * return the first line that's verifying this filter. Otherwise, the <code>ProcessScanner</code> will be activated until the
 * end of the underlying process, or if it receives a thread interrupted signal.
 */
public class ProcessScanner extends Thread
{
  private final Predicate<String> filterResult;
  private final ProcessBuilder processBuilder;
  private final boolean firstInstanceOnly;
  private final FactoryOutputStreamReader factoryOutputStreamReader;
  private int exitValue;
  private ProcessOutputReader outputCapture;
  private ProcessOutputReader errorCapture;
  private Process associatedProcess;

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter. Takes on first
   * occurrence or not according to <code>firstInstanceOnly</code> value.
   *
   * @param processBuilder    Process builder associated with this instance.
   * @param filterResult      The process filter that is awaiting.
   * @param firstInstanceOnly Takes only first instance if <code>true</code>, or all instances if <code>false</code>.
   */
  public ProcessScanner(ProcessBuilder processBuilder, String filterResult, boolean firstInstanceOnly)
  {
    this(processBuilder, p -> (p.contains(filterResult)),
      firstInstanceOnly);
    States.validateNotNullOnly(filterResult);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder The process builder associated with this instance.
   * @param filterResult   The process filter that is awaiting.
   */
  public ProcessScanner(ProcessBuilder processBuilder, String filterResult)
  {
    this(processBuilder, States.validateNotNullOnly(filterResult), false);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the predicate filter.
   *
   * @param processBuilder The process builder associated with this instance.
   * @param filter         The predicate filter that will control the final result
   */
  public ProcessScanner(ProcessBuilder processBuilder, Predicate<String> filter)
  {
    this(processBuilder, filter, false);
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder    The process builder associated with this instance.
   * @param filter            The predicate filter that will control the final result.
   * @param firstInstanceOnly Stop on first instance if <code>true</code>, <code>false</code> otherwise.
   */
  public ProcessScanner(ProcessBuilder processBuilder, Predicate<String> filter, boolean firstInstanceOnly)
  {
    this(processBuilder, filter, firstInstanceOnly, new ProcessOutputStreamFactory());
  }

  /**
   * Builds an instance of ProcessScanner for the given process builder and the awaiting filter.
   *
   * @param processBuilder            The process builder associated with this instance.
   * @param filter                    The predicate filter that will control the final result.
   * @param firstInstanceOnly         Stop on first instance if <code>true</code>, <code>false</code> otherwise.
   * @param factoryOutputStreamReader The factory output stream to use with this instance.
   */
  public ProcessScanner(ProcessBuilder processBuilder, Predicate<String> filter, boolean firstInstanceOnly,
                        FactoryOutputStreamReader factoryOutputStreamReader)
  {
    this.processBuilder = processBuilder;
    this.filterResult = States.validateNotNullOnly(filter);
    this.firstInstanceOnly = firstInstanceOnly;
    this.exitValue = -1;
    this.factoryOutputStreamReader = States.validateNotNullOnly(factoryOutputStreamReader);
  }

  /**
   * Gets the standard output result
   *
   * @return The standard output result
   */
  public ProcessOutputReader getStandardOutput()
  {
    return this.outputCapture;
  }

  /**
   * Gets the error output result
   *
   * @return The error output result.
   */
  public ProcessOutputReader getErrorOutput()
  {
    return this.errorCapture;
  }

  @Override
  public void run()
  {
    associatedProcess = null;
    try
    {
      associatedProcess = this.processBuilder.start();
      outputCapture = factoryOutputStreamReader.makeOutputStream(associatedProcess.getInputStream(), filterResult,
        firstInstanceOnly);
      errorCapture = factoryOutputStreamReader.makeOutputStream(associatedProcess.getErrorStream(), filterResult,
        firstInstanceOnly);

      outputCapture.start();
      errorCapture.start();

      try
      {
        this.exitValue = associatedProcess.waitFor();
        outputCapture.join();
        errorCapture.join();
      } catch (InterruptedException e)
      {
        // Do nothing
      }

    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      interrupt();
    }
  }

  @Override
  public void interrupt()
  {
    if (null != associatedProcess)
    {
      try
      {
        this.exitValue = associatedProcess.exitValue();
      } catch (IllegalThreadStateException e)
      {
        // Do nothing
      }

      associatedProcess.destroy();
      associatedProcess = null;
    }

    if (this.exitValue == -1)
    {
      try
      {
        this.exitValue = getStandardOutput().getOutputResult() == null ? -1 : 0;
      } catch (NullPointerException e)
      {
        // Do nothing. In some certain circumstances due to concurret access we can get a NullPointerException.
      }
    }
    super.interrupt();
  }

  /**
   * Gets the exit value from the underlying associated process.
   *
   * @return The exit value from the underlying process. <code>-1</code> if the exit value hasn't been evaluated.
   */
  public int getExitValue()
  {
    return exitValue;
  }
}
