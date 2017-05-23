/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.io.process;

import org.hlib4j.concept.Rule;
import org.hlib4j.math.Counter;
import org.hlib4j.math.RangeException;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Creates a process output reading. This class is a thread type to read an output stream from a process. It's
 * possible to define if only the first instance must be taking account.
 */
public class ProcessOutputReader extends Thread
{
  private final InputStream inputStream;
  private final StringBuffer stringBuffer;
  private final Rule<String> ruleCounter;
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
  public ProcessOutputReader(InputStream inputStream, final String filter, boolean stopOnFirstOccurrence)
  {
    this(inputStream, new Rule<String>()
    {

      /**
       * Verifies if the element is valid according to the rule.
       *
       * @param element Element to control by the rule.
       * @return <code>true</code> if the rule implementation determines if the
       * element is valid, <code>false</code> otherwise.
       */
      @Override
      public boolean accept(String element)
      {
        return (filter == null || element.contains(filter));
      }
    }, stopOnFirstOccurrence);
  }

  /**
   * Builds an instance of ProcessOutputStream with a predicate filter and takes all occurrences
   *
   * @param inputStream InputStream to read
   * @param filter      Predicate filter.
   */
  public ProcessOutputReader(InputStream inputStream, Rule<String> filter)
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
  public ProcessOutputReader(InputStream inputStream, final Rule<String> filter, boolean stopOnFirstOccurrence)
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

    this.ruleCounter = new Rule<String>()
    {

      @Override
      public boolean accept(String element)
      {
        if (occurrenceCounter.isValid() && filter.accept(element))
        {
          stringBuffer.append(element).append('\n');
          occurrenceCounter.increment();
        }
        return (occurrenceCounter.isValid() && filter.accept(element));
      }
    };
  }

  /**
   * Gets the output result.
   *
   * @return The output result string or <code>null</code> value if the criteria is not applicable.
   */
  public String getOutputResult()
  {
    if (stringBuffer.length() == 0)
    {
      return null;
    }

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
        this.ruleCounter.accept(text_reader.nextLine());
      }
    }

    text_reader.close();
  }
}
