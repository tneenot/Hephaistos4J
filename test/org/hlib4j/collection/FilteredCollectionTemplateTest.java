package org.hlib4j.collection;

import org.junit.Test;

import java.util.Vector;

/**
 * Created by tneenot on 03/04/15.
 */
public class FilteredCollectionTemplateTest extends FilteredTemplateTest<java.util.Collection<Integer>, Not<Integer>> {

    @Override
    protected void initializeElementsReferences() {
        this.sourceListRef = new Vector<>();
        this.ruleRef = new Not<>(1);
        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);

    }

    @Override
    protected void destroyElementsReferences() {
        this.sourceListRef = null;
        this.filteredListRef = null;
        this.ruleRef = null;
    }

    @Test
    public void test_RunAllInheritsTest() {
    }
}
