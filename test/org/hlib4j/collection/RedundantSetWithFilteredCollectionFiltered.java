/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.Test;

import java.util.Collection;

/**
 * Unit tests class to control {@link RedundantSet} class using as a filtered collection type. Uses this class with the {@link
 * CollectionBehaviorTest} to control the Liskov principle.
 */
public class RedundantSetWithFilteredCollectionFiltered extends TemplateTestCollectionFilteredBehavior<Collection<Integer>, Not<Integer>>
{

  @Override
  protected void instantiateReferencesTestData()
  {
    this.collectionOfThisTemplate = new RedundantSet<>();
    this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
    this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
    this.invalidCollectionValues = new RedundantSet<>();
  }

  @Test
  public void test_ForceRunAllUnitTests()
  {
    // Default method to force to run all unit tests due to xUnit limitation constraint.
  }
}
