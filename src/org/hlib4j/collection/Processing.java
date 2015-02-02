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

import java.util.function.Predicate;

/**
 * Runs a process on a specific value given as argument. The Processing class is
 * using within a collection for which a predicate is applying. Process is
 * applying with {@link Processing#perform(java.lang.Object)} method. The
 * process is only calling if and only if
 * {@link Processing#test(java.lang.Object)} return <code>true</code> for
 * inner predicate.<br><br>
 *
 * Inner predicate is specifying with
 * {@link Processing#Processing(java.util.function.Predicate)} constructor.
 * Default constructor is a convenient constructor that implements an internal predicate that returns
 * always true.
 *
 * @param <E> Element of the processing.
 * @author Tioben Neenot
 */
public abstract class Processing <E> implements Predicate<E> {

    /**
     * Predicate for this processing class.
     */
    private Predicate<E> predicate = null;

    /**
     * Default constructor that's implementing a predicate that's returning always
     * <code>true</code>.
     */
    public Processing()
    {
        this(p-> true);
    }

    /**
     * Builds an instance of the Processing class with a predicate value
     * @param predicate Predicate value
     */
    public Processing(Predicate<E> predicate)
    {
        this.predicate = States.validate(predicate);
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param e the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(E e) {

        if(this.predicate.test(e))
        {
           return perform(e);
        }

        return false;
    }

    /**
     * Applies a treatment for the element given as parameter.
     *
     * @param e Element on which the treatment will be applied.
     * @return <code>true</code> if treatment has been performed,
     * <code>false</code> if treatment was not necessary.
     */
    public abstract boolean perform(E e);
}
