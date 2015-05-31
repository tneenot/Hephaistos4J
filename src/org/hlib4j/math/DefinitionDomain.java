package org.hlib4j.math;

/**
 * This interface is a concept to represent a definition domain. Methods implemented by the class that realize this interface offers some methods
 * to define if a value or another domain is include into the current one. A definition domain is corresponding to a group of values. The included
 * values management it let to implementation.
 */
public interface DefinitionDomain<Type> {

    /**
     * Controls if the given value is include into the domain or not.
     *
     * @param value Value for verifying if it include within this domain or not
     * @return <code>True</code> if the value is include into the current one.
     */
    boolean isInclude(Type value);

    /**
     * Controls if the given domain is include into the domain or not.
     *
     * @param otherDefinitionDomain DefinitionDomain for verifying if it include within this domain nr not.
     * @return <code>True</code> if the domain is include into the current one.
     */
    boolean isInclude(DefinitionDomain<Type> otherDefinitionDomain);

    /**
     * Gets the lower limit value of the definition domain.
     *
     * @return Lower limit value of the definition domain.
     */
    Type getLowerLimitValue();

    /**
     * Gets the upper limit value of the definition domain.
     *
     * @return Upper limit value of the definition domain.
     */
    Type getUpperLimitValue();
}
