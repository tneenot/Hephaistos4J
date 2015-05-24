package org.hlib4j.collection;

import org.hlib4j.util.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Tests class for DuplicateSet class.
 */
public class DuplicateSetTest {

    private DuplicateSet<Integer> duplicateSetTesting = null;

    @Before
    public void setUp() {
        this.duplicateSetTesting = new DuplicateSet<>();
    }


    @Test
    public void test_remove_InvalidNullValue_NotRemoved() {

        DuplicateSet<Integer> _local_set = new DuplicateSet<>(this.makeCollectionWithRedundantValues());
        Assert.assertFalse(_local_set.remove(null));
    }

    @Test
    public void test_add_NullValue_True() {
        Assert.assertTrue(this.duplicateSetTesting.add(null));
    }

    @Test
    public void test_add_NullValueTwice_True() {
        // Setup
        this.duplicateSetTesting.add(null);

        // SUT
        Assert.assertTrue(this.duplicateSetTesting.add(null));
    }

    @Test
    public void test_size_NullValueTwice_ValidSize() {
        // Setup
        this.setupWithNullValueTwice();

        // SUT
        Assert.assertEquals(2, this.duplicateSetTesting.size());
    }

    private void setupWithNullValueTwice() {
        this.duplicateSetTesting.add(null);
        this.duplicateSetTesting.add(null);
    }

    @Test
    public void test_countElementFor_NullValueTwice_ValidSize() {
        // Setup
        this.setupWithNullValueTwice();

        // SUT
        Assert.assertEquals(2, this.duplicateSetTesting.countElementFor(null));
    }

    @Test
    public void test_countElementFor_InvalidValue_ZeroAwaiting() {
        Assert.assertEquals(0, this.duplicateSetTesting.countElementFor(4));
    }

    @Test
    public void test_remove_InvalidValue_False() {
        Assert.assertFalse(this.duplicateSetTesting.remove(4));
    }

    @Test
    public void test_countElementFor_AfterAllRedundantValuesRemoved_NoValueLeft() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        this.removeAllSameValues();

        // Assert
        Assert.assertEquals(0, this.duplicateSetTesting.countElementFor(1));
    }

    private int removeAllSameValues() {
        int _nb_removed_values = 3;

        for (int i = 0; i < _nb_removed_values; ++i) {
            this.duplicateSetTesting.remove(1);
        }

        return _nb_removed_values;
    }

    private int setupWithDifferentValues() {
        int _nb_added_values = 2;

        this.duplicateSetTesting.add(2);
        this.duplicateSetTesting.add(3);

        return _nb_added_values;
    }

    @Test
    public void test_size_AfterAllRedundantValuesRemoved_ValidSize() {
        // Setup
        int _added_values = this.setupWitSameValues();
        _added_values += this.setupWithDifferentValues();

        // SUT
        _added_values -= this.removeAllSameValues();

        // Assert
        Assert.assertEquals(_added_values, this.duplicateSetTesting.size());
    }

    private int setupWitSameValues() {
        int _nb_added_values = 3;
        for (int i = 0; i < _nb_added_values; ++i) {
            this.duplicateSetTesting.add(1);
        }

        return _nb_added_values;
    }

    @Test
    public void test_remove_OneValueAmongRedundantValues_Valid() {
        // Setup
        this.duplicateSetTesting.add(1);
        this.addAndRemoveSameValue(1);

        // Assert
        Assert.assertTrue(this.duplicateSetTesting.remove(1));
    }

    private void addAndRemoveSameValue(int value) {
        this.duplicateSetTesting.add(value);
        this.duplicateSetTesting.remove(value);
    }

    @Test
    public void test_remove_ValidValue() {
        // Setup
        this.duplicateSetTesting.add(1);

        // Assert
        Assert.assertTrue(this.duplicateSetTesting.remove(1));
    }

    @Test
    public void test_countElementFor_RemoveRedundantValue_ValidDataLeft() {
        // Setup
        this.duplicateSetTesting.add(5);
        this.addAndRemoveSameValue(5);

        // Assert
        Assert.assertEquals(1, this.duplicateSetTesting.countElementFor(5));
    }

    @Test
    public void test_size_RemoveRendudantValue_ValidCollectionSize() {
        // Setup
        this.setupWitSameValues();
        this.addAndRemoveSameValue(5);

        // Assert
        Assert.assertEquals(3, this.duplicateSetTesting.size());
    }

    @Test
    public void test_countElementFor_MiscellaneaousValues_Valid() {
        // Setup
        this.setupWithDifferentValues();

        Assert.assertEquals(1, this.duplicateSetTesting.countElementFor(2));
    }

    @Test
    public void test_size_twice_SameValues() {
        // Setup
        int _nb_values = this.setupWitSameValues();

        // SUT
        Assert.assertEquals(_nb_values, this.duplicateSetTesting.size());
    }

    @Test
    public void test_size_OnEmptyList() {
        // Setup
        DuplicateSet<Integer> _local_set = new DuplicateSet<>();

        // SUT
        Assert.assertEquals(0, _local_set.size());
    }

    @Test
    public void test_countElementFor_sameValues() {
        // Setup
        this.setupWitSameValues();

        // SUT
        Assert.assertEquals(3, this.duplicateSetTesting.countElementFor(1));
    }

    @Test
    public void test_add_twice_valid() {
        // Setup
        duplicateSetTesting.add(1);

        // SUT
        Assert.assertTrue(this.duplicateSetTesting.add(1));
    }

    @Test
    public void test_add_twice_SizeIsValid() {
        // Setup
        this.setupWitSameValues();

        // SUT
        Assert.assertEquals(3, this.duplicateSetTesting.size());
    }

    @Test
    public void test_countElementFor_allRedundantValues_added() {
        // Setup
        Collection<Integer> _src_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _local_set = new DuplicateSet<>(_src_collection);
        _local_set.addAll(_src_collection);

        // SUT
        for (Integer i : _local_set) {
            Assert.assertTrue(_local_set.countElementFor(i) > 1);
        }
    }

    @Test
    public void test_size_allValues_added() {
        // Setup
        DuplicateSet<Integer> _local_set = new DuplicateSet<>(this.makeCollectionWithRedundantValues());
        int _counter = 0;
        for (Integer i : _local_set) ++_counter;

        // SUT
        Assert.assertEquals(_counter, _local_set.size());
    }

    @Test
    public void test_removeAll_SameCollectionsContents_SomeValuesRemoved() {
        // Setup
        Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);

        // SUT
        Assert.assertTrue(_set_ref.removeAll(_other_collection));
    }

    @Test
    public void test_removeAll_SameCollectionsContents_NoValuesLeft() {
        // Setup
        Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);

        // SUT
        _set_ref.removeAll(_other_collection);

        // Assert
        Assert.assertEquals(0, _set_ref.size());
    }

    @Test
    public void test_removeAll_DifferentCollectionContents_SomeNotValuesRemoved() {
        // Setup
        Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>();

        // SUT
        Assert.assertFalse(_set_ref.removeAll(_other_collection));
    }

    @Test
    public void test_removeAll_DifferentCollectionContents_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5, 6, 7);
        setupWithDifferentValues();

        // SUT
        this.duplicateSetTesting.removeAll(_other_collection);

        // Assert
        Assert.assertEquals(3, this.duplicateSetTesting.size());
    }

    @Test
    public void test_retainAll_SameCollectionsContents_ValuesRetains() {
        // Setup
        Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);

        // SUT
        Assert.assertTrue(_set_ref.retainAll(_other_collection));
    }

    @Test
    public void test_retainAll_SameCollectionsContent_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = this.makeCollectionWithRedundantValues();
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);
        int _original_size = _set_ref.size();

        // SUT
        _set_ref.retainAll(_other_collection);

        // Assert
        Assert.assertEquals(_original_size, _set_ref.size());
    }

    @Test
    public void test_retainAll_SameCollectionContent_CountElementForSoleValueUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);
        _set_ref.add(1);

        // SUT
        _set_ref.retainAll(_other_collection);

        // Assert
        Assert.assertEquals(0, _set_ref.countElementFor(1));
    }

    @Test
    public void test_retainAll_SameCollectionContent_CountElementForSameValueNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);
        _set_ref.add(5);

        // SUT
        _set_ref.retainAll(_other_collection);

        // Assert
        Assert.assertEquals(1, _set_ref.countElementFor(5));
    }

    @Test
    public void test_retainAll_SameCollectionContent_SizeValueNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);
        DuplicateSet<Integer> _set_ref = new DuplicateSet<>(_other_collection);
        _set_ref.add(5);

        // SUT
        _set_ref.retainAll(_other_collection);

        // Assert
        Assert.assertEquals(1, _set_ref.size());
    }

    @Test
    public void test_addAll_SameCollectionContent_ElementAdded() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(1);

        // SUT
        Assert.assertTrue(this.duplicateSetTesting.addAll(_other_collection));
    }

    @Test
    public void test_addAll_SameCollectionContent_CountElementForUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(1);
        this.duplicateSetTesting.add(1);

        // SUT
        this.duplicateSetTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(2, this.duplicateSetTesting.countElementFor(1));
    }

    @Test
    public void test_addAll_SameCollectionContent_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(1);

        // SUT
        this.duplicateSetTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(1, this.duplicateSetTesting.size());
    }

    @Test
    public void test_addAll_DifferentCollection_ElementAdded() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);

        // SUT
        Assert.assertTrue(this.duplicateSetTesting.addAll(_other_collection));
    }

    @Test
    public void test_addAll_DifferentCollection_SizeUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);
        this.duplicateSetTesting.add(1);

        // SUT
        this.duplicateSetTesting.addAll(_other_collection);

        // Asset
        Assert.assertEquals(2, this.duplicateSetTesting.size());
    }

    @Test
    public void test_addAll_DifferentCollection_CountForElementUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);

        // SUT
        this.duplicateSetTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(1, this.duplicateSetTesting.countElementFor(5));
    }

    @Test
    public void test_toArray_OnEmptySetList_EmptyArray() {
        // Setup
        DuplicateSet<Integer> _set_local = new DuplicateSet<>();

        // SUT
        Assert.assertEquals(0, _set_local.toArray().length);
    }

    @Test
    public void test_toArray_NonEmptyArray() {
        Assert.assertEquals(1, this.duplicateSetTesting.toArray().length);
    }

    @Test
    public void test_toArray_WithDuplicateValues_ValidArrayLength() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Assert.assertEquals(3, this.duplicateSetTesting.toArray().length);
    }

    @Test
    public void test_toArray_WithDuplicateValues_ArrayWithDuplicatesValues() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Object[] _raw_values = this.duplicateSetTesting.toArray();

        // Assert
        for (Object _value : _raw_values) {
            Assert.assertTrue(this.duplicateSetTesting.contains(_value));
        }
    }

    @Test
    public void test_toArray_WithNoDuplicateValues_ArrayWitNoDuplicatesValues() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Assert.assertEquals(this.duplicateSetTesting.size(), this.duplicateSetTesting.toArray().length);
    }

    @Test
    public void test_toArrayT_OnEmptySetList_EmptyArray() {
        // Setup
        DuplicateSet<Integer> _set_local = new DuplicateSet<>();

        // SUT
        Assert.assertEquals(0, _set_local.toArray(new Integer[0]));
    }

    @Test
    public void test_toArrayT_NonEmptyArray() {
        Assert.assertEquals(1, this.duplicateSetTesting.toArray(new Integer[this.duplicateSetTesting.size()]));
    }

    @Test
    public void test_toArrayT_WithDuplicateValues_ValidArrayLength() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Assert.assertEquals(3, this.duplicateSetTesting.toArray(new Integer[this.duplicateSetTesting.size()]).length);
    }

    @Test
    public void test_toArrayT_WithDuplicateValues_ArrayWithDuplicatesValues() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Integer[] _raw_values = this.duplicateSetTesting.toArray(new Integer[this.duplicateSetTesting.size()]);

        // Assert
        for (Object _value : _raw_values) {
            Assert.assertTrue(this.duplicateSetTesting.contains(_value));
        }
    }

    @Test
    public void test_toArrayT_WithNoDuplicateValues_ArrayWitNoDuplicatesValues() {
        // Setup
        this.setupWithDifferentValues();

        // SUT
        Assert.assertEquals(this.duplicateSetTesting.size(), this.duplicateSetTesting.toArray(new Integer[this.duplicateSetTesting.size()]).length);
    }

    @Test
    public void test_Iterator_WithDuplicateValues_DuplicateValuesAreExisting() {
        // Setup
        this.setupWitSameValues();

        // Assert
        for (Integer i : this.duplicateSetTesting) {
            Assert.assertEquals(1, i.intValue());
        }
    }

    @Test
    public void test_Iterator_WithNoDuplicateValues_NoDuplicateValuesAreExisting() {
        // Setup
        this.setupWithDifferentValues();

        // Assert
        int _previous = 0;
        for (Integer i : this.duplicateSetTesting) {
            Assert.assertNotEquals(_previous, i.intValue());
            _previous = i.intValue();
        }
    }

    @Test
    public void test_CopyConstructor_MoveSetListIntoAnotherCollection_SameSize() {
        // Setup
        this.setupWitSameValues();

        // SUT
        Collection<Integer> _other_collection = new ArrayList<>(this.duplicateSetTesting);

        // Assert
        Assert.assertEquals(_other_collection.size(), this.duplicateSetTesting.size());
    }

    @Test
    public void test_CopyConstructor_MoveSetListIntoAnotherCollection_NewCollectionWithDuplicateValues() {
        // Setup
        this.setupWitSameValues();

        // SUT
        Collection<Integer> _other_collection = new ArrayList<>(this.duplicateSetTesting);

        // Assert
        for (Integer i : _other_collection) {
            Assert.assertEquals(1, i.intValue());
        }
    }

    @After
    public void tearDown() {
        this.duplicateSetTesting.clear();
        this.duplicateSetTesting = null;
    }

    private Collection<Integer> makeCollectionWithRedundantValues() {
        RandomGenerator _random_generator = new RandomGenerator();
        _random_generator.generateRandomValues(10);

        return _random_generator.getRandomElements();
    }
}
