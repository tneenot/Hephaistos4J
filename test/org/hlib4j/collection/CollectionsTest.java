/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the {@link java.util.Collections} class.
 *
 * @author Tioben Neenot
 */
public class CollectionsTest
{

  /**
   * Collection reference for tests
   */
  private Collection<Integer> ref = null;

  /**
   * Rule reference for tests
   */
  private Predicate<Integer> ruleRef = null;

  /**
   * Test initialisation
   */
  @Before
  public void setUp()
  {
    ruleRef = new Predicate<Integer>()
    {
      @Override
      public boolean test(Integer integer)
      {
        return integer != null;
      }
    };
    ref = Collections.makeFilteredList(new ArrayList<Integer>(), ruleRef);
  }

  /**
   * Test cleaning
   *
   * @throws Throwable If cleaning error
   */
  @After
  public void tearDown() throws Throwable
  {
    ref.clear();
    ref = null;
    ruleRef = null;
  }

  /**
   * Test of checkedCollection method, of class Collections.<br>
   * <ul>
   * <li><b>Description : </b>Gets a checked collection</li>
   * <li><b>Result : </b>Checked collection created</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_MakeFilteredCollection_NotNull()
  {
    Collection<Integer> collection = new ArrayList<>();
    assertNotNull(Collections.makeFilteredCollection(collection, this.ruleRef));
  }

  /**
   * Test of checkedList method, of class Collections.<br>
   * <ul>
   * <li><b>Description : </b>Gets a checked list</li>
   * <li><b>Result : </b>Checked list created</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_MakeFilteredList_NotNull()
  {
    List<Integer> list = new ArrayList<>();
    assertNotNull(Collections.makeFilteredList(list, this.ruleRef));
  }

  /**
   * Test of checkedMap method, of class Collections.<br>
   * <ul>
   * <li><b>Description : </b>Gets a checked map</li>
   * <li><b>Result : </b>Map created</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_MakeFilteredMap_NotNull()
  {
    Map<String, Integer> map = new HashMap<>();
    assertNotNull(Collections.makeFilteredMap(map, this.ruleRef));
  }

  /**
   * Test of clean method, of class Collections. <br>
   * <ul>
   * <li><b>Description : </b>Runs the clean method on a sub-collection</li>
   * <li><b>Result : </b>Clean runs without error</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Clean_Collection()
  {
    Collection<Integer> _col = new ArrayList<>();
    Collection<?> _sub_col = Collections.makeFilteredCollection(_col, this.ruleRef);
    _col.add(null);
    assertEquals(1, Collections.clean(_sub_col));
  }

  /**
   * Test of clean method, of class Collections with a classical collection that doesn't implements a Rule.
   */
  @Test
  public void test_Clean_ClassicalCollection()
  {
    Collection<Integer> _col = new ArrayList<>();
    _col.add(2);
    assertEquals(-1, Collections.clean(_col));
  }

  /**
   * Test of clean method, of class Collections. <br>
   * <ul>
   * <li><b>Description : </b>Runs the clean method on a sub map</li>
   * <li><b>Result : </b>Clean runs without error</li>
   * <li><b>Comments : </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Clean_Map()
  {
    Map<String, Integer> _map = new HashMap<>();
    Map<?, ?> _sub_map = Collections.makeFilteredMap(_map, this.ruleRef);
    _map.put("test", null);
    assertEquals(1, Collections.clean(_sub_map));
  }

  /**
   * Test of clean method, of class Collections on a classical Map that doesn't implements a Rule.
   */
  @Test
  public void test_Clean_ClassicalMap()
  {
    Map<Integer, String> _map = new HashMap<>();
    _map.put(Integer.SIZE, "Hi");
    assertEquals(-1, Collections.clean(_map));
  }

  /**
   * Test add method of the collection.
   * <ul>
   * <li><b>Description: </b>Adds a valid value</li>
   * <li><b>Results: </b>Value added</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Add_ValiData_Collection_True()
  {
    Assert.assertTrue(ref.add(4));
  }

  /**
   * Test add method of the collection
   * <ul>
   * <li><b>Description: </b>Adds an invalid value</li>
   * <li><b>Results: </b>false</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Add_InvalidData_Collection_False()
  {
    Assert.assertFalse(ref.add(null));
  }

  /**
   * Test addAll method of the collection
   * <ul>
   * <li><b>Description: </b>Adds a valid collection to the collection</li>
   * <li><b>Results: </b>All values added</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_AddAll_ValidCollection_True()
  {
    Collection<Integer> _col = new ArrayList<>();
    _col.add(2);
    _col.add(4);

    Predicate<Integer> not_3 = (p) ->
    {
      return p != 3;
    };

    Collection<Integer> _ref_col = Collections.makeFilteredCollection(new LinkedList<>(), not_3);
    Assert.assertTrue(_ref_col.addAll(_col));

    List<Integer> _ref_list = Collections.makeFilteredList(new LinkedList<>(), not_3);
    Assert.assertTrue(_ref_list.addAll(0, _col));
  }

  /**
   * Test addAll method of the collection
   * <ul>
   * <li><b>Description: </b>Adds an invalid collection to the sub-collection</li>
   * <li><b>Results: </b>false</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_AddAll_InvalidCollection_False()
  {
    Collection<Integer> _col = new ArrayList<>();
    _col.add(2);
    _col.add(4);
    _col.add(null);

    Assert.assertFalse(ref.addAll(_col));
  }

  /**
   * Adds 2 collections to a filtered list type collection.
   */
  @Test
  public void test_AddAll_ValidFromIndex_True()
  {
    List<Integer> _ref_list = Collections.makeFilteredList(new ArrayList<>(), this.ruleRef);
    Collection<Integer> _col = new ArrayList<>();
    _col.add(2);
    _col.add(4);

    _ref_list.addAll(_col);

    Collection<Integer> _col2 = new ArrayList<>();
    _col2.add(6);
    _col2.add(8);

    Assert.assertTrue(_ref_list.addAll(1, _col2));
  }

  /**
   * Adds 2 collections to a filtered list type collection. The first one contains only valid data, and the second one contains several data with one invalid.
   */
  @Test
  public void test_AddAll_InvalidFromIndex_False()
  {
    List<Integer> _ref_list = Collections.makeFilteredList(new ArrayList<>(), this.ruleRef);
    Collection<Integer> _col = new ArrayList<>();
    _col.add(2);
    _col.add(4);

    _ref_list.addAll(_col);

    Collection<Integer> _col2 = new ArrayList<>();
    _col2.add(6);
    _col2.add(8);
    _col2.add(null);

    Assert.assertFalse(_ref_list.addAll(1, _col2));
  }

  /**
   * Test clear method of the collection.
   * <ul>
   * <li><b>Description: </b>Adds a valid value and runs the clear method.</li>
   * <li><b>Results: </b>All values cleared</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Clear_Collection()
  {
    ref.add(6);
    ref.clear();

    Assert.assertEquals(0, ref.size());
  }

  /**
   * Test contains method of the collection.
   * <ul>
   * <li><b>Description: </b>Adds several valid values and controls if the collection contains a valid value</li>
   * <li><b>Results: </b>Collection contains the valid value</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Contains_ValidData()
  {
    ref.add(4);
    ref.add(6);
    ref.add(8);

    Assert.assertTrue(ref.contains(6));
  }

  /**
   * Test contains method of the collection.
   * <ul>
   * <li><b>Description: </b>Adds several valid values and controls if the collection contains an invalid value</li>
   * <li><b>Results: </b>Collection doesn't contains the invalid value</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Contains_InvalidData()
  {
    ref.add(4);
    ref.add(6);
    ref.add(8);

    Assert.assertFalse(ref.contains(3));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Build a checked collection from a source collection. Adds invalid values into the source
   * collection. Calls the
   * <code>toArray()</code> method and controls if the array contains invalids values.</li>
   * <li><b>Results: </b>The array doesn't contains invalids values</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Contains_FromToArray_InvalidValues_False()
  {
    Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
    Collection<Integer> _values = new ArrayList<>();
    _values.add(4);
    _values.add(6);
    _values.add(8);

    Collection<Integer> _cols = Collections.makeFilteredCollection(_values, (p) -> p % 2 == 0);

    _values.addAll(_invalid_values);

    List<Object> _result = Arrays.asList(_cols.toArray());
    for (Object _raw_value : _invalid_values)
    {
      Assert.assertFalse(_result.contains(_raw_value));
    }
  }

  /**
   * <ul>
   * <li><b>Description: </b>Build a sub-collection from a source collection. Adds invalid values into the source
   * collection. Calls the
   * <code>toArray(T[])</code> method and controls if the array contains invalids values.</li>
   * <li><b>Results: </b>The array doesn't contains invalids values</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Contains_FromToArrayT_InvalidValues_False()
  {
    Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
    Collection<Integer> _values = new ArrayList<>();
    _values.add(4);
    _values.add(6);
    _values.add(8);

    Collection<Integer> _cols = Collections.makeFilteredCollection(_values, (p) -> p % 2 == 0);

    _values.addAll(_invalid_values);

    List<Integer> _result = Arrays.asList(_cols.toArray(new Integer[_cols.size()]));
    for (Integer _raw_value : _invalid_values)
    {
      Assert.assertFalse(_result.contains(_raw_value));
    }
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a sub-collection from a source collection. Adds invalid values into the source
   * collection. Calls the
   * <code>iterator()</code> method and controls if the array contains invalids values.</li>
   * <li><b>Results: </b>The array doesn't contains invalids values</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Contains_WithInternalIterator_InvalidValues_False()
  {
    Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
    Collection<Integer> _values = new ArrayList<>();
    _values.add(4);
    _values.add(6);
    _values.add(8);

    Collection<Integer> _cols = Collections.makeFilteredCollection(_values, (p) -> p % 2 == 0);

    _values.addAll(_invalid_values);

    for (Integer _col : _cols)
    {
      Assert.assertFalse(_invalid_values.contains(_col));
    }
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a sub-collection from a source collection. Adds invalid values into the source
   * collection. Calls the
   * <code>retainAll(Collection&lt;?&gt; c)</code> method and controls if the collection contains invalids values.</li>
   * <li><b>Results: </b>false</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_RetainAll_InvalidValues_False()
  {
    Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
    Collection<Integer> _values = new ArrayList<>();
    _values.add(4);
    _values.add(6);
    _values.add(8);

    Collection<Integer> _cols = Collections.makeFilteredCollection(_values, (p) -> p % 2 == 0);

    _values.addAll(_invalid_values);

    Assert.assertFalse(_cols.retainAll(_invalid_values));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a sub-collection from a source collection. Adds invalid values into the source
   * collection. Calls the
   * <code>retainAll(Collection&lt;?&gt; c)</code> method and controls if the collection contains invalids values.</li>
   * <li><b>Results: </b>Only specific elements are retaining into the collection</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_RetainAll_ValidValues_True()
  {
    Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
    Collection<Integer> _values = new ArrayList<>();
    _values.add(4);
    _values.add(6);
    _values.add(8);

    Collection<Integer> _cols = Collections.makeFilteredCollection(_values, (p) -> p % 2 == 0);

    _values.addAll(_invalid_values);

    Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
  }

  @Test
  public void test_Collection_Equals_ToNull_False()
  {
    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
    Assert.assertFalse(_col.equals(null));
  }

  @Test
  public void test_List_Equals_ToNull_False()
  {
    Collection<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    Assert.assertFalse(_list.equals(null));
  }

  @Test
  public void test_Map_Equals_ToNull_False()
  {
    Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
    Assert.assertFalse(_map.equals(null));
  }

  @Test
  public void test_Collection_Equals_ToIntegerType_False()
  {
    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
    Assert.assertFalse(_col.equals(new Integer(5)));
  }

  @Test
  public void test_List_Equals_ToIntegerType_False()
  {
    Collection<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    Assert.assertFalse(_list.equals(new Integer(5)));
  }

  @Test
  public void test_Map_Equals_ToIntegerType_False()
  {
    Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
    Assert.assertFalse(_map.equals(new Integer(5)));
  }

  @Test
  public void test_List_Equals_ToCollectionType_False()
  {
    Collection<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
    Assert.assertFalse(_list.equals(_col));
  }

  @Test
  public void test_Collection_Equals_ToListType_False()
  {
    Collection<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);

    Assert.assertFalse(_col.equals(_list));
  }

  @Test
  public void test_Map_Equals_ToCollectionType_False()
  {
    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
    Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);

    Assert.assertFalse(_map.equals(_col));
  }

  @Test
  public void test_Map_Equals_ToListType_False()
  {
    Collection<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);

    Assert.assertFalse(_map.equals(_list));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Test the hashcode of the collection</li>
   * <li><b>Results: </b>Hashcode is not 0</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_HashCode_ValidHashCode()
  {
    Assert.assertTrue(0 != ref.hashCode());
  }

  /**
   * <ul>
   * <li><b>Description: </b>Gets a sub-collection with a
   * <code>null</code> collection parameter</li>
   * <li><b>Results: </b>Throws an exception</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test(expected = NullPointerException.class)
  public void test_MakeCollection_NullParamters_NullPointerException()
  {
    Collections.makeFilteredCollection(null, null);
  }

  /**
   * <ul>
   * <li><b>Description: </b>Gets a sub-collection with a
   * <code>null</code> filter</li>
   * <li><b>Results: </b>Throws an exception</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test(expected = NullPointerException.class)
  public void test_MakeCollection_NullFilter_NullPointerException()
  {
    Collections.makeFilteredCollection(new ArrayList<>(), null);
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a collection and adds forbidden values. Control if values are into the
   * sub-collection thanks to
   * <code>ContainsAll(...)</code> method.</li>
   * <li><b>Results: </b>All forbidden values are into the collection</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_ContainsAll_ValidEntries_True()
  {
    Collection<Integer> _src = new ArrayList<>();
    Collection<Integer> _ccol = Collections.makeFilteredCollection(_src, (p) -> p % 3 == 0);

    _src.add(1);
    _src.add(2);
    _src.add(3);
    _src.add(4);

    Assert.assertTrue(_ccol.containsAll(Arrays.asList(1, 3)));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Control the default value for the property.</li>
   * <li><b>Results: </b>Default value conforms</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_IsEmpty_True()
  {
    Assert.assertTrue(ref.isEmpty());

    Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<Integer>(), (p) -> p != 1);
    Assert.assertTrue(_col.isEmpty());
  }

  /**
   * <ul>
   * <li><b>Description: </b>Remove valid value from the collection.</li>
   * <li><b>Results: </b>Value removed</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Remove_ValidData_True()
  {
    ref.add(4);
    ref.add(6);
    ref.add(8);

    Assert.assertTrue(ref.remove(6));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Remove a value that doesn't exist into the collection</li>
   * <li><b>Results: </b>Value not removed</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_Remove_InvalidData_False()
  {
    ref.add(4);
    ref.add(6);
    ref.add(8);

    Assert.assertFalse(ref.remove(3));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a collection and adds forbidden values. Remove a collection of values that don't
   * exist in the collection.</li>
   * <li><b>Results: </b>Values don't remove from collection</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_RemoveAll_InvalidValues_False()
  {
    Collection<Integer> _src = new ArrayList<>();
    Collection<Integer> _sub_col = Collections.makeFilteredCollection(_src, (p) -> p % 2 == 0);

    _src.add(1);
    _src.add(2);
    _src.add(3);
    _src.add(4);

    Assert.assertFalse(_sub_col.removeAll(Arrays.asList(7, 8)));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a collection and adds forbidden values. Remove all values from the collection.</li>
   * <li><b>Results: </b>Values removed from collection</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_RemoveAll_ValidValues_True()
  {
    Collection<Integer> _src = new ArrayList<>();
    Collection<Integer> _ccol = Collections.makeFilteredCollection(_src, (p) -> p % 1 == 0);

    _src.add(1);
    _src.add(2);
    _src.add(3);
    _src.add(4);

    Assert.assertTrue(_ccol.removeAll(Arrays.asList(1, 2, 3, 4)));
  }

  /**
   * <ul>
   * <li><b>Description: </b>Builds a collection with multiples of 2 and another one with not multiples of 2. Compare
   * values between each collection.</li>
   * <li><b>Results: </b>Values are different.</li>
   * <li><b>Comments: </b>None.</li>
   * </ul>
   */
  @Test
  public void test_ContainsAll_InvalidEntries_False()
  {
    Collection<Integer> _multiple_of_2 = Collections.makeFilteredCollection(new ArrayList<>(), (p) -> p % 2 == 0);
    for (int i = 1; i < 20; ++i)
    {
      _multiple_of_2.add(i);
    }

    Predicate<Integer> multiple = (p) ->
    {
      return p % 2 == 0;
    };

    Collection<Integer> _not_multiple_of_2 = Collections.makeFilteredCollection(new ArrayList<>(), multiple.negate());
    for (int i = 1; i < 20; ++i)
    {
      _not_multiple_of_2.add(i);
    }

    Assert.assertFalse(_multiple_of_2.containsAll(_not_multiple_of_2));
  }

  /**
   * Test hashCode() method for collections.
   */
  @Test
  public void test_HashCode_List_Valid()
  {
    Assert.assertTrue(0 != (Collections.makeFilteredList(new LinkedList<Integer>(), (p) -> p != 1)).hashCode());
  }

  @Test
  public void test_HashCode_Map_Valid()
  {
    Assert.assertTrue(0 != (Collections.makeFilteredMap(new HashMap<Integer, Integer>(), (p) -> p != 1)).hashCode());
  }

  /**
   * Adds a valid element and access it with get method.
   */
  @Test
  public void test_List_GetPos_Valid()
  {
    List<Integer> _ref = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _ref.add(2);
    _ref.add(3);
    _ref.add(1);

    Assert.assertEquals(2, (int) _ref.get(0));
    Assert.assertEquals(3, (int) _ref.get(1));
  }

  /**
   * Test
   * <code>indexOf(Object o)</code> for a list type.
   */
  @Test
  public void test_IndexOf_ValidData()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);

    Assert.assertEquals(1, _list.indexOf(3));
  }

  /**
   * Test
   * <code>listIterator</code> for a list type.
   */
  @Test
  public void test_List_Iterator_Pos_NotNull()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(3);
    _list.add(8);

    ListIterator<Integer> _it = _list.listIterator(0);
    Assert.assertNotNull(_it);
  }

  @Test
  public void test_List_Iterator_NotNull()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(3);
    _list.add(8);

    ListIterator<Integer> _it = _list.listIterator();
    Assert.assertNotNull(_it);
  }

  /**
   * Test
   * <code>listIterator.add(E e)</code> to control if forbidden element is managing or not.
   */
  @Test
  public void test_ListIterator_Add_ValidValue()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 5);
    for (int i = 0; i < 10; ++i)
    {
      _list.add(i);
    }

    ListIterator<Integer> _it = _list.listIterator();
    _it.add(12);
    Assert.assertTrue(_list.contains(12));
  }

  @Test
  public void test_ListIterator_Add_InvalidValidValue()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 5);
    for (int i = 0; i < 10; ++i)
    {
      _list.add(i);
    }

    ListIterator<Integer> _it = _list.listIterator();

    _it.add(5);
    Assert.assertFalse(_list.contains(5));
  }

  @Test
  public void test_ListIterator_Set_ValidValue()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 5);
    for (int i = 0; i < 10; ++i)
    {
      _list.add(i);
    }

    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    _it.set(28);
    Assert.assertTrue(_list.contains(28));
  }

  @Test
  public void test_ListIterator_Set_InvalidValue()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 5);
    for (int i = 0; i < 10; ++i)
    {
      _list.add(i);
    }

    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    _it.set(5);
    Assert.assertFalse(_list.contains(5));
  }

  /**
   * Test remove method
   */
  @Test
  public void test_ListIterator_RemoveValue()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);

    ListIterator<Integer> _it = _list.listIterator();
    _it.next();
    _it.remove();

    Assert.assertFalse(_list.contains(2));
  }

  /**
   * Test next(), hasPrevious(), nextIndex()... from ListIterator.
   */
  @Test
  public void test_ListIterator_HasNext()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    Assert.assertTrue(_it.hasNext());
  }

  @Test
  public void test_ListIterator_Next()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    Assert.assertEquals(5, (int) _it.next());
  }

  @Test
  public void test_ListIterator_NextIndex()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    Assert.assertEquals(1, _it.nextIndex());
  }

  @Test
  public void test_ListIterator_Previous()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    Assert.assertEquals(5, (int) _it.previous());
  }

  @Test
  public void test_ListIterator_PreviousIndex()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    Assert.assertEquals(0, _it.previousIndex());
  }

  @Test
  public void test_ListIterator_HasPrevious()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(5);
    ListIterator<Integer> _it = _list.listIterator();

    _it.next();
    Assert.assertTrue(_it.hasPrevious());
  }

  /**
   * Create a sublist for 2 indexes
   */
  @Test
  public void test_SubList_ValidData()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);

    List<Integer> _list2 = _list.subList(0, 2);
    int[] _ref =
      {
        2, 3
      };
    for (int i : _ref)
    {
      Assert.assertTrue(_list2.contains(i));
    }
  }

  @Test
  public void test_SubList_InvalidData()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);

    List<Integer> _list2 = _list.subList(0, 2);
    int[] _ref =
      {
        4, 5, 8
      };
    for (int i : _ref)
    {
      Assert.assertFalse(_list2.contains(i));
    }
  }

  /**
   * List.Set(int, E) test
   */
  @Test
  public void test_Set_ValidData()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);


    Assert.assertNotNull(_list.set(1, 14));
  }

  @Test
  public void test_Set_InvalidData()
  {
    List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), (p) -> p != 1);
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);

    Assert.assertNull(_list.set(3, 1));
  }

  /**
   * Test AbstractList&lt;E&gt;.removeRange(int, int).
   */
  @Test
  public void test_RemoveRange_ValidRange()
  {
    LinkedList<Integer> _list = new LinkedList<>();
    _list.add(2);
    _list.add(3);
    _list.add(4);
    _list.add(5);
    _list.add(8);

    AListFake<Integer> _list2 = new AListFake<>((FilteredList<Integer>) Collections.makeFilteredList(_list, (p) -> p != 1));

    _list2.removeRange(0, 3);
    Assert.assertEquals(2, _list.size());
  }
}

/**
 * Test class to test removeRange implementation.
 *
 * @param <E> Type of date
 * @author Tioben Neenot
 */
class AListFake<E> extends AbstractList<E>
{

  /**
   * Internal FilteredList class
   */
  private final FilteredList<E> list;

  /**
   * Builds an instance of AList class.
   *
   * @param l Reference to a sub list
   */
  AListFake(FilteredList<E> l)
  {
    list = l;
  }

  @Override
  public void removeRange(int b, int e)
  {
    list.removeRange(b, e);
  }

  @Override
  public E get(int index)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int size()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}