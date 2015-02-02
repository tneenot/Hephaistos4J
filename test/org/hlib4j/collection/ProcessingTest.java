package org.hlib4j.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

public class ProcessingTest {

    /**
     * Test of test method, of class Processing.
     */
    @Test
    public void testTest()
    {
        System.out.println( "test" );
        Object e = new Object();
        Processing< Object > instance = new ProcessingImpl< Object >();
        boolean expResult = true;
        boolean result = instance.test( e );
        Assert.assertEquals(expResult, result);
    }

    /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void testPerformTrue()
    {
        System.out.println( "perform true" );
        Object e = new Object();
        Processing< Object > instance = new ProcessingImpl< Object >();
        boolean expResult = true;
        boolean result = instance.perform( e );
        Assert.assertEquals( expResult, result );
    }

       /**
     * Test of perform method, of class Processing.
     */
    @Test
    public void testPerformFalse()
    {
        System.out.println( "perform false" );
        Object e = null;
        Processing< Object > instance = new ProcessingImpl< Object >();
        boolean expResult = false;
        boolean result = instance.perform( e );
        Assert.assertEquals( expResult, result );
    }


    /**
     * Implementation class for unit tests.
     */
    class ProcessingImpl < E > extends Processing< E >
    {

        /**
         * Count if perform is calling or not.
         */
        private int count = 0;

        public ProcessingImpl()
        {
            super();
        }

        public ProcessingImpl(Predicate<E> r)
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