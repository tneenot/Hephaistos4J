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
    protected C collectionOfThisTemplate;
    protected C testingCollection;
    protected C invalidCollectionValues;
    protected int testingCollectionOriginalSize = 0;
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
        this.collectionOfThisTemplate.addAll(this.randomGenerator.getRandomElements());
        this.testingCollection.addAll(this.randomGenerator.getRandomElements());
        this.testingCollectionOriginalSize = this.testingCollection.size();

        RandomGenerator _invalid_values = new RandomGenerator();
        _invalid_values.generateRandomValues(10);

        this.invalidCollectionValues.addAll(_invalid_values.getRandomElements());
    }

    @After
    public void tearDown() {
        deleteTestData();

        destroyElementsReferences();

        this.randomGenerator = null;
    }

    private void deleteTestData() {
        this.testingCollection.clear();
        this.collectionOfThisTemplate.clear();
        this.testingCollectionOriginalSize = 0;

        this.randomGenerator.getRandomElements().clear();
    }

    private void destroyElementsReferences() {
        this.invalidCollectionValues = null;
        this.testingCollection = null;
        this.collectionOfThisTemplate = null;
    }

    @SuppressWarnings({"StatementWithEmptyBody", "SuspiciousMethodCalls"})
    protected void purgeAValueFromCollection(Collection<Integer> sourceList, Object value) {
        while (sourceList.remove(value)) {
            // Do nothing else
        }
    }

    @Test
    public void test_HashCode_Valid() {
        Assert.assertTrue(0 != this.testingCollection.hashCode());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void test_Equals_ToItSelf() {
        Assert.assertTrue(this.testingCollection.equals(this.testingCollection));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void test_Equals_NotEqualsWithAnIntegerType() {
        Assert.assertFalse(this.testingCollection.equals(new Integer(5)));
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void test_Equals_NotEqualsWithNullValue() {
        Assert.assertFalse(this.testingCollection.equals(null));
    }

    @Test
    public void test_Equals_EqualsWithObjectType() {
        Assert.assertFalse(this.testingCollection.equals(new Object()));
    }

    @Test
    public void test_RemoveAll_NoValidValues() {
        Assert.assertFalse(this.testingCollection.removeAll(this.invalidCollectionValues));
    }

    @Test
    public void test_RemoveAll_NoValidValues_ReferencesValuesNotRemoved() {
        this.testingCollection.removeAll(this.invalidCollectionValues);

        Assert.assertEquals(this.testingCollectionOriginalSize, this.testingCollection.size());
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues() {
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);

        Assert.assertTrue(this.testingCollection.removeAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues_ReferencesValuesRemoved() {
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);
        this.testingCollection.removeAll(_list);

        Assert.assertNotEquals(this.testingCollectionOriginalSize, this.testingCollection.size());
    }

    @Test
    public void test_Clear_NoValuesLeft() {
        this.testingCollection.clear();

        Assert.assertEquals(0, this.testingCollection.size());
    }

    @Test
    public void test_IsEmpty_InvalidForNonEmptyCollection() {
        Assert.assertFalse(this.testingCollection.isEmpty());
    }

    @Test
    public void test_IsEmpty_EmptyAfterCollectionClearing() {
        // Setup
        this.testingCollection.clear();

        // Assert
        Assert.assertTrue(this.testingCollection.isEmpty());
    }

    @Test
    public void test_ContainsAll_AwaitingValidValues() {
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);

        Assert.assertTrue(this.testingCollection.containsAll(this.randomGenerator.getSubListFromList(_list, 2)));
    }

    @Test
    public void test_RetainAll_WithValidValues() {
        List<Integer> _list = new ArrayList<>(this.collectionOfThisTemplate);

        Assert.assertTrue(this.testingCollection.retainAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }

    @Test
    public void test_RetainAll_WithInvalidValues() {
        Assert.assertFalse(this.testingCollection.retainAll(this.invalidCollectionValues));
    }

    @Test
    public void test_RetainAll_WithInvalidValues_NotContainsBadValues() {
        this.testingCollection.retainAll(this.invalidCollectionValues);

        for (Integer i : this.invalidCollectionValues) {
            Assert.assertFalse(this.testingCollection.contains(i));
        }
    }

    @Test
    public void test_toArrayT_RemoveInvalidValues() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.collectionOfThisTemplate.addAll(this.invalidCollectionValues);

        // Exercise
        Integer[] _result = this.testingCollection.toArray(new Integer[this.testingCollection.size()]);

        // Assert
        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Assert.assertNotEquals(_invalid_value, _result[_cpt].intValue());
        }
    }

    @Test
    public void test_toArrayT_RemoveValues_EmptyArray() {
        // Setup
        this.testingCollection.clear();

        // Assert
        Assert.assertNull(this.testingCollection.toArray(new Integer[2])[0]);
    }

    @Test
    public void test_toArray_RemoveInvalidValues() {
        // Setup
        this.collectionOfThisTemplate.addAll(this.invalidCollectionValues);
        int _invalid_value = getAnInvalidValue();

        // Exercise
        Object[] _result = this.testingCollection.toArray();

        // Assert - Control if the invalid value exists into the final array
        for (int _cpt = 0; _cpt < _result.length; ++_cpt) {
            Integer i = (Integer) _result[_cpt];
            Assert.assertNotEquals(_invalid_value, i.intValue());
        }
    }

    @Test
    public void test_toArray_RemoveValues_EmptyArray() {
        // Setup
        this.testingCollection.clear();

        // Asset
        Assert.assertEquals(0, this.testingCollection.toArray().length);
    }

    protected int getAnInvalidValue() {
        List<Integer> _parsing_list = new ArrayList<>(this.invalidCollectionValues);
        return this.randomGenerator.getOnceValueFrom(_parsing_list);
    }

    @Test
    public void test_Contains_InvalidValue() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithInvalidData() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.collectionOfThisTemplate.addAll(this.invalidCollectionValues);

        // Assert
        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.testingCollection.contains(_valid_value));
    }

    protected int getAValidValue() {
        List<Integer> _parsing_list = new ArrayList<>(this.collectionOfThisTemplate);
        return this.randomGenerator.getOnceValueFrom(_parsing_list);
    }

    @Test
    public void test_Size_ControlSizeValue() {
        Assert.assertEquals(this.testingCollectionOriginalSize, this.testingCollection.size());
    }

    @Test
    public void test_Add_ValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.testingCollection.add(_valid_value));
    }

    @Test
    public void test_Add_ValidData_ControlIsInCollection() {
        // Setup
        int _valid_value = getAValidValue();

        // Remove old value to be sure while it will be added, there will be only one first occurence of this value
        purgeAValueFromCollection(this.testingCollection, _valid_value);

        // Exercise
        this.testingCollection.add(_valid_value);

        // Assert
        Assert.assertTrue(this.testingCollection.contains(_valid_value));
    }

    @Test
    public void test_AddAll_ValidData() {
        // Setup
        Collection<Integer> _copy = new ArrayList<>(this.testingCollection);

        // Assert
        Assert.assertTrue(this.testingCollection.addAll(_copy));
    }

    @Test
    public void test_AddAll_InvalidData() {
        Assert.assertFalse(this.testingCollection.addAll(this.invalidCollectionValues));
    }

    @Test
    public void test_Add_InvalidData() {
        int _invalid_value = getAnInvalidValue();

        Assert.assertFalse(this.testingCollection.add(_invalid_value));
    }

    @Test
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        int _invalid_value = getAnInvalidValue();

        this.testingCollection.add(_invalid_value);

        Assert.assertFalse(this.testingCollection.contains(_invalid_value));
    }

    @Test
    public void test_Iterator_NotNull() {
        Assert.assertNotNull(this.testingCollection.iterator());
    }

    @Test
    public void test_Iterator_NoInvalidValue_FromExternalCollection() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.collectionOfThisTemplate.add(_invalid_value);

        // Assert
        Iterator<Integer> _it = this.testingCollection.iterator();
        while (_it.hasNext()) {
            Assert.assertNotEquals(1, _it.next().intValue());
        }
    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertTrue(this.testingCollection.remove(getAValidValue()));
    }

    @Test
    public void test_Remove_InvalidValue() {
        Assert.assertFalse(this.testingCollection.remove(getAnInvalidValue()));
    }

    @Test
    public void test_Remove_InvalidValue_FromExternalCollection() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.collectionOfThisTemplate.add(_invalid_value);

        // Assert
        Assert.assertFalse(this.testingCollection.remove(_invalid_value));
    }
}
