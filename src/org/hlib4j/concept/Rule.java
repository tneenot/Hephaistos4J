package org.hlib4j.concept;
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

import org.hlib4j.collection.Collections;

/**
 * A rule is a concept that's controlling the validity for an element. Rule
 * allows to attach a control on the element itself. The {@link #accept(Object)}
 * method's allows to control if an element is valid with the rule
 * implementation and can be accepted. <br><br>
 * 
 * A rule can be linked to a collection for managing this collection during data
 * insertion for example.
 *
 * @param <E> Element managed by this rule.
 * @author Tioben Neenot
 * @see Collections
 */
public interface Rule < E >
{

	/**
	 * Verifies if the element is valid according to the rule.
	 *
     * @param element Element to control by the rule.
     * @return <code>true</code> if the rule implementation determines if the
	 * element is valid, <code>false</code> otherwise.
	 */
    boolean accept(E element);
}
