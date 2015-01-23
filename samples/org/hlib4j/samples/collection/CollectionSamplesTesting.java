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

import org.hlib4j.collection.Collections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This sample allow to show code difference between standard filtering collection and filtering with
 * {@link org.hlib4j.collection.Collections} classes. The objective is to show differences of cost coding between
 * classical method and proposal method for collection filtering.
 * 
 * For this sample, we're using a text file as input from which elements will be filtering.
 *
 * @author Tioben Neenot
 */
public class CollectionSamplesTesting
{

	/**
	 * @param args the command line arguments
	 */
	public static void main( String[] args )
	{
		Collection< People > _peoples = new ArrayList<>();

		try
		{
			// First of all read all text elements by passing comments lines and parse all lines as People bean
			BufferedReader _reader = new BufferedReader( new FileReader( "samples/org/hlib4j/samples/collection/peoples.txt" ) );
			String _raw;
			while ( ( _raw = _reader.readLine() ) != null )
			{
				if ( _raw.charAt( 0 ) != '#' )
				{
					_peoples.add( People.parse( _raw ) );
				}
			}

			_reader.close();
		}
		catch ( FileNotFoundException ex )
		{
			Logger.getLogger( CollectionSamplesTesting.class.getName() ).log( Level.SEVERE, null, ex );
		}
		catch ( IOException | ParseException ex )
		{
			Logger.getLogger( CollectionSamplesTesting.class.getName() ).log( Level.SEVERE, null, ex );
		}

		System.out.println( "Read peoples list: " + _peoples );

		buildFemalesClassic( _peoples );
		buildFemalesNewMethod( _peoples );

		// Fix first names with upper case if their first character is a lower case
		Collection< People > _upper_cases = Collections.makeFilteredCollection( _peoples, new UppercaseFirstName() );
		System.out.println( "Upper cases collection: " + _upper_cases );
	}

	/**
	 * Builds only females collection with classic method
	 *
	 * @param p Source collection
	 */
	private static void buildFemalesClassic( Collection< People > p )
	{
		Iterator< People > _it_people = p.iterator();
		Collection< People > _females1 = new ArrayList<>();
		while ( _it_people.hasNext() )
		{
			People _p = _it_people.next();
			if ( _p.isFemale() )
			{
				_females1.add( _p );
			}
		}

		System.out.println( "Only females\n\tClassic method: " + _females1 );
	}

	/**
	 * Builds only females collection with new method
	 *
	 * @param p Source collection
	 */
	private static void buildFemalesNewMethod( Collection< People > p )
	{
		Collection< People > _females2 = Collections.makeFilteredCollection( p, new FemaleRule() );
		System.out.println( "\tNew method: " + _females2 );
	}
}
