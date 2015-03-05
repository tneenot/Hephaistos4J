package org.hlib4j.collection;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*  
*  Copyright (C) 2015 Tioben Neenot
*  
*  This program is free software; you can redistribute it and/or modify it under
*  the terms of the GNU General Public License as published by the Free Software
*  Foundation; either version 2 of the License, or (at your option) any later
*  version.
*  
*  This program is distributed in the hope that it will be useful, but WITHOUT
*  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
*  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
*  details.
*  
*  You should have received a copy of the GNU General Public License along with
*  this program; if not, write to the Free Software Foundation, Inc., 51
*  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*  
*/

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the {@link java.util.Collections} class.
 *
 * @author Tioben Neenot
 */
public class CollectionsTest {

    /**
     * Collection reference for tests
     */
    private Collection<Integer> ref = null;

    /**
     * Rule reference for tests
     */
    private Rule<Integer> ruleRef = null;

    /**
     * Test initialisation
     */
    @Before
    public void setUp() {
        ruleRef = new Not<>(null);
        ref = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
    }

    /**
     * Test cleaning
     *
     * @throws Throwable If cleaning error
     */
    @After
    public void tearDown() throws Throwable {
        ref.clear();
        ref = null;
        ruleRef = null;
    }

    @Test
    public void test_MakeFilteredCollection() {
        Collection<Integer> collection = new ArrayList<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredCollection(collection, rule));
    }

    @Test
    public void test_MakeFilteredList() {
        List<Integer> list = new ArrayList<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredList(list, rule));
    }

    @Test
    public void test_MakeFilteredMap() {
        Map<String, Integer> map = new HashMap<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredMap(map, rule));
    }

    @Test
    public void test_Clean_FilteredSubCollection() {
        Collection<Integer> _col = new ArrayList<>();
        Collection<?> _sub_col = Collections.makeFilteredCollection(_col, new Not<>(null));
        _col.add(null);
        assertEquals(1, Collections.clean(_sub_col));
    }

    /**
     * Test of clean method, of class Collections with a classical collection that doesn't implements a Rule.
     */
    @Test
    public void test_Clean_StandardCollection() {
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
    public void test_Clean_FilteredSubMap() {
        Map<String, Integer> _map = new HashMap<>();
        Map<?, ?> _sub_map = Collections.makeFilteredMap(_map, new Not<>(null));
        _map.put("test", null);
        assertEquals(1, Collections.clean(_sub_map));
    }

    /**
     * Test of clean method, of class Collections on a classical Map that doesn't implements a Rule.
     */
    @Test
    public void test_Clean_ClassicalMap() {
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
    public void test_Add_ValidValue() {
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
    public void test_Add_InvalidValue() {
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
    public void test_AddAll_FilteredCollectionFromValidCollection() {
        Collection<Integer> _col = new ArrayList<>();
        _col.add(2);
        _col.add(4);

        Collection<Integer> _ref_col = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(3));
        Assert.assertTrue(_ref_col.addAll(_col));
    }

    @Test
    public void test_AddAll_FilteredListFromValidCollection() {
        Collection<Integer> _col = new ArrayList<>();
        _col.add(2);
        _col.add(4);

        List<Integer> _ref_list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(3));
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
    public void test_AddAll_FromInvalidCollection() {
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
    public void test_AddAll_Index_FromValidCollection() {
        List<Integer> _ref_list = Collections.makeFilteredList(new ArrayList<>(), this.ruleRef);
        _ref_list.add(2);
        _ref_list.add(4);

        Collection<Integer> _col = new ArrayList<>();
        _col.add(6);
        _col.add(8);

        Assert.assertTrue(_ref_list.addAll(1, _col));
    }

    /**
     * Adds 2 collections to a filtered list type collection. The first one contains only valid data, and the second one contains several data with one invalid.
     */
    @Test
    public void test_AddAll_Index_FromInvalidCollection() {
        List<Integer> _ref_list = Collections.makeFilteredList(new ArrayList<>(), this.ruleRef);
        _ref_list.add(2);
        _ref_list.add(4);

        Collection<Integer> _col = new ArrayList<>();
        _col.add(6);
        _col.add(8);
        _col.add(null);

        Assert.assertFalse(_ref_list.addAll(1, _col));
    }

    @Test
    public void test_FilteredCollection_Add_ControlSizeValue() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_FilteredCollection_AddFromExternalCollection_ControlSizeValue() {
        Collection<Integer> _values = new Vector<>();
        _values.add(1);

        Collection<Integer> _col = Collections.makeFilteredCollection(_values, new Not<>(1));
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_FilteredList_Add_ControlSizeValue() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_FilteredList_AddFromExternalList_ControlSizeValue() {
        List<Integer> _values = new ArrayList<>();
        _values.add(1);

        List<Integer> _col = Collections.makeFilteredList(_values, new Not<>(1));
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_FilteredMap_Put_ControlSizeValue() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        _map.put(2, 2);
        Assert.assertEquals(1, _map.size());
    }

    @Test
    public void test_FilteredMap_PutFromExternalMap_ControlSizeValue() {
        Map<Integer, Integer> _values = new HashMap<>();
        _values.put(1, 1);

        Map<Integer, Integer> _map = Collections.makeFilteredMap(_values, new Not<>(1));
        Assert.assertEquals(0, _map.size());
    }

    @Test
    public void test_FilteredCollection_Clear() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        _col.clear();
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_FilteredList_Clear() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        _col.clear();
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_FilteredMap_Clear() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        _map.put(2, 2);
        _map.clear();
        Assert.assertEquals(0, _map.size());
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
    public void test_FilteredList_Contains_True() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(ref.contains(6));
    }

    @Test
    public void test_FilteredList_Contains_FromExternalList_True() {
        List<Integer> _col = Collections.makeFilteredList((List<Integer>) this.ref, this.ruleRef);

        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(_col.contains(6));
    }


    @Test
    public void test_FilteredList_Contains_FromExternalList_False() {
        List<Integer> _list = new ArrayList<>();
        List<Integer> _col = Collections.makeFilteredList(_list, new Not<>(1));

        _list.add(4);
        _list.add(6);
        _list.add(8);
        _list.add(1);

        Assert.assertFalse(_col.contains(1));
    }

    @Test
    public void test_FilteredCollection_Contains_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertTrue(_col.contains(6));
    }

    @Test
    public void test_FilteredCollection_Contains_FromExternalCollection_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(this.ref, this.ruleRef);

        this.ref.add(4);
        this.ref.add(6);
        this.ref.add(8);

        Assert.assertTrue(_col.contains(6));
    }

    @Test
    public void test_FilteredCollection_Contains_FromExternalCollection_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(this.ref, new Not<>(1));

        this.ref.add(4);
        this.ref.add(6);
        this.ref.add(8);
        this.ref.add(1);

        Assert.assertFalse(_col.contains(1));
    }

    @Test
    public void test_FilteredMap_ContainsValue_True() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertTrue(_map.containsValue(4));
    }

    @Test
    public void test_FilteredMap_ContainsKey_True() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertTrue(_map.containsKey(4));
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
    public void test_FilteredList_Contains_False() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertFalse(ref.contains(3));
    }


    @Test
    public void test_FilteredMap_ContainsValue_False() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertFalse(_map.containsValue(5));
    }

    @Test
    public void test_FilteredMap_ContainsKey_False() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertFalse(_map.containsKey(5));
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
    public void test_FilteredCollection_ToArray_RemoveInvalidValues() {
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        _values.addAll(_invalid_values);

        List<Object> _result = Arrays.asList(_cols.toArray());
        for (Object _raw_value : _invalid_values) {
            Assert.assertFalse(_result.contains(_raw_value));
        }
    }

    @Test
    public void test_FilteredList_ToArray_RemoveInvalidValues() {
        List<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));

        _values.addAll(_invalid_values);

        List<Object> _result = Arrays.asList(_cols.toArray());
        for (Object _raw_value : _invalid_values) {
            Assert.assertFalse(_result.contains(_raw_value));
        }
    }

    @Test
    public void test_FilteredMap_Values_RemoveInvalidValues() {
        Map<Integer, Integer> _invalid_values = new HashMap<>();
        _invalid_values.put(3, 3);

        Map<Integer, Integer> _values = new HashMap<>();
        _values.put(4, 4);
        _values.put(6, 6);
        _values.put(8, 8);

        Map<Integer, Integer> _cols = Collections.makeFilteredMap(_values, new Multiple<>(2));

        _values.putAll(_invalid_values);

        Collection<Integer> _result = _cols.values();
        for (Integer _raw_value : _invalid_values.values()) {
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
    public void test_FilteredCollection_ToArrayT_RemoveInvalidValues() {
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        List<Integer> _result = Arrays.asList(_cols.toArray(new Integer[_cols.size()]));
        for (Integer _raw_value : _invalid_values) {
            Assert.assertFalse(_result.contains(_raw_value));
        }
    }

    @Test
    public void test_FilteredList_ToArrayT_RemoveInvalidValues() {
        List<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));

        List<Integer> _result = Arrays.asList(_cols.toArray(new Integer[_cols.size()]));
        for (Integer _raw_value : _invalid_values) {
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
    public void test_FilteredCollection_Iterator_RemoveInvalidValue() {
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        for (Integer _col : _cols) {
            Assert.assertFalse(_invalid_values.contains(_col));
        }
    }

    @Test
    public void test_FilteredCollection_RetainAll() {
        Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        Assert.assertFalse(_cols.retainAll(_invalid_values));
    }

    @Test
    public void test_FilteredList_RetainAll() {
        List<Integer> _invalid_values = Arrays.asList(3, null, 5);
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));

        Assert.assertFalse(_cols.retainAll(_invalid_values));
    }

    @Test
    public void test_FilteredCollection_RetainAll_Valids() {
        Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));
        _values.addAll(_invalid_values);

        Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
    }

    @Test
    public void test_FilteredList_RetainAll_Valids() {
        Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));
        _values.addAll(_invalid_values);

        Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
    }

    @Test
    public void test_FilteredList_Equals_WithNull() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(null));
    }

    @Test
    public void test_FilteredCollection_Equals_WithNull() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals((null)));
    }

    @Test
    public void test_FilteredMap_Equals_WithNull() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
        Assert.assertFalse(_map.equals((null)));
    }

    @Test
    public void test_FilteredList_Equals_NotEqualsWithInteger() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(new Integer(5)));
    }

    @Test
    public void test_FilteredCollection_Equals_NotEqualsWithInteger() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals(new Integer(5)));
    }

    @Test
    public void test_FilteredMap_Equals_NotEqualsWithInteger() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
        Assert.assertFalse(_map.equals(new Integer(5)));
    }

    @Test
    public void test_FilteredList_NotEqualsWithACollectionType() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(_col));
    }

    @Test
    public void test_FiltredCollection_NotEqualsWithAListType() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals(_list));
    }

    @Test
    public void test_FiltredMap_NotEqualsWithAListType() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
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
    public void test_HashCode() {
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
    public void test_Exception_IfCollectionIsNull() {
        Collections.makeFilteredCollection(null, new Multiple<>(0.0f));
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
    public void test_Exception_IfRuleIsNull() {
        Collections.makeFilteredCollection(new ArrayList<>(), null);
    }

    @Test
    public void test_FilteredCollection_ContainsAll_Other() {
        Collection<Integer> _src = new ArrayList<>();
        Collection<Integer> _ccol = Collections.makeFilteredCollection(_src, new Multiple<>(3));

        _src.add(1);
        _src.add(2);
        _src.add(3);
        _src.add(4);

        Assert.assertTrue(_ccol.containsAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_FilteredList_ContainsAll_Other() {
        List<Integer> _src = new ArrayList<>();
        List<Integer> _ccol = Collections.makeFilteredList(_src, new Multiple<>(3));

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
    public void test_FilteredCollection_IsEmpty() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_FilteredList_IsEmpty() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_FilteredMap_IsEmpty() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
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
    public void test_FilteredList_Remove() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(ref.remove(6));
    }

    @Test
    public void test_FilteredCollection_Remove() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertTrue(_col.remove(6));
    }

    @Test
    public void test_FilteredMap_Remove() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _col.put(1, 4);
        _col.put(2, 6);
        _col.put(3, 8);

        Assert.assertEquals(new Integer(4), _col.remove(1));
    }

    @Test
    public void test_FilteredList_Remove_FalseSinceBadValue() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertFalse(ref.remove(3));
    }

    @Test
    public void test_FilteredCollection_Remove_FalseSinceBadValue() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertFalse(_col.remove(3));
    }

    @Test
    public void test_FilteredMap_Remove_NullSinceBadKey() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _col.put(1, 4);
        _col.put(2, 6);
        _col.put(3, 8);

        Assert.assertNull(_col.remove(4));
    }


    @Test
    public void test_FilteredCollection_RemoveAll_FalseSinceBadValues() {
        Collection<Integer> _ccol = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertFalse(_ccol.removeAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_FilteredCollection_RemoveAll_True() {
        Collection<Integer> _ccol = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertTrue(_ccol.removeAll(Arrays.asList(2, 4)));
    }

    @Test
    public void test_FilteredList_RemoveAll_FalseSinceBadValues() {
        List<Integer> _ccol = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertFalse(_ccol.removeAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_FilteredList_RemoveAll_True() {
        List<Integer> _ccol = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertTrue(_ccol.removeAll(Arrays.asList(2, 4)));
    }


    @Test
    public void test_FilteredCollection_RemoveAll_OutOfBoundValues() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertFalse(_col.removeAll(Arrays.asList(7, 8)));
    }

    @Test
    public void test_FilteredList_RemoveAll_OutOfBoundValues() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertFalse(_col.removeAll(Arrays.asList(7, 8)));
    }

    @Test
    public void test_FilteredCollection_RemoveAll_AllValues() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(1));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertTrue(_col.removeAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void test_FilteredList_RemoveAll_AllValues() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(1));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertTrue(_col.removeAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void test_FilteredCollection_ContainsAll_TwoDifferentsCollection() {
        Collection<Integer> _multiple_of_2 = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));
        for (int i = 1; i < 20; ++i) {
            _multiple_of_2.add(i);
        }

        Collection<Integer> _not_multiple_of_2 = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(new Multiple<>(2)));
        for (int i = 1; i < 20; ++i) {
            _not_multiple_of_2.add(i);
        }

        Assert.assertFalse(_multiple_of_2.containsAll(_not_multiple_of_2));
    }

    @Test
    public void test_FilteredList_ContainsAll_TwoDifferentsCollection() {
        List<Integer> _multiple_of_2 = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));
        for (int i = 1; i < 20; ++i) {
            _multiple_of_2.add(i);
        }

        List<Integer> _not_multiple_of_2 = Collections.makeFilteredList(new ArrayList<>(), new Not<>(new Multiple<>(2)));
        for (int i = 1; i < 20; ++i) {
            _not_multiple_of_2.add(i);
        }

        Assert.assertFalse(_multiple_of_2.containsAll(_not_multiple_of_2));
    }

    @Test
    public void test_FilteredCollection_HashCode_ValueNotZero() {
        Assert.assertTrue(0 != (Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1))).hashCode());
    }

    @Test
    public void test_FilteredList_HashCode_ValueNotZero() {
        Assert.assertTrue(0 != (Collections.makeFilteredList(new LinkedList<>(), new Not<>(1))).hashCode());
    }

    @Test
    public void test_FilteredMap_HashCode_ValueNotZero() {
        Assert.assertTrue(0 != (Collections.makeFilteredMap(new HashMap<>(), new Not<>(1))).hashCode());
    }


    @Test
    public void test_FilteredList_GetMethod() {
        List<Integer> _ref = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _ref.add(2);
        _ref.add(3);
        _ref.add(1);

        Assert.assertEquals(3, (int) _ref.get(1));
    }

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_FilteredList_EqualsMinimumTrue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_FilteredCollection_EqualsMinimumTrue() {
        Collection<Integer> _list = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_FilteredMap_EqualsMinimumTrue() {
        Map<Integer, Integer> _list = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to another one.
     */
    @Test
    public void test_FilteredList_EqualsFalse_NotSameList() {
        List<Integer> _list1 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        List<Integer> _list2 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_FilteredCollection_EqualsFalse_NotSameList() {
        Collection<Integer> _list1 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Collection<Integer> _list2 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_FilteredMap_EqualsFalse_NotSameList() {
        Map<Integer, Integer> _list1 = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        Map<Integer, Integer> _list2 = Collections.makeFilteredMap(new HashMap<>(), new Not<>(2));
        _list2.put(5, 5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    /**
     * Test
     * <code>indexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_FilteredList_IndexOf() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);
        _list.add(4);

        Assert.assertEquals(1, _list.indexOf(3));
    }

    /**
     * Test
     * <code>lastIndexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_FilteredList_LastIndexOf() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);
        _list.add(4);
        _list.add(3);
        _list.add(8);

        Assert.assertEquals(3, _list.lastIndexOf(3));
    }

    /**
     * Test
     * <code>listIterator</code> for a list type.
     */
    @Test
    public void test_FileredList_ListIterator_NotNull() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);
        _list.add(4);
        _list.add(3);
        _list.add(8);

        ListIterator<Integer> _it = _list.listIterator(0);
        Assert.assertNotNull(_it);
    }

    @Test
    public void test_FileredList_ListIterator_Int_NotNull() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);

        ListIterator<Integer> _it = _list.listIterator(0);
        Assert.assertNotNull(_it);
    }


    /**
     * Test
     * <code>listIterator.add(E e)</code> to control if forbidden element is managing or not.
     */
    @Test
    public void test_FilteredList_Iterator_AddingValidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));

        ListIterator<Integer> _it = _list.listIterator();
        _it.add(12);
        Assert.assertTrue(_list.contains(12));
    }

    @Test
    public void test_FilteredList_Iterator_AddingInvalidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));
        ListIterator<Integer> _it = _list.listIterator();

        _it.add(5);
        Assert.assertFalse(_list.contains(5));
    }

    @Test
    public void test_FilteredList_Iterator_SetValidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));
        _list.add(2);
        _list.add(3);

        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        _it.set(28);
        Assert.assertTrue(_list.contains(28));
    }

    @Test
    public void test_FilteredList_Iterator_SetInvalidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));
        _list.add(1);
        _list.add(2);
        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        _it.set(5);
        Assert.assertFalse(_list.contains(5));
    }

    /**
     * Test remove method
     */
    @Test
    public void test_FilteredList_ListIterator_Remove() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
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
    public void test_FilteredList_ListIterator_HasNext() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_FilteredList_ListIterator_Next() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertEquals(5, (int) _it.next());
    }

    @Test
    public void test_FilteredList_ListIterator_NextIndex() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_FilteredList_ListIterator_Previous() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        Assert.assertEquals(5, (int) _it.previous());
    }

    @Test
    public void test_FilteredList_ListIterator_PreviousIndex() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertEquals(-1, _it.previousIndex());
    }

    @Test
    public void test_FilteredList_ListIterator_HasPrevious() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();
        Assert.assertFalse(_it.hasPrevious());
    }

    /**
     * Create a sub-list for 2 indexes
     */
    @Test
    public void test_FilteredList_SubList_Valid() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
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
        for (int i : _ref) {
            Assert.assertTrue(_list2.contains(i));
        }
    }

    @Test
    public void test_FilteredList_SubList_Invalid() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);
        _list.add(4);
        _list.add(5);
        _list.add(8);

        List<Integer> _list2 = _list.subList(0, 2);
        int[] _ref2 =
                {
                        4, 5, 8
                };
        for (int i : _ref2) {
            Assert.assertFalse(_list2.contains(i));
        }
    }

    /**
     * List.Set(int, E) test
     */
    @Test
    public void test_FilteredList_SetToIndex_ValidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);

        _list.set(1, 14);
        Assert.assertTrue(_list.contains(14));
    }

    @Test
    public void test_FilteredList_SetToIndex_InvalidValue() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);

        _list.set(2, 1);
        Assert.assertFalse(_list.contains(1));
    }

    /**
     * Test AbstractList&lt;E&gt;.removeRange(int, int).
     */
    @Test
    public void test_FilteredList_RemoveRange() {
        LinkedList<Integer> _list = new LinkedList<>();
        _list.add(2);
        _list.add(3);
        _list.add(4);
        _list.add(5);
        _list.add(8);

        AListFake<Integer> _list2 = new AListFake<>((FilteredList<Integer>) Collections.makeFilteredList(_list, new Not<>(1)));

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
class AListFake<E> extends AbstractList<E> {

    /**
     * Internal FilteredList class
     */
    private final FilteredList<E> list;

    /**
     * Builds an instance of AList class.
     *
     * @param l Reference to a sub list
     */
    AListFake(FilteredList<E> l) {
        list = l;
    }

    @Override
    public void removeRange(int b, int e) {
        list.removeRange(b, e);
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}