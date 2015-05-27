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
 * Convenient class to test some <code>null</code> or empty states for different objects type.
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
	 * @param o
	 *          Object reference to test
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise. <br>
	 *         <b>Note: don't use this method on array objects, otherwise the result would be <code>false</code>. Uses the
	 *         <code>isNullOrEmptyArray(...)</code> in this case.</b>
	 */
	public static boolean isNullOrEmpty(Object o)
	{
		try
		{
			return ((Collection<?>) o).isEmpty();
		}
		catch (NullPointerException e1)
		{
			return true;
		}
		catch (ClassCastException e1)
		{
			return "".equals(o.toString().trim());
		}
	}


	/**
	 * Controls if an array is <code>null</code> or empty.
	 *
	 * @param array
	 *          Array to control
     * @param <T> Type of array
	 * @return <code>true</code> if current condition has been verified, <code>false</code> otherwise.
	 */
    public static <T> boolean isNullOrEmptyArray(T[] array)
    {
        return isNullOrEmpty(array) || array.length == 0;
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
	public static <T> T validate(T element) throws AssertionError {
		if (!isNullOrEmpty(element)) { return element; }

		throw new AssertionError("Invalid element");
	}

    /**
     * Validate if the given element is not <code>null</code>. This method returns the tested
     * element if it's valid. This choice allows to chain method calling during variable affectation for example.
     *
     * @param element Element to control.
     * @param <T> Element type.
     * @return The element if it's valid.
     * @throws AssertionError If the element is not valid.
     */
    public static <T> T validateNotNullOnly(T element) throws AssertionError {
        if(null != element) return element;

        throw new AssertionError("Invalid null element");
    }

	/**
	 * Controls that the array given as parameter is not <code>null</code> and not empty. This method returns the tested
	 * array if it's valid. This choice allows to chain method calling during variable affectation for example.
	 *
	 * @param array
	 *          Array to control
     * @param <T> Type of array
	 * @return The array controlled if it's valid.
	 * @throws AssertionError
	 *           If array is not valid.
	 */
	public static <T> T[] validateArray(T[] array) throws AssertionError {
		if (!isNullOrEmptyArray(array)) { return array; }

		throw new AssertionError("Invalid null or empty array");
	}

}
