/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */
package org.hlib4j.collection;

import org.hlib4j.concept.Cleaner;
import org.hlib4j.util.States;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A
 * <code>Map</code> that's controlling its elements according to a predicate.
 * For this managedMap, all elements according to the predicate, will be added into
 * the managedMap. Otherwise, this element will be rejected. If developer uses the
 * elements list returned by the {@link #values()} method, all elements will set
 * into a {@link FilteredCollection} class. As defined by the constructor
 * {@link #FilteredMap(java.util.Map, java.util.function.Predicate)}, this class is not a real managedMap itself, but takes
 * the control of an external managedMap. All elements in the managedMap will be managed
 * according to the predicate. If the external managedMap contains forbidden
 * elements yet, this managedMap will delete forbidden elements.
 *
 * @param <K> The key type for this collection
 * @param <V> The value type for this collection
 * @author Tioben Neenot
 * @see FilteredCollection
 */
final class FilteredMap<K, V> extends AbstractMap<K, V> implements Cleaner
{

  /**
   * Internal managedMap to manage all records
   */
  private Map<K, V> managedMap = null;
  /**
   * The ruleForThisMap to apply to all managedMap records
   */
  private Predicate<V> ruleForThisMap = null;

  /**
   * Build an instance of this managedMap.
   *
   * @param sourceMap      Map to use for records managing
   * @param ruleForThisMap The predicate to use with the Map
   */
  FilteredMap(Map<K, V> sourceMap, Predicate<V> ruleForThisMap)
  {
    super();

    try
    {
      this.managedMap = States.validate(sourceMap);
      this.ruleForThisMap = States.validate(ruleForThisMap);
    } catch (AssertionError e)
    {
      throw new NullPointerException(e.getMessage() + ". Null Map or ruleForThisMap.");
    }

    // Purge all records from the original managedMap that are not conforms with the
    // ruleForThisMap
    clean();
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
    result = prime * result + this.managedMap.hashCode();
    return result;
  }

  /*
   * (non javadoc)
   *
   * @see java.lang.Object#equals(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final FilteredMap<K, V> other = (FilteredMap<K, V>) obj;
    if (this.managedMap != other.managedMap && !this.managedMap.equals(other.managedMap))
    {
      return false;
    }
    return this.ruleForThisMap == other.ruleForThisMap || this.ruleForThisMap.equals(other.ruleForThisMap);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#values()
   */
  @Override
  public Collection<V> values()
  {
    return new FilteredCollection<>(this.managedMap.values(), this.ruleForThisMap);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#clear()
   */
  @Override
  public void clear()
  {
    this.managedMap.clear();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#containsKey(java.lang.Object)
   */
  @Override
  public boolean containsKey(Object key)
  {
    return this.managedMap.containsKey(key);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(Object value)
  {
    return this.managedMap.containsValue(value);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#entrySet()
   */
  @Override
  public Set<java.util.Map.Entry<K, V>> entrySet()
  {
    return this.managedMap.entrySet();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#get(java.lang.Object)
   */
  @Override
  public V get(Object key)
  {
    return this.managedMap.get(key);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#isEmpty()
   */
  @Override
  public boolean isEmpty()
  {
    return this.managedMap.isEmpty();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#keySet()
   */
  @Override
  public Set<K> keySet()
  {
    return this.managedMap.keySet();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public V put(K key, V value)
  {
    if (!this.ruleForThisMap.test(value))
    {
      return null;
    }

    return this.managedMap.put(key, value);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends K, ? extends V> m)
  {
    for (K _key : m.keySet())
    {
      put(_key, m.get(_key));
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#remove(java.lang.Object)
   */
  @Override
  public V remove(Object key)
  {
    return this.managedMap.remove(key);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.AbstractMap#size()
   */
  @Override
  public int size()
  {
    return this.managedMap.size();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.hlib4j.collection.Cleaner#clean()
   */
  @Override
  public int clean()
  {
    int _original_size = this.managedMap.size();

    // Purge forbidden values according to the ruleForThisMap
    {
      new FilteredCollection<>(this.managedMap.values(), this.ruleForThisMap);
    }
    return _original_size - this.managedMap.size();
  }
}
