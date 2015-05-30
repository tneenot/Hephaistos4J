package org.hlib4j.collection;

import org.junit.Test;

import java.util.List;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public abstract class CollectionFilteredTTemplateTest<C extends java.util.Collection<Integer>, R extends Rule> extends CollectionTTemplateTest<C> {

    protected R ruleRef = null;

    @Override
    protected void createTestData() {
        List<Integer> _elements_list = this.randomGenerator.getRandomElements();

        for (Integer i : _elements_list) {
            if (this.testingCollection.add(i) == false) {
                // Adds twice to get an invalid elements list with at least more than one element
                this.invalidCollectionValues.add(i);
                this.invalidCollectionValues.add(i);
            }
        }

        this.testingCollectionOriginalSize = this.testingCollection.size();
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullCollectionParameter_NullPointerException() {
        Collections.makeFilteredCollection(null, this.ruleRef);
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredCollection(this.collectionOfThisTemplate, null);
    }

}
