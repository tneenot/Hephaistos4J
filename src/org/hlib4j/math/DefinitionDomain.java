package org.hlib4j.math;

/**
 * This abstract class is a concept to represent a definition domain. Methods implemented by the class that realize this interface offers
 * some methods  * to define if a value or another domain is include into the current one. A definition domain is corresponding to a
 * group of values. The included values management it let to implementation.
 */
public abstract class DefinitionDomain<Type> {

    /**
     * Controls if the given value is include into the domain or not.
     *
     * @param value Value for verifying if it include within this domain or not
     * @return <code>True</code> if the value is include into the current one.
     */
    public abstract boolean isInclude(Type value);

    /**
     * Controls if the given domain is include into the domain or not.
     *
     * @param otherDefinitionDomain DefinitionDomain for verifying if it include within this domain nr not.
     * @return <code>True</code> if the domain is include into the current one.
     */
    public abstract boolean isInclude(DefinitionDomain<Type> otherDefinitionDomain);

    /**
     * Gets the lower limit value of the definition domain.
     *
     * @return Lower limit value of the definition domain.
     */
    public abstract Type getLowerLimitValue();

    /**
     * Gets the upper limit value of the definition domain.
     *
     * @return Upper limit value of the definition domain.
     */
    public abstract Type getUpperLimitValue();

    /**
     * List of range limits definition.
     */
    public enum LimitType {
        /**
         * Braces used to define a range limits of type of : <b>[..;..[</b>.
         */
        CLOSE_OPEN(LimitType.getBraceRight(), LimitType.getBraceRight()),

        /**
         * Braces used to define a range limits of type of: <b>]..;..]</b>
         */
        OPEN_CLOSE(LimitType.getBraceLeft(), LimitType.getBraceLeft()),

        /**
         * Braces used to define a range limits of type of: <b>[..;..]</b>.
         */
        BOTH_CLOSE(LimitType.getBraceRight(), LimitType.getBraceLeft()),

        /**
         * Braces used to define a range limits of type of: <b>]..;..[</b>.
         */
        BOTH_OPEN(LimitType.getBraceLeft(), LimitType.getBraceRight());

        private final static String LEFT = "]";
        private final static String RIGHT = "[";
        private String right;
        private String left;

        LimitType(String left, String right) {
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
