package org.hlib4j.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests to test the definition domain interface itself thru the {@link DefinitionDomainTTemplateTest}.
 */
public class DefinitionDomainInterfaceTest extends DefinitionDomainTTemplateTest<DefinitionDomain<Integer>, Integer> {
    @Override
    public void setUp() throws Exception {
        this.definitionDomainTesting = new FakeDefinitionDomainAlwaysTrue();
        this.invalidDefinitionDomainInclude = new FakeDefinitionDomainAlwaysFalse();
        this.validDataInclude = 1;
        this.invalidDataInclude = -2;
    }

    @Override
    public void test_isInclude_SelfDataFromDefinitionDomain_DataInclude() {
        Assert.assertTrue(definitionDomainTesting.isInclude(this.validDataInclude));
    }

    @Override
    public void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    public void test_RunAllTest() {
    }
}
