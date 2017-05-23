/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import java.util.Collection;

/**
 * Unit tests for the {@link RedundantSet} include into the {@link CollectionBehaviorTest} to control the Liskov principle.
 */
public class RedundantSetWithAsGenericCollection extends CollectionBehaviorTest<Collection<Integer>>
{

  @Override
  protected void instantiateReferencesTestData()
  {
    this.collectionOfThisTemplate = new RedundantSet<>();
    this.invalidCollectionValues = new RedundantSet<>();
    this.testingCollection = new RedundantSet<>();
  }

  @Override
  public void test_Add_InvalidData_DataNotAdded()
  {
    // Not available for this collection type. Skip test.
  }

  @Override
  public void test_AddAll_InvalidData_NotAddedData()
  {
    // Not available for this collection type. Skip test.
  }

  @Override
  public void test_Add_InvalidData_ControlIsNotInCollection()
  {
    // Not available for this collection type. Skip test.
  }
}
