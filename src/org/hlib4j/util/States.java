package org.hlib4j.util;
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


import java.util.Collection;

/**
 * Convenient class to test some <code>null</code> or empty states for different types of object.
 *
 * @author Tioben Neenot &lt;tioben.neenot@laposte.net&gt;
 */
public class States
{

	/**
	 * Avoid the build of a default instance.
	 */
	private States()
	{
		// Do nothing
	}

	/**
	 * Controls if the object reference is <code>null</code> or empty.
	 *
	 * @param objectCollection
	 *          Object reference to test
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise. <br>
	 *         <b>Note: don't use this method on array objects, otherwise the result would be <code>false</code>. Uses the
	 *         <code>isNullOrEmptyArray(...)</code> in this case.</b>
	 */
	public static boolean isNullOrEmpty(Object objectCollection)
	{
		try
		{
			return ((Collection<?>) objectCollection).isEmpty();
		}
		catch (NullPointerException e1)
		{
			return true;
		}
		catch (ClassCastException e1)
		{
			return "".equals(objectCollection.toString().trim());
		}
	}

	/**
	 * Controls if an array of <code>Object</code> is <code>null</code> or empty.
	 *
	 * @param objectArray
	 *          Array of Object.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(Object[] objectArray)
	{
		return isNullOrEmpty(objectArray) || objectArray.length == 0;
	}

	/**
	 * Controls if an array of <code>int</code> is <code>null</code> or empty.
	 *
	 * @param i
	 *          Array of integers.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(int[] i)
	{
		return isNullOrEmpty(i) || i.length == 0;
	}

	/**
	 * Controls if an array of <code>short</code> is <code>null</code> or empty.
	 *
	 * @param shortArray
	 *          Array of short.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(short[] shortArray)
	{
		return isNullOrEmpty(shortArray) || shortArray.length == 0;
	}

	/**
	 * Controls if an array of <code>long</code> is <code>null</code> or empty.
	 *
	 * @param longArray
	 *          Array of longs.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(long[] longArray)
	{
		return isNullOrEmpty(longArray) || longArray.length == 0;
	}

	/**
	 * Controls if an array of <code>boolean</code> is <code>null</code> or empty.
	 *
	 * @param booleanArray
	 *          Array of booleans.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(boolean[] booleanArray)
	{
		return isNullOrEmpty(booleanArray) || booleanArray.length == 0;
	}

	/**
	 * Controls if an array of <code>float</code> is <code>null</code> or empty.
	 *
	 * @param floatArray
	 *          Array of floats.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(float[] floatArray)
	{
		return isNullOrEmpty(floatArray) || floatArray.length == 0;
	}

	/**
	 * Controls if an array of <code>double</code> is <code>null</code> or empty.
	 *
	 * @param doubleArray
	 *          Array of doubles.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(double[] doubleArray)
	{
		return isNullOrEmpty(doubleArray) || doubleArray.length == 0;
	}

	/**
	 * Controls if an array of <code>char</code> is <code>null</code> or empty.
	 *
	 * @param charArray
	 *          Array of chars.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(char[] charArray)
	{
		return isNullOrEmpty(charArray) || charArray.length == 0;
	}

	/**
	 * Controls if an array of <code>byte</code> is <code>null</code> or empty.
	 *
	 * @param byteArray
	 *          Array of bytes.
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
	public static boolean isNullOrEmptyArray(byte[] byteArray)
	{
		return isNullOrEmpty(byteArray) || byteArray.length == 0;
	}

	/**
	 * Controls that element given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * element if it's valid. This choice allows to chain method calling during variable affectation for example. The
	 * using of <code>T</code> parameter type assumes to enable the automatic cast for the type of data given as
	 * parameter. In this case, the template is valid for all types of Java, except for not <code>null</code> array. If a
	 * not <code>null</code> but empty array is given as argument, this method wasn't be able to detect the state of this
	 * array.
	 *
	 * @param <T>
	 *          Type of the parameter.
	 * @param element
	 *          Element to control.
	 * @return The element controlled if it's valid.
	 * @throws AssertionError
	 *           If element is not valid.
	 */
	public static <T> T validate(T element)
	{
		if (!isNullOrEmpty(element)) { return element; }

		throw new AssertionError("Invalid element");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param objectArray
	 *          Array to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static Object[] validateArray(Object[] objectArray)
	{
		if (!isNullOrEmptyArray(objectArray)) { return objectArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param intArray
	 *          Array of <code>int</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static int[] validateArray(int[] intArray)
	{
		if (!isNullOrEmptyArray(intArray)) { return intArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param shortArray
	 *          Array of <code>short</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static short[] validateArray(short[] shortArray)
	{
		if (!isNullOrEmptyArray(shortArray)) { return shortArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param longArray
	 *          Array of <code>long</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static long[] validateArray(long[] longArray)
	{
		if (!isNullOrEmptyArray(longArray)) { return longArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param booleanArray
	 *          Array of <code>boolean</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static boolean[] validateArray(boolean[] booleanArray)
	{
		if (!isNullOrEmptyArray(booleanArray)) { return booleanArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param floatArray
	 *          Array of <code>float</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static float[] validateArray(float[] floatArray)
	{
		if (!isNullOrEmptyArray(floatArray)) { return floatArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param doubleArray
	 *          Array of <code>double</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static double[] validateArray(double[] doubleArray)
	{
		if (!isNullOrEmptyArray(doubleArray)) { return doubleArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param charArray
	 *          Array of <code>char</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static char[] validateArray(char[] charArray)
	{
		if (!isNullOrEmptyArray(charArray)) { return charArray; }

		throw new AssertionError("Invalid array");
	}

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param byteArray
	 *          Array of <code>char</code> to control
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static byte[] validateArray(byte[] byteArray)
	{
		if (!isNullOrEmptyArray(byteArray)) { return byteArray; }

		throw new AssertionError("Invalid array");
	}
}
