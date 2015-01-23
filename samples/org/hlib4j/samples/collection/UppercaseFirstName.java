package org.hlib4j.samples.collection;
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

import org.hlib4j.collection.Processing;

/**
 * Fix first letter of the first name to upper case.
 *
 * @author Tioben Neenot
 */
public class UppercaseFirstName extends Processing< People >
{

	@Override
	public boolean perform( People e )
	{
		char c = e.getFirstname().charAt( 0 );
		if ( c >= 'a' && c <= 'z' )
		{
			e.setFirstname( ( ( char ) ( c - ' ' ) ) + e.getFirstname().substring( 1 ) );
			return true;
		}

		return false;
	}
}
