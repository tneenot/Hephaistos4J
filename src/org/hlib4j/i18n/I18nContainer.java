package org.hlib4j.i18n;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manager for {@link I18n} definition. This manager allows to define the list of {@link I18n} implementation to take
 * account. While a new locale must be applied, a call to {@link #update()} or {@link #update(Locale)} will change all
 * locals for each {@link I18n} implementation.<br> <br> Each {@link I18n} implementation will be added by
 * {@link #add(I18n)} method only if {@link I18n} is a valid implementation. A valid implementation means
 * {@link I18n#getBaseName()} musn't be null or empty and must be existing, otherwise the implementation will not be
 * added, and {@link #add(I18n)} will throw an exception of type of <code>NullPointerException</code> or
 * <code>MissingResourceException</code>.
 * <br><br>
 * The <code>I18nContainer</code> can be used for a non graphic user's interface features for example. If a graphic user
 * interface need to implement the i18n features, used {@link I18nLayout}.
 *
 * @author Tioben Neenot
 * @see I18n
 */
public class I18nContainer
{

	/**
	 * Internal collection of I18n implementation.
	 */
	private final Collection< I18n > i18nCollections;
	/**
	 * Last locale used.
	 */
	private Locale             lastLocale;

	/**
	 * Builds an instance of I18nManager with default current locale.
	 */
	public I18nContainer()
	{
		this( Locale.getDefault() );
	}

	/**
	 * Build an instance of I18nManager with a specific locale as default.
	 *
	 * @param locale Default locale
	 */
	public I18nContainer( Locale locale )
	{
		this.i18nCollections = new ArrayList<>();
		if ( null == locale )
		{
			throw new NullPointerException();
		}

		lastLocale = locale;
	}

	/**
	 * Gets last locale used.
	 *
	 * @return Last locale.
	 */
	public Locale getLastLocale()
	{
		return lastLocale;
	}

	/**
	 * Adds an I18n implementation only if this one is valid. A valid implementation means {@link I18n#getBaseName()}
	 * musn't be null or empty and must be existing.
	 *
	 * @param i18n I18n implementation
	 * @return <code>true</code> if I18n implementation is valid and not exists into I18nContainer, <code>false</code>
	 * otherwise.
	 */
	public boolean add( I18n i18n )
	{
		if ( null != i18n && !i18nCollections.contains( i18n ) )
		{
			// Build a ResourceBundle to test if this one is valid or not
			{
				ResourceBundle.getBundle( i18n.getBaseName(), getLastLocale() );
			}

			// No error, so bundle based on baseName of I18n is valid
			return i18nCollections.add( i18n );
		}

		return false;
	}

	/**
	 * Removes an I18n instance from I18nContainer.
	 *
	 * @param i18n I18n instance to remove
	 * @return <code>true</code> if instance has been removed, <code>false</code> otherwise (can be meaning locale doesn't
	 * exist).
	 */
	public boolean remove( I18n i18n )
	{
		return i18nCollections.remove( i18n );
	}

	/**
	 * Controls if an I18n instance already exists into I18nContainer or not.
	 *
	 * @param i18n I18n instance to control.
	 * @return <code>true</code> if I18n instance exists, or <code>false</code> otherwise.
	 */
	public boolean contains( I18n i18n )
	{
		return i18nCollections.contains( i18n );
	}

	/**
	 * Return the number of I18n implementation taking account by this I18nContainer instance.
	 *
	 * @return Number of I18n implementation taking account by I18nContainer instance.
	 */
	public int size()
	{
		return i18nCollections.size();
	}

	/**
	 * Updates all I18n implementations with last locale.
	 */
	public void update()
	{
		update( getLastLocale() );
	}

	/**
	 * Updates all I18n implementations with a specific locale. This locale will become the last locale after update.
	 *
	 * @param locale Locale to use for all I18n implementation
	 */
	public void update( Locale locale )
	{
		for ( I18n _i18n : i18nCollections )
		{
			_i18n.change( locale );
		}

		lastLocale = locale;
	}
}
