package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public class FilteredListTest extends FilteredTTemplateTest<List<Integer>, Not<Integer>> {

    @Override
    protected void instanciateReferencesTestData() {
        this.sourceListRef = new LinkedList<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());
        this.filteredListRef = Collections.makeFilteredList(this.sourceListRef, this.ruleRef);
        this.invalidListRef = new ArrayList<>();
    }


    /**
     * Test
     * <code>indexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_IndexOf_ValueExistsForValidIndex() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.filteredListRef);

        // Exercise
        int _value = this.filteredListRef.indexOf(_idx);

        // Assert
        Assert.assertEquals(_value, this.filteredListRef.indexOf(_idx));
    }

    /**
     * Test
     * <code>lastIndexOf(Object o)</code> for a list type.
     */
    @Test
    public void test_LastIndexOf_ValueExistsForValidIndex() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.filteredListRef);
        int _value = this.sourceListRef.lastIndexOf(_idx);

        // Assert
        Assert.assertEquals(_value, this.filteredListRef.lastIndexOf(_idx));
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
        // Setup
        int _valid_value = getAValidValue();
        purgeAValueFromCollection(this.filteredListRef, _valid_value);

        // Exercise
        this.filteredListRef.listIterator().add(_valid_value);

        // Assert
        Assert.assertTrue(this.filteredListRef.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_Add_InvalidValueNotAdded() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.filteredListRef.listIterator().add(_invalid_value);

        // Assert
        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
    }

    @Test
    public void test_ListIterator_Set_ValidValueSetted() {
        // Setup
        int _valid_value = getAValidValue();
        purgeAValueFromCollection(this.filteredListRef, _valid_value);
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Exercise
        _it.next();
        _it.set(_valid_value);

        // Assert
        Assert.assertTrue(this.filteredListRef.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_Set_InvalidValueNotSetted() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Exercise
        _it.next();
        _it.set(_invalid_value);

        // Assert
        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
    }

    @Test
    public void test_ListIterator_Remove_ValidValueRemoved() {
        // Setup
        int _valid_value = this.filteredListRef.get(0);
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Exercise
        _it.next();
        _it.remove();

        // Assert
        Assert.assertFalse(this.filteredListRef.contains(_valid_value));
    }

    @Test
    public void test_ListIterator_HasNext_HasNextValue() {
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        Assert.assertTrue(_it.hasNext());
    }

    @Test
    public void test_ListIterator_Next_ValidNextIndex() {
        // Setup
        int _valid_value = this.filteredListRef.get(0);

        // Exercise
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Assert
        Assert.assertEquals(_valid_value, (int) _it.next());
    }

    @Test
    public void test_ListIterator_NextIndex_ValidNextIndex() {
        // Setup
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Exercise
        _it.next();

        // Assert
        Assert.assertEquals(1, _it.nextIndex());
    }

    @Test
    public void test_ListIterator_Previous_ValidPreviousValue() {
        // Setup
        int _valid_value = this.filteredListRef.get(0);
        ListIterator<Integer> _it = this.filteredListRef.listIterator();

        // Exercise
        _it.next();

        // Assert
        Assert.assertEquals(_valid_value, (int) _it.previous());
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
        // Setup
        List<Integer> _ref = this.sourceListRef.subList(0, 2);

        // Exercise
        List<Integer> _list2 = this.filteredListRef.subList(0, 2);

        // Assert
        for (int i : _ref) {
            Assert.assertTrue(_list2.contains(i));
        }
    }

    @Test
    public void test_SubList_NotAwaitingData_Exclude() {
        // Setup
        List<Integer> _ref2 = this.randomGenerator.getSubListFromList(this.invalidListRef, this.invalidListRef.size() - 1);

        // Exercise
        List<Integer> _list2 = this.filteredListRef.subList(0, 2);

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
        purgeAValueFromCollection(this.filteredListRef, _valid_value);

        // Exercise
        this.filteredListRef.set(1, _valid_value);

        // Assert
        Assert.assertTrue(this.filteredListRef.contains(_valid_value));
    }

    @Test
    public void test_Set_Index_InvalidValue_NotSetted() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.filteredListRef.set(2, _invalid_value);

        // Assert
        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
    }

    @Test
    public void test_RemoveRange_ValuesRemoved() {
        // Setup
        ListFake<Integer> _list2 = new ListFake<>((FilteredList<Integer>) Collections.makeFilteredList(this.filteredListRef, this.ruleRef));

        // Exercise
        _list2.removeRange(0, 3);

        Assert.assertEquals(this.filteredListRefSize - 3, this.filteredListRef.size());
    }

    @Test
    public void test_HashCode_Valid() {
        Assert.assertTrue(0 != this.filteredListRef.hashCode());
    }

    @Test
    public void test_Get_Int_ValidValue() {
        // Setup
        int _idx = this.randomGenerator.getOnceIndexFrom(this.filteredListRef);
        int _value = this.filteredListRef.get(_idx);

        // Assert
        Assert.assertEquals(_value, (int) this.filteredListRef.get(_idx));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NotAdded() {
        Assert.assertFalse(this.filteredListRef.addAll(1, this.invalidListRef));
    }

    @Test
    public void test_AddAll_Index_FromInvalidList_NoInvalidValueAdded() {
        this.filteredListRef.addAll(1, this.invalidListRef);

        Assert.assertFalse(this.filteredListRef.contains(getAnInvalidValue()));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection() {
        // Setup
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        // Assert
        Assert.assertTrue(this.filteredListRef.addAll(1, _list));
    }

    @Test
    public void test_AddAll_Index_FromValidCollection_ValidValueAdded() {
        // Setup
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        // Exercise
        this.filteredListRef.addAll(1, _list);

        // Assert
        Assert.assertTrue(this.filteredListRef.contains(getAValidValue()));
    }

    @Test
    public void test_Size_FromExternalListWithInvalidValue() {
        List<Integer> _col = Collections.makeFilteredList(this.sourceListRef, this.ruleRef);

        // Note: this.sourceListRef was contained n elements + 1 before to be added to a new filtered list type.
        Assert.assertEquals(this.filteredListRefSize, _col.size());
    }

    @Test
    public void test_Equals_NotEqualsWithDifferentListDefinition() {
        List<Character> _list2 = Collections.makeFilteredList(new LinkedList<Character>(), new Not<Character>('r'));

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

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullListParameter_NullPointerException() {
        Collections.makeFilteredList(null, this.ruleRef);
    }
}
