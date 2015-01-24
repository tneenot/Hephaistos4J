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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bean class for People like record.
 *
 * @author Tioben Neenot
 */
class People
{

	/**
	 * People's firstname
	 */
	private String  firstname;
	/**
	 * People's lastname
	 */
	private String  lastname;
	/**
	 * People's birthdate
	 */
	private Date    birthdate;
	/**
	 * People's type
	 */
	private boolean female;

	/**
	 * Returns a people instance from string description
	 *
	 * @param desc People description
	 * @return A People instance
	 * @throws ParseException If error during People parsing value.
	 */
	static People parse( String desc ) throws ParseException
	{
		String[] _elements = desc.split( ":" );

		People _people = new People();
		_people.female = _elements[ 0 ].toLowerCase().equals( "f" );
		_people.firstname = _elements[ 1 ];
		_people.lastname = _elements[ 2 ];
		_people.birthdate = new SimpleDateFormat( "y-M-d" ).parse( _elements[ 3 ] );

		return _people;
	}

	/**
	 * Gets people's firstname
	 *
	 * @return People's firstname
	 */
	String getFirstname()
	{
		return firstname;
	}

	/**
	 * Sets people's firstname
	 *
	 * @param n People's firstname
	 */
	void setFirstname( String n )
	{
		firstname = n;
	}

	/**
	 * Gets people type
	 *
	 * @return <code>true</code> if people is a type of * * female, <code>false</code> otherwise.
	 */
	boolean isFemale()
	{
		return female;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 79 * hash + ( this.firstname != null ? this.firstname.hashCode() : 0 );
		hash = 79 * hash + ( this.lastname != null ? this.lastname.hashCode() : 0 );
		hash = 79 * hash + ( this.birthdate != null ? this.birthdate.hashCode() : 0 );
		hash = 79 * hash + ( this.female ? 1 : 0 );
		return hash;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final People other = ( People ) obj;
		if ( ( this.firstname == null ) ? ( other.firstname != null ) : !this.firstname.equals( other.firstname ) )
		{
			return false;
		}
		if ( ( this.lastname == null ) ? ( other.lastname != null ) : !this.lastname.equals( other.lastname ) )
		{
			return false;
		}

		return  ( this.birthdate != other.birthdate && ( this.birthdate == null || !this.birthdate.equals( other.birthdate ) ) ) && this.female == other.female;
	}

	@Override
	public String toString()
	{
		return "People{" + "firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate + ", female=" + female + '}';
	}
}
