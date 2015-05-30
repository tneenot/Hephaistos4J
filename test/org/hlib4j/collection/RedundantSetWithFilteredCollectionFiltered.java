package org.hlib4j.collection;

import org.junit.Test;

import java.util.Collection;

/**
 * Unit tests class to control {@link RedundantSet} class using as a filtered collection type. Uses this class with the {@link
 * CollectionTTemplateTest} to control the Liskov principle.
 */
public class RedundantSetWithFilteredCollectionFiltered extends CollectionFilteredTTemplateTest<Collection<Integer>, Not<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new RedundantSet<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.collectionListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new RedundantSet<>();
    }

    @Test
    public void test_RunAllUnitTests() {

    }
}
