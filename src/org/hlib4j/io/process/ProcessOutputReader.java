/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.math.Counter;
import org.hlib4j.math.RangeException;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Creates a process output reading. This class is a thread type to read an output stream from a process. It's
 * possible to define if only the first instance must be taking account.
 */
public class ProcessOutputReader extends Thread
{
  private final InputStream inputStream;
  private final StringBuffer stringBuffer;
  private final Predicate<String> ruleCounter;
  private Counter occurrenceCounter;

  /**
   * Builds an instance of ProcessOutputStream with no filter and takes all string occurrences.
   *
   * @param inputStream InputStream to read
   */
  public ProcessOutputReader(InputStream inputStream)
  {
    this(inputStream, (String) null);
  }

  /**
   * Builds an instance of ProcessOutputStream with a defined filter and takes all string occurrences.
   *
   * @param inputStream InputStream to read
   * @param filter      Filter to take account for the final result. If <code>null</code> takes all string occurrences.
   */
  public ProcessOutputReader(InputStream inputStream, String filter)
  {
    this(inputStream, filter, false);
  }

  /**
   * Builds an instance of ProcessOutputStream with a defined filter and takes the first occurrence only if
   * <code>stopOnFirstOccurence</code> is <code>true</code>.
   *
   * @param inputStream           InputStream to read
   * @param filter                Filter to take account for the final result. If <code>null</code> takes all string occurrences.
   * @param stopOnFirstOccurrence If <code>true</code> takes only the first occurrence that's corresponding to
   *                              <code>filter</code>. If <code>filter</code> is <code>null</code>, it will take only
   *                              the first string.
   */
  public ProcessOutputReader(InputStream inputStream, String filter, boolean stopOnFirstOccurrence)
  {
    this(inputStream, s -> (null == filter || s.contains(filter)), stopOnFirstOccurrence);
  }

  /**
   * Builds an instance of ProcessOutputStream with a predicate filter and takes all occurrences
   *
   * @param inputStream InputStream to read
   * @param filter      Predicate filter.
   */
  public ProcessOutputReader(InputStream inputStream, Predicate<String> filter)
  {
    this(inputStream, filter, false);
  }

  /**
   * Builds an instance of ProcessOutputStream with a predicate filter and takes all occurences only if
   * <code>stopOnFirstOccurrence</code> is <code>true</code>.
   *
   * @param inputStream           InputStream to read
   * @param filter                Predicate filter
   * @param stopOnFirstOccurrence Stop on first occurrence find if <code>true</code>. Otherwise, takes all occurrences.
   */
  public ProcessOutputReader(InputStream inputStream, Predicate<String> filter, boolean stopOnFirstOccurrence)
  {
    this.inputStream = inputStream;
    this.stringBuffer = new StringBuffer();

    try
    {
      if (stopOnFirstOccurrence)
      {
        occurrenceCounter = new Counter(0, 1, 0);
      } else
      {
        occurrenceCounter = new Counter(0, Integer.MAX_VALUE, 0);
      }
    } catch (RangeException e)
    {
      // Do nothing
    }

    this.ruleCounter = new Predicate<String>()
    {

      /**
       * Evaluates this predicate on the given argument.
       *
       * @param element the input argument
       * @return {@code true} if the input argument matches the predicate,
       * otherwise {@code false}
       */
      @Override
      public boolean test(String element)
      {
        if (occurrenceCounter.isValid() && filter.test(element))
        {
          stringBuffer.append(element).append('\n');
          occurrenceCounter.increment();
          return true;
        }
        return false;
      }
    };
  }

  /**
   * Gets the output result.
   *
   * @return The output result string or an empty string.
   */
  public String getOutputResult()
  {
    return stringBuffer.toString();
  }

  @Override
  public void run()
  {
    Scanner text_reader = new Scanner(this.inputStream);
    while (occurrenceCounter.isValid() && text_reader.hasNext())
    {
      synchronized (stringBuffer)
      {
        this.ruleCounter.test(text_reader.nextLine());
      }
    }

    text_reader.close();
  }
}
