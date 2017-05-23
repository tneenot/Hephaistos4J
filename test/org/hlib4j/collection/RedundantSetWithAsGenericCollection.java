/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 * Copyright (C) 2015 Tioben Neenot
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
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
