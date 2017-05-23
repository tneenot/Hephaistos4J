/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.time;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * This class allows to compute the flow of time between two startTime and endTime marks time.
 * <p>
 * <H1>When TimeFlow is initializing ?</H1>
 * <code>TimeFlow</code> is considering as initialized, while {@link #begin()} or {@link #end()} was called. In this case
 * {@link #getStartTime()} and {@link #getEndTime()} will returned the value between the moment where the <code>begin</code>
 * or <code>end</code> was called. The {@link #getTimeFlow()} will return the amount of time between that's corresponding
 * between start and end time.
 * <p>
 * <H1>When TimeFlow is not initializing ?</H1>
 * <code>TimeFlow</code> is considering as not initialized while {@link #begin()} or {@link #end()} wasn't called. In this case
 * {@link #getStartTime()} and {@link #getEndTime()} will returned <code>-1</code> value. The {@link #getTimeFlow()} will raise
 * an <code>IllegalStateException</code>.
 * <p>
 * <H1>TimeFlow description</H1>
 * The time flow description is given with {@link #toString()}.
 * <p>
 * <H1>Time values management</H1>
 * While {@link #begin()} or {@link #end()} is calling alone, the {@link #getStartTime()} and {@link #getEndTime()} are equals.
 */
public class TimeFlow
{
  private long startTime;
  private long endTime;

  /**
   * Default constructor.
   */
  public TimeFlow()
  {
    startTime = endTime = -1L;
  }

  public long getStartTime()
  {
    return this.startTime;
  }

  public long getEndTime()
  {
    return this.endTime;
  }

  /**
   * Marks the begin of time flow.
   *
   * @return This instance for calling chaining.
   */
  public TimeFlow begin()
  {
    endTime = startTime = Calendar.getInstance(Locale.UK).getTimeInMillis();

    return this;
  }

  /**
   * Marks the end of time flow.
   *
   * @return This instance for calling chaining.
   */
  public TimeFlow end()
  {
    if (startTime == -1L)
    {
      begin();
    }

    endTime = Calendar.getInstance(Locale.UK).getTimeInMillis();

    return this;
  }

  public long getTimeFlow()
  {
    if (startTime == -1L || endTime == -1L)
    {
      throw new IllegalStateException("Time flow not initialized with begin/end");
    }

    return (endTime - startTime);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TimeFlow timeFlow = (TimeFlow) o;

    if (startTime != timeFlow.startTime) return false;
    return endTime == timeFlow.endTime;

  }

  @Override
  public int hashCode()
  {
    int result = (int) (startTime ^ (startTime >>> 32));
    result = 31 * result + (int) (endTime ^ (endTime >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    Calendar uk_calendar = Calendar.getInstance(Locale.UK);
    uk_calendar.setTimeInMillis(getTimeFlow() - 3600000);
    return DateFormat.getTimeInstance().format(uk_calendar.getTime());
  }
}
