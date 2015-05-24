package org.hlib4j.collection;

import java.util.*;

/**
 * Collection of elements with duplicate elements authorized. This collection doesn't each duplicate element as an specific
 * instance, but register only the first instance as reference element. All other same elements are counted as a supplementary
 * instance without recorded them. That's meaning in the internal representation, the element is recorded once. Other elements
 * are counted as supplementary instance, but the effective instance will not be setting into the collection.<br><br>
 *
 * To know the number of redundant elements, use the {@link #countElementFor(Object)}. The method {@link #size()} is taking account
 * of redundant elements for its size.
 */
public class DuplicateSet<T> extends AbstractSet<T> {

    // TODO: replace Integer by a new class: Counter => Increment(), Decrement(). Counter gets the inital value and the default step for value counting.
    private Map<T, Integer> internalDuplicateValues;

    public DuplicateSet(Collection<T> values) {
        this();

        addAll(values);
    }

    /**
     * Build an empty collection for the DuplicateSet class.
     */
    public DuplicateSet() {
        super();
        this.internalDuplicateValues = new HashMap<>();
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        int _real_size = 0;

        for (T _element : this.internalDuplicateValues.keySet()) {
            _real_size += this.countElementFor(_element);
        }

        return _real_size;
    }

    public boolean add(T value) {
        if (this.internalDuplicateValues.containsKey(value)) {
            Integer _occurrence_counter = this.internalDuplicateValues.get(value);
            this.internalDuplicateValues.put(value, new Integer(_occurrence_counter.intValue() + 1));
        } else {
            this.internalDuplicateValues.put(value, 1);
        }

        return true;
    }

    @Override
    public boolean remove(Object value) {
        if (this.internalDuplicateValues.containsKey(value)) {
            Integer _occurrence_counter = this.internalDuplicateValues.get(value);
            if (_occurrence_counter.intValue() > 1) {
                this.internalDuplicateValues.put((T) value, new Integer(_occurrence_counter - 1));
            } else {
                this.internalDuplicateValues.remove(value);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean addAll(Collection<? extends T> values) {
        for (T v : values) add(v);
        return true;
    }

    public int countElementFor(T element) {
        if (this.internalDuplicateValues.containsKey(element)) {
            return this.internalDuplicateValues.get(element).intValue();
        }

        return 0;
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
        this.internalDuplicateValues.clear();
    }
}
