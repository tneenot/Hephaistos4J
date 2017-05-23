/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 * Copyright (C) 2015 Tioben Neenot
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package org.hlib4j.collection;

import java.util.AbstractList;

/**
 * Test class to test removeRange implementation.
 *
 * @param <E> Type of date
 * @author Tioben Neenot
 */
class ListFake<E> extends AbstractList<E>
{

  /**
   * Internal FilteredList class
   */
  private final FilteredList<E> list;

  /**
   * Builds an instance of AList class.
   *
   * @param l Reference to a sub list
   */
  ListFake(FilteredList<E> l)
  {
    list = l;
  }

  @Override
  public void removeRange(int b, int e)
  {
    list.removeRange(b, e);
  }

  @Override
  public E get(int index)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int size()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
