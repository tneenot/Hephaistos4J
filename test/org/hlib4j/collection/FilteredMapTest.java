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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests on a map managed by {@link org.hlib4j.collection.Collections#makeFilteredMap(java.util.Map, Rule)} with data.
 */
public class FilteredMapTest {

    private Map<Integer, Integer> refFilteredMap;

    private Map<Integer, Integer> refSourceMap;

    @Before
    public void setUp() {
        this.refSourceMap = new HashMap<>();

        this.refFilteredMap = Collections.makeFilteredMap(this.refSourceMap, new Not<Integer>(1));
        this.refFilteredMap.put(2, 4);
        this.refFilteredMap.put(4, 6);
        this.refFilteredMap.put(6, 8);

        this.refSourceMap.put(10, 1);
    }

    @After
    public void tearDown() {
        this.refFilteredMap.clear();
        this.refSourceMap.clear();

        this.refSourceMap = null;
        this.refFilteredMap = null;


    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertEquals(new Integer(4), this.refFilteredMap.remove(2));
    }

    @Test
    public void test_HashCode_FilteredMap_Valid() {
        Assert.assertTrue(0 != this.refFilteredMap.hashCode());
    }

    @Test
    public void test_Equals_ToItSelf_True() {
        Assert.assertTrue(this.refFilteredMap.equals(this.refFilteredMap));
    }

    @Test
    public void test_Equals_NotEqualsWithDifferentMapDefinition() {
        Map<Integer, Integer> _list2 = Collections.makeFilteredMap(new HashMap<Integer, Integer>(), new Not<Integer>(2));

        Assert.assertFalse(this.refFilteredMap.equals(_list2));
    }

    @Test
    public void test_Equals_NotEqualsWithAListType() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<Integer>(), new Not<Integer>(1));

        Assert.assertFalse(this.refFilteredMap.equals(_list));
    }

    @Test
    public void test_Equals_NotEqualsWithIntegerType() {
        Assert.assertFalse(this.refFilteredMap.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_NotEqualWithNullValue() {
        Assert.assertFalse(this.refFilteredMap.equals((null)));
    }

    @Test
    public void test_IsEmpty_WithAnEmptyMap() {
        Map<Integer, Integer> _col = Collections.makeFilteredMap(new HashMap<Integer, Integer>(), new Not<Integer>(1));

        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_IsEmpty_WithANonEmptyMap() {
        Assert.assertFalse(this.refFilteredMap.isEmpty());
    }

    @Test
    public void test_PutAll_RemoveInvalidValues() {
        Map<Integer, Integer> _invalid_value = new HashMap<>();
        _invalid_value.put(10, 1);

        this.refFilteredMap.putAll(_invalid_value);

        Collection<Integer> _result = this.refFilteredMap.values();
        for (Integer _raw_value : _invalid_value.values()) {
            Assert.assertFalse(_result.contains(_raw_value));
        }
    }

    @Test
    public void test_Put_ValidValue() {
        Assert.assertNotNull(this.refFilteredMap.put(6, 8));
    }

    @Test
    public void test_Put_InvalidValue() {
        Assert.assertNull(this.refFilteredMap.put(4, 1));
    }

    @Test
    public void test_Remove_NonExistentValue() {
        Assert.assertNull(this.refFilteredMap.remove(12));
    }

    @Test
    public void test_Remove_ExistentValue() {
        Assert.assertNotNull(this.refFilteredMap.remove(6));
    }

    @Test
    public void test_ContainsKey_NonExistentKey() {
        Assert.assertFalse(this.refFilteredMap.containsKey(15));
    }

    @Test
    public void test_ContainsKey_ExistentKey() {
        Assert.assertTrue(this.refFilteredMap.containsKey(4));
    }

    @Test
    public void test_ContainsValue_NonExistentValue() {
        Assert.assertFalse(this.refFilteredMap.containsValue(15));
    }

    @Test
    public void test_ContainsValue_ExistentValue() {
        Assert.assertTrue(this.refFilteredMap.containsValue(8));
    }

    @Test
    public void test_Clear_SizeIsZero() {
        this.refFilteredMap.clear();

        Assert.assertEquals(0, this.refFilteredMap.size());
    }

    @Test
    public void test_Size_SizeIsNotZero() {
        Assert.assertNotEquals(0, this.refFilteredMap.size());
    }

    @Test
    public void test_MakeFilteredMap_ValidParameters() {
        Assert.assertNotNull(Collections.makeFilteredMap(new HashMap<>(), new Not<>(null)));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredMap_NullMapParameter_NullPointerException() {
        Collections.makeFilteredMap(null, new Not<>(null));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredMap_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredMap(new HashMap<>(), null);
    }
}
