/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.math.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Tests class for RedundantSet class.
 */
public class RedundantSetTest
{

  private RedundantSet<Integer> redundantSetTesting = null;

  @Before
  public void setUp()
  {
    this.redundantSetTesting = new RedundantSet<>();
  }


  @Test
  public void test_remove_InvalidNullValue_NotRemoved()
  {

    RedundantSet<Integer> _local_set = new RedundantSet<>(this.makeCollectionWithRedundantValues());
    Assert.assertFalse(_local_set.remove(null));
  }

  @Test
  public void test_Add_NullValue_AddedValue()
  {
    Assert.assertTrue(this.redundantSetTesting.add(null));
  }

  @Test
  public void test_add_NullValueTwice_True()
  {
    // Setup
    this.redundantSetTesting.add(null);

    // SUT
    Assert.assertTrue(this.redundantSetTesting.add(null));
  }

  @Test
  public void test_size_NullValueTwice_ValidSize()
  {
    // Setup
    this.setupWithNullValueTwice();

    // SUT
    Assert.assertEquals(2, this.redundantSetTesting.size());
  }

  private void setupWithNullValueTwice()
  {
    this.redundantSetTesting.add(null);
    this.redundantSetTesting.add(null);
  }

  @Test
  public void test_countElementFor_NullValueTwice_ValidSize()
  {
    // Setup
    this.setupWithNullValueTwice();

    // SUT
    Assert.assertEquals(2, this.redundantSetTesting.countElementFor(null));
  }

  @Test
  public void test_countElementFor_InvalidValue_ZeroAwaiting()
  {
    Assert.assertEquals(0, this.redundantSetTesting.countElementFor(4));
  }

  @Test
  public void test_remove_InvalidValue_False()
  {
    Assert.assertFalse(this.redundantSetTesting.remove(4));
  }

  @Test
  public void test_countElementFor_AfterAllRedundantValuesRemoved_NoValueLeft()
  {
    // Setup
    this.setupWithSameValues();
    this.setupWithDifferentValues();

    // SUT
    this.removeAllSameValues();

    // Assert
    Assert.assertEquals(0, this.redundantSetTesting.countElementFor(1));
  }

  private int removeAllSameValues()
  {
    int _nb_removed_values = 3;

    for (int i = 0; i < _nb_removed_values; ++i)
    {
      this.redundantSetTesting.remove(1);
    }

    return _nb_removed_values;
  }

  private int setupWithDifferentValues()
  {
    int _nb_added_values = 2;

    this.redundantSetTesting.add(2);
    this.redundantSetTesting.add(3);

    return _nb_added_values;
  }

  @Test
  public void test_size_AfterAllRedundantValuesRemoved_ValidSize()
  {
    // Setup
    int _added_values = this.setupWithSameValues();
    _added_values += this.setupWithDifferentValues();

    // SUT
    _added_values -= this.removeAllSameValues();

    // Assert
    Assert.assertEquals(_added_values, this.redundantSetTesting.size());
  }

  private int setupWithSameValues()
  {
    int _nb_added_values = 3;
    for (int i = 0; i < _nb_added_values; ++i)
    {
      this.redundantSetTesting.add(1);
    }

    return _nb_added_values;
  }

  @Test
  public void test_remove_OneValueAmongRedundantValues_Valid()
  {
    // Setup
    this.redundantSetTesting.add(1);
    this.addAndRemoveSameValue(1);

    // Assert
    Assert.assertTrue(this.redundantSetTesting.remove(1));
  }

  private void addAndRemoveSameValue(int value)
  {
    this.redundantSetTesting.add(value);
    this.redundantSetTesting.remove(value);
  }

  @Test
  public void test_remove_ValidValue()
  {
    // Setup
    this.redundantSetTesting.add(1);

    // Assert
    Assert.assertTrue(this.redundantSetTesting.remove(1));
  }

  @Test
  public void test_countElementFor_RemoveRedundantValue_ValidDataLeft()
  {
    // Setup
    this.redundantSetTesting.add(5);
    this.addAndRemoveSameValue(5);

    // Assert
    Assert.assertEquals(1, this.redundantSetTesting.countElementFor(5));
  }

  @Test
  public void test_size_RemoveRedundantValue_ValidCollectionSize()
  {
    // Setup
    this.setupWithSameValues();
    this.addAndRemoveSameValue(5);

    // Assert
    Assert.assertEquals(3, this.redundantSetTesting.size());
  }

  @Test
  public void test_countElementFor_MiscellaneaousValues_Valid()
  {
    // Setup
    this.setupWithDifferentValues();

    Assert.assertEquals(1, this.redundantSetTesting.countElementFor(2));
  }

  @Test
  public void test_size_twice_SameValues()
  {
    // Setup
    int _nb_values = this.setupWithSameValues();

    // SUT
    Assert.assertEquals(_nb_values, this.redundantSetTesting.size());
  }

  @Test
  public void test_size_OnEmptyList()
  {
    // Setup
    RedundantSet<Integer> _local_set = new RedundantSet<>();

    // SUT
    Assert.assertEquals(0, _local_set.size());
  }

  @Test
  public void test_countElementFor_ForSameValues_AwaitingNbOfElementsFound()
  {
    // Setup
    this.setupWithSameValues();

    // SUT
    Assert.assertEquals(3, this.redundantSetTesting.countElementFor(1));
  }

  @Test
  public void test_Add_TwiceSameValue_Added()
  {
    // Setup
    redundantSetTesting.add(1);

    // SUT
    Assert.assertTrue(this.redundantSetTesting.add(1));
  }

  @Test
  public void test_add_twice_SizeIsValid()
  {
    // Setup
    this.setupWithSameValues();

    // SUT
    Assert.assertEquals(3, this.redundantSetTesting.size());
  }

  @Test
  public void test_countElementFor_allRedundantValues_added()
  {
    // Setup
    Collection<Integer> _src_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _local_set = new RedundantSet<>(_src_collection);
    _local_set.addAll(_src_collection);

    // SUT
    for (Integer i : _local_set)
    {
      Assert.assertTrue(_local_set.countElementFor(i) > 1);
    }
  }

  @Test
  public void test_size_allValues_added()
  {
    // Setup
    RedundantSet<Integer> _local_set = new RedundantSet<>(this.makeCollectionWithRedundantValues());
    int _counter = 0;
    for (Integer i : _local_set) ++_counter;

    // SUT
    Assert.assertEquals(_counter, _local_set.size());
  }

  @Test
  public void test_removeAll_SameCollectionsContents_SomeValuesRemoved()
  {
    // Setup
    Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _set_ref = new RedundantSet<>(_other_collection);

    // SUT
    Assert.assertTrue(_set_ref.removeAll(_other_collection));
  }

  @Test
  public void test_removeAll_SameCollectionsContents_NoValuesLeft()
  {
    // Setup
    Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _set_ref = new RedundantSet<>(_other_collection);

    // SUT
    _set_ref.removeAll(_other_collection);

    // Assert
    Assert.assertEquals(0, _set_ref.size());
  }

  @Test
  public void test_removeAll_DifferentCollectionContents_SomeNotValuesRemoved()
  {
    // Setup
    Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _set_ref = new RedundantSet<>();

    // SUT
    Assert.assertFalse(_set_ref.removeAll(_other_collection));
  }

  @Test
  public void test_removeAll_DifferentCollectionContents_SizeNotUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5, 6, 7);
    int _nb_elements = this.setupWithDifferentValues();
    _nb_elements += this.setupWithSameValues();

    // SUT
    this.redundantSetTesting.removeAll(_other_collection);

    // Assert
    Assert.assertEquals(_nb_elements, this.redundantSetTesting.size());
  }

  @Test
  public void test_retainAll_SameCollectionsContents_ValuesRetains()
  {
    // Setup
    Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _set_ref = new RedundantSet<>(_other_collection);

    // SUT
    Assert.assertTrue(_set_ref.retainAll(_other_collection));
  }

  @Test
  public void test_retainAll_SameCollectionsContent_SizeNotUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
    RedundantSet<Integer> _set_ref = new RedundantSet<>(_other_collection);
    int _original_size = _set_ref.size();

    // SUT
    _set_ref.retainAll(_other_collection);

    // Assert
    Assert.assertEquals(_original_size, _set_ref.size());
  }

  @Test
  public void test_retainAll_SameCollectionContent_CountElementForSoleValueUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);
    RedundantSet<Integer> _set_ref = new RedundantSet<>(_other_collection);
    _set_ref.add(1);

    // SUT
    _set_ref.retainAll(_other_collection);

    // Assert
    Assert.assertEquals(0, _set_ref.countElementFor(1));
  }

  @Test
  public void test_retainAll_SameCollectionContent_CountElementForSameValueNotUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);
    RedundantSet<Integer> _set_ref = new RedundantSet<>();
    _set_ref.add(5);

    // SUT
    _set_ref.retainAll(_other_collection);

    // Assert
    Assert.assertEquals(1, _set_ref.countElementFor(5));
  }

  @Test
  public void test_retainAll_SameCollectionContent_SizeValueNotUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);
    RedundantSet<Integer> _set_ref = new RedundantSet<>();
    _set_ref.add(5);

    // SUT
    _set_ref.retainAll(_other_collection);

    // Assert
    Assert.assertEquals(1, _set_ref.size());
  }

  @Test
  public void test_addAll_SameCollectionContent_ElementAdded()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(1);

    // SUT
    Assert.assertTrue(this.redundantSetTesting.addAll(_other_collection));
  }

  @Test
  public void test_addAll_SameCollectionContent_CountElementForUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(1);
    this.redundantSetTesting.add(1);

    // SUT
    this.redundantSetTesting.addAll(_other_collection);

    // Assert
    Assert.assertEquals(2, this.redundantSetTesting.countElementFor(1));
  }

  @Test
  public void test_addAll_SameCollectionContent_SizeNotUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(1);

    // SUT
    this.redundantSetTesting.addAll(_other_collection);

    // Assert
    Assert.assertEquals(1, this.redundantSetTesting.size());
  }

  @Test
  public void test_addAll_DifferentCollection_ElementAdded()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);

    // SUT
    Assert.assertTrue(this.redundantSetTesting.addAll(_other_collection));
  }

  @Test
  public void test_addAll_DifferentCollection_SizeUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);
    this.redundantSetTesting.add(1);

    // SUT
    this.redundantSetTesting.addAll(_other_collection);

    // Asset
    Assert.assertEquals(2, this.redundantSetTesting.size());
  }

  @Test
  public void test_addAll_DifferentCollection_CountForElementUpdated()
  {
    // Setup
    Collection<Integer> _other_collection = Arrays.asList(5);

    // SUT
    this.redundantSetTesting.addAll(_other_collection);

    // Assert
    Assert.assertEquals(1, this.redundantSetTesting.countElementFor(5));
  }

  @Test
  public void test_toArray_OnEmptySetList_EmptyArray()
  {
    // Setup
    RedundantSet<Integer> _set_local = new RedundantSet<>();

    // SUT
    Assert.assertEquals(0, _set_local.toArray().length);
  }

  @Test
  public void test_toArray_NonEmptyArray()
  {
    // Setup
    this.redundantSetTesting.add(1);

    Assert.assertEquals(1, this.redundantSetTesting.toArray().length);
  }

  @Test
  public void test_toArray_WithDuplicateValues_ValidArrayLength()
  {
    // Setup
    int _nb_of_elements = this.setupWithDifferentValues();

    // SUT
    Assert.assertEquals(_nb_of_elements, this.redundantSetTesting.toArray().length);
  }

  @Test
  public void test_toArray_WithDuplicateValues_ArrayWithDuplicatesValues()
  {
    // Setup
    this.setupWithDifferentValues();

    // SUT
    Object[] _raw_values = this.redundantSetTesting.toArray();

    // Assert
    for (Object _value : _raw_values)
    {
      Assert.assertTrue(this.redundantSetTesting.contains(_value));
    }
  }

  @Test
  public void test_toArray_WithNoDuplicateValues_ArrayWitNoDuplicatesValues()
  {
    // Setup
    this.setupWithDifferentValues();

    // SUT
    Assert.assertEquals(this.redundantSetTesting.size(), this.redundantSetTesting.toArray().length);
  }

  @Test
  public void test_toArray_WithEmptyCollection_ArrayWithZeroLength()
  {

    Assert.assertEquals(0, this.redundantSetTesting.toArray().length);
  }

  @Test
  public void test_toArrayT_InvalidArraySize_FirstCellWithNull()
  {
    // Setup
    int _nb_of_elements = this.setupWithDifferentValues();

    // SUT
    Assert.assertNull(this.redundantSetTesting.toArray(new Integer[_nb_of_elements + 1])[_nb_of_elements]);
  }

  @Test
  public void test_toArrayT_OnEmptySetList_EmptyArray()
  {
    // Setup
    RedundantSet<Integer> _set_local = new RedundantSet<>();

    // SUT
    Assert.assertEquals(0, _set_local.toArray(new Integer[0]).length);
  }

  @Test
  public void test_toArrayT_NonEmptyArray()
  {
    // Setup
    this.redundantSetTesting.add(1);

    // SUT
    Assert.assertEquals(1, this.redundantSetTesting.toArray(new Integer[0]).length);
  }

  @Test
  public void test_toArrayT_WithDuplicateValues_ValidArrayLength()
  {
    // Setup
    int _nb_of_elements = this.setupWithDifferentValues();

    // SUT
    Assert.assertEquals(_nb_of_elements, this.redundantSetTesting.toArray(new Integer[0]).length);
  }

  @Test
  public void test_toArrayT_WithDuplicateValues_ArrayWithDuplicatesValues()
  {
    // Setup
    this.setupWithDifferentValues();

    // SUT
    Integer[] _raw_values = this.redundantSetTesting.toArray(new Integer[0]);

    // Assert
    for (Object _value : _raw_values)
    {
      Assert.assertTrue(this.redundantSetTesting.contains(_value));
    }
  }

  @Test
  public void test_toArrayT_WithNoDuplicateValues_ArrayWitNoDuplicatesValues()
  {
    // Setup
    this.setupWithDifferentValues();

    // SUT
    Assert.assertEquals(this.redundantSetTesting.size(), this.redundantSetTesting.toArray(new Integer[0]).length);
  }

  @Test
  public void test_toArrayT_WithEmptyCollection_ArrayWithZeroLength()
  {

    Assert.assertEquals(0, this.redundantSetTesting.toArray(new Integer[0]).length);
  }

  @Test
  public void test_Iterator_WithDuplicateValues_DuplicateValuesAreExisting()
  {
    // Setup
    this.setupWithSameValues();

    // Assert
    for (Integer i : this.redundantSetTesting)
    {
      Assert.assertEquals(1, i.intValue());
    }
  }

  @Test
  public void test_Iterator_WithNoDuplicateValues_NoDuplicateValuesAreExisting()
  {
    // Setup
    this.setupWithDifferentValues();

    // Assert
    int _previous = 0;
    for (Integer i : this.redundantSetTesting)
    {
      Assert.assertNotEquals(_previous, i.intValue());
      _previous = i.intValue();
    }
  }

  @Test
  public void test_Iterator_WithDuplicateValues_DuplicateValuesAreExistingWithClassicalWay()
  {
    // Setup
    int _nb_of_elements = this.setupWithSameValues();
    int _counter = 0;

    // SUT
    Iterator<Integer> _it = this.redundantSetTesting.iterator();
    while (_it.hasNext())
    {
      ++_counter;
      _it.next();
    }

    // Assert
    Assert.assertEquals(_nb_of_elements, this.redundantSetTesting.size());

  }

  @Test
  public void test_CopyConstructor_MoveSetListIntoAnotherCollection_SameSize()
  {
    // Setup
    this.setupWithSameValues();

    // SUT
    Collection<Integer> _other_collection = new ArrayList<>(this.redundantSetTesting);

    // Assert
    Assert.assertEquals(_other_collection.size(), this.redundantSetTesting.size());
  }

  @Test
  public void test_CopyConstructor_MoveSetListIntoAnotherCollection_NewCollectionWithDuplicateValues()
  {
    // Setup
    this.setupWithSameValues();

    // SUT
    Collection<Integer> _other_collection = new ArrayList<>(this.redundantSetTesting);

    // Assert
    for (Integer i : _other_collection)
    {
      Assert.assertEquals(1, i.intValue());
    }
  }

  @Test(expected = java.lang.UnsupportedOperationException.class)
  public void test_Remove_FromIterator_IllegalStateException()
  {
    // Setup
    this.redundantSetTesting = new RedundantSet<>(this.makeCollectionWithRedundantValues());

    // SUT
    Iterator<Integer> _it = this.redundantSetTesting.iterator();

    // Assert by invoking internal exception
    _it.next();
    _it.remove();

  }

  @After
  public void tearDown()
  {
    this.redundantSetTesting.clear();
    this.redundantSetTesting = null;
  }

  private Collection<Integer> makeCollectionWithRedundantValues()
  {
    RandomGenerator _random_generator = new RandomGenerator();
    _random_generator.generateRandomValues(10);

    return _random_generator.getRandomElements();
  }
}
