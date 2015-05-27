package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by tneenot on 03/04/15.
 */
public class CollectionFilteredTest extends CollectionFilteredTTemplateTest<Collection<Integer>, Not<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new Vector<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.collectionListRef = Collections.makeFilteredCollection(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new Vector<>();

    }

    @Test
    public void test_Equals_EqualsWithSameCollectionType_SameData() {
        // Setup
        Vector<Integer> _copy = new Vector<>(this.collectionListRef);

        FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(_copy, this.ruleRef);

        // Assert
        Assert.assertTrue(this.collectionListRef.equals(_col));
    }

    @Test
    public void test_Equals_NotEqualsWithAnotherCollectionType_NoData() {
        // Setup
        FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(new Vector<Integer>(), new Multiple<Integer>(2));

        // Assert
        Assert.assertFalse(this.collectionListRef.equals(_col));
    }
}
