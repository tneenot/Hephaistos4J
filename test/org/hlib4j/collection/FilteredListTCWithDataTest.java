package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests on a collection List managed by {@link org.hlib4j.collection.Collections#makeFilteredList(java.util.List, Rule)} with valid data.
 */
public class FilteredListTCWithDataTest {

    private List<Integer> sourceListRef;

    private List<Integer> filteredListRef;

    private int filteredListRefSize = 0;

    @Before
    public void setUp() {
        this.sourceListRef = new LinkedList<>();

        this.filteredListRef = Collections.makeFilteredList(this.sourceListRef, new Not<>(1));
        this.filteredListRef.add(2);
        this.filteredListRef.add(3);
        this.filteredListRef.add(4);
        this.filteredListRef.add(6);
        this.filteredListRef.add(8);

        this.sourceListRef.add(1);
        this.sourceListRef.add(10);

        filteredListRefSize = this.filteredListRef.size();
    }

    @After
    public void tearDown() {
        this.filteredListRef.clear();
        this.filteredListRef = null;

        this.sourceListRef.clear();
        this.sourceListRef = null;
    }

    /**
     * Test
     * <code>indexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_IndexOf_ValueExistsForValidIndex() {
        Assert.assertEquals(1, this.filteredListRef.indexOf(3));
    }

    /**
     * Test
     * <code>lastIndexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_LastIndexOf_ValueExistsForValidIndex() {
        Assert.assertEquals(1, this.filteredListRef.lastIndexOf(3));
    }

    /**
     * Test
     * <code>listIterator</code> for a list type.
     */
    @Test
    public void test_ListIterator_Int_NotNull() {
        Assert.assertNotNull(this.filteredListRef.listIterator(0));
    }

    @Test
    public void test_ListIterator_NotNull() {
        Assert.assertNotNull(this.filteredListRef.listIterator());
    }

    /**
     * Test
     * <code>listIterator.add(E e)</code> to control if forbidden element is managing or not.
     */
    @Test
    public void test_Add_ListIterator_ValidValueAdded() {
        this.filteredListRef.listIterator().add(12);

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_Add_ListIterator_InvalidValueNotAdded() {
        this.filteredListRef.listIterator().add(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_Set_ListIterator_ValidValueSetted() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.set(28);

        Assert.assertTrue(this.filteredListRef.contains(28));
    }

    @Test
    public void test_Set_ListIterator_InvalidValueNotSetted() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.set(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    /**
     * Test remove method
     */
    @Test
    public void test_Remove_ListIterator_ValueRemoved() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.remove();

        Assert.assertFalse(this.filteredListRef.contains(2));
    }

    /**
     * Test next(), hasPrevious(), nextIndex()... from ListIterator.
     */
    @Test
    public void test_HasNext_ListIterator_HasNextValue() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_Next_ListIterator_ValidNextIndex() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertEquals(2, (int) _it.next());
    }

    @Test
    public void test_NextIndex_ListIterator_ValidNextIndex() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();

        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_Previous_ListIterator_ValidPreviousValue() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();

        Assert.assertEquals(2, (int) _it.previous());
    }

    @Test
    public void test_ListIterator_PreviousIndex_NoPreviousIndex() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertEquals(-1, _it.previousIndex());
    }

    @Test
    public void test_ListIterator_HasPrevious_NoPrevious() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertFalse(_it.hasPrevious());
    }

    /**
     * Create a sub-list for 2 indexes
     */
    @Test
    public void test_SubList_AwaitingData_Include() {
        List<Integer> _list2 = this.filteredListRef.subList(0, 2);
        int[] _ref =
                {
                        2, 3
                };

        for (int i : _ref) {
            Assert.assertTrue(_list2.contains(i));
        }
    }

    @Test
    public void test_SubList_NotAwaitingData_Exclude() {
        List<Integer> _list2 = this.filteredListRef.subList(0, 2);
        int[] _ref2 =
                {
                        4, 6, 8
                };
        for (int i : _ref2) {
            Assert.assertFalse(_list2.contains(i));
        }
    }

    /**
     * List.Set(int, E) test
     */
    @Test
    public void test_Set_Index_ValidValue_Setted() {
        this.filteredListRef.set(1, 14);

        Assert.assertTrue(this.filteredListRef.contains(14));
    }

    @Test
    public void test_Set_Index_InvalidValue_NotSetted() {
        this.filteredListRef.set(2, 1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_RemoveRange_ValuesRemoved() {
        AListFake<Integer> _list2 = new AListFake<>((FilteredList<Integer>) Collections.makeFilteredList(this.filteredListRef, new Not<>(1)));
        _list2.removeRange(0, 3);

        Assert.assertEquals(3, this.filteredListRef.size());
    }

    @Test
    public void test_HashCode_Valid() {
        Assert.assertTrue(0 != this.filteredListRef.hashCode());
    }

    @Test
    public void test_Get_Int_ValidValue() {
        Assert.assertEquals(3, (int) this.filteredListRef.get(1));
    }

    @Test
    public void test_Equals_ToItSelf() {
        Assert.assertTrue(this.filteredListRef.equals(this.filteredListRef));
    }

    @Test
    public void test_Equals_NotEqualsWithACollectionType() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<>(), new Not<>(1));

        Assert.assertFalse(this.filteredListRef.equals(_col));
    }

    @Test
    public void test_Equals_NotEqualsWithAnIntegerType() {
        Assert.assertFalse(this.filteredListRef.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_NotEqualsWithNullValue() {
        Assert.assertFalse(this.filteredListRef.equals(null));
    }

    @Test
    public void test_RemoveAll_NoValidValues() {
        Assert.assertFalse(this.filteredListRef.removeAll(Arrays.asList(7, 9)));
    }

    @Test
    public void test_RemoveAll_NoValidValues_ReferencesValuesNotRemoved() {
        this.filteredListRef.removeAll(Arrays.asList(7, 9));

        Assert.assertEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues() {
        Assert.assertTrue(this.filteredListRef.removeAll(Arrays.asList(2, 4, 6)));
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues_ReferencesValuesRemoved() {
        this.filteredListRef.removeAll(Arrays.asList(2, 4, 6));

        Assert.assertNotEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_Clear_NoValuesLeft() {
        this.filteredListRef.clear();

        Assert.assertEquals(0, this.filteredListRef.size());
    }

    @Test
    public void test_IsEmpty_ForEmptyList() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<>(), new Not<>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_IsEmpty_ForNonEmptyList() {
        Assert.assertFalse(this.filteredListRef.isEmpty());
    }

    @Test
    public void test_ContainsAll_AwaitingValidValues() {
        Assert.assertTrue(this.filteredListRef.containsAll(Arrays.asList(2, 4)));
    }

    @Test
    public void test_RetainAll_WithValidValues() {
        Assert.assertTrue(this.filteredListRef.retainAll(Arrays.asList(2, 4, 8)));
    }

    @Test
    public void test_RetainAll_WithValidValues_ContainsOnlyTheseValues() {
        List<Integer> _right_values = Arrays.asList(2, 4, 8);

        this.filteredListRef.retainAll(_right_values);

        for (Integer i : this.filteredListRef) {
            Assert.assertTrue(_right_values.contains(i));
        }
    }

    @Test
    public void test_RetainAll_WithInvalidValues() {
        Assert.assertFalse(this.filteredListRef.retainAll(Arrays.asList(1, 1, 1)));
    }

    @Test
    public void test_RetainAll_WithInvalidValues_NotContainsBadValues() {
        List<Integer> _invalid_values = Arrays.asList(1, 1, 1);

        this.filteredListRef.retainAll(_invalid_values);

        for (Integer i : _invalid_values) {
            Assert.assertFalse(this.filteredListRef.contains(i));
        }
    }

    @Test
    public void test_ToArrayT_RemoveInvalidValues() {
        Integer[] _result = this.filteredListRef.toArray(new Integer[this.filteredListRef.size()]);

        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Assert.assertNotEquals(1, _result[_cpt].intValue());
        }
    }

    @Test
    public void test_ToArray_RemoveInvalidValues() {
        Object[] _result = this.filteredListRef.toArray();

        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Integer i = (Integer) _result[_cpt];
            Assert.assertNotEquals(1, i.intValue());
        }
    }

    @Test
    public void test_Contains_InvalidValue() {
        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_Contains_FromExternalListWithInvalidData() {
        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_Contains_FromExternalListWithValidData() {
        Assert.assertTrue(this.filteredListRef.contains(10));
    }

    @Test
    public void test_Size_ControlSizeValue() {
        Assert.assertEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NotAdded() {
        Assert.assertFalse(this.filteredListRef.addAll(1, Arrays.asList(1, 1, 1)));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NoInvalidValueAdded() {
        this.filteredListRef.addAll(1, Arrays.asList(1, 1, 1));

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_AddAll_Index_FromValidList() {
        Assert.assertTrue(this.filteredListRef.addAll(1, Arrays.asList(12, 12, 12)));
    }

    @Test
    public void test_AddAll_Index_FromValidList_ValidValueAdded() {
        this.filteredListRef.addAll(1, Arrays.asList(12, 12, 12));

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_Add_ValidData() {
        Assert.assertTrue(this.filteredListRef.add(12));
    }

    @Test
    public void test_Add_ValidData_ControlIsInList() {
        this.filteredListRef.add(12);

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_Add_InvalidData() {
        Assert.assertFalse(this.filteredListRef.add(1));
    }

    @Test
    public void test_Add_InvalidData_ControlIsNotInList() {
        this.filteredListRef.add(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_MakeFilteredList_ValidParameters() {
        assertNotNull(Collections.makeFilteredList(new ArrayList<>(), new Not<>(null)));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredList_NullListParameter() {
        assertNotNull(Collections.makeFilteredList(null, new Not<>(null)));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredList_NullRuleParameter() {
        assertNotNull(Collections.makeFilteredList(new ArrayList<>(), null));
    }
}
