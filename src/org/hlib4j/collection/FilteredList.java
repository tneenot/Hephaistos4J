// TODO It's necessary to factorise code between FilteredCollection and this class (+ FitleredMap ?). This code is not DRY !
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

package org.hlib4j.collection;

import java.util.*;

/**
 * A
 * <code>List</code> where elements are filtering according to its  {@link Rule} definition. For this
 * FilteredList, only all elements according to the {@link Rule}, will be added.
 * Otherwise, this element will be rejected. If developer uses the elements managedList returned by the {@link #toArray()}
 * method, even if he adds a forbidden value, this value will not be backed to this collection. If the external
 * collection given as argument to the constructor {@link #FilteredList(List, Rule)}} contains
 * forbidden elements yet, the <code>FilteredList</code> will delete them. <br><br>
 * 
 * This class is using as implementations for {@link org.hlib4j.collection.Collections#makeFilteredList(java.util.List, Rule)}.
 *
 * @param <ElementType> The data type of this managedList
 * @author Tioben Neenot
 * @see Rule
 */
final class FilteredList < ElementType > extends AbstractList< ElementType > implements Cleaner
{

	/**
	 * The filter links with this managedList
	 */
	private Rule< ElementType > filter      = null;
	/**
	 * The elements of this managedList
	 */
	private List< ElementType > managedList = null;

	/**
	 * Builds an instance of the <code>FilteredList</code>. This class is a wrapper on a real
	 * collection, and takes the control of the external collection, according to filter definition.
	 *
	 * @param originalList    List links with this wrapper for which all elements will be managing bye the given filter.
	 * @param ruleForThisList {@link Rule} to apply on each element of this lisst.
	 */
	FilteredList( List< ElementType > originalList, Rule< ElementType > ruleForThisList )
	{
		super();

		if ( null == originalList || null == ruleForThisList )
		{
			throw new NullPointerException();
		}

		this.filter = ruleForThisList;
		this.managedList = originalList;

		// Force the cleaning on this filter
		clean();
	}

	/*
 * (non-Javadoc)
 *
 * @see org.hlib4j.collection.Cleaner#clean()
 */
	@Override
	public int clean()
	{
		int _original_size = this.managedList.size();

		{
			new FilteredCollection<ElementType>( this.managedList, this.filter );
		}

		return _original_size - this.managedList.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#add(java.lang.Object)
	 */
	@Override
	public boolean add( ElementType elementType )
	{
		if ( !this.filter.accept( elementType ) )
		{
			return false;
		}

		return this.managedList.add( elementType );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#add(int, java.lang.Object)
	 */
	@Override
	public void add( int index, ElementType elementType )
	{
		if ( this.filter.accept( elementType ) )
		{
			this.managedList.add( index, elementType );
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll( int index, Collection< ? extends ElementType > c )
	{
		for ( ElementType elementType : c )
		{
			add( index, elementType );
		}

		return clean() == 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#clear()
	 */
	@Override
	public void clear()
	{
		this.managedList.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.managedList.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public ElementType get( int index )
	{
		return this.managedList.get( index );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( obj == null )
		{
			return false;
		}
		if ( !super.equals( obj ) )
		{
			return false;
		}
		if ( !( obj instanceof FilteredList ) )
		{
			return false;
		}
		FilteredList< ? > other = ( FilteredList< ? > ) obj;
		if ( !this.managedList.equals( other.managedList ) )
		{
			return false;
		}

		return this.filter.equals( other.filter );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf( Object o )
	{
		return this.managedList.indexOf( o );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#iterator()
	 */
	@Override
	public Iterator< ElementType > iterator()
	{
		// Clean collection in case of link collection to this one is a reference
		clean();
		return this.managedList.iterator();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf( Object o )
	{
		return this.managedList.lastIndexOf( o );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#listIterator()
	 */
	@Override
	public ListIterator< ElementType > listIterator()
	{
		// Clean collection in case of link collection to this one is a reference
		clean();
		return new RestrainListIterator( this.managedList.listIterator() );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#listIterator(int)
	 */
	@Override
	public ListIterator< ElementType > listIterator( int index )
	{
		// Clean collection in case of link collection to this one is a reference
		clean();
		return new RestrainListIterator( this.managedList.listIterator( index ) );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#remove(int)
	 */
	@Override
	public ElementType remove( int index )
	{
		return this.managedList.remove( index );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#removeRange(int, int)
	 */
	@Override
	protected void removeRange( int fromIndex, int toIndex )
	{
		for ( int i = fromIndex; i < toIndex; ++i )
		{
			remove( i );
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#set(int, java.lang.Object)
	 */
	@Override
	public ElementType set( int index, ElementType elementType )
	{
		if ( !this.filter.accept( elementType ) )
		{
			return null;
		}

		return this.managedList.set( index, elementType );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractList#subList(int, int)
	 */
	@Override
	public List< ElementType > subList( int fromIndex, int toIndex )
	{
		return new FilteredList<>( this.managedList.subList( fromIndex, toIndex ), this.filter );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size()
	{
		return this.managedList.size();
	}

	/**
	 * An iterator controlled by the own filter of this managedList.
	 *
	 * @author Tioben Neenot
	 */
	private class RestrainListIterator implements ListIterator< ElementType >
	{

		/**
		 * Real managedList iterator of this managedList
		 */
		private ListIterator< ElementType > realListIterator = null;

		/**
		 * Builds an instance of RestrainListIterator
		 *
		 * @param it Embedded ListIterator type.
		 */
		private RestrainListIterator( ListIterator< ElementType > it )
		{
			this.realListIterator = it;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#add(java.lang.Object)
		 */
		@Override
		public void add( ElementType elementType )
		{
			if ( FilteredList.this.filter.accept( elementType ) )
			{
				this.realListIterator.add( elementType );
			}
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			return this.realListIterator.hasNext();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#hasPrevious()
		 */
		@Override
		public boolean hasPrevious()
		{
			return this.realListIterator.hasPrevious();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#next()
		 */
		@Override
		public ElementType next()
		{
			return this.realListIterator.next();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#nextIndex()
		 */
		@Override
		public int nextIndex()
		{
			return this.realListIterator.nextIndex();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#previous()
		 */
		@Override
		public ElementType previous()
		{
			return this.realListIterator.previous();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#previousIndex()
		 */
		@Override
		public int previousIndex()
		{
			return this.realListIterator.previousIndex();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#remove()
		 */
		@Override
		public void remove()
		{
			this.realListIterator.remove();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.ListIterator#set(java.lang.Object)
		 */
		@Override
		public void set( ElementType elementType )
		{
			if ( FilteredList.this.filter.accept( elementType ) )
			{
				this.realListIterator.set( elementType );
			}
		}
	}
}
