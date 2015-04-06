package org.hlib4j.util;

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

    public void generateValues(int NbElements) {
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

    public int getOnceValue() {
        return getOnceValueFrom(this.randomElements);
    }

    public int getOnceValueFrom(List<Integer> elements) {
        Random _random = getRandom();

        int _index = _random.nextInt(elements.size());

        return elements.get(_index);
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
                _sub_list.add(getOnceValue());
            }
        }

        return _sub_list;
    }
}
