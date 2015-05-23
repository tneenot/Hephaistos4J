package org.hlib4j.collection;

import org.hlib4j.util.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Tests class for SetList class.
 */
public class SetListTest {

    private SetList<Integer> setTesting = null;

    @Before
    public void setUp() {
        this.setTesting = new SetList<>();
        this.setTesting.add(1);
    }


    @Test
    public void test_remove_InvalidNullValue_NotRemoved() {

        SetList<Integer> _local_set = new SetList<>(makeCollectionWithRedundantValues());
        Assert.assertFalse(_local_set.remove(null));
    }

    @Test
    public void test_add_NullValue_True() {
        Assert.assertTrue(this.setTesting.add(null));
    }

    @Test
    public void test_add_NullValueTwice_True() {
        // Setup
        this.setTesting.add(null);

        // SUT
        Assert.assertTrue(this.setTesting.add(null));
    }

    @Test
    public void test_size_NullValueTwice_ValidSize() {
        // Setup
        setupWithNullValueTwice();

        // SUT
        Assert.assertEquals(1, this.setTesting.size());
    }

    private void setupWithNullValueTwice() {
        this.setTesting.add(null);
        this.setTesting.add(null);
    }

    @Test
    public void test_countElementFor_NullValueTwice_ValidSize() {
        // Setup
        setupWithNullValueTwice();

        // SUT
        Assert.assertEquals(2, this.setTesting.countElementFor(null));
    }

    @Test
    public void test_countElementFor_InvalidValue_ZeroAwaiting() {
        Assert.assertEquals(0, this.setTesting.countElementFor(4));
    }

    @Test
    public void test_remove_InvalidValue_False() {
        Assert.assertFalse(this.setTesting.remove(4));
    }

    @Test
    public void test_countElementFor_AfterAllRedundantValuesRemoved_NoValueLeft() {
        // Setup
        setupWithDifferentValues();

        // SUT
        removeSameValueTwice();

        // Assert
        Assert.assertEquals(0, this.setTesting.countElementFor(1));
    }

    private void removeSameValueTwice() {
        this.setTesting.remove(1);
        this.setTesting.remove(1);
    }

    private void setupWithDifferentValues() {
        this.setTesting.add(1);
        this.setTesting.add(2);
    }

    public void test_size_AfterAllRedundantValuesRemoved_ValidSize() {
        // Setup
        setupWithDifferentValues();

        // SUT
        removeSameValueTwice();

        // Assert
        Assert.assertEquals(1, this.setTesting.size());
    }

    @Test
    public void test_remove_AllRedundantValues_Valid() {
        addAndRemoveSameValue();


        // Assert
        Assert.assertTrue(setTesting.remove(1));
    }

    private void addAndRemoveSameValue() {
        // Setup
        this.setTesting.add(1);

        // SUT
        this.setTesting.remove(1);
    }

    @Test
    public void test_remove_ValidValue() {
        Assert.assertTrue(this.setTesting.remove(1));
    }

    @Test
    public void test_countElementFor_RemoveRedundantValue_ValidDataLeft() {
        // Setup
        addAndRemoveSameValue();

        // Assert
        Assert.assertEquals(1, this.setTesting.countElementFor(1));
    }

    @Test
    public void test_size_RemoveRendudantValue_ValidCollectionSize() {
        // Setup
        addAndRemoveSameValue();

        // Assert
        Assert.assertEquals(2, this.setTesting.size());
    }

    @Test
    public void test_countElementFor_MiscellaneaousValues_Valid() {
        // Setup
        setupWithDifferentValues();

        Assert.assertEquals(1, this.setTesting.countElementFor(2));
    }

    @Test
    public void test_size_twice_SameValues() {
        // Setup
        setupWitSameValues();

        // SUT
        Assert.assertEquals(1, this.setTesting.size());
    }

    private void setupWitSameValues() {
        this.setTesting.add(1);
        this.setTesting.add(1);
    }

    @Test
    public void test_countElementFor_sameValues() {
        // Setup
        setupWitSameValues();

        // SUT
        Assert.assertEquals(2, this.setTesting.countElementFor(1));
    }

    @Test
    public void test_add_twice_valid() {
        // Setup
        setTesting.add(1);

        // SUT
        Assert.assertTrue(this.setTesting.add(1));
    }

    @Test
    public void test_countElementFor_allValues_added() {
        // Setup
        SetList<Integer> _local_set = new SetList<>(makeCollectionWithRedundantValues());

        // SUT
        for (Integer i : _local_set) {
            Assert.assertTrue(_local_set.countElementFor(i) > 1);
        }
    }

    @Test
    public void test_size_allValues_added() {
        // Setup
        SetList<Integer> _local_set = new SetList<>(makeCollectionWithRedundantValues());
        int _counter = 0;
        for (Integer i : _local_set) ++_counter;

        // SUT
        Assert.assertEquals(_counter, _local_set.size());
    }

    @Test
    public void test_removeAll_SameCollectionsContents_SomeValuesRemoved() {
        // Setup
        Collection<Integer> _other_collection = makeCollectionWithRedundantValues();
        SetList<Integer> _set_ref = new SetList<>(_other_collection);

        // SUT
        Assert.assertTrue(_set_ref.removeAll(_other_collection));
    }

    @Test
    public void test_removeAll_SameCollectionsContents_NoValuesLeft() {
        // Setup
        Collection<Integer> _other_collection = makeCollectionWithRedundantValues();
        SetList<Integer> _set_ref = new SetList<>(_other_collection);

        // SUT
        _set_ref.removeAll(_other_collection);

        // Assert
        Assert.assertEquals(0, _set_ref.size());
    }

    @Test
    public void test_removeAll_DifferentCollectionContents_SomeNotValuesRemoved() {
        // Setup
        Collection<Integer> _other_collection = makeCollectionWithRedundantValues();
        SetList<Integer> _set_ref = new SetList<>();

        // SUT
        Assert.assertFalse(_set_ref.removeAll(_other_collection));
    }

    @Test
    public void test_removeAll_DifferentCollectionContents_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5, 6, 7);
        setupWithDifferentValues();

        // SUT
        this.setTesting.removeAll(_other_collection);

        // Assert
        Assert.assertEquals(3, this.setTesting.size());
    }

    @Test
    public void test_retainAll_SameCollectionsContents_ValuesRetains() {
        // Setup
        Collection<Integer> _other_collection = makeCollectionWithRedundantValues();
        SetList<Integer> _set_ref = new SetList<>(_other_collection);

        // SUT
        Assert.assertTrue(_set_ref.retainAll(_other_collection));
    }

    @Test
    public void test_retainAll_SameCollectionsContent_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = makeCollectionWithRedundantValues();
        SetList<Integer> _set_ref = new SetList<>(_other_collection);
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
        SetList<Integer> _set_ref = new SetList<>(_other_collection);
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
        SetList<Integer> _set_ref = new SetList<>(_other_collection);
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
        SetList<Integer> _set_ref = new SetList<>(_other_collection);
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
        Assert.assertTrue(this.setTesting.addAll(_other_collection));
    }

    @Test
    public void test_addAll_SameCollectionContent_CountElementForUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(1);

        // SUT
        this.setTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(2, this.setTesting.countElementFor(1));
    }

    @Test
    public void test_addAll_SameCollectionContent_SizeNotUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(1);

        // SUT
        this.setTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(1, this.setTesting.size());
    }

    @Test
    public void test_addAll_DifferentCollection_ElementAdded() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);

        // SUT
        Assert.assertTrue(this.setTesting.addAll(_other_collection));
    }

    @Test
    public void test_addAll_DifferentCollection_SizeUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);

        // SUT
        this.setTesting.addAll(_other_collection);

        // Asset
        Assert.assertEquals(2, this.setTesting.size());
    }

    @Test
    public void test_addAll_DifferentCollection_CountForElementUpdated() {
        // Setup
        Collection<Integer> _other_collection = Arrays.asList(5);

        // SUT
        this.setTesting.addAll(_other_collection);

        // Assert
        Assert.assertEquals(1, this.setTesting.countElementFor(5));
    }

    // TODO: toArray() methods

    @After
    public void tearDown() {
        this.setTesting.clear();
        this.setTesting = null;
    }

    private Collection<Integer> makeCollectionWithRedundantValues() {
        RandomGenerator _random_generator = new RandomGenerator();
        _random_generator.generateRandomValues(10);

        return _random_generator.getRandomElements();
    }
}
