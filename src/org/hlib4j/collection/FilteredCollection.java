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

import java.util.*;

/**
 * A <code>Collection</code> where elements are filtering according to its  {@link Rule} definition. For this
 * FilteredCollection, only all elements according to the {@link Rule}, will be added.
 * Otherwise, this element will be rejected. If developer uses the elements list returned by the {@link #toArray()}
 * method, even if he adds a forbidden value, this value will not be backed to this collection. If the external
 * collection given as argument to the constructor {@link #FilteredCollection(java.util.Collection, Rule)}} contains
 * forbidden elements yet, the <code>FilteredCollection</code> will delete them. <br><br>
 * <p>
 * This class is using as implementations for {@link org.hlib4j.collection.Collections#makeFilteredCollection(java.util.Collection, Rule)}.
 *
 * @param <ElementType> Elements types of this collection.
 * @author Tioben Neenot
 * @see Rule
 */
class FilteredCollection<ElementType> extends AbstractCollection<ElementType> implements org.hlib4j.concept.Cleaner {

    /**
     * The filter to manage all elements in the managedCollection
     */
    private Rule<ElementType> filter = null;

    /**
     * The collection to manage
     */
    private Collection<ElementType> managedCollection = null;

    /**
     * Builds an instance of this <code>FilteredCollection</code>. This class is a wrapper on a real
     * collection, and takes the control of the external collection, according to filter definition.
     *
     * @param originalCollection    Collection links with this wrapper for which all elements will be managing by the given
     *                              filter.
     * @param ruleForThisCollection {@link Rule} to apply on each element of this collection.
     */
    FilteredCollection(Collection<ElementType> originalCollection, Rule<ElementType> ruleForThisCollection) {
        super();

        try {
            this.setFilter(ruleForThisCollection);
            this.setManagedCollection(originalCollection);
        } catch (AssertionError e) {
            throw new NullPointerException(e.getMessage() + ". Null element.");
        }

        // Force the cleaning on this collection
        clean();
    }

    protected void setManagedCollection(Collection<ElementType> originalCollection) {
        this.managedCollection = States.validateNotNullOnly(originalCollection);
    }

    protected Rule<ElementType> getFilter() {
        return this.filter;
    }

    protected void setFilter(Rule<ElementType> ruleForThisCollection) {
        this.filter = States.validate(ruleForThisCollection);
    }

    /*
     * (non-Javadoc)
     *
     *  @see org.hlib4j.concept.Cleaner#clean()
     */
    @SuppressWarnings("unchecked")
    @Override
    public int clean() {
        int _counter = 0;
        // Control the collection contents. Here remove all elements that contains forbidden value.
        List<Object> _raw_list = Arrays.asList(this.managedCollection.toArray());
        for (Object _element : _raw_list) {
            if (!this.filter.accept((ElementType) _element)) {
                this.managedCollection.remove(_element);
                ++_counter;
            }
        }

        return _counter;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#add(java.lang.Object)
     */
    @Override
    public boolean add(ElementType element) {

        if (!this.filter.accept(element)) {
            return false;
        }

        return this.managedCollection.add(element);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends ElementType> otherCollection) {
        boolean _is_all_added = true;

        for (ElementType e : otherCollection) {
            // If once add is false, so isAllAdded will be false
            _is_all_added &= add(e);
        }

        return _is_all_added;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#clear()
     */
    @Override
    public void clear() {
        this.managedCollection.clear();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object element) {
        clean();
        return this.managedCollection.contains(element);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> otherCollection) {
        return this.managedCollection.containsAll(otherCollection);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return this.managedCollection.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<ElementType> iterator() {
        // Clean all element of the link collection, in case of element would be added by the last one.
        clean();
        return this.managedCollection.iterator();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object element) {
        clean();
        return this.managedCollection.remove(element);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> otherCollection) {
        return this.managedCollection.removeAll(otherCollection);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#retainAll(java.util.Collection)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> initialCollection) {
        // Here we don't accept this operation if some elements from the initial collection can't be according with the filter
        // of this collection.
        for (Object _element : initialCollection) {
            if (!this.filter.accept((ElementType) _element)) {
                return false;
            }
        }

        return this.managedCollection.retainAll(initialCollection);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        this.clean();
        return this.managedCollection.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#toArray()
     */
    @Override
    public Object[] toArray() {
        // Clean all element of the link collection, in case of element would be added by the last one.
        clean();
        return this.managedCollection.toArray();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.AbstractCollection#toArray(T[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        // Clean all element of the link collection, in case of element would be added by the last one.
        clean();
        return this.managedCollection.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilteredCollection<?> that = (FilteredCollection<?>) o;

        if (filter != null ? !filter.equals(that.filter) : that.filter != null) return false;
        return !(managedCollection != null ? !managedCollection.equals(that.managedCollection) : that.managedCollection != null);

    }

    @Override
    public int hashCode() {
        int result = filter != null ? filter.hashCode() : 0;
        result = 31 * result + (managedCollection != null ? managedCollection.hashCode() : 0);
        return result;
    }
}
