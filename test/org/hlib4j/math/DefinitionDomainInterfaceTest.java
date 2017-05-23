/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests to test the definition domain interface itself with the {@link DefinitionDomainBehaviorTest}.
 */
public class DefinitionDomainInterfaceTest extends DefinitionDomainBehaviorTest<DefinitionDomain<Integer>, Integer>
{
  @Override
  public void setUp() throws Exception
  {
    this.definitionDomainTesting = new DefinitionDomainFake();
    this.invalidDefinitionDomainInclude = new DefinitionDomainAlwaysFalseFake();
    this.validDataInclude = 1;
    this.invalidDataInclude = -2;
  }

  @Override
  public void test_isInclude_SelfDataFromDefinitionDomain_DataInclude()
  {
    Assert.assertTrue(definitionDomainTesting.isInclude(this.validDataInclude));
  }

  @Override
  public void tearDown() throws Exception
  {
    // Do nothing
  }

  @Test
  public void test_ForceRunAllInheritsTest()
  {
  }
}
