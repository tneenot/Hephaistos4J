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

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public class FilteredListTest extends CollectionFilteredBehaviorTest<List<Integer>, Not<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.collectionOfThisTemplate = new LinkedList<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.testingCollection = Collections.makeFilteredList(this.collectionOfThisTemplate, this.ruleRef);
        this.invalidCollectionValues = new ArrayList<>();
    }


    /**
     * Test
     * <code>indexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_IndexOf_ValueExistsForValidIndex() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.testingCollection);

        // Exercise
        int _value = this.testingCollection.indexOf(_idx);

        // Assert
        Assert.assertEquals(_value, this.testingCollection.indexOf(_idx));
    }

    /**
     * Test
     * <code>lastIndexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_LastIndexOf_ValidIndex_ValueExists() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.testingCollection);
        int _value = this.collectionOfThisTemplate.lastIndexOf(_idx);

        // Assert
        Assert.assertEquals(_value, this.testingCollection.lastIndexOf(_idx));
    }

    /**
     * Test
     * <code>listIterator</code> for a list type.
     */
    @Test
    public void test_ListIterator_Int_NotNull() {
        Assert.assertNotNull(this.testingCollection.listIterator(0));
    }

    @Test
    public void test_ListIterator_ValidIterator_NotNull() {
        Assert.assertNotNull(this.testingCollection.listIterator());
    }

    /**
     * Test
     * <code>listIterator.add(E e)</code> to control if forbidden element is managing or not.
     */
    @Test
    public void test_ListIterator_Add_ValidValueAdded() {
        // Setup
        int _valid_value = getAValidValue();
        purgeAValueFromCollection(this.testingCollection, _valid_value);

        // Exercise
        this.testingCollection.listIterator().add(_valid_value);

        // Assert
        Assert.assertTrue(this.testingCollection.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_Add_InvalidValueNotAdded() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.testingCollection.listIterator().add(_invalid_value);

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_ListIterator_Set_ValidValueSetted() {
        // Setup
        int _valid_value = getAValidValue();
        purgeAValueFromCollection(this.testingCollection, _valid_value);
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Exercise
        _it.next();
        _it.set(_valid_value);

        // Assert
        Assert.assertTrue(this.testingCollection.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_Set_InvalidValueNotSetted() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Exercise
        _it.next();
        _it.set(_invalid_value);

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_ListIterator_Remove_ValidValueRemoved() {
        // Setup
        int _valid_value = this.testingCollection.get(0);
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Exercise
        _it.next();
        _it.remove();

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_HasNext_HasNextValue() {
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_ListIterator_Next_ValidNextIndex() {
        // Setup
        int _valid_value = this.testingCollection.get(0);

        // Exercise
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Assert
        Assert.assertEquals(_valid_value, (int) _it.next());
    }

    @Test
    public void test_ListIterator_NextIndex_ValidNextIndex() {
        // Setup
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Exercise
        _it.next();

        // Assert
        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_ListIterator_Previous_ValidPreviousValue() {
        // Setup
        int _valid_value = this.testingCollection.get(0);
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        // Exercise
        _it.next();

        // Assert
        Assert.assertEquals(_valid_value, (int) _it.previous());
    }

    @Test
    public void test_ListIterator_PreviousIndex_NoPreviousIndex() {
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        Assert.assertEquals(-1, _it.previousIndex());
    }

    @Test
    public void test_ListIterator_HasPrevious_NoPrevious() {
        ListIterator<Integer> _it = this.testingCollection.listIterator();

        Assert.assertFalse(_it.hasPrevious());
    }

    /**
     * Create a sub-list for 2 indexes
     */
    @Test
    public void test_SubList_AwaitingData_Include() {
        // Setup
        List<Integer> _ref = this.collectionOfThisTemplate.subList(0, 2);

        // Exercise
        List<Integer> _list2 = this.testingCollection.subList(0, 2);

        // Assert
        for (int i : _ref) {
            Assert.assertTrue(_list2.contains(i));
        }
    }

    @Test
    public void test_SubList_NotAwaitingData_Exclude() {
        // Setup
        List<Integer> _ref2 = this.randomGenerator.getSubListFromList(this.invalidCollectionValues, this.invalidCollectionValues.size() - 1);

        // Exercise
        List<Integer> _list2 = this.testingCollection.subList(0, 2);

        // Assert
        for (int i : _ref2) {
            Assert.assertFalse(String.format("Invalid data found into the collection: %d", i), _list2.contains(i));
        }
    }

    /**
     * List.Set(int, E) test
     */
    @Test
    public void test_Set_Index_ValidValue_Setted() {
        // Setup
        int _valid_value = getAValidValue();
        purgeAValueFromCollection(this.testingCollection, _valid_value);

        // Exercise
        this.testingCollection.set(1, _valid_value);

        // Assert
        Assert.assertTrue(this.testingCollection.contains(_valid_value));
    }

    @Test
    public void test_Set_Index_InvalidValue_NotSetted() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.testingCollection.set(2, _invalid_value);

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_RemoveRange_ValidRangeValues_ValuesRemoved() {
        // Setup
        ListFake<Integer> _list2 = new ListFake<>((FilteredList<Integer>) Collections.makeFilteredList(this.testingCollection, this.ruleRef));

        // Exercise
        _list2.removeRange(0, 3);

        Assert.assertEquals(this.testingCollectionOriginalSize - 3, this.testingCollection.size());
    }

    @Test
    public void test_HashCode_DefaultHashCode_Valid() {
        Assert.assertTrue(0 != this.testingCollection.hashCode());
    }

    @Test
    public void test_Get_ValidIntIndex_ValidValue() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.testingCollection);
        int _value = this.testingCollection.get(_idx);

        // Assert
        Assert.assertEquals(_value, (int) this.testingCollection.get(_idx));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NotAdded() {
        Assert.assertFalse(this.testingCollection.addAll(1, this.invalidCollectionValues));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NoInvalidValueAdded() {
        this.testingCollection.addAll(1, this.invalidCollectionValues);

        Assert.assertFalse(this.testingCollection.contains(getAnInvalidValue()));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection() {
        // Setup
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);

        // Assert
        Assert.assertTrue(this.testingCollection.addAll(1, _list));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection_ValidValueAdded() {
        // Setup
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);

        // Exercise
        this.testingCollection.addAll(1, _list);

        // Assert
        Assert.assertTrue(this.testingCollection.contains(getAValidValue()));
    }

    @Test
    public void test_Size_FromExternalListWithInvalidValue_AwaitingSizeValidSinceInvalidValueRemoved() {
        List<Integer> _col = Collections.makeFilteredList(this.collectionOfThisTemplate, this.ruleRef);

        // Note: this.collectionOfThisTemplate was contained n elements + 1 before to be added to a new filtered list type.
        Assert.assertEquals(this.testingCollectionOriginalSize, _col.size());
    }

    @Test
    public void test_Equals_WithDifferentListDefinition_NotEquals() {
        List<Character> _list2 = Collections.makeFilteredList(new LinkedList<Character>(), new Not<Character>('r'));

        Assert.assertFalse(this.testingCollection.equals(_list2));
    }

    @Test
    public void test_IsEmpty_ForEmptyList_Valid() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<Integer>(), new Not<Integer>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_Equals_ForCollectionType_NotEquals() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<Integer>(), new Not<Integer>(1));

        Assert.assertFalse(this.testingCollection.equals(_col));
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullListParameter_NullPointerException() {
        Collections.makeFilteredList(null, this.ruleRef);
    }
}
