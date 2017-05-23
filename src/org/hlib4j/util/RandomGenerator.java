/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * This class is using to generate a list of values. These values are generated with random values
 */
public class RandomGenerator
{

  // This counter is using to force to build 2 real different lists during 2 consequent calls.
  private static long counterCalling = Long.MIN_VALUE;

  private List<Integer> randomElements = null;

  /**
   * Builds an instance of RandomGenerator.
   */
  public RandomGenerator()
  {
    this.randomElements = new ArrayList<>();
  }

  /**
   * Generate random values for the number of elements given as parameter.
   *
   * @param NbElements Number of random values to generate
   */
  public void generateRandomValues(int NbElements)
  {
    Random _random = getRandom();

    this.randomElements.clear();
    this.randomElements = new ArrayList<>();
    for (int i = 0; i < NbElements; ++i)
      this.randomElements.add(_random.nextInt());
  }

  private Random getRandom()
  {
    ++counterCalling;

    long _seed_value = Calendar.getInstance().getTimeInMillis();
    return new Random(_seed_value + counterCalling);
  }

  /**
   * Gets a new value from the internal random generator. This values doesn't correspond to those generated throw {@link
   * #generateRandomValues(int)}. If is the case, is only due to hazard !
   *
   * @return A specific random new value.
   */
  public int getIsolatedValue()
  {
    return getRandom().nextInt();
  }

  /**
   * Gets only one value from those generated with {@link #generateRandomValues(int)}.
   *
   * @return A value from internal generated values
   */
  public int getOnceValue()
  {
    return this.getOnceValueFrom(this.randomElements);
  }

  /**
   * Gets only a value from the list of values given as parameter. The choice of this value is based only random index given with
   * {@link #getOnceIndexFrom(List)}.
   *
   * @param elements List of values
   * @return One value from the list of values.
   */
  public int getOnceValueFrom(List<Integer> elements)
  {

    return elements.get(getOnceIndexFrom(elements));
  }

  /**
   * Gets a random index from the list of elements given as parameter.
   *
   * @param elements List of element.
   * @return A random index from the list of elements.
   */
  public int getOnceIndexFrom(List<Integer> elements)
  {
    try
    {
      States.validate(elements);
    } catch (AssertionError e)
    {
      return -1;
    }

    Random _random = getRandom();

    return _random.nextInt(elements.size());
  }

  /**
   * Gets a random index from the internal list of random values.
   *
   * @return A random index from the internal list of random values.
   */
  public int getOnceIndex()
  {
    return this.getOnceIndexFrom(this.randomElements);
  }

  /**
   * Gets the list of random elements generated with {@link #generateRandomValues(int)}.
   *
   * @return List of random elements.
   */
  public List<Integer> getRandomElements()
  {
    return this.randomElements;
  }

  /**
   * Gets a sub-list from the random elements generated with {@link #generateRandomValues(int)}.
   *
   * @param nbSubListElements Number of elements for the sub-list.If the number of elements for the sub-list is greater than the number of
   *                          elements of the internal list, the sub-list will be empty.
   * @return The sub-list that's coming from the internal random elements list.
   */
  public List<Integer> getSubList(int nbSubListElements)
  {
    return getSubListFromList(this.getRandomElements(), nbSubListElements);
  }

  /**
   * Gets a sub-list from the list given as parameter. If the number of elements for the sub-list is greater than the number of
   * elements of the source list, the sub-list will be empty.
   *
   * @param referenceList     The list from which the sub-list will be build.
   * @param nbSubListElements The number of elements for the sub-list.
   * @return The sub-list according to the number of elements of the sub-list.
   */
  public List<Integer> getSubListFromList(List<Integer> referenceList, int nbSubListElements)
  {
    List<Integer> _sub_list = new ArrayList<>();

    if (nbSubListElements >= 0 && nbSubListElements < referenceList.size())
    {
      for (int i = 0; i < nbSubListElements; ++i)
      {
        _sub_list.add(getOnceValueFrom(referenceList));
      }
    }

    return _sub_list;
  }
}
