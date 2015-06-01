package org.hlib4j.math;


/**
 * {@link DefinitionDomain} unit tests with a {@link Range} class implementation.
 */
public class RangeWithDefinitionDomainTest extends DefinitionDomainTTemplateTest<Range<Integer>, Integer> {
    @Override
    public void setUp() throws Exception {
        this.definitionDomainTesting = new Range<Integer>(DefinitionDomain.LimitType.CLOSE_OPEN, 1, 3, 2);
        this.validDefinitionDomainInclude = new Range<Integer>(DefinitionDomain.LimitType.CLOSE_OPEN, 1, 2, 1);
        this.invalidDefinitionDomainInclude = new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 1, 3, 2);
        this.validDataInclude = 2;
        this.invalidDataInclude = 4;
    }
}
