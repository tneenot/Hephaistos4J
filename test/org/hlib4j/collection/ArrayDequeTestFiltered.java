package org.hlib4j.collection;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Unit tests based on template for {@link ArrayDeque} type.
 */
public class ArrayDequeTestFiltered extends CollectionFilteredTTemplateTest<Collection<Integer>, Not<Integer>> {
    @Override
    protected void instanciateReferencesTestData() {
        this.collectionOfThisTemplate = new ArrayDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
        this.invalidCollectionValues = new ArrayDeque<>();

    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
