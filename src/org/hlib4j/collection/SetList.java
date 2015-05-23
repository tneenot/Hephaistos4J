package org.hlib4j.collection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by tneenot on 5/23/15.
 */
public class SetList<T> extends AbstractSet<T> {
    public SetList(Collection<T> integers) {
    }

    public SetList() {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    public boolean add(T i) {
        return false;
    }

    public int countElementFor(T i) {
        return -1;
    }
}
