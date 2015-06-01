package org.hlib4j.math;

import org.hlib4j.util.RandomGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link Range} class.
 */
public class RangeTest {

    private static final int RANGE_VALUES = 10;

    private Range<Integer> rangeInTest;
    private int validValue;

    @Before
    public void setUp() throws Exception {
        RandomGenerator _random = new RandomGenerator();
        validValue = _random.getIsolatedValue();

        rangeInTest = new Range(DefinitionDomain.LimitType.BOTH_CLOSE, validValue, validValue + RANGE_VALUES);
        validValue += 1;
    }

    @Test
    public void test_isInclude_ValidValue_ValueInclude() {
        Assert.assertTrue(rangeInTest.isInclude(validValue));
    }

    @Test
    public void test_isInclude_LowerLimit_ValueInclude() {
        Assert.assertTrue(rangeInTest.isInclude(rangeInTest.getLowerLimitValue()));
    }

    @Test
    public void test_isInclude_InvalidValueLessThanhLowerLimit_ValueNotInclude() {
        // Setup
        int _invalid_value = rangeInTest.getLowerLimitValue() - 1;

        // Assert
        Assert.assertFalse(rangeInTest.isInclude(_invalid_value));
    }

    @Test
    public void test_isInclude_UpperLimit_ValueInclude() {
        Assert.assertTrue(rangeInTest.isInclude(rangeInTest.getUpperLimitValue()));
    }

    @Test
    public void test_isInclude_InvalidValueMoreThanUpperLimit_ValueNotInclude() {
        // Setup
        int _invalid_value = rangeInTest.getUpperLimitValue() + 1;

        // Assert
        Assert.assertFalse(rangeInTest.isInclude(_invalid_value));
    }

    @Test
    public void test_isInclude_SelfRange_RangeInclude() {
        Assert.assertTrue(rangeInTest.isInclude(rangeInTest));
    }

    @Test
    public void test_isInclude_ValidSubRange_RangeInclude() throws RangeException {
        // Setup
        Range<Integer> _sub_range = new Range<>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue() + 1, this
                .rangeInTest.getUpperLimitValue() - 1);

        // Assert
        Assert.assertTrue(rangeInTest.isInclude(_sub_range));
    }

    @Test
    public void test_isInclude_InvalidSubRange_RangeNotInclude() throws RangeException {
        // Setup
        Range<Integer> _sub_range = new Range<>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue() - 1, this
                .rangeInTest.getUpperLimitValue() + 1);

        // Assert
        Assert.assertFalse(rangeInTest.isInclude(_sub_range));
    }

    @Test
    public void test_isInclude_BothOpenRangeDefinition_RangeNotInclude() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(DefinitionDomain.LimitType.BOTH_OPEN, this.rangeInTest.getLowerLimitValue(), this
                .rangeInTest.getUpperLimitValue(), validValue + 1);

        // Asset
        Assert.assertFalse(this.rangeInTest.isInclude(_other_range));
    }

    @Test
    public void test_isInclude_OpenCloseRangeDefinition_RangeNotInclude() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(DefinitionDomain.LimitType.OPEN_CLOSE, this.rangeInTest.getLowerLimitValue(), this
                .rangeInTest.getUpperLimitValue());

        // Assert
        Assert.assertFalse(this.rangeInTest.isInclude(_other_range));
    }

    @Test
    public void test_isInclude_CloseOpenRangeDefinition_RangeNotInclude() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(DefinitionDomain.LimitType.CLOSE_OPEN, this.rangeInTest.getLowerLimitValue(), this
                .rangeInTest.getUpperLimitValue());

        // Assert
        Assert.assertFalse(this.rangeInTest.isInclude(_other_range));
    }

    @Test
    public void test_GetCurrentValue_DefaultValue_EqualToLowerLimit() {
        Assert.assertEquals(rangeInTest.getLowerLimitValue(), rangeInTest.getCurrentValue());
    }

    @Test(expected = RangeException.class)
    public void test_SetCurrentValue_InvalidValueWithMoreThanUpperLimit_IllegalArgumentException() throws RangeException {
        rangeInTest.setCurrentValue(rangeInTest.getUpperLimitValue() + 1);
    }

    @Test(expected = RangeException.class)
    public void test_SetCurrentValue_InvalidValueLessThanLowerLimit_IllegalArgumentException() throws RangeException {
        rangeInTest.setCurrentValue(rangeInTest.getLowerLimitValue() - 1);
    }

    @Test
    public void test_SetCurrentValue_ValidValue_ValueUpdated() throws RangeException {
        // SUT
        rangeInTest.setCurrentValue(validValue);

        // Assert
        Assert.assertEquals(validValue, rangeInTest.getCurrentValue().intValue());
    }

    @Test
    public void test_SetCurrentValue_UpperLimit_ValidValue() throws RangeException {
        // SUT
        rangeInTest.setCurrentValue(rangeInTest.getUpperLimitValue());

        // Assert
        Assert.assertEquals(rangeInTest.getUpperLimitValue(), rangeInTest.getCurrentValue());
    }

    @Test
    public void test_Equals_SelfRange_Equals() {
        Assert.assertEquals(this.rangeInTest, this.rangeInTest);
    }

    @Test
    public void test_Equals_WithBothOpenRange_NotEquals() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(DefinitionDomain.LimitType.BOTH_OPEN, this.rangeInTest.getLowerLimitValue(), this
                .rangeInTest.getUpperLimitValue(), validValue + 1);

        // Assert
        Assert.assertNotEquals(_other_range, this.rangeInTest);
    }

    @Test
    public void test_Equals_WithCloseOpenRange_NotEquals() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<>(DefinitionDomain.LimitType.CLOSE_OPEN, this.rangeInTest.getLowerLimitValue(), this.rangeInTest
                .getUpperLimitValue());

        // Assert
        Assert.assertNotEquals(_other_range, this.rangeInTest);
    }

    @Test
    public void test_Equals_WithOpenCloseRange_NotEquals() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<>(DefinitionDomain.LimitType.OPEN_CLOSE, this.rangeInTest.getLowerLimitValue(), this.rangeInTest
                .getUpperLimitValue());

        // Assert
        Assert.assertNotEquals(_other_range, this.rangeInTest);
    }

    @Test
    public void test_Equals_WithSubRange_NotEquals() throws RangeException {
        // Setup
        Range<Integer> _sub_range = new Range<Integer>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue() + 1, this
                .rangeInTest.getUpperLimitValue() - 1);

        // Assert
        Assert.assertNotEquals(_sub_range, this.rangeInTest);
    }

    @Test
    public void test_HashCode_ValidHashCode() {
        Assert.assertTrue(rangeInTest.hashCode() != 0);
    }

    @Test
    public void test_HashCode_DifferentRange_HashCodeNotEquals() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue() - 1, this
                .rangeInTest.getUpperLimitValue() + 1);

        // Assert
        Assert.assertNotEquals(rangeInTest.hashCode(), _other_range.hashCode());
    }

    @Test
    public void test_HashCode_SameRangeValues_HashCodeEquals() throws RangeException {
        // Setup
        Range<Integer> _same_range = new Range<Integer>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue(), this
                .rangeInTest.getUpperLimitValue());

        // Assert
        Assert.assertEquals(this.rangeInTest.hashCode(), _same_range.hashCode());
    }

    @Test
    public void test_toString_ValidDescription() throws Exception {
        // Setup
        String _awaiting_description = "[" + rangeInTest.getLowerLimitValue() + ";" + rangeInTest.getUpperLimitValue() + "]=" +
                rangeInTest.getCurrentValue();

        // Assert
        Assert.assertEquals(_awaiting_description, rangeInTest.toString());
    }

    @Test
    public void test_toString_DifferentRange_DescriptionNotEquals() throws RangeException {
        // Setup
        Range<Integer> _other_range = new Range<Integer>(this.rangeInTest.getLimitType(), this.rangeInTest.getLowerLimitValue() - 1, this
                .rangeInTest.getUpperLimitValue() + 1);

        // Assert
        Assert.assertNotEquals(_other_range.toString(), rangeInTest.toString());
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_SwitchUpperAndLowerLimit_RangeException() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_OPEN, 1, -1);
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_InvalidCurrentValue_RangeException() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 1, 2, 3);
    }

    @Test
    public void test_Constructor_ValidCurrentValue() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 1, 3, 2);
    }

    @Test
    public void test_Constructor_BothCloseWithSameLimits_ValidCurrentValue() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 1, 1, 1);
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_BothCloseWithSameLimitsAndInvalidCurrentValue_RangeException() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 0, 0, 1);
    }

    @Test
    public void test_Constructor_OpenCloseNotSameLimits_CurrentValueIsGreaterThanLimitValue() throws RangeException {
        // Setup
        Range<Integer> _range = new Range<Integer>(DefinitionDomain.LimitType.OPEN_CLOSE, 0, 1);

        // Assert
        Assert.assertEquals(_range.getCurrentValue(), _range.getUpperLimitValue());
    }

    @Test
    public void test_Constructor_CloseOpenNotSameLimits_CurrentValueIsEqualsToLowerLimitValue() throws RangeException {
        // Setup
        Range<Integer> _range = new Range<>(DefinitionDomain.LimitType.CLOSE_OPEN, 0, 1);

        // Assert
        Assert.assertEquals(_range.getCurrentValue(), _range.getLowerLimitValue());
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_BothOpenNotSameLimitsWithSufficentRange_ImpossibleToSetDefaultValue_RangeException() throws
            RangeException {
        new Range<>(DefinitionDomain.LimitType.BOTH_OPEN, 1, 3);
    }

    @Test(expected = RangeException.class)
    public void test_Constructor_BothOpenNotSameLimitsWithInsufficentRange_RangeException() throws RangeException {
        new Range<Integer>(DefinitionDomain.LimitType.BOTH_OPEN, 0, 1);
    }

    @After
    public void tearDown() throws Exception {
        validValue = 0;
        rangeInTest = null;
    }
}
