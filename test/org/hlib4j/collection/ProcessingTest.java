package org.hlib4j.collection;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*
*  Copyright (C) 2015 Tioben Neenot
*
*  This program is free software; you can redistribute it and/or modify it under
*  the terms of the GNU General Public License as published by the Free Software
*  Foundation; either version 2 of the License, or (at your option) any later
*  version.
*
*  This program is distributed in the hope that it will be useful, but WITHOUT
*  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
*  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
*  details.
*
*  You should have received a copy of the GNU General Public License along with
*  this program; if not, write to the Free Software Foundation, Inc., 51
*  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*
*/

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