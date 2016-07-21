/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
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

package org.hlib4j.util;

import org.hlib4j.math.RangeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TiobenNeenot on 01/07/2016.
 */
public class RelativeProgressStepTest {

    private RelativeProgressStep relativeProgressStep;

    @Before
    public void setUp() throws Exception {
        relativeProgressStep = new RelativeProgressStep(4);
    }

    @Test
    public void test_GetSuccessor_DefaultValue_NullValue() throws Exception {
        Assert.assertNull(relativeProgressStep.getSuccessor());
    }

    @Test
    public void test_SetSuccessor_SetASucessor_SucessorUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(2));

        Assert.assertNotNull(relativeProgressStep.getSuccessor());
    }

    @Test
    public void test_SetProgress_SetAProgressValue_ValueUpdated() throws Exception {
        relativeProgressStep.setProgress(0.2);

        Assert.assertNotEquals(0.2, relativeProgressStep.getProgress());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_SetProgress_SetAValueWithASuccessor_ExceptionAwaiting() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(5));
        double ref_value = relativeProgressStep.getProgress();

        relativeProgressStep.setProgress(0.6);
    }

    @Test
    public void test_SetProgress_SetAValueForASuccessor_ValueUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(5));
        double ref_value = relativeProgressStep.getSuccessor().getProgress();

        relativeProgressStep.getSuccessor().setProgress(0.4);

        Assert.assertNotEquals(ref_value, relativeProgressStep.getSuccessor().getProgress(), 0.0);
    }

    @Test
    public void test_GetProgress_SetAValueForSuccessor_ValueUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(4));
        double ref_value = relativeProgressStep.getProgress();

        relativeProgressStep.getSuccessor().setProgress(0.45);

        Assert.assertNotEquals(ref_value, relativeProgressStep.getProgress(), 0.0);
    }

    @Test
    public void test_GetProgress_DefaultProgress_ValueIsZero() throws Exception {
        Assert.assertEquals(0.0, relativeProgressStep.getProgress(), 0.0);
    }

    @Test
    public void test_GetProgress_WithNoSuccessor_EqualsToGetProgress() throws Exception {
        relativeProgressStep.setProgress(0.4);

        Assert.assertEquals(relativeProgressStep.getProgress(), relativeProgressStep.getProgress(), 0.0);
    }

    @Test
    public void test_GetProgress_AfterStepIncrement_ValueUpdated() throws Exception {
        relativeProgressStep.setProgress(0.4);
        double ref_value = relativeProgressStep.getProgress();

        relativeProgressStep.nextStep();

        Assert.assertNotEquals(ref_value, relativeProgressStep.getProgress(), 0.0);
    }

    @Test
    public void test_GetProgress_AfterUpdateValueForSuccessor_ValueUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(6));

        relativeProgressStep.getSuccessor().setProgress(0.6);

        Assert.assertEquals(0.02, relativeProgressStep.getProgress(), 0.01);
    }

    @Test
    public void test_GetProgress_AfterUpdateValueForSuccessorAndStep_ValueUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(6));

        relativeProgressStep.getSuccessor().nextStep();
        relativeProgressStep.getSuccessor().setProgress(0.6);

        Assert.assertEquals(0.06, relativeProgressStep.getProgress(), 0.01);
    }

    @Test
    public void test_GetProgress_AfterUpdateValueForSuccessorAndStepAndStepForFirstRank_ValueUpdated() throws Exception {
        relativeProgressStep.setSuccessor(new RelativeProgressStep(6));

        relativeProgressStep.getSuccessor().nextStep();
        relativeProgressStep.nextStep();
        relativeProgressStep.getSuccessor().setProgress(0.6);

        Assert.assertEquals(0.316, relativeProgressStep.getProgress(), 0.001);
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_MaximumStepToZero_AwaitingException() throws Exception {
        new RelativeProgressStep(0);
    }

    @Test
    public void test_Constructor_MaximumStepToMinimum_InstanceBuildWithoutError() throws Exception {
        new RelativeProgressStep(1);
    }

    @Test
    public void test_GetProgress_ControlProgressAfterCounterStepIncrement_ValueIsZero() throws Exception {
        relativeProgressStep.setProgress(0.6);

        relativeProgressStep.nextStep();
        relativeProgressStep.previousStep();

        Assert.assertEquals(0, relativeProgressStep.getProgress(), 0);
    }

    @Test
    public void test_NextStep_GoToNextStep_NextStepIsValid() throws Exception {
        Assert.assertTrue(relativeProgressStep.nextStep());
    }

    @Test
    public void test_PreviousStep_GoToPreviousStep_PreviousStepNotValid() throws Exception {
        Assert.assertFalse(relativeProgressStep.previousStep());
    }

    @Test
    public void test_NexStep_GoToNextStepWhileNotExist_NextStepNotValid() throws Exception {
        RelativeProgressStep relative_progress = new RelativeProgressStep(1);

        Assert.assertFalse(relative_progress.nextStep());
    }

    @Test
    public void test_PreviousStep_GoToPreviousStepWhileNotExist_PreviousStepNotValid() throws Exception {
        RelativeProgressStep relative_progress = new RelativeProgressStep(1);

        Assert.assertFalse(relative_progress.previousStep());
    }

    @Test
    public void test_PreviousStep_GoToPreviousStepWhileNextStepNotExist_PreviousStepValid() throws Exception {
        RelativeProgressStep relative_progress = new RelativeProgressStep(2);
        relative_progress.nextStep();
        relative_progress.nextStep();

        Assert.assertTrue(relative_progress.previousStep());
    }

    @Test
    public void test_GetCurrentStep_GetFirstStepValue_FirstStepValueConforms() throws Exception {
        Assert.assertEquals(1, relativeProgressStep.getCurrentStep());
    }

    @Test
    public void test_GetCurrentStep_GetSecondStepValue_SecondStepValueConforms() throws Exception {
        relativeProgressStep.nextStep();

        Assert.assertEquals(2, relativeProgressStep.getCurrentStep());
    }

    @Test
    public void test_GetCurrentStep_GetTheNextStepAndPrevious_EqualsToInitialValue() throws Exception {
        relativeProgressStep.nextStep();
        relativeProgressStep.previousStep();

        Assert.assertEquals(1, relativeProgressStep.getCurrentStep());
    }
}