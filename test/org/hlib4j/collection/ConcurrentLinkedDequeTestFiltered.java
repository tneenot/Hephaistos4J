package org.hlib4j.collection;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Unit tests based on template for ConcurrentLinkedDeque type.
 */
public class ConcurrentLinkedDequeTestFiltered extends CollectionFilteredTTemplateTest<Collection<Integer>, Not<Integer>> {
    @Override
    protected void instanciateReferencesTestData() {
        this.collectionOfThisTemplate = new ConcurrentLinkedDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
        this.invalidCollectionValues = new ConcurrentLinkedDeque<>();

    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
