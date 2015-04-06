package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests on a collection managed by {@link org.hlib4j.collection.Collections#makeFilteredCollection(java.util.Collection, Rule)} with data.
 */
public class FilteredCollectionTest {

    private Collection<Integer> refFilteredCollection = null;

    private Collection<Integer> refSourceCollection = null;

    /**
     * Rule reference for tests
     */
    private Rule<Integer> ruleRef = null;

    /**
     * Test initialisation
     */
    @Before
    public void setUp() {
        this.ruleRef = new Multiple<>(2);

        this.refSourceCollection = new Vector<>();

        this.refFilteredCollection = Collections.makeFilteredCollection(this.refSourceCollection, ruleRef);

        for (int i = 1; i < 20; ++i) {
            this.refFilteredCollection.add(i);
        }
    }

    /**
     * Test cleaning
     *
     * @throws Throwable If cleaning error
     */
    @After
    public void tearDown() throws Throwable {
        this.refFilteredCollection.clear();
        this.refSourceCollection.clear();

        this.refFilteredCollection = null;
        this.refSourceCollection = null;
        this.ruleRef = null;
    }

    @Test
    public void test_Add_ValidValue() {
        Assert.assertTrue(this.refFilteredCollection.add(24));
    }

    @Test
    public void test_Add_InvalidValue() {
        Assert.assertFalse(this.refFilteredCollection.add(21));
    }

    @Test
    public void test_AddAll_FromValidCollection() {
        Assert.assertTrue(this.refSourceCollection.addAll(Arrays.asList(22, 24, 26)));
    }

    @Test
    public void test_AddAll_FromInvalidCollection() {
        Assert.assertFalse(this.refFilteredCollection.addAll(Arrays.asList(1, 3, 5)));
    }

    @Test
    public void test_Size_AddInvalidValueFromExternalCollection() {
        int _original_size = this.refFilteredCollection.size();
        this.refSourceCollection.add(21);

        Assert.assertEquals(_original_size, this.refFilteredCollection.size());
    }

    @Test
    public void test_Size_AddValidValueFromExternalCollection() {
        int _original_size = this.refFilteredCollection.size();
        this.refSourceCollection.add(22);

        Assert.assertNotEquals(_original_size, this.refSourceCollection.size());
    }

    @Test
    public void test_Clear_SizeAfterClearing() {
        this.refFilteredCollection.clear();

        Assert.assertEquals(0, this.refFilteredCollection.size());
    }

    @Test
    public void test_Contains_ValidValue() {
        this.refFilteredCollection.add(22);

        Assert.assertTrue(this.refFilteredCollection.contains(22));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithValidValue() {
        this.refSourceCollection.add(22);

        Assert.assertTrue(this.refFilteredCollection.contains(22));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithInvalidValues() {
        this.refSourceCollection.add(1);

        Assert.assertFalse(this.refFilteredCollection.contains(1));
    }

    @Test
    public void test_ToArray_RemoveInvalidValues() {
        // Test fixture
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        this.refSourceCollection.addAll(_invalid_values);

        // SUT
        List<Object> _result = Arrays.asList(this.refFilteredCollection.toArray());

        // Test result
        for (Object _raw_value : _invalid_values) {
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
    public void test_Contains_ToArrayT_RemoveInvalidValues() {
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);
        Collection<Integer> _values = new ArrayList<>();
        _values.add(4);
        _values.add(6);
        _values.add(8);

        Collection<Integer> _cols = Collections.makeFilteredCollection(_values, new Multiple<Integer>(2));

        List<Integer> _result = Arrays.asList(_cols.toArray(new Integer[_cols.size()]));
        for (Integer _raw_value : _invalid_values) {
            Assert.assertFalse(_result.contains(_raw_value));
        }
    }

    @Test
    public void test_Contains_RemoveInvalidValue_FromInternalIterator() {
        Collection<Integer> _invalid_values = Arrays.asList(3, 1, 5);

        for (Integer _col : this.refFilteredCollection) {
            Assert.assertFalse(_invalid_values.contains(_col));
        }
    }

    @Test
    public void test_RetainAll_FromInvalidValues() {
        Assert.assertFalse(this.refFilteredCollection.retainAll(Arrays.asList(3, null, 5)));
    }

    @Test
    public void test_RetainAll_FromValidValues() {
        Assert.assertTrue(this.refFilteredCollection.retainAll(Arrays.asList(4, 8, 16)));
    }

    @Test
    public void test_Equals_NotEqualsWithNull() {
        Assert.assertFalse(this.refFilteredCollection.equals(null));
    }


    @Test
    public void test_Equals_NotEqualsWithInteger() {
        Assert.assertFalse(this.refFilteredCollection.equals(new Integer(5)));
    }

    @Test
    public void test_Equals_NotEqualsWithAListType() {
        List<Integer> _list = Collections.makeFilteredList(new ArrayList<Integer>(), ruleRef);

        Assert.assertFalse(this.refFilteredCollection.equals(_list));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredCollection_NullCollectionParameter_NullPointerException() {
        Collections.makeFilteredCollection(null, new Multiple<>(0.0f));
    }

    @Test(expected = NullPointerException.class)
    public void test_MakeFilteredCollection_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredCollection(new ArrayList<>(), null);
    }

    @Test
    public void test_ContainsAll_ValidValues() {
        Assert.assertTrue(this.refFilteredCollection.containsAll(Arrays.asList(2, 4)));
    }

    @Test
    public void test_IsEmpty_WithEmptyCollection() {
        Collection<Integer> _col = Collections.makeFilteredCollection(new ArrayList<Integer>(), new Not<Integer>(1));

        Assert.assertTrue(_col.isEmpty());
    }

    @Test
    public void test_IsEmpty_WithNonEmptyCollection() {
        Assert.assertFalse(this.refFilteredCollection.isEmpty());
    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertTrue(this.refFilteredCollection.remove(6));
    }

    @Test
    public void test_Remove_BadValue() {
        Assert.assertFalse(this.refFilteredCollection.remove(3));
    }

    @Test
    public void test_RemoveAll_WithValidValues() {
        Assert.assertTrue(this.refFilteredCollection.removeAll(Arrays.asList(2, 4, 6)));
    }

    @Test
    public void test_RemoveAll_FromOutOfBoundValues() {
        Assert.assertFalse(this.refFilteredCollection.removeAll(Arrays.asList(22, 24)));
    }

    @Test
    public void test_ContainsAll_FromADifferentCollection() {
        // Test fixture
        Collection<Integer> _not_multiple_of_2 = Collections.makeFilteredCollection(new ArrayList<Integer>(), new Not<Integer>(new Multiple<>(2)));
        for (int i = 1; i < 20; ++i) {
            _not_multiple_of_2.add(i);
        }

        // SUT
        Assert.assertFalse(this.refFilteredCollection.containsAll(_not_multiple_of_2));
    }

    @Test
    public void test_HashCode_ValidHashCode() {
        Assert.assertTrue(0 != this.refFilteredCollection.hashCode());
    }

    @Test
    public void test_Equals_ToItSelf() {
        Assert.assertTrue(this.refFilteredCollection.equals(this.refFilteredCollection));
    }
}
