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

package org.hlib4j.collection;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * Unit tests based on template for {@link ArrayDeque} type.
 */
public class ArrayDequeTestFiltered extends CollectionFilteredBehaviorTest<Collection<Integer>, Not<Integer>> {
    @Override
    protected void instanciateReferencesTestData() {
        this.collectionOfThisTemplate = new ArrayDeque<>();
        this.ruleRef = new Not<>(this.randomGenerator.getOnceValue());

        this.testingCollection = Collections.makeFilteredCollection(this.collectionOfThisTemplate, this.ruleRef);
        this.invalidCollectionValues = new ArrayDeque<>();

    }

    // Used only to force all running tests
    @Test
    public void test_RunAllUnitTests() {
    }
}
