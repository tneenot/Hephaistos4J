/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2016 Tioben Neenot
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
 */

package org.hlib4j.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TimeFlow} class.
 */
public class TimeFlowTest {
    private TimeFlow timeFlow;

    @Before
    public void setUp() throws Exception {
        timeFlow = new TimeFlow();
    }

    @Test
    public void test_getStartTime_TimeFlowNotInitialized_ValueIsLowerThanZero() throws Exception {
        Assert.assertEquals(-1L, timeFlow.getStartTime());
    }

    @Test
    public void test_getStartTime_TimeFlowInitialized_ValueIsGreaterThanZero() {
        timeFlow.begin();

        Assert.assertTrue(timeFlow.getStartTime() > 0);
    }

    @Test
    public void test_getEndTime_TimeFlowNotInitialized_ValueIsLowerThanZero() throws Exception {
        Assert.assertEquals(-1L, timeFlow.getEndTime());
    }

    @Test
    public void test_getEndTime_TimeFlowInitialized_ValueIsGreaterThanZero() {
        timeFlow.end();

        Assert.assertTrue(timeFlow.getEndTime() > 0);
    }

    @Test
    public void test_begin_StartTimeAndEndTime_ValuesAreEquals() throws Exception {

        timeFlow.begin();

        Assert.assertEquals(timeFlow.getStartTime(), timeFlow.getEndTime());
    }

    @Test
    public void test_end_StartTimeAndEndTime_ValuesAreEquals() throws Exception {

        timeFlow.end();

        Assert.assertEquals(timeFlow.getEndTime(), timeFlow.getStartTime());
    }

    @Test(expected = IllegalStateException.class)
    public void test_getTimeFlow_TimeFlowNotInitialized_ExceptionAwaiting() throws Exception {

        timeFlow.getTimeFlow();
    }

    @Test
    public void test_getTimeFlow_WaitOneSecond_GreaterOrEqualToOneSecond() throws InterruptedException {
        timeFlow.begin();
        Thread.currentThread().sleep(1000);
        timeFlow.end();

        Assert.assertTrue(timeFlow.getTimeFlow() >= 1000L);
    }

    @Test
    public void test_equals_CompareToItSelf_AreEquals() throws Exception {
        Assert.assertTrue(timeFlow.equals(timeFlow));
    }

    @Test
    public void test_equals_CompareToSameInstance_AreEquals() {
        Assert.assertEquals(timeFlow, new TimeFlow());
    }

    @Test
    public void test_equals_CompareToNull_NotEquals() {
        Assert.assertFalse(timeFlow.equals(null));
    }

    @Test
    public void test_equals_CompareToObject_NotEquals() {
        Assert.assertNotEquals(timeFlow, new Object());
    }

    @Test
    public void test_equals_CompareToDifferentInstances_NotEquals() {
        timeFlow.begin();

        Assert.assertNotEquals(timeFlow, new TimeFlow());
    }

    @Test
    public void test_hashCode_WithSameInstanceType_ValuesAreEquals() throws Exception {
        Assert.assertEquals(timeFlow.hashCode(), new TimeFlow().hashCode());
    }

    @Test
    public void test_hashCode_WithDifferentInstances_ValuesAreNotEquals() {
        timeFlow.end();

        Assert.assertNotEquals(timeFlow.hashCode(), new TimeFlow().hashCode());
    }

    @Test
    public void test_toString_WithInitializedTimeFlow_DescriptionConforms() throws Exception {
        timeFlow.begin();
        Thread.currentThread().sleep(1000);
        timeFlow.end();

        Assert.assertEquals("00:00:01", timeFlow.toString());
    }

}