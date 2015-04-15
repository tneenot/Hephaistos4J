package org.hlib4j.collection;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Unit tests based on template for ConcurrentLinkedDeque type.
 */
public class ConcurrentLinkedDequeTest extends FilteredTTemplateTest<Collection<Integer>, Not<Integer>> {
    @Override
    protected void initializeElementsReferences() {
        this.sourceListRef = new ConcurrentLinkedDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new ConcurrentLinkedDeque<>();

    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
