package org.hlib4j.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tneenot on 06/04/15.
 */
public class NumberGeneratorTest {

    @Test
    public void test_Generate_ListOfRandomValues() {
        List<Integer> list = NumberGenerator.generateValues(10);

        Assert.assertEquals(10, list.size());
    }

    @Test
    public void test_Generate_Compare2Lists() {
        for (int i = 0; i < 100; ++i) {
            List<Integer> _list1 = NumberGenerator.generateValues(5);
            List<Integer> _list2 = NumberGenerator.generateValues(5);

            Assert.assertNotEquals(_list1, _list2);
        }
    }

    @Test
    public void test_GenerateValues_NoRedundancesElements() {
        // Setup
        List<Integer> _list = NumberGenerator.generateValues(6);
        List<Integer> _copy = new ArrayList<>();
        Collections.copy(_list, _copy);
        int _awaiting_elements = _list.size();

        // Exercise
        for (Integer _element : _list) {
            --_awaiting_elements;
            _copy.remove(_element);
        }

        // Assert
        Assert.assertEquals(_awaiting_elements, 0);
    }
}
