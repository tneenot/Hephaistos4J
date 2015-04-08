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
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public abstract class FilteredTemplateTest<C extends java.util.Collection<Integer>, R extends Rule> {

    protected C sourceListRef;

    protected C filteredListRef;

    protected C invalidListRef;

    protected int filteredListRefSize = 0;

    protected R ruleRef = null;

    protected RandomGenerator randomGenerator;

    @Before
    public void setUp() {
        initializeElementsReferences();

        createTestData();
    }

    protected abstract void initializeElementsReferences();

    protected void createTestData() {
        List<Integer> _elements_list = this.randomGenerator.getRandomElements();

        for (Integer i : _elements_list) {
            if (this.filteredListRef.add(i) == false) {
                // Adds twice to get an invalid elements list with at least more than one element
                this.invalidListRef.add(i);
                this.invalidListRef.add(i);
            }
        }

        this.filteredListRefSize = this.filteredListRef.size();
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

        this.randomGenerator.getRandomElements().clear();
    }

    protected abstract void destroyElementsReferences();


    protected void purgeAValueFromCollection(Collection<Integer> sourceList, Object value) {
        while (sourceList.remove(value)) {
            // Do nothing else
        }
    }

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
    public void test_Equals_EqualsWithObjectType() {
        Assert.assertFalse(this.filteredListRef.equals(new Object()));
    }

    @Test
    public void test_RemoveAll_NoValidValues() {
        Assert.assertFalse(this.filteredListRef.removeAll(this.invalidListRef));
    }

    @Test
    public void test_RemoveAll_NoValidValues_ReferencesValuesNotRemoved() {
        this.filteredListRef.removeAll(this.invalidListRef);

        Assert.assertEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.filteredListRef.removeAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }

    @Test
    public void test_RemoveAll_AllGivenValidValues_ReferencesValuesRemoved() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);
        this.filteredListRef.removeAll(_list);

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
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.filteredListRef.containsAll(this.randomGenerator.getSubListFromList(_list, 2)));
    }

    @Test
    public void test_RetainAll_WithValidValues() {
        List<Integer> _list = new ArrayList<>(this.sourceListRef);

        Assert.assertTrue(this.filteredListRef.retainAll(this.randomGenerator.getSubListFromList(_list, 3)));
    }


    @Test
    public void test_RetainAll_WithInvalidValues() {
        Assert.assertFalse(this.filteredListRef.retainAll(this.invalidListRef));
    }

    @Test
    public void test_RetainAll_WithInvalidValues_NotContainsBadValues() {
        this.filteredListRef.retainAll(this.invalidListRef);

        for (Integer i : this.invalidListRef) {
            Assert.assertFalse(this.filteredListRef.contains(i));
        }
    }

    @Test
    public void test_ToArrayT_RemoveInvalidValues() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.sourceListRef.addAll(this.invalidListRef);

        // Exercise
        Integer[] _result = this.filteredListRef.toArray(new Integer[this.filteredListRef.size()]);

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
        Object[] _result = this.filteredListRef.toArray();

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
        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithInvalidData() {
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.sourceListRef.addAll(this.invalidListRef);

        // Assert
        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
    }

    @Test
    public void test_Contains_FromExternalCollectionWithValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.filteredListRef.contains(_valid_value));
    }

    protected int getAValidValue() {
        List<Integer> _parsing_list = new ArrayList<>(this.sourceListRef);
        return this.randomGenerator.getOnceValueFrom(_parsing_list);
    }

    @Test
    public void test_Size_ControlSizeValue() {
        Assert.assertEquals(this.filteredListRefSize, this.filteredListRef.size());
    }

    @Test
    public void test_Add_ValidData() {
        int _valid_value = getAValidValue();

        Assert.assertTrue(this.filteredListRef.add(_valid_value));
    }

    @Test
    public void test_Add_ValidData_ControlIsInCollection() {
        // Setup
        int _valid_value = getAValidValue();

        // Remove old value to be sure while it will be added, there will be only one first occurence of this value
        purgeAValueFromCollection(this.filteredListRef, _valid_value);

        // Exercise
        this.filteredListRef.add(_valid_value);

        // Assert
        Assert.assertTrue(this.filteredListRef.contains(_valid_value));
    }

    @Test
    public void test_AddAll_ValidData() {
        // Setup
        Collection<Integer> _copy = new ArrayList<>(this.filteredListRef);

        // Assert
        Assert.assertTrue(this.filteredListRef.addAll(_copy));
    }

    @Test
    public void test_AddAll_InvalidData() {
        Assert.assertFalse(this.filteredListRef.addAll(this.invalidListRef));
    }

    @Test
    public void test_Add_InvalidData() {
        int _invalid_value = getAnInvalidValue();

        Assert.assertFalse(this.filteredListRef.add(_invalid_value));
    }

    @Test
    public void test_Add_InvalidData_ControlIsNotInCollection() {
        int _invalid_value = getAnInvalidValue();

        this.filteredListRef.add(_invalid_value);

        Assert.assertFalse(this.filteredListRef.contains(_invalid_value));
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
        // Setup
        int _invalid_value = getAnInvalidValue();

        // Exercise
        this.sourceListRef.add(_invalid_value);

        // Assert
        Iterator<Integer> _it = this.filteredListRef.iterator();
        while (_it.hasNext()) {
            Assert.assertNotEquals(1, _it.next().intValue());
        }
    }

    @Test
    public void test_Remove_ValidValue() {
        Assert.assertTrue(this.filteredListRef.remove(getAValidValue()));
    }

    @Test
    public void test_Remove_InvalidValue() {
        Assert.assertFalse(this.filteredListRef.remove(getAnInvalidValue()));
    }

    @Test
    public void test_Remove_InvalidValue_FromExternalCollection() {
        // Setup
        int _invalid_value = getAnInvalidValue();
        this.sourceListRef.add(_invalid_value);

        // Assert
        Assert.assertFalse(this.filteredListRef.remove(_invalid_value));
    }
}
