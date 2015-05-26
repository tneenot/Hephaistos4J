package org.hlib4j.collection;

import java.util.*;

/**
 * Collection of elements with redudant elements authorized. This collection doesn't each duplicate element as an specific
 * instance, but register only the first instance as reference element. All other same elements are counted as a supplementary
 * instance without recorded them. That's meaning in the internal representation, the element is recorded once. Other elements
 * are counted as supplementary instance, but the effective instance will not be setting into the collection.<br><br>
 *
 * To know the number of redundant elements, use the {@link #countElementFor(Object)}. The method {@link #size()} is taking account
 * of redundant elements for its size.
 */
public class RedundantSet<T> extends AbstractSet<T> {

    // TODO: replace Integer by a new class: Counter => Increment(), Decrement(). Counter gets the inital value and the default step for value counting.
    private Map<T, Integer> internalRedundantValues;

    public RedundantSet(Collection<T> values) {
        this();

        addAll(values);
    }

    /**
     * Build an empty collection for the RedundantSet class.
     */
    public RedundantSet() {
        super();
        this.internalRedundantValues = new HashMap<>();
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        int _real_size = 0;

        for (T _element : this.internalRedundantValues.keySet()) {
            _real_size += this.countElementFor(_element);
        }

        return _real_size;
    }

    public boolean add(T value) {
        if (this.internalRedundantValues.containsKey(value)) {
            Integer _occurrence_counter = this.internalRedundantValues.get(value);
            this.internalRedundantValues.put(value, new Integer(_occurrence_counter.intValue() + 1));
        } else {
            this.internalRedundantValues.put(value, 1);
        }

        return true;
    }

    @Override
    public boolean remove(Object value) {
        if (this.internalRedundantValues.containsKey(value)) {
            Integer _occurrence_counter = this.internalRedundantValues.get(value);
            if (_occurrence_counter.intValue() > 1) {
                this.internalRedundantValues.put((T) value, new Integer(_occurrence_counter - 1));
            } else {
                this.internalRedundantValues.remove(value);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> otherCollection) {
        boolean _are_some_removing = true;
        for (Object _element : otherCollection) {
            _are_some_removing &= this.remove(_element);
        }

        return _are_some_removing;
    }

    @Override
    public boolean retainAll(Collection<?> otherCollection) {
        boolean _are_some_retaining = false;
        List<T> _duplicate_for_loop = (List<T>) Arrays.asList(this.internalRedundantValues.values());
        for (Object _element : _duplicate_for_loop) {
            if (otherCollection.contains(_element) == false) {
                this.internalRedundantValues.remove(_element);
                _are_some_retaining = true;
            }
        }

        return _are_some_retaining;
    }

    @Override
    public boolean addAll(Collection<? extends T> values) {
        for (T v : values) add(v);
        return true;
    }

    public int countElementFor(T element) {
        return this.internalRedundantValues.containsKey(element) ? this.internalRedundantValues.get(element).intValue() : 0;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public void clear() {
        this.internalRedundantValues.clear();
    }
}
