/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 * Copyright (C) 2015 Tioben Neenot
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package org.hlib4j.math;

/**
 * Range class defining a definition domain that's single brace representing a range of values whose concrete values are given by
 * <code>T</code>
 * definition. Range definition have got some limits that can be opens or closes. Here, rather than to use a collection of values that
 * can be represents a definition domain, the implementation define a rule of available values.<br><br>
 * <p>
 * Limits are defining with the {@link DefinitionDomain.LimitType} enumeration where the {@link DefinitionDomain.LimitType#RIGHT} definition is the <b>[</b> character
 * and {@link DefinitionDomain.LimitType#LEFT} is the <b>]</b> character.
 */
public class Range<T> extends DefinitionDomain<T>
{

  private T lowerLimitValue;
  private T upperLimitValue;
  private T currentValue;
  private LimitType limitType;

  public Range(LimitType limitType, T lowerLimitValue, T upperLimitValue) throws RangeException
  {
    setCommonMembers(limitType, lowerLimitValue, upperLimitValue);
    setCurrentValue(findBetterDefaultValue());
  }

  public Range(LimitType limitType, T lowerLimitValue, T upperLimitValue, T currentValue) throws RangeException
  {
    setCommonMembers(limitType, lowerLimitValue, upperLimitValue);
    setCurrentValue(currentValue);
  }

  private void setCommonMembers(LimitType limitType, T lowerLimitValue, T upperLimitValue) throws RangeException
  {
    if (lowerLimitValue.hashCode() > upperLimitValue.hashCode())
    {
      throw new RangeException("Lower limit greater than upper limit");
    }

    this.limitType = limitType;
    this.lowerLimitValue = lowerLimitValue;
    this.upperLimitValue = upperLimitValue;
  }

  private T findBetterDefaultValue() throws RangeException
  {
    if (this.limitType.getLeft().equals(LimitType.BOTH_CLOSE.getLeft()))
    {
      return this.lowerLimitValue;
    }

    if (this.limitType.getRight().equals(LimitType.BOTH_CLOSE.getRight()))
    {
      return this.upperLimitValue;
    }

    throw new RangeException("Default value can't be found according to range definition");
  }

  @Override
  public boolean isInclude(T value)
  {
    // Control of current value is realizing according to lower and upper limits in one hand and the LimitType definition in
    // other hand.
    if ((this.limitType.getLeft().equals(LimitType.BOTH_CLOSE.getLeft()) && value.hashCode() < this.lowerLimitValue.hashCode()) || (this.
      limitType.getRight().equals(LimitType.BOTH_CLOSE.getRight()) && value.hashCode() > this.upperLimitValue.hashCode()))
    {
      return false;
    }

    if ((this.limitType.getLeft().equals(LimitType.BOTH_OPEN.getLeft()) && value.hashCode() <= this.lowerLimitValue.hashCode()) || (this
      .limitType.getRight().equals(LimitType.BOTH_OPEN.getRight()) && value.hashCode() >= this.upperLimitValue.hashCode()))
    {
      return false;
    }

    return (value.hashCode() >= this.lowerLimitValue.hashCode() && value.hashCode() <= this.upperLimitValue.hashCode());
  }

  @Override
  public boolean isInclude(DefinitionDomain<T> otherDefinitionDomain)
  {
    // Direct pass: controls if this definition domain is equal to the current one
    if (this.equals(otherDefinitionDomain))
    {
      return true;
    }

    // Pass 2:  Control limits of definition domain
    boolean _are_valid_limits = areSameLimitValues(otherDefinitionDomain);

    if (_are_valid_limits)
    {
      // Pass 3: compare limits definition
      Range<T> _other_definition = (Range<T>) otherDefinitionDomain;

      _are_valid_limits &= isSameLowerLimitDeclaration(_other_definition) && isSameUpperLimitDeclaration(_other_definition);
    }

    return _are_valid_limits;
  }

  private boolean areSameLimitValues(DefinitionDomain<T> otherDefinitionDomain)
  {
    return this.isInclude(otherDefinitionDomain.getLowerLimitValue()) && this.isInclude(otherDefinitionDomain
      .getUpperLimitValue());
  }

  private boolean isSameLowerLimitDeclaration(Range<T> otherDefinition)
  {
    return this.limitType.getLeft().equals(otherDefinition.getLimitType().getLeft());
  }

  private boolean isSameUpperLimitDeclaration(Range<T> otherDefinition)
  {
    return this.limitType.getRight().equals(otherDefinition.getLimitType().getRight());
  }

  public T getCurrentValue()
  {
    return currentValue;
  }

  public void setCurrentValue(T currentValue) throws RangeException
  {
    if (this.isInclude(currentValue) == false)
    {
      throw new RangeException("Current value is out of bounds: " + currentValue + " for: " + this);
    }

    this.currentValue = currentValue;
  }

  public T getLowerLimitValue()
  {
    return lowerLimitValue;
  }

  public T getUpperLimitValue()
  {
    return upperLimitValue;
  }

  public LimitType getLimitType()
  {
    return limitType;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (!(o instanceof Range)) return false;

    Range<?> range = (Range<?>) o;

    if (!lowerLimitValue.equals(range.lowerLimitValue)) return false;
    if (!upperLimitValue.equals(range.upperLimitValue)) return false;
    if (!currentValue.equals(range.currentValue)) return false;
    return limitType == range.limitType;

  }

  @Override
  public int hashCode()
  {
    int result = lowerLimitValue.hashCode();
    result = 31 * result + upperLimitValue.hashCode();
    result = 31 * result + currentValue.hashCode();
    result = 31 * result + limitType.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    StringBuffer _buffer = new StringBuffer();
    _buffer.append(this.limitType.getLeft()).append(this.lowerLimitValue).append(";").append(this.upperLimitValue).append(this.limitType
      .getRight()).append("=").append(this.currentValue);
    return _buffer.toString();
  }

}
