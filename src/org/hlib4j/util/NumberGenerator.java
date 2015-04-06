package org.hlib4j.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * This class is using to generate a list of values. These values are generated with random values.
 * Created by tneenot on 06/04/15.
 */
public class NumberGenerator {

    // This counter is using to force to build 2 real different lists during 2 consequent calls.
    private static long counterCalling = Long.MIN_VALUE;

    public static List<Integer> generateValues(int NbElements) {
        ++counterCalling;

        long _seed_value = Calendar.getInstance().getTimeInMillis();
        Random _random = new Random(_seed_value + counterCalling);

        List<Integer> _elements_list = new ArrayList<>();
        for (int i = 0; i < NbElements; ++i)
            _elements_list.add(_random.nextInt());

        return _elements_list;
    }
}
