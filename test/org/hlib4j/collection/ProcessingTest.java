package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

public class ProcessingTest {

    /**
     * Test of test method, of class Processing.
     */
    @Test
    public void test_Test_True()
    {
        Object e = new Object();
        Processing< Object > instance = new ProcessingFake< Object >();
        boolean expResult = true;
        boolean result = instance.test( e );
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void test_Perform_True()
    {
        Object e = new Object();
        Processing< Object > instance = new ProcessingFake< Object >();
        boolean expResult = true;
        boolean result = instance.perform( e );
        Assert.assertEquals( expResult, result );
    }

       /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void test_Perform_False()
    {
        Object e = null;
        Processing< Object > instance = new ProcessingFake< Object >();
        boolean expResult = false;
        boolean result = instance.perform( e );
        Assert.assertEquals( expResult, result );
    }


    /**
     * Implementation class for unit tests.
     */
    class ProcessingFake< E > extends Processing< E >
    {

        /**
         * Count if perform is calling or not.
         */
        private int count = 0;

        public ProcessingFake()
        {
            super();
        }

        public ProcessingFake(Predicate<E> r)
        {
            super(r);
        }

        @Override
        public boolean perform( Object e )
        {
            ++count;

            return e != null;

        }

        public int getCount()
        {
            return count;
        }
    }
}