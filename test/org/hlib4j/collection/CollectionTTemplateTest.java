package org.hlib4j.collection;

import org.hlib4j.util.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This template class groups the common unit tests for all Collection type classes. Inherit of this template abstract class into your self unit
 * tests classes to ensure that the Liskov principle is respected.
 */
public abstract class CollectionTTemplateTest<C extends java.util.Collection<Integer>> {
    protected C sourceListRef;
    protected C collectionListRef;
    protected C invalidListRef;
    protected int collectionListRefSize = 0;
    protected RandomGenerator randomGenerator;

    @Before
    public void setUp() {
        buildRandomTestValues();

        instanciateReferencesTestData();

        createTestData();
    }

    private void buildRandomTestValues() {
        this.randomGenerator = new RandomGenerator();
        this.randomGenerator.generateRandomValues(10);
    }

    protected abstract void instanciateReferencesTestData();

    protected void createTestData() {
        this.sourceListRef.addAll(this.randomGenerator.getRandomElements());
        this.collectionListRef.addAll(this.randomGenerator.getRandomElements());
        this.collectionListRefSize = this.collectionListRef.size();

        RandomGenerator _invalid_values = new RandomGenerator();
        _invalid_values.generateRandomValues(10);

        this.invalidListRef.addAll(_invalid_values.getRandomElements());
    }

    @After
    public void tearDown() {
        deleteTestData();

        destroyElementsReferences();

        this.randomGenerator = null;
    }

    private void deleteTestData() {
        this.collectionListRef.clear();
        this.sourceListRef.clear();
        this.collectionListRefSize = 0;

        this.randomGenerator.getRandomElements().clear();
    }

    private void destroyElementsReferences() {
        this.invalidListRef = null;
        this.collectionListRef = null;
        this.sourceListRef = null;
    }

    @SuppressWarnings({"StatementWithEmptyBody", "SuspiciousMethodCalls"})
    protected void purgeAValueFromCollection(Collection<Integer> sourceList, Object value) {
        while (sourceList.remove(value)) {
            // Do nothing else
        }
    }

    @Test
    public void test_HashCode_Valid() {
        Assert.assertTrue(0 != this.collectionListRef.hashCode());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void test_Equals_ToItSelf() {
        Assert.assertTrue(this.collectionListRef.equals(this.collectionListRef));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void test_Equals_NotEqualsWithAnIntegerType() {
        Assert.assertFalse(this.collectionListRef.equals(new Integer(5)));
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void test_Equals_NotEqualsWithNullValue() {
        Assert.assertFalse(this.collectionListRef.equals(null));
    }

    @Test
    public void test_Equals_EqualsWithObjectType() {
        Assert.assertFalse(this.collectionListRef.equals(new Object()));
    }

    @Test
    public void test_RemoveAll_NoValidValues() {
        Assert.assertFalse(this.collectionListRef.removeAll(this.invalidListRef));
    }

    @Test
    public void test_RemoveAll_NoValidValues_ReferencesValuesNotRemoved() {
        this.collectionListRef.removeAll(this.invalidListRef);

        Assert.assertEquals(this.collectionListRefSize, this.collectionListRef.size());
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.collectionListRef.removeAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues_ReferencesValuesRemoved() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);
        this.collectionListRef.removeAll(_list);

        Assert.assertNotEquals(this.collectionListRefSize, this.collectionListRef.size());
    }

    @Test
    public void test_Clear_NoValuesLeft() {
        this.collectionListRef.clear();

        Assert.assertEquals(0, this.collectionListRef.size());
    }

    @Test
    public void test_IsEmpty_InvalidForNonEmptyCollection() {
        Assert.assertFalse(this.collectionListRef.isEmpty());
    }

    @Test
    public void test_ContainsAll_AwaitingValidValues() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.collectionListRef.containsAll(this.randomGenerator.getSubListFromList(_list, 2)));
    }

    @Test
    public void test_RetainAll_WithValidValues() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.collectionListRef.retainAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }

    @Test
    public void test_RetainAll_WithInvalidValues() {
        Assert.assertFalse(this.collectionListRef.retainAll(this.invalidListRef));
    }

    @Test
    public void test_RetainAll_WithInvalidValues_NotContainsBadValues() {
        this.collectionListRef.retainAll(this.invalidListRef);

        for (Integer i : this.invalidListRef) {
            Assert.assertFalse(this.collectionListRef.contains(i));
        }
    }

    @Test
    public void test_ToArrayT_RemoveInvalidValues() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.sourceListRef.addAll(this.invalidListRef);

        // Exercise
        Integer[] _result = this.collectionListRef.toArray(new Integer[this.collectionListRef.size()]);

        // Assert
        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Assert.assertNotEquals(_invalid_value, _result[_cpt].intValue());
        }
    }

    @Test
    public void test_ToArray_RemoveInvalidValues() {
        // Setup
        this.sourceListRef.addAll(this.invalidListRef);
        int _invalid_value = getAnInvalidValue();

        // Exercise
        Object[] _result = this.collectionListRef.toArray();

        // Assert - Control if the invalid value exists into the final array
        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Integer i = (Integer) _result[_cpt];
            Assert.assertNotEquals(_invalid_value, i.intValue());
        }
    }

    protected int getAnInvalidValue() {
        List<Integer> _parsing_list = new ArrayList<>(this.invalidListRef);
        return this.randomGenerator.getOnceValueFrom(_parsing_list);
    }

    @Test
    public void test_Contains_InvalidValue() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Assert
        Assert.assertFalse(this.collectionListRef.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithInvalidData() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.sourceListRef.addAll(this.invalidListRef);

        // Assert
        Assert.assertFalse(this.collectionListRef.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.collectionListRef.contains(_valid_value));
    }

    protected int getAValidValue() {
        List<Integer> _parsing_list = new ArrayList<>(this.sourceListRef);
        return this.randomGenerator.getOnceValueFrom(_parsing_list);
    }

    @Test
    public void test_Size_ControlSizeValue() {
        Assert.assertEquals(this.collectionListRefSize, this.collectionListRef.size());
    }

    @Test
    public void test_Add_ValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.collectionListRef.add(_valid_value));
    }

    @Test
    public void test_Add_ValidData_ControlIsInCollection() {
        // Setup
        int _valid_value = getAValidValue();

        // Remove old value to be sure while it will be added, there will be only one first occurence of this value
        purgeAValueFromCollection(this.collectionListRef, _valid_value);

        // Exercise
        this.collectionListRef.add(_valid_value);

        // Assert
        Assert.assertTrue(this.collectionListRef.contains(_valid_value));
    }

    @Test
    public void test_AddAll_ValidData() {
        // Setup
        Collection<Integer> _copy = new ArrayList<>(this.collectionListRef);

        // Assert
        Assert.assertTrue(this.collectionListRef.addAll(_copy));
    }

    @Test
    public void test_AddAll_InvalidData() {
        Assert.assertFalse(this.collectionListRef.addAll(this.invalidListRef));
    }

    @Test
    public void test_Add_InvalidData() {
        int _invalid_value = getAnInvalidValue();

        Assert.assertFalse(this.collectionListRef.add(_invalid_value));
    }

    @Test
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        int _invalid_value = getAnInvalidValue();

        this.collectionListRef.add(_invalid_value);

        Assert.assertFalse(this.collectionListRef.contains(_invalid_value));
    }

    @Test
    public void test_Iterator_NotNull() {
        Assert.assertNotNull(this.collectionListRef.iterator());
    }

    @Test
    public void test_Iterator_NoInvalidValue_FromExternalCollection() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.sourceListRef.add(_invalid_value);

        // Assert
        Iterator<Integer> _it = this.collectionListRef.iterator();
        while (_it.hasNext()) {
            Assert.assertNotEquals(1, _it.next().intValue());
        }
    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertTrue(this.collectionListRef.remove(getAValidValue()));
    }

    @Test
    public void test_Remove_InvalidValue() {
        Assert.assertFalse(this.collectionListRef.remove(getAnInvalidValue()));
    }

    @Test
    public void test_Remove_InvalidValue_FromExternalCollection() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.sourceListRef.add(_invalid_value);

        // Assert
        Assert.assertFalse(this.collectionListRef.remove(_invalid_value));
    }
}
