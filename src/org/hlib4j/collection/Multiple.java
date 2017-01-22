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

import org.hlib4j.concept.Rule;

/**
 * Multiple is a <code>Rule</code>, computes the multiple for the number value.
 *
 * @param <E>
 *          Type of number
 * @author Tioben Neenot
 */
public class Multiple<E extends Number> implements Rule<E>
{
// >>> Uses the E into constructor rather than to implement each number type. Fix accept and construction with synchronized to take
// account the hypothesis where AtomicInteger would be able to be used to.
    /**
	 * Element reference for multiple
	 */
	private final Number refValue;

    /**
     * Builds an instance of the Multiple for a number of E type
     *
     * @param aNumber Reference value of the multiple
     */
    public Multiple(E aNumber) {
        synchronized (this) {
            this.refValue = aNumber;
        }
    }

    public boolean accept(E element) {
        synchronized (this) {
            try {
                return (element.doubleValue() % this.refValue.doubleValue()) == 0.0;
            } catch (ClassCastException e) {
                // Do nothing, only for implementation
            }
        }

        return false;
    }
}
