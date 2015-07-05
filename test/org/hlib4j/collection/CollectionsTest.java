package org.hlib4j.collection;
/*
*  Hephaistos 4 Java library: a library with facilities to get more concise code.
*  
*  Copyright (C) 2015 Tioben Neenot
*  
*  This program is free software; you can redistribute it and/or modify it under
*  the terms of the GNU General Public License as published by the Free Software
*  Foundation; either version 2 of the License, or (at your option) any later
*  version.
*  
*  This program is distributed in the hope that it will be useful, but WITHOUT
*  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
*  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
*  details.
*  
*  You should have received a copy of the GNU General Public License along with
*  this program; if not, write to the Free Software Foundation, Inc., 51
*  Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*  
*/

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link java.util.Collections} class.
 *
 * @author Tioben Neenot
 */
public class CollectionsTest {

    @Test
    public void test_Clean_OnFilteredCollection_CleaningCollection() {
        Collection<Integer> _col = new ArrayList<>();
        Collection<?> _sub_col = Collections.makeFilteredCollection(_col, new Not<Integer>(null));
        _col.add(null);

        assertEquals(1, Collections.clean(_sub_col));
    }

    @Test
    public void test_Clean_OnStandardCollection_NotCleaningCollection() {
        Collection<Integer> _col = new ArrayList<>();
        _col.add(2);

        assertEquals(-1, Collections.clean(_col));
    }

    @Test
    public void test_Clean_OnFilteredMap_CleaningMap() {
        Map<String, Integer> _map = new HashMap<>();
        Map<?, ?> _sub_map = Collections.makeFilteredMap(_map, new Not<Integer>(null));
        _map.put("test", null);

        assertEquals(1, Collections.clean(_sub_map));
    }

    @Test
    public void test_Clean_OnClassicalMap_NotCleaningMap() {
        Map<Integer, String> _map = new HashMap<>();
        _map.put(Integer.SIZE, "Hi");

        assertEquals(-1, Collections.clean(_map));
    }
}
