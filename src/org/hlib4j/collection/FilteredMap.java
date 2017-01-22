/*
 * Tioben library: a library with solutions for redundant aspects into
 * development.
 * 
 * Copyright (C) 2012-2013 Tioben Neenot
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
 */
package org.hlib4j.collection;

import org.hlib4j.concept.Rule;
import org.hlib4j.util.States;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A
 * <code>Map</code> that's controlling its elements according to a {@link Rule}.
 * For this map, all elements according to the {@link Rule}, will be added into
 * the map. Otherwise, this element will be rejected. If developer uses the
 * elements list returned by the {@link #values()} method, all elements will set
 * into a {@link FilteredCollection} class. As defined by the constructor
 * {@link #FilteredMap(Map, Rule)}, this class is not a real map itself, but takes
 * the control of an external map. All elements in the map will be managed
 * according to the {@link Rule}. If the external map contains forbidden
 * elements yet, this map will delete forbidden elements.
 *
 * @param <K> The key type for this collection
 * @param <V> The value type for this collection
 * @author Tioben Neenot
 * @see FilteredCollection
 */
final class FilteredMap<K, V> extends AbstractMap<K, V> implements org.hlib4j.concept.Cleaner {

    /**
     * Internal map to manage all records
     */
    private Map<K, V> map = null;
    /**
     * The filter to apply to all map records
     */
    private Rule<V> filter = null;

    /**
     * Build an instance of this map.
     *
     * @param originalMap Map to use for records managing
     * @param filter      {@link Rule} to use with the Map
     */
    FilteredMap(Map<K, V> originalMap, Rule<V> filter) {
        super();

        try {
            this.map = States.validate(originalMap);
            this.filter = States.validate(filter);
        } catch (AssertionError e) {
            throw new NullPointerException(e.getMessage() + ". Null Map or filter.");
        }

        // Purge all records from the original map that are not conforms with the
        // filter
        clean();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FilteredMap<?, ?> that = (FilteredMap<?, ?>) o;

        if (!map.equals(that.map)) return false;
        return filter.equals(that.filter);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + map.hashCode();
        result = 31 * result + filter.hashCode();
        return result;
    }

    /*
         * (non-Javadoc)
         *
         * @see java.util.AbstractMap#values()
         */
    @Override
    public Collection<V> values() {
        return new FilteredCollection<>(this.map.values(), this.filter);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#clear()
     */
    @Override
    public void clear() {
        this.map.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#containsKey(java.lang.Object)
     */
    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#containsValue(java.lang.Object)
     */
    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#get(java.lang.Object)
     */
    @Override
    public V get(Object key) {
        return this.map.get(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#keySet()
     */
    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public V put(K key, V value) {
        if (!this.filter.accept(value)) {
            return null;
        }

        return this.map.put(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (K _key : map.keySet()) {
            put(_key, map.get(_key));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#remove(java.lang.Object)
     */
    @Override
    public V remove(Object key) {
        return this.map.remove(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractMap#size()
     */
    @Override
    public int size() {
        this.clean();
        return this.map.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hlib4j.concept.Cleaner#clean()
     */
    @Override
    public int clean() {
        int _original_size = this.map.size();

        // Purge forbidden values according to the filter
        {
            new FilteredCollection<>(this.map.values(), this.filter);
        }
        return _original_size - this.map.size();
    }
}
