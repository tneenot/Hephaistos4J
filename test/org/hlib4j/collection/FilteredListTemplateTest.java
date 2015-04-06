package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public class FilteredListTemplateTest extends FilteredTemplateTest<List<Integer>, Not<Integer>> {

    @Override
    protected void initializeElementsReferences() {
        this.sourceListRef = new LinkedList<>();
        this.ruleRef = new Not<>(1);
        this.filteredListRef = Collections.makeFilteredList(this.sourceListRef, this.ruleRef);
    }

    @Override
    protected void destroyElementsReferences() {
        this.sourceListRef = null;
        this.filteredListRef = null;
        this.ruleRef = null;
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
    public void test_ListIterator_Add_ValidValueAdded() {
        this.filteredListRef.listIterator().add(12);

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_ListIterator_Add_InvalidValueNotAdded() {
        this.filteredListRef.listIterator().add(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_ListIterator_Set_ValidValueSetted() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.set(28);

        Assert.assertTrue(this.filteredListRef.contains(28));
    }

    @Test
    public void test_ListIterator_Set_InvalidValueNotSetted() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.set(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_ListIterator_Remove_ValueRemoved() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();
        _it.remove();

        Assert.assertFalse(this.filteredListRef.contains(2));
    }

    @Test
    public void test_ListIterator_HasNext_HasNextValue() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_ListIterator_Next_ValidNextIndex() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertEquals(2, (int) _it.next());
    }

    @Test
    public void test_ListIterator_NextIndex_ValidNextIndex() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();
        _it.next();

        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_ListIterator_Previous_ValidPreviousValue() {
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
        ListFake<Integer> _list2 = new ListFake<>((FilteredList<Integer>) Collections.makeFilteredList(this.filteredListRef, new Not<Integer>(1)));
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
    public void test_AddAll_Index_FromInvalidList_NotAdded() {
        Assert.assertFalse(this.filteredListRef.addAll(1, Arrays.asList(1, 1, 1)));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NoInvalidValueAdded() {
        this.filteredListRef.addAll(1, Arrays.asList(1, 1, 1));

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection() {
        Assert.assertTrue(this.filteredListRef.addAll(1, Arrays.asList(12, 12, 12)));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection_ValidValueAdded() {
        this.filteredListRef.addAll(1, Arrays.asList(12, 12, 12));

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_Size_FromExternalListWithInvalidValue() {
        List<Integer> _col = Collections.makeFilteredList(this.sourceListRef, this.ruleRef);

        // Note: this.sourceListRef was contained 7 elements before to be added to a new filtered list type.
        Assert.assertEquals(6, _col.size());
    }

    @Test
    public void test_Equals_NotEqualsWithDifferentListDefinition() {
        List<Integer> _list2 = Collections.makeFilteredList(new LinkedList<Integer>(), new Not<Integer>(2));

        Assert.assertFalse(this.filteredListRef.equals(_list2));
    }

    @Test
    public void test_IsEmpty_ValidForEmptyList() {
        List<Integer> _col = Collections.makeFilteredList(new ArrayList<Integer>(), new Not<Integer>(1));
        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_Equals_NotEqualsWithACollectionType() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<Integer>(), new Not<Integer>(1));

        Assert.assertFalse(this.filteredListRef.equals(_col));
    }
}
