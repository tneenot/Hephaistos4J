/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 * Copyright (C) 2015 Tioben Neenot
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package org.hlib4j.math;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Template tests for {@link DefinitionDomain} class implementation. This template tests class allows to control the
 * behavior for all implementations classes.
 */
public abstract class DefinitionDomainBehaviorTest<D extends DefinitionDomain<T>, T>
{

  protected D definitionDomainTesting = null;
  protected D validDefinitionDomainInclude = null;
  protected D invalidDefinitionDomainInclude = null;
  protected T validDataInclude = null;
  protected T invalidDataInclude = null;

  @Before
  public abstract void setUp() throws Exception;

  @Test
  public void test_isInclude_ValidDataInclude_DataInclude()
  {
    Assert.assertTrue(definitionDomainTesting.isInclude(validDataInclude));
  }

  @Test
  public void test_isInclude_InvalidDataInclude_DataNotInclude()
  {
    Assert.assertFalse(definitionDomainTesting.isInclude(invalidDataInclude));
  }

  @Test
  public void test_isInclude_SelfDataFromDefinitionDomain_DataInclude()
  {
    Assert.assertTrue(this.definitionDomainTesting.isInclude(this.validDataInclude));
  }

  @Test
  public void test_isInclude_ValidDefinitionDomain_DefinitionDomainInclude()
  {
    Assert.assertTrue(definitionDomainTesting.isInclude(validDefinitionDomainInclude));
  }

  @Test
  public void test_isInclude_InvalidDefinitionDomain_DefinitionDomainNotInclude()
  {
    Assert.assertFalse(definitionDomainTesting.isInclude(invalidDefinitionDomainInclude));
  }

  @Test
  public void test_isInclude_SelfDefinitionDomain_DefinitionDomainInclude()
  {
    Assert.assertTrue(definitionDomainTesting.isInclude(definitionDomainTesting));
  }

  @After
  public void tearDown() throws Exception
  {
  }
}

/**
 * This class is using for {@link DefinitionDomainBehaviorTest} self tests. It's allowing to control that tests of template are conforms
 * to the awaiting results to ensure valid definition for all {@link DefinitionDomain} implementation.
 */
class DefinitionDomainFake extends DefinitionDomain<Integer>
{
  @Override
  public boolean isInclude(Integer value)
  {
    return value >= 0;

  }

  @Override
  public boolean isInclude(DefinitionDomain<Integer> otherDefinitionDomain)
  {
    return !(otherDefinitionDomain instanceof DefinitionDomainAlwaysFalseFake);

  }

  @Override
  public Integer getLowerLimitValue()
  {
    return 0;
  }

  @Override
  public Integer getUpperLimitValue()
  {
    return 3;
  }
}

/**
 * This class is using for {@link DefinitionDomainBehaviorTest} self tests. It's allowing to control that tests of template are conforms
 * to the awaiting results to ensure valid definition for all {@link DefinitionDomain} implementation. This class return always false for
 * its methods, for self {@link DefinitionDomainBehaviorTest} self control.
 */
class DefinitionDomainAlwaysFalseFake extends DefinitionDomain<Integer>
{
  @Override
  public boolean isInclude(Integer value)
  {
    return false;
  }

  @Override
  public boolean isInclude(DefinitionDomain<Integer> otherDefinitionDomain)
  {
    return false;
  }

  @Override
  public Integer getLowerLimitValue()
  {
    return 0;
  }

  @Override
  public Integer getUpperLimitValue()
  {
    return 3;
  }
}
