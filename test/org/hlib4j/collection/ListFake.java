/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
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
