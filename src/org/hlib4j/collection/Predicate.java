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

import org.hlib4j.util.States;

/**
 * A convenience class to compare a reference value with another one.
 * The comparison has validated if and
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
 * To use the {@link Predicate} with this class, implement the following rule:<br>
 * <br>
 * <pre>
 * Predicate&lt;Integer&gt; e = new Predicate&lt;A&gt;(5);
 * ...
 * int another_int = 5;
 * e.accept(another_int);
 * </pre>
 * <br>
 * The method will return <code>true</code> if the other int has the
 * same value than the first given as reference into the constructor.<br>
 * <br>
 *
 * @param <E> value type for comparison
 * @author Tioben Neenot
 */
public class Predicate<E> implements Rule<E> {

    /**
     * The equal clause to gets the referenced value
     */
    private E simpleValue = null;


    /**
     * Builds an instance of a PredicateMethod with a value only as reference.
     *
     * @param simpleValue Reference value
     */
    public Predicate(E simpleValue) {
        this.simpleValue = simpleValue;
    }


    /**
     * @see Rule#accept(Object)
     */
    @Override
    public boolean accept(E element) {

        return States.isNullOrEmpty(this.simpleValue) ? this.simpleValue == element : this.simpleValue.equals(element);
    }
}
