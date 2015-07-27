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
import java.lang.reflect.Method;

/**
 * A convenience class to compare a reference value with a value returns by a
 * method name specified into constructor. The comparison has validated if and
 * only if the &lt;E&gt; type referenced from the constructor, is equal to the
 * the value type given in the {@link #accept(Object)} method. For example,
 * suppose we have a class like this:<br>
 * <pre> 
 * class A 
 * { 
 *   private boolean aFlag;
 * 
 *   public A(boolean v) { aFlag = v; }
 * 
 *   public boolean isFlag() { return aFlag; }
 * }</pre>
 * <br>
 * To use the {@link PredicateMethod} with this class, implement the following rule:<br>
 * <br>
 * <pre>
 * PredicateMethod&lt;A&gt; e = new PredicateMethod&lt;A&gt;(new A(true), "isFlag");
 * ...
 * A another_a = new A(true);
 * e.accept(another_a);
 * </pre>
 * <br>
 * The method will return <code>true</code> if the other "A" instance has the
 * same value than the first occurrence as reference, and this occurrence has
 * the same method.<br>
 * <br>
 * <b>Note : </b>if the {@link #accept(Object)} method doesn't receive another
 * type than the model in the constructor, a <code>ClassCastException</code>
 * will be thrown. If the model doesn't implements the method name, a
 * <code>ClassCastException</code> will be thrown. <br>
 * <br>
 * <code>PredicateMethod</code> can be used a simple value as reference thanks to
 * {@link #PredicateMethod(Object, String)} constructor. In this case only value will be
 * compared during {@link #accept(Object)} method calling.
 *
 * @param <E> value type for comparison
 * @author Tioben Neenot
 */
public class PredicateMethod< E > implements Rule < E >
{
	/**
     * The property to use to compare a value
     */
    private String propertyName = null;

	/**
	 * The equal clause to gets the referenced value
	 */
    private Object valueFromPropertyName = null;

	/**
	 * Builds an instance of the <code>PredicateMethod</code> class.
	 *
	 * @param model      Model type of this class
	 * @param methodName The method's name for the class. The value returned by this
     *                   propertyName will be compare to the &lt;E&gt; type.
     * @throws InvocationTargetException Invocation method error
	 * @throws IllegalAccessException    Method invocation error
	 * @throws IllegalArgumentException  Invocation error into given model
	 *                                   <b>Note: </b> If method not found, a ClassCastException will be thrown.
	 */
	public PredicateMethod(E model, String methodName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		// Control if the property name exists in the element and gets the value
		// returned by this method, as referenced value
        this.valueFromPropertyName = isValid(model, methodName).invoke(model);
        this.propertyName = methodName;
    }

    /**
	 * Controls if the propertyName exists in the element
	 *
	 * @param element      The element reference
	 * @param propertyName The property name to find
	 * @return The field found.
	 * @throws InvocationTargetException If <code>propertyName</code> doesn't exist
	 */
	private static Method isValid(Object element, String propertyName) throws InvocationTargetException
	{
		try
		{
			return element.getClass().getMethod( propertyName );
		}
		catch ( NoSuchMethodException | SecurityException e )
		{
			throw new InvocationTargetException(null, "The " + propertyName + " doesn't exist in the "
																			+ ( element == null ? null : element.getClass().getName() ) );
		}
	}

	/**
     * Controls if the element <code>element</code> is conforming to the {@link Rule}.
     * If <code>e</code> element doesn't contains the <code>propertyName</code>
     * method a <code>ClassCastException</code> will be thrown. If method exist
	 * and invocation error is occurs, so return <code>false</code>. If
	 * <code>PredicateMethod</code> was built for a reference value only with
	 * {@link #PredicateMethod(Object, String)} constructor , so in this case accept method runs
	 * only on this value.
	 *
	 * @see org.hlib4j.collection.Rule#accept(java.lang.Object)
	 */
	@Override
    public boolean accept(E element) {
        Object _target_value_from_element;

        try
        {
            // Controls if the method name exists in the element
            _target_value_from_element = isValid(element, this.propertyName).invoke(element);
        }
        catch ( IllegalAccessException | IllegalArgumentException | InvocationTargetException e1 )
        {
            return false;
        }

        return this.valueFromPropertyName.equals(_target_value_from_element);
    }
}
