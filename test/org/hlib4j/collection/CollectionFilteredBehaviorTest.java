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

import org.hlib4j.concept.Rule;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests on a collection List managed by {@link Collections#makeFilteredList(List, Rule)} with data.
 */
public abstract class CollectionFilteredBehaviorTest<C extends java.util.Collection<Integer>, R extends Rule> extends CollectionBehaviorTest<C> {

    protected R ruleRef = null;

    @Override
    protected void createTestData() {
        List<Integer> _elements_list = this.randomGenerator.getRandomElements();

        for (Integer i : _elements_list) {
            if (this.testingCollection.add(i) == false) {
                // Adds twice to get an invalid elements list with at least more than one element
                this.invalidCollectionValues.add(i);
                this.invalidCollectionValues.add(i);
            }
        }

        this.testingCollectionOriginalSize = this.testingCollection.size();
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullCollectionParameter_NullPointerException() {
        Collections.makeFilteredCollection(null, this.ruleRef);
    }

    @Test(expected = NullPointerException.class)
    public void test_Constructor_NullRuleParameter_NullPointerException() {
        Collections.makeFilteredCollection(this.collectionOfThisTemplate, null);
    }

}
