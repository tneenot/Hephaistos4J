package org.hlib4j.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by tneenot on 06/04/15.
 */
public class RandomGeneratorTest {

    private RandomGenerator randomGenerator = null;

    @Before
    public void setUp() {
        this.randomGenerator = new RandomGenerator();
        this.randomGenerator.generateValues(10);
    }

    @After
    public void tearDown() {
        this.randomGenerator = null;
    }

    @Test
    public void test_Generate_ListOfRandomValuesSizeValid() {
        Assert.assertEquals(10, this.randomGenerator.getRandomElements().size());
    }

    @Test
    public void test_Generate_InvalidListSizeElements() {
        RandomGenerator _random_generator = new RandomGenerator();
        _random_generator.generateValues(-1);

        Assert.assertTrue(_random_generator.getRandomElements().isEmpty());
    }

    @Test
    public void test_Generate_ZeroListSizeElements() {
        RandomGenerator _random_generator = new RandomGenerator();
        _random_generator.generateValues(0);

        Assert.assertTrue(_random_generator.getRandomElements().isEmpty());
    }

    @Test
    public void test_Generate_Compare2Lists() {
        RandomGenerator otherRandomGenerator = new RandomGenerator();

        for (int i = 0; i < 100; ++i) {
            otherRandomGenerator.generateValues(10);

            Assert.assertNotEquals(this.randomGenerator.getRandomElements(), otherRandomGenerator.getRandomElements());
        }
    }

    @Test
    public void test_GenerateValues_NoRedundantElements() {
        // Setup
        List<Integer> _copy = new ArrayList<>();
        Collections.copy(this.randomGenerator.getRandomElements(), _copy);
        int _awaiting_elements = this.randomGenerator.getRandomElements().size();

        // Exercise
        for (Integer _element : this.randomGenerator.getRandomElements()) {
            --_awaiting_elements;
            _copy.remove(_element);
        }

        // Assert
        Assert.assertEquals(_awaiting_elements, 0);
    }

    @Test
    public void test_GetOnceValue_ValueReturnedFromList() {
        int _value = this.randomGenerator.getOnceValueFromInnerList();

        Assert.assertTrue(this.randomGenerator.getRandomElements().contains(_value));
    }

    @Test
    public void test_GetSubList_ValidSubListSize() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(3);

        Assert.assertEquals(3, _sub_list.size());
    }

    @Test
    public void test_GetSubList_ValidSubListElement() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(3);

        Assert.assertTrue(this.randomGenerator.getRandomElements().containsAll(_sub_list));
    }

    @Test
    public void test_GetSubList_NegativeNumberOfElements() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(-1);

        Assert.assertTrue(_sub_list.isEmpty());
    }

    @Test
    public void test_GetSubList_SoMuchElements() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(this.randomGenerator.getRandomElements().size() + 1);

        Assert.assertTrue(_sub_list.isEmpty());
    }

    @Test
    public void test_GetSubList_SameSizeThanInnerList() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(this.randomGenerator.getRandomElements().size());

        Assert.assertTrue(_sub_list.isEmpty());
    }

    @Test
    public void test_GetSubList_ZeroListSize() {
        List<Integer> _sub_list = this.randomGenerator.getSubList(0);

        Assert.assertTrue(_sub_list.isEmpty());
    }

    @Test
    public void test_GetOnceIndexFrom_ValidList() {
        // Setup
        List<Integer> _list = Arrays.asList(3, 2, 1);

        // Exercise
        int _idx = this.randomGenerator.getOnceIndexFrom(_list);

        // Assert
        Assert.assertTrue(_idx >= 0 && _idx <= 2);
    }

    @Test
    public void test_GetOnceIndexFrom_EmptyList() {
        // Exercise
        int _idx = this.randomGenerator.getOnceIndexFrom(new ArrayList<Integer>());

        // Assert
        Assert.assertEquals(-1, _idx);
    }

    @Test
    public void test_GetOnceIndexFrom_NullList() {
        Assert.assertEquals(-1, this.randomGenerator.getOnceIndexFrom(null));
    }

    @Test
    public void test_GetOnceIndex_ValidIndex() {
        // Exercise
        int _idx = this.randomGenerator.getOnceIndex();

        Assert.assertTrue(_idx >= 0 && _idx <= this.randomGenerator.getRandomElements().size());
    }

    @Test
    public void test_GetIsolatedValue_ValidIsolatedValue() {
        int _nb_loop = 10000;

        System.out.println("Nb of loops: " + _nb_loop);

        int _previous_value = this.randomGenerator.getIsolatedValue();

        for (int i = 0; i < _nb_loop; ++i) {
            int _isolated_value = this.randomGenerator.getIsolatedValue();

            Assert.assertNotEquals(String.format("The current value is not an isolated value: {0}", _isolated_value), _previous_value, _isolated_value);

            _previous_value = _isolated_value;
        }
    }
}
