package org.hlib4j.util;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * This class is using to generate a list of values. These values are generated with random values.
 * Created by tneenot on 06/04/15.
 */
public class RandomGenerator {

    // This counter is using to force to build 2 real different lists during 2 consequent calls.
    private static long counterCalling = Long.MIN_VALUE;

    private List<Integer> randomElements = null;

    public RandomGenerator() {
        this.randomElements = new ArrayList<>();
    }

    public void generateRandomValues(int NbElements) {
        Random _random = getRandom();

        this.randomElements.clear();
        this.randomElements = new ArrayList<>();
        for (int i = 0; i < NbElements; ++i)
            this.randomElements.add(_random.nextInt());
    }

    private Random getRandom() {
        ++counterCalling;

        long _seed_value = Calendar.getInstance().getTimeInMillis();
        return new Random(_seed_value + counterCalling);
    }

    public int getIsolatedValue() {
        return getRandom().nextInt();
    }

    public int getOnceValue() {
        return this.getOnceValueFrom(this.randomElements);
    }

    public int getOnceValueFrom(List<Integer> elements) {

        return elements.get(getOnceIndexFrom(elements));
    }

    public int getOnceIndexFrom(List<Integer> elements) {
        try {
            States.validate(elements);
        } catch (AssertionError e) {
            return -1;
        }

        Random _random = getRandom();

        return _random.nextInt(elements.size());
    }

    public int getOnceIndex() {
        return this.getOnceIndexFrom(this.randomElements);
    }

    public List<Integer> getRandomElements() {
        return this.randomElements;
    }

    public List<Integer> getSubList(int nbSubListElements) {
        return getSubListFromList(this.getRandomElements(), nbSubListElements);
    }

    public List<Integer> getSubListFromList(List<Integer> referenceList, int nbSubListElements) {
        List<Integer> _sub_list = new ArrayList<>();

        if (nbSubListElements >= 0 && nbSubListElements < referenceList.size()) {
            for (int i = 0; i < nbSubListElements; ++i) {
                _sub_list.add(getOnceValueFrom(referenceList));
            }
        }

        return _sub_list;
    }
}
