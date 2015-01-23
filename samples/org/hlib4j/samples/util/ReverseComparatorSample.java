package org.hlib4j.samples.util;
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

import org.hlib4j.util.ReverseComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Gives a sample of implementation of {@link ReverseComparator}.
 *
 * @author Tioben Neenot
 */
public class ReverseComparatorSample
{

	/**
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		Character[] _decreased = new Character[ 26 ];
		for ( int i = 0; i < _decreased.length; ++i )
		{
			_decreased[ i ] = ( ( char ) ( 90 - i ) );
		}

		List< Character > _list = new ArrayList<>( Arrays.asList( _decreased ) );

		System.out.println( "Before sort: " + _list );
		Collections.sort( _list );

		System.out.println( "After sort (natural order): " + _list );

		Collections.sort( _list, new ReverseComparator< Character >() );

		System.out.println( "After sort with ReverseComparator: " + _list );
	}
}
