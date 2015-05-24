package org.hlib4j.collection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by tneenot on 5/23/15.
 */
public class DuplicateSet<T> extends AbstractSet<T> {

    public DuplicateSet(Collection<T> integers) {
    }

    public DuplicateSet() {
        super();
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return -1;
    }

    public boolean add(T i) {
        return false;
    }

    public int countElementFor(T i) {
        return -1;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }
}
