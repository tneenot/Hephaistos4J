package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

public class ProcessingTest {

    /**
     * Test of test method, of class Processing.
     */
    @Test
    public void test_Test_ValidProcessing_True() {
        Processing<Object> instance = new ProcessingFake<>();
        Assert.assertTrue(instance.test(new Object()));
    }

    @Test
    public void test_Test_NullObject_False() {
     Processing<Object> instance = new ProcessingFake<>();
        Assert.assertFalse(instance.test(null));
    }

    /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void test_Perform_ValidProcessing_True() {
        Processing<Object> instance = new ProcessingFake<>();
        Assert.assertTrue(instance.perform(new Object()));
    }

    /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void test_Perform_NullObject_False() {
        Processing<Object> instance = new ProcessingFake<>();
        Assert.assertFalse(instance.perform(null));
    }


    /**
     * Implementation class for unit tests.
     */
    class ProcessingFake<E> extends Processing<E> {

        /**
         * Count if perform is calling or not.
         */
        private int count = 0;

        public ProcessingFake() {
            super();
        }

        public ProcessingFake(Predicate<E> r) {
            super(r);
        }

        @Override
        public boolean perform(Object e) {
            ++count;

            return e != null;

        }

        public int getCount() {
            return count;
        }
    }
}