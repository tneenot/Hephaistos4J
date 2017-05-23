/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Unit tests based on template for {@link ArrayDeque} type.
 */
public class ArrayDequeTestFiltered extends CollectionFilteredBehaviorTest<Collection<Integer>, Not<Integer>>
{
  @Override
  protected void instantiateReferencesTestData()
  {
    this.collectionOfThisTemplate = new ArrayDeque<>();
    this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

    this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
    this.invalidCollectionValues = new ArrayDeque<>();

  }

  // Used only to force all running tests
  @Test
  public void test_ForceRunInheritTests()
  {
  }
}
