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
    public void test_MakeFilteredCollection_ValidCollection_NotNull() {
        Collection<Integer> collection = new ArrayList<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredCollection(collection, rule));
    }

    @Test
    public void test_MakeFilteredList_ValidList_NotNull() {
        List<Integer> list = new ArrayList<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredList(list, rule));
    }

    @Test
    public void test_MakeFilteredMap_ValidMap_NotNull() {
        Map<String, Integer> map = new HashMap<>();
        Rule<Integer> rule = new Not<>(null);
        assertNotNull(Collections.makeFilteredMap(map, rule));
    }

    @Test
    public void test_Clean_OnFilteredCollection_Ok() {
        Collection<Integer> _col = new ArrayList<>();
        Collection<?> _sub_col = Collections.makeFilteredCollection(_col, new Not<>(null));
        _col.add(null);
        assertEquals(1, Collections.clean(_sub_col));
    }

    /**
     * Test of clean method, of class Collections with a classical collection that doesn't implements a Rule.
     */
    @Test
    public void test_Clean_OnStandardCollection_Nok() {
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
    public void test_Clean_OnFilteredMap_Ok() {
        Map<String, Integer> _map = new HashMap<>();
        Map<?, ?> _sub_map = Collections.makeFilteredMap(_map, new Not<>(null));
        _map.put("test", null);
        assertEquals(1, Collections.clean(_sub_map));
    }

    /**
     * Test of clean method, of class Collections on a classical Map that doesn't implements a Rule.
     */
    @Test
    public void test_Clean_OnClassicalMap_Nok() {
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
    public void test_Add_ValidValue_True() {
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
    public void test_Add_InvalidValue_False() {
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
    public void test_AddAll_FilteredCollectionFromValidCollection_True() {
        Collection<Integer> _col = new ArrayList<>();
        _col.add(2);
        _col.add(4);

        Collection<Integer> _ref_col = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(3));
        Assert.assertTrue(_ref_col.addAll(_col));
    }

    @Test
    public void test_AddAll_FilteredListFromValidCollection_True() {
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
    public void test_AddAll_FromInvalidCollection_False() {
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
    public void test_AddAll_Index_FromValidCollection_True() {
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
    public void test_AddAll_Index_FromInvalidCollection_False() {
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
    public void test_Size_FilteredCollection_ControlSizeValue_Ok() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_Size_FilteredCollection_FromExternalCollection_Ok() {
        Collection<Integer> _values = new Vector<>();
        _values.add(2);

        Collection<Integer> _col = Collections.makeFilteredCollection(_values, new Not<>(1));
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_Size_FilteredList_ControlSizeValue_Ok() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_Size_FilteredList_FromExternalList_ControlSizeValue() {
        List<Integer> _values = new ArrayList<>();
        _values.add(2);

        List<Integer> _col = Collections.makeFilteredList(_values, new Not<>(1));
        Assert.assertEquals(1, _col.size());
    }

    @Test
    public void test_Size_FilteredMap_ControlSizeValue_Ok() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        _map.put(2, 2);
        Assert.assertEquals(1, _map.size());
    }

    @Test
    public void test_Size_FilteredMap_FromExternalMap_ControlSizeValue_Ok() {
        Map<Integer, Integer> _values = new HashMap<>();
        _values.put(2, 2);

        Map<Integer, Integer> _map = Collections.makeFilteredMap(_values, new Not<>(1));
        Assert.assertEquals(1, _map.size());
    }

    @Test
    public void test_Clear_FilteredCollection_Ok() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        _col.clear();
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_Clear_FilteredList_Ok() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        _col.add(2);
        _col.clear();
        Assert.assertEquals(0, _col.size());
    }

    @Test
    public void test_Clear_FilteredMap_Ok() {
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
    public void test_Contains_FilteredList_True() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(ref.contains(6));
    }

    @Test
    public void test_Contains_FilteredList_FromExternalList_True() {
        List<Integer> _col = Collections.makeFilteredList((List<Integer>) this.ref, this.ruleRef);

        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(_col.contains(6));
    }


    @Test
    public void test_Contains_FilteredList_FromExternalList_False() {
        List<Integer> _list = new ArrayList<>();
        List<Integer> _col = Collections.makeFilteredList(_list, new Not<>(1));

        _list.add(4);
        _list.add(6);
        _list.add(8);
        _list.add(1);

        Assert.assertFalse(_col.contains(1));
    }

    @Test
    public void test_Contains_FilteredCollection_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertTrue(_col.contains(6));
    }

    @Test
    public void test_Contains_FilteredCollection_FromExternalCollection_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(this.ref, this.ruleRef);

        this.ref.add(4);
        this.ref.add(6);
        this.ref.add(8);

        Assert.assertTrue(_col.contains(6));
    }

    @Test
    public void test_Contains_FilteredCollection_FromExternalCollection_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(this.ref, new Not<>(1));

        this.ref.add(4);
        this.ref.add(6);
        this.ref.add(8);
        this.ref.add(1);

        Assert.assertFalse(_col.contains(1));
    }

    @Test
    public void test_ContainsValue_FilteredMap_True() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertTrue(_map.containsValue(4));
    }

    @Test
    public void test_ContainsKey_FilteredMap_True() {
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
    public void test_Contains_FilteredList_False() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertFalse(ref.contains(3));
    }


    @Test
    public void test_ContainsValue_FilteredMap_False() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _map.put(1, 1);
        _map.put(4, 4);

        Assert.assertFalse(_map.containsValue(5));
    }

    @Test
    public void test_ContainsKey_FilteredMap_False() {
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
    public void test_Contains_FilteredCollection_ToArray_RemoveInvalidValues() {
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
    public void test_Contains_FilteredList_ToArray_RemoveInvalidValues() {
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
    public void test_Contains_FilteredMap_Values_RemoveInvalidValues() {
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
    public void test_Contains_FilteredCollection_ToArrayT_RemoveInvalidValues() {
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
    public void test_Contains_FilteredList_ToArrayT_RemoveInvalidValues() {
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
    public void test_Contains_FilteredCollection_Iterator_RemoveInvalidValue() {
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
    public void test_RetainAll_FilteredCollection_WithInvalidValues_False() {
        Collection<Integer> _invalid_values = Arrays.asList(3, null, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        Assert.assertFalse(_cols.retainAll(_invalid_values));
    }

    @Test
    public void test_RetainAll_FilteredList_WithInvalidValues_False() {
        List<Integer> _invalid_values = Arrays.asList(3, null, 5);
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));

        Assert.assertFalse(_cols.retainAll(_invalid_values));
    }

    @Test
    public void test_RetainAll_FilteredCollection_WithValidValues_True() {
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
    }

    @Test
    public void test_RetainAll_FilteredList_WithValidValues_True() {
        List<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        List<Integer> _cols = Collections.makeFilteredList(_values, new Multiple<>(2));

        Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
    }

    @Test
    public void test_Equals_FilteredList_WithNull_False() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(null));
    }

    @Test
    public void test_Equals_FilteredCollection_WithNull_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals((null)));
    }

    @Test
    public void test_Equals_FilteredMap_WithNull_False() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
        Assert.assertFalse(_map.equals((null)));
    }

    @Test
    public void test_Equals_FilteredList_EqualsWithInteger_False() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_FilteredCollection_EqualsWithInteger_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_FilteredMap_EqualsWithInteger_False() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), ruleRef);
        Assert.assertFalse(_map.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_FilteredList_EqualsWithACollectionType_False() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_list.equals(_col));
    }

    @Test
    public void test_Equals_FilteredCollection_EqualsWithAListType_False() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<>(), ruleRef);
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), ruleRef);
        Assert.assertFalse(_col.equals(_list));
    }

    @Test
    public void test_Equals_FilteredMap_EqualsWithAListType_False() {
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
    public void test_FilteredList_HashCode_Valid() {
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
    public void test_FilteredCollection_NullCollection_NullPointerException() {
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
    public void test_FilteredCollection_NullRule_NullPointerException() {
        Collections.makeFilteredCollection(new ArrayList<>(), null);
    }

    @Test
    public void test_ContainsAll_FilteredCollection_ValidValues_True() {
        Collection<Integer> _src = new ArrayList<>();
        Collection<Integer> _ccol = Collections.makeFilteredCollection(_src, new Multiple<>(3));

        _src.add(1);
        _src.add(2);
        _src.add(3);
        _src.add(4);

        Assert.assertTrue(_ccol.containsAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_ContainsAll_FilteredList_ValidValues_True() {
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
    public void test_IsEmpty_FilteredCollection_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_IsEmpty_FilteredList_True() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_IsEmpty_FilteredMap_True() {
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
    public void test_Remove_FilteredList_ValidValue_True() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertTrue(ref.remove(6));
    }

    @Test
    public void test_Remove_FilteredCollection_ValidValue_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertTrue(_col.remove(6));
    }

    @Test
    public void test_Remove_FilteredMap_ValidValue_True() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _col.put(1, 4);
        _col.put(2, 6);
        _col.put(3, 8);

        Assert.assertEquals(new Integer(4), _col.remove(1));
    }

    @Test
    public void test_Remove_FilteredList_BadValue_False() {
        ref.add(4);
        ref.add(6);
        ref.add(8);

        Assert.assertFalse(ref.remove(3));
    }

    @Test
    public void test_Remove_FilteredCollection_BadValue_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), this.ruleRef);
        _col.add(4);
        _col.add(6);
        _col.add(8);

        Assert.assertFalse(_col.remove(3));
    }

    @Test
    public void test_Remove_FilteredMap_BadValue_Null() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<>(), this.ruleRef);
        _col.put(1, 4);
        _col.put(2, 6);
        _col.put(3, 8);

        Assert.assertNull(_col.remove(4));
    }


    @Test
    public void test_RemoveAll_FilteredCollection_BadList_False() {
        Collection<Integer> _ccol = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertFalse(_ccol.removeAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_RemoveAll_FilteredCollection_ValidList_True() {
        Collection<Integer> _ccol = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertTrue(_ccol.removeAll(Arrays.asList(2, 4)));
    }

    @Test
    public void test_RemoveAll_FilteredList_InvalidList_False() {
        List<Integer> _ccol = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertFalse(_ccol.removeAll(Arrays.asList(1, 3)));
    }

    @Test
    public void test_RemoveAll_FilteredList_ValidList_True() {
        List<Integer> _ccol = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _ccol.add(1);
        _ccol.add(2);
        _ccol.add(3);
        _ccol.add(4);

        Assert.assertTrue(_ccol.removeAll(Arrays.asList(2, 4)));
    }


    @Test
    public void test_RemoveAll_FilteredCollection_OutOfBoundValues_False() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(2));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertFalse(_col.removeAll(Arrays.asList(7, 8)));
    }

    @Test
    public void test_RemoveAll_FilteredList_OutOfBoundValues_False() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(2));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertFalse(_col.removeAll(Arrays.asList(7, 8)));
    }

    @Test
    public void test_RemoveAll_FilteredCollection_AllValues_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(1));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertTrue(_col.removeAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void test_RemoveAll_FilteredList_AllValues_True() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Multiple<>(1));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertTrue(_col.removeAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void test_ContainsAll_FilteredCollection_TwoDifferentsCollection_False() {
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
    public void test_ContainsAll_FilteredList_TwoDifferentsCollection_False() {
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
    public void test_HashCode_FilteredCollection_Valid() {
        Assert.assertTrue(0 != (Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1))).hashCode());
    }

    @Test
    public void test_HashCode_FilteredList_Valid() {
        Assert.assertTrue(0 != (Collections.makeFilteredList(new LinkedList<>(), new Not<>(1))).hashCode());
    }

    @Test
    public void test_HashCode_FilteredMap_Valid() {
        Assert.assertTrue(0 != (Collections.makeFilteredMap(new HashMap<>(), new Not<>(1))).hashCode());
    }


    @Test
    public void test_Get_FilteredList_ValidValue_Valid() {
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
    public void test_Equals_FilteredList_ToItSelf_True() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_Equals_FilteredCollection_ToItSelf_True() {
        Collection<Integer> _list = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_Equals_FilteredMap_ToItSelf_True() {
        Map<Integer, Integer> _list = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    /**
     * Compare a collection to another one.
     */
    @Test
    public void test_Equals_FilteredList_DifferentList_False() {
        List<Integer> _list1 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        List<Integer> _list2 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_Equals_FilteredCollection_DifferentList_False() {
        Collection<Integer> _list1 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Collection<Integer> _list2 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_Equals_FilteredMap_DifferentMap_False() {
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
    public void test_IndexOf_FilteredList_ValidIndex_Ok() {
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
    public void test_LastIndexOf_FilteredList_ValidIndex_Ok() {
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
    public void test_ListIterator_FilteredList_NotNull() {
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
    public void test_ListIterator_Int_FilteredList_NotNull() {
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
    public void test_Contains_FilteredList_ListIterator_AddValidValue_True() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));

        ListIterator<Integer> _it = _list.listIterator();
        _it.add(12);
        Assert.assertTrue(_list.contains(12));
    }

    @Test
    public void test_Contains_FilteredList_ListIterator_AddingInvalidValue_False() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));
        ListIterator<Integer> _it = _list.listIterator();

        _it.add(5);
        Assert.assertFalse(_list.contains(5));
    }

    @Test
    public void test_Contains_FilteredList_ListIterator_SetValidValue_True() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(5));
        _list.add(2);
        _list.add(3);

        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        _it.set(28);
        Assert.assertTrue(_list.contains(28));
    }

    @Test
    public void test_Contains_FilteredList_ListIterator_SetInvalidValue_False() {
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
    public void test_Contains_FilteredList_ListIterator_Remove_False() {
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
    public void test_HasNext_FilteredList_ListIterator_True() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_Next_FilteredList_ListIterator_Ok() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertEquals(5, (int) _it.next());
    }

    @Test
    public void test_NextIndex_FilteredList_ListIterator_Ok() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_Previous_FilteredList_ListIterator_Ok() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        _it.next();
        Assert.assertEquals(5, (int) _it.previous());
    }

    @Test
    public void test_PreviousIndex_FilteredList_ListIterator_Ok() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();

        Assert.assertEquals(-1, _it.previousIndex());
    }

    @Test
    public void test_HasPrevious_FilteredList_ListIterator_False() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(5);
        ListIterator<Integer> _it = _list.listIterator();
        Assert.assertFalse(_it.hasPrevious());
    }

    /**
     * Create a sub-list for 2 indexes
     */
    @Test
    public void test_Contains_FilteredList_SubList_Valid_True() {
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
    public void test_Contains_FilteredList_SubList_Invalid_False() {
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
    public void test_Contains_FilteredList_Set_Index_ValidValue_True() {
        List<Integer> _list = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        _list.add(2);
        _list.add(3);

        _list.set(1, 14);
        Assert.assertTrue(_list.contains(14));
    }

    @Test
    public void test_Contains_FilteredList_Set_Index_InvalidValue_False() {
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
    public void test_Size_FilteredList_RemoveRange_ValidRange_Ok() {
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