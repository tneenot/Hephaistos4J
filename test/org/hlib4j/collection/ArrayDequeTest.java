package org.hlib4j.collection;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Unit tests based on template for ArrayDeque type.
 */
public class ArrayDequeTest extends FilteredTTemplateTest<Collection<Integer>, Not<Integer>> {
    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new ArrayDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new ArrayDeque<>();

    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
