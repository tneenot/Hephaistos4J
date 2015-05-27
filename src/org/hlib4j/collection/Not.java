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
 * Builds a predicate whose the result is the opposite to the parent predicate itself. The predicate is given by a
 * method for an object, that will return a value.
 *
 * @author Tioben Neenot
 */
public class Not<E> implements Rule<E>
{

    private final Object refValue;

    /**
	 * Builds an instance of the opposite of a {@link PredicateMethod} according to model and method's name.
	 *
     * @param refValue
     *          Reference value for this rule.
     */
	public Not(Object refValue)
	{
		this.refValue = refValue;
	}


    @Override
	public boolean accept(E theValue)
	{
        // If the reference value and the comparison value are null so return the opposite of this state
        boolean _flag_ctrl = States.isNullOrEmpty(this.refValue) & States.isNullOrEmpty(theValue);
        if(_flag_ctrl) { return false; }

        // If the reference value is not null, compare it with the parameter
        if(!States.isNullOrEmpty(this.refValue)) {
            return !refValue.equals(theValue);
        }

        // Otherwise, compare the parameter with the reference value
        return !this.refValue.equals(theValue);
    }

}
