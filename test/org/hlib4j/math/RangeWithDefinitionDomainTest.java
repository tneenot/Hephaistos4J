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


/**
 * {@link DefinitionDomain} unit tests with a {@link Range} class implementation.
 */
public class RangeWithDefinitionDomainTest extends DefinitionDomainBehaviorTest<Range<Integer>, Integer> {
    @Override
    public void setUp() throws Exception {
        this.definitionDomainTesting = new Range<Integer>(DefinitionDomain.LimitType.CLOSE_OPEN, 1, 3, 2);
        this.validDefinitionDomainInclude = new Range<Integer>(DefinitionDomain.LimitType.CLOSE_OPEN, 1, 2, 1);
        this.invalidDefinitionDomainInclude = new Range<Integer>(DefinitionDomain.LimitType.BOTH_CLOSE, 1, 3, 2);
        this.validDataInclude = 2;
        this.invalidDataInclude = 4;
    }
}
