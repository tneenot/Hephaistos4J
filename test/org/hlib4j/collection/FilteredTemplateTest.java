package org.hlib4j.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public abstract class FilteredTemplateTest<C extends java.util.Collection, R extends Rule> {

    protected C sourceListRef;

    protected C filteredListRef;

    protected int filteredListRefSize = 0;

    protected R ruleRef = null;

    @Before
    public void setUp() {
        initializeElementsReferences();

        createTestData();
    }

    protected abstract void initializeElementsReferences();

    protected void createTestData() {
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
        deleteTestData();

        destroyElementsReferences();
    }

    protected void deleteTestData() {
        this.filteredListRef.clear();
        this.sourceListRef.clear();
        this.filteredListRefSize = 0;
    }

    protected abstract void destroyElementsReferences();


    @Test
    public void test_HashCode_Valid() {
        Assert.assertTrue(0 != this.filteredListRef.hashCode());
    }

    @Test
    public void test_Equals_ToItSelf() {
        Assert.assertTrue(this.filteredListRef.equals(this.filteredListRef));
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
    public void test_IsEmpty_InvalidForNonEmptyCollection() {
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
    public void test_Contains_FromExternalCollectionWithInvalidData() {
        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithValidData() {
        Assert.assertTrue(this.filteredListRef.contains(10));
    }

    @Test
    public void test_Size_ControlSizeValue() {
        Assert.assertEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_Add_ValidData() {
        Assert.assertTrue(this.filteredListRef.add(12));
    }

    @Test
    public void test_Add_ValidData_ControlIsInCollection() {
        this.filteredListRef.add(12);

        Assert.assertTrue(this.filteredListRef.contains(12));
    }

    @Test
    public void test_Add_InvalidData() {
        Assert.assertFalse(this.filteredListRef.add(1));
    }

    @Test
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        this.filteredListRef.add(1);

        Assert.assertFalse(this.filteredListRef.contains(1));
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullCollectionParameter_NullPointerException() {
        Collections.makeFilteredCollection(null, this.ruleRef);
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredCollection(this.sourceListRef, null);
    }

    @Test
    public void test_Iterator_NotNull() {
        Assert.assertNotNull(this.filteredListRef.iterator());
    }

    @Test
    public void test_Iterator_NoInvalidValue_FromExternalCollection() {
        this.sourceListRef.add(1);

        Iterator<Integer> _it = this.filteredListRef.iterator();
        while (_it.hasNext()) {
            Assert.assertNotEquals(1, _it.next().intValue());
        }
    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertTrue(this.filteredListRef.remove(2));
    }

    @Test
    public void test_Remove_InvalidValue() {
        Assert.assertFalse(this.filteredListRef.remove(1));
    }

    @Test
    public void test_Remove_InvalidValue_FromExternalCollection() {
        this.sourceListRef.add(1);
        Assert.assertFalse(this.filteredListRef.remove(1));
    }
}
