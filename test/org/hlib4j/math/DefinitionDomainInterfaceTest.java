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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests to test the definition domain interface itself thru the {@link DefinitionDomainBehaviorTest}.
 */
public class DefinitionDomainInterfaceTest extends DefinitionDomainBehaviorTest<DefinitionDomain<Integer>, Integer> {
    @Override
    public void setUp() throws Exception {
        this.definitionDomainTesting = new DefinitionDomainFake();
        this.invalidDefinitionDomainInclude = new DefinitionDomainAlwaysFalseFake();
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
    public void test_ForceRunAllInheritsTest() {
    }
}
