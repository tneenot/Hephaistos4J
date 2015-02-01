package org.hlib4j.collection;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*  
*  Copyright (C) 2015 Tioben Neenot
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
*  
*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Multiple is a <code>Rule</code>, computes the multiple for the number value.
 *
 * @param <E>
 *          Type of number
 * @author Tioben Neenot
 */
public class Multiple<E extends Number> implements Rule<E>
{

	/**
	 * Element reference for multiple
	 */
	private final Number refValue;

	/**
	 * Builds an instance of the Multiple for a Float type
	 *
	 * @param aFloat
	 *          Reference value of the multiple
	 */
	public Multiple(Float aFloat)
	{
		this.refValue = aFloat;
	}

	/**
	 * Builds an instance of the Multiple for a Double type
	 *
	 * @param aDouble
	 *          Reference value of the multiple
	 */
	public Multiple(Double aDouble)
	{
		this.refValue = aDouble;
	}

	/**
	 * Builds an instance of the Multiple for a Byte type
	 *
	 * @param aByte
	 *          Reference value of the multiple
	 */
	public Multiple(Byte aByte)
	{
		this.refValue = aByte;
	}

	/**
	 * Builds an instance of the Multiple for an Integer type
	 *
	 * @param anInteger
	 *          Reference value of the multiple
	 */
	public Multiple(Integer anInteger)
	{
		this.refValue = anInteger;
	}

	/**
	 * Builds an instance of the Multiple for a Long type
	 *
	 * @param aLong
	 *          Reference value of the multiple
	 */
	public Multiple(Long aLong)
	{
		this.refValue = aLong;
	}

	/**
	 * Builds an instance of the Multiple for a Short type
	 *
	 * @param aShort
	 *          Reference value of the multiple
	 */
	public Multiple(Short aShort)
	{
		this.refValue = aShort;
	}

	/**
	 * Builds an instance of the Multiple for an AtomicInteger type. Ensure that this constructor will be synchronized
	 * during its call.
	 *
	 * @param anAtomicInteger
	 *          Reference value of the multiple
	 */
	public Multiple(AtomicInteger anAtomicInteger)
	{
		synchronized (this)
		{
			this.refValue = anAtomicInteger;
		}
	}

	/**
	 * Builds an instance of the Multiple for an AtomicLong type. Ensure that this constructor will be synchronized during
	 * its call.
	 *
	 * @param anAtomicLong
	 *          Reference value of the multiple
	 */
	public Multiple(AtomicLong anAtomicLong)
	{
		synchronized (this)
		{
			this.refValue = anAtomicLong;
		}
	}

	/**
	 * Builds an instance of the Multiple for a BigInteger type
	 *
	 * @param aBigInteger
	 *          Reference value of the multiple
	 */
	public Multiple(BigInteger aBigInteger)
	{
		this.refValue = aBigInteger;
	}

	/**
	 * Builds an instance of the Multiple for a BigDecimal type
	 *
	 * @param aBigDecimal
	 *          Reference value of the multiple
	 */
	public Multiple(BigDecimal aBigDecimal)
	{
		this.refValue = aBigDecimal;
	}

	/**
	 * Verify if the Float element is valid or not.
	 *
	 * @param aFloat
	 *          Float element to control.
	 * @return <code>true</code> if Float element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Float aFloat)
	{
		try
		{
			return (aFloat % (Float) this.refValue) == 0.0f;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Double element is valid or not.
	 *
	 * @param aDouble
	 *          Double element to control.
	 * @return <code>true</code> if Double element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Double aDouble)
	{
		try
		{
			return (aDouble % (Double) this.refValue) == 0.0d;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Byte element is valid or not.
	 *
	 * @param aByte
	 *          Byte element to control.
	 * @return <code>true</code> if Byte element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Byte aByte)
	{
		try
		{
			return (aByte % (Byte) this.refValue) == 0;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Integer element is valid or not.
	 *
	 * @param anInteger
	 *          Integer element to control.
	 * @return <code>true</code> if Integer element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Integer anInteger)
	{
		try
		{
			return (anInteger % (Integer) this.refValue) == 0;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Long element is valid or not.
	 *
	 * @param aLong
	 *          Long element to control.
	 * @return <code>true</code> if Long element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Long aLong)
	{
		try
		{
			return (aLong % (Long) this.refValue) == 0L;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Short element is valid or not.
	 *
	 * @param aShort
	 *          Short element to control.
	 * @return <code>true</code> if Short element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(Short aShort)
	{
		try
		{
			return (aShort % (Short) this.refValue) == 0;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the AttomicInteger element is valid or not. Ensure that this method will be synchronized during its call.
	 *
	 * @param anAtomicInteger
	 *          AtomicInteger element to control.
	 * @return <code>true</code> if AtomicInteger element is valid, <code>false</code> otherwise.
	 */
	public synchronized boolean accept(AtomicInteger anAtomicInteger)
	{
		try
		{
            return (anAtomicInteger.intValue() % this.refValue.intValue()) == 0;
        }
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the AtomicLong element is valid or not. Ensure that this method will be synchronized during its call.
	 *
	 * @param anAtomicLong
	 *          AtomicLong element to control.
	 * @return <code>true</code> if AtomicLong element is valid, <code>false</code> otherwise.
	 */
	public synchronized boolean accept(AtomicLong anAtomicLong)
	{
		try
		{
            return (anAtomicLong.longValue() % this.refValue.longValue()) == 0L;
        }
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the BigInteger element is valid or not.
	 *
	 * @param aBigInteger
	 *          BigInteger element to control.
	 * @return <code>true</code> if BigInteger element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(BigInteger aBigInteger)
	{
		try
		{
			return (aBigInteger.intValue() % this.refValue.intValue()) == 0;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the BigDecimal element is valid or not.
	 *
	 * @param aBigDecimal
	 *          BigDecimal element to control.
	 * @return <code>true</code> if BigDecimal element is valid, <code>false</code> otherwise.
	 */
	public boolean accept(BigDecimal aBigDecimal)
	{
		try
		{
			return (aBigDecimal.doubleValue() % this.refValue.doubleValue()) == 0.0d;
		}
		catch (ClassCastException e)
		{
			// Do nothing, only for implementation
		}

		return false;
	}

	/**
	 * Verify if the Number element is valid or not.
	 *
	 * @param aNumber
	 *          Number element to control.
	 * @return Always <code>false</code> because a control for an abstract Number value type it's impossible.
	 */
	@Override
	public boolean accept(Number aNumber)
	{
		return ((aNumber.byteValue() % this.refValue.byteValue()) == 0);
	}
}
