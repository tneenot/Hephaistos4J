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

import java.util.Comparator;

/**
 * This class is an implementation of {@link Comparator} type. This comparator
 * reverse the result of natural comparable corresponding to the element of type
 * <code>T</code>. This comparator can be used to get a sort into a reverse
 * order for example.
 *
 * @param <T> The comparable type for this comparator.
 * @author Tioben Neenot
 */
public class ReverseComparator < T extends Comparable< T > > implements Comparator< T >
{

	/**
	 * Compare 2 <code>T</code> occurrences type, and reverse the natural order
	 * given by their original respective {@link Comparator}.
	 *
	 * @param firstOccurrence First occurrence for the comparator
	 * @param secondOccurrence Second occurrence for the comparator
	 * @return The inverse result based on values for natural comparable type for
	 * <code>o1</code> and <code>o2</code>.
	 */
	@Override
	public int compare( T firstOccurrence, T secondOccurrence )
	{
		return firstOccurrence.compareTo( secondOccurrence ) * -1;
	}
}
