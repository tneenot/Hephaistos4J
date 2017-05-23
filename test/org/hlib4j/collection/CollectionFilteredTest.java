/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Vector;

/**
 * Unit test classes for a {@link Vector} class type.
 */
public class CollectionFilteredTest extends CollectionFilteredBehaviorTest<Collection<Integer>, Not<Integer>>
{

  @Override
  protected void instantiateReferencesTestData()
  {
    this.collectionOfThisTemplate = new Vector<>();
    this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
    this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
    this.invalidCollectionValues = new Vector<>();

  }

  @Test
  public void test_Equals_EqualsWithSameCollectionType_SameData()
  {
    // Setup
    Vector<Integer> _copy = new Vector<>(this.testingCollection);

    FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(_copy, this.ruleRef);

    // Assert
    Assert.assertTrue(this.testingCollection.equals(_col));
  }

  @Test
  public void test_Equals_NotEqualsWithAnotherCollectionType_NoData()
  {
    // Setup
    FilteredCollection<Integer> _col = (FilteredCollection) Collections.makeFilteredCollection(new Vector<Integer>(), new Multiple<Integer>(2));

    // Assert
    Assert.assertFalse(this.testingCollection.equals(_col));
  }
}
