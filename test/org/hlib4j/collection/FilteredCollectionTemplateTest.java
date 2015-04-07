package org.hlib4j.collection;

import org.hlib4j.util.RandomGenerator;
import org.junit.Test;

import java.util.Vector;

/**
 * Created by tneenot on 03/04/15.
 */
public class FilteredCollectionTemplateTest extends FilteredTemplateTest<java.util.Collection<Integer>, Not<Integer>> {

    @Override
    protected void initializeElementsReferences() {
        this.randomGenerator = new RandomGenerator();
        this.randomGenerator.generateValues(10);
        this.sourceListRef = new Vector<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new Vector<>();

    }

    @Override
    protected void destroyElementsReferences() {
        this.invalidListRef = null;
        this.filteredListRef = null;
        this.sourceListRef = null;
        this.ruleRef = null;
        this.randomGenerator = null;
    }

    @Test
    public void test_RunAllInheritsTest() {
    }
}
