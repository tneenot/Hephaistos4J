package org.hlib4j.collection;

import org.junit.Test;

import java.util.Collection;

/**
 * Created by tneenot on 5/26/15.
 */
public class RedundantSetWithFilteredCollectionFiltered extends CollectionFilteredTTemplateTest<Collection<Integer>, Not<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new RedundantSet<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.collectionListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new RedundantSet<>();
    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
