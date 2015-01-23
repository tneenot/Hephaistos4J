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

import java.lang.reflect.InvocationTargetException;

/**
 * Builds a predicate whose the result is the opposite to the parent predicate itself. The predicate is given by a
 * method for an object, that will return a value.
 *
 * @param <E>
 *          Type of element for the predicate
 * @author Tioben Neenot
 * @see Predicate
 */
public class Not<E> extends Predicate<E>
{

	/**
	 * Builds an instance of the opposite of a {@link Predicate}.
	 *
	 * @param value
	 *          reference value for opposite of a {@link Predicate}.
	 * @see org.hlib4j.collection.Predicate#Predicate(Object)
	 */
	public Not(Object value)
	{
		super(value);
	}

	/**
	 * Builds an instance of the opposite of a {@link Predicate} according to model and method's name.
	 *
	 * @param model
	 *          Model type of this class
	 * @param methodName
	 *          The method's name for the class. The value returned by this methodName will be compare to the &lt;E&gt;
	 *          type.
	 * @throws InvocationTargetException
	 *           Invocation method error
	 * @throws IllegalAccessException
	 *           Method invocation error
	 * @throws IllegalArgumentException
	 *           Invocation error into given model
	 * 
	 *           <b>Note: </b> If method not found, a ClassCastException will be thrown.
	 * @see org.hlib4j.collection.Predicate#Predicate(Object, String)
	 */
	public Not(E model, String methodName) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException
	{
		super(model, methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hlib4j.collection.Predicate#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(E e)
	{
		return !super.accept(e);
	}

}
