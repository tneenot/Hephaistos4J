package org.hlib4j.math;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Template tests for {@link DefinitionDomain} class implementation.
 */
public abstract class DefinitionDomainTTemplateTest<D extends DefinitionDomain<T>, T> {

    protected D definitionDomainTesting = null;
    protected D validDefinitionDomainInclude = null;
    protected D invalidDefinitionDomainInclude = null;
    protected T validDataInclude = null;
    protected T invalidDataInclude = null;

    @Before
    public abstract void setUp() throws Exception;

    @Test
    public void test_isInclude_ValidDataInclude_DataInclude() {
        Assert.assertTrue(definitionDomainTesting.isInclude(validDataInclude));
    }

    @Test
    public void test_isInclude_InvalidDataInclude_DataNotInclude() {
        Assert.assertFalse(definitionDomainTesting.isInclude(invalidDataInclude));
    }

    @Test
    public void test_isInclude_SelfDataFromDefinitionDomain_DataInclude() {
        Assert.assertTrue(this.definitionDomainTesting.isInclude(this.validDataInclude));
    }

    @Test
    public void test_isInclude_ValidDefinitionDomain_DefinitionDomainInclude() {
        Assert.assertTrue(definitionDomainTesting.isInclude(validDefinitionDomainInclude));
    }

    @Test
    public void test_isInclude_InvalidDefinitionDomain_DefinitionDomainNotInclude() {
        Assert.assertFalse(definitionDomainTesting.isInclude(invalidDefinitionDomainInclude));
    }

    @Test
    public void test_isInclude_SelfDefinitionDomain_DefinitionDomainInclude() {
        Assert.assertTrue(definitionDomainTesting.isInclude(definitionDomainTesting));
    }

    @After
    public void tearDown() throws Exception {
    }
}

/**
 * This class is using for {@link DefinitionDomainTTemplateTest} self tests. It's allowing to control that tests of template are conforms
 * to the awaiting results to ensure valid defintion for all {@link DefinitionDomain} implementation.
 */
class FakeDefinitionDomain extends DefinitionDomain<Integer> {
    @Override
    public boolean isInclude(Integer value) {
        if (value < 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isInclude(DefinitionDomain<Integer> otherDefinitionDomain) {
        if (otherDefinitionDomain instanceof FakeDefinitionDomainAlwaysFalse) {
            return false;
        }

        return true;
    }

    @Override
    public Integer getLowerLimitValue() {
        return 0;
    }

    @Override
    public Integer getUpperLimitValue() {
        return 3;
    }
}

/**
 * This class is using for {@link DefinitionDomainTTemplateTest} self tests. It's allowing to control that tests of template are conforms
 * to the awaiting results to ensure valid defintion for all {@link DefinitionDomain} implementation. This class return always false for
 * its methods, for self {@link DefinitionDomainTTemplateTest} self control.
 */
class FakeDefinitionDomainAlwaysFalse extends DefinitionDomain<Integer> {
    @Override
    public boolean isInclude(Integer value) {
        return false;
    }

    @Override
    public boolean isInclude(DefinitionDomain<Integer> otherDefinitionDomain) {
        return false;
    }

    @Override
    public Integer getLowerLimitValue() {
        return 0;
    }

    @Override
    public Integer getUpperLimitValue() {
        return 3;
    }
}
