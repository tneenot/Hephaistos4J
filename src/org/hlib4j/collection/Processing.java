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

import org.hlib4j.util.States;

/**
 * Runs a process on a specific value given as argument. The Processing class is
 * using within a collection for which a {@link Rule} is applying. Process is
 * applying with {@link Processing#perform(java.lang.Object)} method. The
 * process is only calling if and only if
 * {@link Processing#accept(java.lang.Object)} return <code>true</code> for
 * inner {@link Rule}.<br><br>
 * 
 * Inner {@link Rule} is specifying with
 * {@link Processing#Processing(org.hlib4j.collection.Rule)} constructor.
 * Default constructor implement an internal rule that's returning always true.
 *
 * @param <E> Element of the processing.
 * @author Tioben Neenot
 */
public abstract class Processing < E > implements Rule< E >
{

	/**
	 * Rule using to validate accept(E e) method.
	 */
	private Rule< E > rule;

	/**
	 * Default constructor that's implementing a rule that's returning always
	 * <code>true</code>.
	 */
	public Processing()
	{
		this( ( Rule< E > ) new True() );
	}

	/**
	 * Processing constructor with a specific rule.
	 *
     * @param rule Rule to use that will be authorizing
     *          {@link Processing#perform(java.lang.Object)} method with
     *          {@link Processing#accept(java.lang.Object)}.
	 * @throws NullPointerException If rule is <code>null</code>.
	 */
    public Processing(Rule<E> rule) {
        if (States.isNullOrEmpty(rule)) {
            throw new NullPointerException("Null rule");
        }

        this.rule = rule;
    }

	@Override
    public boolean accept(E element) {
        boolean _return = rule.accept(element);
        if ( _return )
		{
            perform(element);
        }

		return _return;
	}

	/**
	 * Applies a treatment for the element given as parameter.
	 *
     * @param element Element on which the treatment will be applied.
     * @return <code>true</code> if treatment has been performed,
	 * <code>false</code> if treatment was not necessary.
	 */
    public abstract boolean perform(E element);
}
