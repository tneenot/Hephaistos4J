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
    public void test_Clear_FilteredMap_Ok() {
        Map<Integer, Integer> _map = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        _map.put(2, 2);
        _map.clear();
        Assert.assertEquals(0, _map.size());
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
    public void test_RetainAll_FilteredCollection_WithValidValues_True() {
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<>(2));

        Assert.assertTrue(_cols.retainAll(Arrays.asList(4, 8, 16)));
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
    public void test_FilteredCollection_HashCode_Valid() {
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
    public void test_RemoveAll_FilteredCollection_AllValues_True() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Multiple<>(1));

        _col.add(1);
        _col.add(2);
        _col.add(3);
        _col.add(4);

        Assert.assertTrue(_col.removeAll(Arrays.asList(1, 2, 3, 4)));
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

    /**
     * Compare a collection to itself. Basic test to control if equals method is implemented with a minimum of valid rules.
     */
    @Test
    public void test_Equals_FilteredCollection_ToItSelf_True() {
        Collection<Integer> _list = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Assert.assertTrue(_list.equals(_list));
    }

    @Test
    public void test_HashCode_FilteredMap_Valid() {
        Assert.assertTrue(0 != (Collections.makeFilteredMap(new HashMap<>(), new Not<>(1))).hashCode());
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
    public void test_Equals_FilteredList_NotEqualsWithDifferentListDefinition() {
        List<Integer> _list1 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(1));
        List<Integer> _list2 = Collections.makeFilteredList(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_Equals_FilteredCollection_NotEqualsWithDifferentListDefinition() {
        Collection<Integer> _list1 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(1));
        Collection<Integer> _list2 = Collections.makeFilteredCollection(new LinkedList<>(), new Not<>(2));
        _list2.add(5);

        Assert.assertFalse(_list1.equals(_list2));
    }

    @Test
    public void test_Equals_FilteredMap_NotEqualsWithDifferentMapDefinition() {
        Map<Integer, Integer> _list1 = Collections.makeFilteredMap(new HashMap<>(), new Not<>(1));
        Map<Integer, Integer> _list2 = Collections.makeFilteredMap(new HashMap<>(), new Not<>(2));
        _list2.put(5, 5);

        Assert.assertFalse(_list1.equals(_list2));
    }
}
