package org.hlib4j.math;

/**
 * Range class defining a definition domain that'singleBrace representing a range of values whose concrete values are given by <code>T</code>
 * definition. Range definition have got some limits that can be opens or closes. Here, rather than to use a collection of values that
 * can be represents a definition domain, the implementation define a rule of available values.<br><br>
 * <p>
 * Limits are defining with the {@link org.hlib4j.math.Range.RangeType} enumeration where the {@link org.hlib4j.math.Range
 * .RangeType#RIGHT} definition is the <b>[</b> character and {@link org.hlib4j.math.Range.RangeType#LEFT} is the <b>]</b> character.
 */
public class Range<T extends Comparable<T>> implements DefinitionDomain<T> {

    private T lowerLimitValue;
    private T upperLimitValue;
    private T currentValue;
    private RangeType rangeType;

    public Range(RangeType rangeType, T lowerLimitValue, T upperLimitValue) throws RangeException {
        setCommonMembers(rangeType, lowerLimitValue, upperLimitValue);
        setCurrentValue(findBetterDefaultValue());
    }

    public Range(RangeType rangeType, T lowerLimitValue, T upperLimitValue, T currentValue) throws RangeException {
        setCommonMembers(rangeType, lowerLimitValue, upperLimitValue);
        setCurrentValue(currentValue);
    }

    private void setCommonMembers(RangeType rangeType, T lowerLimitValue, T upperLimitValue) throws RangeException {
        if (lowerLimitValue.compareTo(upperLimitValue) > 0) {
            throw new RangeException("Lower limit can't be greater than upper limit");
        }

        this.rangeType = rangeType;
        this.lowerLimitValue = lowerLimitValue;
        this.upperLimitValue = upperLimitValue;
    }

    private T findBetterDefaultValue() throws RangeException {
        if (this.rangeType.getLeft().equals(RangeType.BOTH_CLOSE.getLeft())) {
            return this.lowerLimitValue;
        }

        if (this.rangeType.getRight().equals(RangeType.BOTH_CLOSE.getRight())) {
            return this.upperLimitValue;
        }

        throw new RangeException("Default value can't be find according to range definition");
    }

    @Override
    public boolean isInclude(T value) {
        // Control of current value is realizing according to lower and upper limits in one hand and the RangeType limits definition in
        // other hand.
        if ((this.rangeType.getLeft().equals(RangeType.BOTH_CLOSE.getLeft()) && value.compareTo(this.lowerLimitValue) < 0) || (this.rangeType.getRight().equals(RangeType.BOTH_CLOSE.getRight()) && value.compareTo(this.upperLimitValue) > 0)) {
            return false;
        }

        if ((this.rangeType.getLeft().equals(RangeType.BOTH_OPEN.getLeft()) && value.compareTo(this.lowerLimitValue) <= 0) || (this
                .rangeType.getRight().equals(RangeType.BOTH_OPEN.getRight()) && value.compareTo(this.upperLimitValue) >= 0)) {
            return false;
        }

        if (value.compareTo(this.lowerLimitValue) < 0 || value.compareTo(this.upperLimitValue) > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isInclude(DefinitionDomain<T> otherDefinitionDomain) {
        // Direct pass: controls if this definition domain is equal to the current one
        if (this.equals(otherDefinitionDomain)) {
            return true;
        }

        // Pass 2:  Control limits of definition domain
        boolean _are_valid_limits = this.isInclude(otherDefinitionDomain.getLowerLimitValue()) && this.isInclude(otherDefinitionDomain
                .getUpperLimitValue());

        if (_are_valid_limits) {
            // Pass 3: compare limits definition
            Range<T> _other_definition = (Range<T>) otherDefinitionDomain;

            _are_valid_limits &= this.lowerLimitValue.compareTo(_other_definition.getLowerLimitValue()) <= 0 && this.rangeType.getLeft()
                    .equals
                            (_other_definition.getRangeType().getLeft()) && this.upperLimitValue.compareTo(_other_definition.getUpperLimitValue())
                    >= 0 && this.rangeType.getRight().equals(_other_definition.getRangeType().getRight());
        }

        return _are_valid_limits;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(T currentValue) throws RangeException {
        if (this.isInclude(currentValue) == false) {
            throw new RangeException("Current value to set is out of bounds: " + currentValue + " for: " + this);
        }

        this.currentValue = currentValue;
    }

    public T getLowerLimitValue() {
        return lowerLimitValue;
    }

    public T getUpperLimitValue() {
        return upperLimitValue;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;

        Range<?> range = (Range<?>) o;

        if (!lowerLimitValue.equals(range.lowerLimitValue)) return false;
        if (!upperLimitValue.equals(range.upperLimitValue)) return false;
        if (!currentValue.equals(range.currentValue)) return false;
        if (rangeType != range.rangeType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lowerLimitValue.hashCode();
        result = 31 * result + upperLimitValue.hashCode();
        result = 31 * result + currentValue.hashCode();
        result = 31 * result + rangeType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuffer _buffer = new StringBuffer();
        _buffer.append(this.rangeType.getLeft()).append(this.lowerLimitValue).append(";").append(this.upperLimitValue).append(this.rangeType
                .getRight()).append("=").append(this.currentValue);
        return _buffer.toString();
    }

    /**
     * List of range limits definition.
     */
    public enum RangeType {
        /**
         * Braces used to define a range limits of type of : <b>[..;..[</b>.
         */
        CLOSE_OPEN(RangeType.getBraceRight(), RangeType.getBraceRight()),

        /**
         * Braces used to define a range limits of type of: <b>]..;..]</b>
         */
        OPEN_CLOSE(RangeType.getBraceLeft(), RangeType.getBraceLeft()),

        /**
         * Braces used to define a range limits of type of: <b>[..;..]</b>.
         */
        BOTH_CLOSE(RangeType.getBraceRight(), RangeType.getBraceLeft()),

        /**
         * Braces used to define a range limits of type of: <b>]..;..[</b>.
         */
        BOTH_OPEN(RangeType.getBraceLeft(), RangeType.getBraceRight());

        private final static String LEFT = "]";
        private final static String RIGHT = "[";
        private String right;
        private String left;

        RangeType(String left, String right) {
            this.left = left;
            this.right = right;
        }

        final static String getBraceLeft() {
            return LEFT;
        }

        final static String getBraceRight() {
            return RIGHT;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }
    }
}
