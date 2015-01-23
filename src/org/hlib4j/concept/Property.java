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

/**
 * Defines a property with its value and read/write access information. A
 * <code>Property</code> can have several kinds of values. If a value is setting to a property with readonly status, an
 * exception occurs. A property has a name that can't be never changed after instantiation.
 *
 * @author Tioben Neenot
 */
public class Property
{

	/**
	 * Flag to specify if the property is readonly or not.
	 */
	private boolean readonly = false;
	/**
	 * Property name
	 */
	private String  name     = null;
	/**
	 * Property value
	 */
	private Object  value    = null;

	/**
	 * Create an instance of the Property class with read/write status and no default value.
	 *
	 * @param name Property name
	 * @throws IllegalArgumentException If name is <code>null</code> or empty
	 */
	public Property( String name ) throws IllegalArgumentException
	{
		this( name, false );
	}

	/**
	 * Create an instance of the Property class with no default value.
	 *
	 * @param name     Property name
	 * @param readonly <code>true</code> if the property is readonly, <code>false</code> otherwise.
	 * @throws IllegalArgumentException If name is <code>null</code> or empty
	 */
	public Property( String name, boolean readonly ) throws IllegalArgumentException
	{
		if ( null == name )
		{
			throw new IllegalArgumentException( "Null name forbidden" );
		}

		if ( "".equals( name.trim() ) )
		{
			throw new IllegalArgumentException( "Empty name forbidden" );
		}

		this.name = name;
		this.readonly = readonly;
	}

	/**
	 * Create an instance of the Property class with a readonly status and default value. Even if the property is readonly
	 * it's possible to set a default value with only this constructor. If the developer set a value later with the
	 * <code>setValue(Object )</code> method, an exception will occur.
	 *
	 * @param name     Property name
	 * @param value    Initial property value
	 * @param readonly Flag to specify if the property is readonly or not.
	 * @throws IllegalArgumentException If name is null or empty
	 */
	public Property( String name, Object value, boolean readonly ) throws IllegalArgumentException
	{
		this( name, readonly );
		this.value = value;
	}

	/**
	 * Gets the property name
	 *
	 * @return The property name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * Gets the property value
	 *
	 * @return The property value
	 */
	public final Object getValue()
	{
		return value;
	}

	/**
	 * Sets a property value
	 *
	 * @param value Value to set for the property
	 * @throws UnsupportedOperationException If set a readonly value.
	 */
	public final void setValue( Object value ) throws UnsupportedOperationException
	{
		if ( true == isReadOnly() )
		{
			throw new UnsupportedOperationException( "The property is readonly" );
		}

		this.value = value;
	}

	/*
	 * Override the equals to compare 2 instances of Property
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object o )
	{
		if ( o instanceof Property )
		{
			Property property = ( Property ) o;

			if ( null == getValue() )
			{
				return getName().equals( property.getName() ) && isReadOnly() == property.isReadOnly();
			}

			return getName().equals( property.getName() ) && getValue().equals( property.getValue() ) && isReadOnly() == property.isReadOnly();
		}

		return false;
	}

	/**
	 * Gets the hashCode for the property
	 *
	 * @return HashCode for the property
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 19 * hash + ( this.name != null ? this.name.hashCode() : 0 );
		hash = 19 * hash + ( this.value != null ? this.value.hashCode() : 0 );
		return hash;
	}

	/**
	 * Defines if the property is readonly or not
	 *
	 * @return <code>true</code> if the property is readonly, <code>false</code> if the property is read/write.
	 */
	public boolean isReadOnly()
	{
		return readonly;
	}

	/**
	 * Return the property description
	 *
	 * @return Property description
	 */
	@Override
	public String toString()
	{
		return getName() + "=" + getValue() + " (readonly:" + isReadOnly() + ")";
	}
}
