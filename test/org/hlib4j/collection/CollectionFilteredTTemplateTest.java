package org.hlib4j.collection;

import org.junit.Test;

import java.util.List;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public abstract class CollectionFilteredTTemplateTest<C extends java.util.Collection<Integer>, R extends Rule> extends CollectionTTemplateTest<C> {

    protected R ruleRef = null;


    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullCollectionParameter_NullPointerException() {
        Collections.makeFilteredCollection(null, this.ruleRef);
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredCollection(this.sourceListRef, null);
    }

}
