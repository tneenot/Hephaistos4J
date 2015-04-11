package org.hlib4j.collection;

import org.junit.Test;

import java.util.ArrayDeque;

/**
 * Unit tests based on template for ArrayDeque type.
 */
public class FilteredArrayDequeTest extends FilteredTemplateTest<java.util.Collection<Integer>, Not<Integer>> {
    @Override
    protected void initializeElementsReferences() {
        this.sourceListRef = new ArrayDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new ArrayDeque<>();

    }

    @Override
    protected void destroyElementsReferences() {
        this.invalidListRef = null;
        this.filteredListRef = null;
        this.ruleRef = null;
        this.sourceListRef = null;
    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
