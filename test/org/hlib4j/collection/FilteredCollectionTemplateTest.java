package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

/**
 * Created by tneenot on 03/04/15.
 */
public class FilteredCollectionTemplateTest extends FilteredTemplateTest<java.util.Collection<Integer>, Not<Integer>> {

    @Override
    protected void initializeElementsReferences() {
        this.sourceListRef = new Vector<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.filteredListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new Vector<>();

    }

    @Test
    public void test_Equals_EqualsWithSameCollectionType_SameData() {
        // Setup
        Vector<Integer> _copy = new Vector<>(this.filteredListRef);

        FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(_copy, this.ruleRef);

        // Assert
        Assert.assertTrue(this.filteredListRef.equals(_col));
    }

    @Test
    public void test_Equals_NotEqualsWithAnotherCollectionType_NoData() {
        // Setup
        FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(new Vector<Integer>(), new Multiple<Integer>(2));

        // Assert
        Assert.assertFalse(this.filteredListRef.equals(_col));
    }
}
