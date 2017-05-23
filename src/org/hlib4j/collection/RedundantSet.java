/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.math.Counter;
import org.hlib4j.math.RangeException;

import java.util.*;

/**
 * Collection of elements with redundant elements authorized. This collection doesn't each duplicate element as an specific
 * instance, but register only the first instance as reference element. All other same elements are counted as a supplementary
 * instance without recorded them. That's meaning in the internal representation, the element is recorded once. Other elements
 * are counted as supplementary instance, but the effective instance will not be setting into the collection.<br><br>
 * <p>
 * To know the number of redundant elements, use the {@link #countElementFor(Object)}. The method {@link #size()} is taking account
 * of redundant elements for its size.
 */
public class RedundantSet<T> extends AbstractSet<T>
{

  private final Map<T, Counter> internalRedundantValues;

  /**
   * Builds a <code>RedundantSet</code> collection based on the element of the collection given as parameters.
   *
   * @param values Element of the collection that will be copied into the current one.
   */
  public RedundantSet(Collection<T> values)
  {
    this();

    addAll(values);
  }

  /**
   * Build an empty collection for the RedundantSet class.
   */
  public RedundantSet()
  {
    super();
    this.internalRedundantValues = new HashMap<>();
  }

  @Override
  public Iterator<T> iterator()
  {
    return new RedundantSetIterator(this.internalRedundantValues);
  }

  @Override
  public int size()
  {
    int _real_size = 0;

    for (T _element : this.internalRedundantValues.keySet())
    {
      _real_size += this.countElementFor(_element);
    }

    return _real_size;
  }

  public boolean add(T value)
  {
    try
    {
      if (this.internalRedundantValues.containsKey(value))
      {
        this.internalRedundantValues.get(value).increment();
      } else
      {
        this.internalRedundantValues.put(value, new Counter(1, Integer.MAX_VALUE));
      }
    } catch (RangeException e)
    {
      return false;
    }

    return true;
  }

  @Override
  public boolean remove(Object value)
  {
    if (this.internalRedundantValues.containsKey(value))
    {
      if (this.internalRedundantValues.get(value).getCurrentValue() > 1)
      {
        this.internalRedundantValues.get(value).decrement();
      } else
      {
        this.internalRedundantValues.remove(value);
      }

      return true;
    }

    return false;
  }

  @Override
  public boolean removeAll(Collection<?> otherCollection)
  {
    boolean _are_some_removing = false;
    for (Object _element : otherCollection)
    {
      _are_some_removing |= this.remove(_element);
    }

    return _are_some_removing;
  }

  @Override
  public boolean retainAll(Collection<?> otherCollection)
  {
    boolean _are_some_retaining = false;
    List<T> _duplicate_for_loop = (List<T>) Arrays.asList(this.internalRedundantValues.keySet().toArray());
    for (Object _element : _duplicate_for_loop)
    {
      if (!otherCollection.contains(_element))
      {
        this.internalRedundantValues.remove(_element);
      } else
      {
        _are_some_retaining = true;
      }
    }

    return _are_some_retaining;
  }

  @Override
  public boolean addAll(Collection<? extends T> values)
  {
    for (T v : values) add(v);
    return true;
  }

  public int countElementFor(T element)
  {
    return this.internalRedundantValues.containsKey(element) ? this.internalRedundantValues.get(element).getCurrentValue() : 0;
  }

  @Override
  public Object[] toArray()
  {
    return this.toArray(new Object[0]);
  }

  @Override
  public <T1> T1[] toArray(T1[] externalArray)
  {

    if (externalArray.length < this.size())
      externalArray = (T1[]) java.lang.reflect.Array.newInstance(externalArray.getClass().getComponentType(), this.size());

    Iterator<T> _it = this.iterator();
    int _counter = 0;
    while (_it.hasNext())
    {
      externalArray[_counter++] = (T1) _it.next();
    }


    if (externalArray.length > this.size())
    {
      externalArray[this.size()] = null;
    }

    return externalArray;
  }

  @Override
  public void clear()
  {
    this.internalRedundantValues.clear();
  }

  /**
   * This class reads each value and loop on the same value according to the occurrences number.
   */
  private class RedundantSetIterator implements Iterator<T>
  {

    // The current collection on which the iterator will be set
    private final Map<T, Counter> redundantSetCollection;

    // The iterator on keys of the current collection
    private final Iterator<T> keySetIterator;

    // The current element gets from current collection keySetIterator
    private T currentElement = null;

    // The occurrence counter on the currentElement
    private int occurrenceCounter;

    public RedundantSetIterator(Map<T, Counter> internalRedundantValues)
    {
      this.redundantSetCollection = internalRedundantValues;
      this.keySetIterator = this.redundantSetCollection.keySet().iterator();
      this.occurrenceCounter = 0;
    }

    @Override
    public boolean hasNext()
    {

      return this.keySetIterator.hasNext() || this.isAnotherOccurrenceExist();

    }

    private boolean isAnotherOccurrenceExist()
    {
      return this.currentElement != null && this.redundantSetCollection.get(this.currentElement).getCurrentValue() > this
        .occurrenceCounter;
    }

    @Override
    public T next()
    {
      if (this.isAnotherOccurrenceExist())
      {
        ++this.occurrenceCounter;
        return this.currentElement;
      }

      this.occurrenceCounter = 1;
      this.currentElement = this.keySetIterator.next();
      return this.currentElement;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException("Unsupported operation");
    }
  }
}
