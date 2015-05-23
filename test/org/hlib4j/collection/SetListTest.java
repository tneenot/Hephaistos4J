package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Tests class for SetList class.
 */
public class SetListTest {

    private SetList<Integer> setTesting = null;

    @Before
    public void setUp() {
        setTesting = new SetList<>();
        setTesting.add(1);
    }


    @Test
    public void test_remove_InvalidNullValue_NotRemoved() {

        SetList<Integer> _local_set = new SetList<>(makeCollectionWithRedundantValues());
        Assert.assertFalse(_local_set.remove(null));
    }

    @Test
    public void test_add_NullValue_True() {
        Assert.assertTrue(setTesting.add(null));
    }

    @Test
    public void test_add_NullValueTwice_True() {
        // Setup
        setTesting.add(null);

        // SUT
        Assert.assertTrue(setTesting.add(null));
    }

    @Test
    public void test_size_NullValueTwice_ValidSize() {
        // Setup
        setTesting.add(null);
        setTesting.add(null);

        // SUT
        Assert.assertEquals(1, setTesting.size());
    }

    @Test
    public void test_countElementFor_NullValueTwice_ValidSize() {
        // Setup
        setTesting.add(null);
        setTesting.add(null);

        // SUT
        Assert.assertEquals(2, setTesting.countElementFor(null));
    }

    @Test
    public void test_countElementFor_InvalidValue_ZeroAwaiting() {
        Assert.assertEquals(0, setTesting.countElementFor(4));
    }

    @Test
    public void test_remove_InvalidValue_False() {
        Assert.assertFalse(setTesting.remove(4));
    }

    @Test
    public void test_countElementFor_AfterAllRedundantValuesRemoved_NoValueLeft() {
        // Setup
        setTesting.add(1);
        setTesting.add(2);

        // SUT
        setTesting.remove(1);
        setTesting.remove(1);

        // Assert
        Assert.assertEquals(0, setTesting.countElementFor(1));
    }

    public void test_size_AfterAllRedundantValuesRemoved_ValidSize() {
        // Setup
        setTesting.add(1);
        setTesting.add(2);

        // SUT
        setTesting.remove(1);
        setTesting.remove(1);

        // Assert
        Assert.assertEquals(1, setTesting.size());
    }

    @Test
    public void test_remove_AllRedundantValues_Valid() {
        // Setup
        setTesting.add(1);

        // SUT
        setTesting.remove(1);

        // Assert
        Assert.assertTrue(setTesting.remove(1));
    }

    @Test
    public void test_remove_ValidValue() {
        Assert.assertTrue(setTesting.remove(1));
    }

    @Test
    public void test_countElementFor_RemoveRedundantValue_ValidDataLeft() {
        // Setup
        setTesting.add(1);

        // SUT
        setTesting.remove(1);

        // Assert
        Assert.assertEquals(1, setTesting.countElementFor(1));
    }

    @Test
    public void test_size_RemoveRendudantValue_ValidCollectionSize() {
        // Setup
        setTesting.add(1);

        // SUT
        setTesting.remove(1);

        // Assert
        Assert.assertEquals(2, setTesting.size());
    }

    @Test
    public void test_countElementFor_MiscelleneaousValues_Valid() {
        // Setup
        setTesting.add(1);
        setTesting.add(2);

        Assert.assertEquals(1, setTesting.countElementFor(2));
    }

    @Test
    public void test_size_twice_SameValues() {
        // Setup
        setTesting.add(1);
        setTesting.add(1);

        // SUT
        Assert.assertEquals(1, setTesting.size());
    }

    @Test
    public void test_countElementFor_sameValues() {
        // Setup
        setTesting.add(1);
        setTesting.add(1);

        // SUT
        Assert.assertEquals(2, setTesting.countElementFor(1));
    }

    @Test
    public void test_add_twice_valid() {
        setTesting.add(1);
        Assert.assertTrue(setTesting.add(1));
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

    @After
    public void tearDown() {
        setTesting.clear();
        setTesting = null;
    }

    private Collection<Integer> makeCollectionWithRedundantValues() {
        return null;
    }
}
