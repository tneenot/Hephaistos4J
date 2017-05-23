/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.collection;

import org.hlib4j.concept.Cleaner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * This class consists exclusively of static methods that operate on or return collections. It contains methods that
 * return a new collection backed by a specified collection. <br>
 * <br>
 * The methods of this class all throw a <code>NullPointerException</code> if the collections or class objects provided
 * to them are <code>null</code>. <br>
 * <br>
 * These collections allow to avoid to write code like the following example. Imagine you want a collection with no
 * odd value. Usually developers write this functionality like this (pseudo-code): <br>
 * <br>
 * <p>
 * <pre>
 * For all values
 * 	if(value%2 == 0)
 * 		collection.add(value);
 * endFor
 * </pre>
 * <p>
 * To avoid to write always the same fundamental code, here developer will use these features that are waiting with a
 * filter to control forbidden values from the original collection. The role of the filter is to control each data while
 * is adding into the collection. While the rule is defining for the new kind of collection, this rule is applying for
 * each element of the collection and silently. If filter implementation accepts a valid data, it will be inserted into
 * collection, otherwise it will be rejected. So, with our example, the same functionality will write like this: <br>
 * <br>
 * <p>
 * <pre>
 * Collection&lt;Integer&gt; collectionTest = Collections.makeFilteredCollection(originalCollection, (n) -&gt; n%2 == 0 );
 * </pre>
 * <p>
 * So each time the developer adds a value like this <code>collectionTest.add(value)</code>, all values that are not even  will not be added into the collection. It's possible for the developer to work with its
 * <code>originalCollection</code> in our example. But in this case, forbidden values can be added compare to the
 * managed <code>collectionTest</code>. This choice can be made for performance reason according to the filter used
 * (e.g: a filter with a complex filter algorithm). If the developer wants to clean its <code>originalCollection </code> for all forbidden values he must to call {@link #clean(Collection)} or {@link #clean(Map)}. In this case all forbidden values
 * will be deleted from the original collection, since the <code>collectionTest</code> and <code>originalCollection</code> are linked together. Here it's another example: <br>
 * <br>
 * <p>
 * <pre>
 * Collection&lt;Integer&gt; collectionTest = Collections.makeFilteredCollection(originalCollection, (n) -&gt; n%2 == 0 );
 * ... // Some other operations
 * originalCollection.add(5);
 * int nbDeletedValues = Collections.clean(collectionTest);
 * ... // other operations
 * </pre>
 * <p>
 * This sample run a {@link #clean(java.util.Collection)} on all elements from <code>originalCollection</code> since <b>these collections are linked together</b>.
 * All <code>clean(...)</code> methods return the number of deleted elements. In our example odd values
 * were deleted from the <code>originalCollection</code>. Don't forget to set in the <code>clean(...)</code> method the
 * right collection that's using the filter effectively. If your collection doesn't contain a filter (so it's not a type
 * of the <code>collectionTest</code> in our example), all forbidden values won't be deleted from your collection.<br>
 * <br>
 * If the developer works with its <code>collectionTest</code> rather than its <code>originalCollection</code>,
 * forbidden values will be deleted automatically while an iterator will be used or a <code>toArray()</code> or
 * <code>toArray[T[])</code> methods. <br>
 * <br>
 * All these rules are valid for a <code>List</code> or a <code>Map</code>. For the <code>List</code> its
 * <code>ListIterator</code> doesn't allow to set a forbidden value in its backed collection, according to its
 * predicate definition.
 *
 * @author Tioben Neenot
 */
public class Collections
{

  /**
   * Avoid the instantiation of this class
   */
  private Collections()
  {
    // Do nothing
  }

  /**
   * Returns a collection for which all elements are managing according to the predicate definition.
   *
   * @param <ElementType>         The type of the element in the collection.
   * @param originalCollection    The original collection on which the rule will be applied.
   * @param ruleForThisCollection The predicate used to define the rule on collection elements managing.
   * @return The collection with a filter rule on collection elements.
   */
  public static <ElementType> Collection<ElementType> makeFilteredCollection(
    Collection<ElementType> originalCollection, Predicate<ElementType> ruleForThisCollection)
  {
    return new FilteredCollection<>(originalCollection, ruleForThisCollection);
  }

  /**
   * Returns a list for which all elements of this list are managing according to the predicate definition.
   *
   * @param <ElementType>   The type of the element in the list
   * @param originalList    The original list on which the rule will be applied.
   * @param ruleForThisList The predicate used to define the rule on list elements managing.
   * @return The list with a filter rule on adding elements.
   */
  public static <ElementType> List<ElementType> makeFilteredList(List<ElementType> originalList,
                                                                 Predicate<ElementType> ruleForThisList)
  {
    return new FilteredList<>(originalList, ruleForThisList);
  }

  /**
   * Returns a map for which all elements of this map are managing according to the predicate definition.
   *
   * @param <K>            The key of the map
   * @param <V>            The value type of the map
   * @param originalMap    The original map on which the rule will be applied.
   * @param ruleForThisMap The predicate used to define the rule on map elements managing..
   * @return The map with a filter rule on adding elements.
   */
  public static <K, V> Map<K, V> makeFilteredMap(Map<K, V> originalMap, Predicate<V> ruleForThisMap)
  {
    return new FilteredMap<>(originalMap, ruleForThisMap);
  }

  /**
   * Delete values from the collection that are not corresponding to a predicate definition. Values will be deleted only if the collection gets a predicate implementation.
   *
   * @param collectionToClean Collection for which all values will be removed according to its underlying rule.
   * @return The number of elements deleted, or <code>-1</code> if the collection doesn't contains the {@link org.hlib4j.concept.Cleaner}
   * definition.
   * <br><br>
   * <b>Note:</b> Each collection returned by a <code>makeFiltered...(...)</code> method implement a {@link org.hlib4j.concept.Cleaner}
   * interface. The underlying method uses this definition to apply its internal <code>Predicate</code> in all element of its collection.
   */
  public static int clean(Collection<?> collectionToClean)
  {
    if (collectionToClean instanceof Cleaner)
    {
      return cleanOn((Cleaner) collectionToClean);
    }

    return -1;
  }

  /**
   * Delete values from the map that are not corresponding to a predicate definition. Values will be deleted only if
   * the map gets a predicate implementation.
   *
   * @param mapToClean Map for which all values must be removed according to its underlying rule..
   * @return The number of elements deleted, or <code>-1</code> if the collection doesn't contains the <code>Cleaner</code> definition.
   * <br><br>
   * <b>Note:</b> Each collection returned by a <code>makeFiltered...(...)</code> method implement a {@link org.hlib4j.concept.Cleaner}
   * interface. The underlying method uses this definition to apply its internal <code>Predicate</code> in all element of its collection.
   */
  public static int clean(Map<?, ?> mapToClean)
  {
    if (mapToClean instanceof Cleaner)
    {
      return cleanOn((Cleaner) mapToClean);
    }

    return -1;
  }

  /**
   * Runs a cleaner operation on the {@link Cleaner} type.
   *
   * @param cleanerElement Element on which the cleaning will be run.
   * @return The number of elements removed.
   */
  private static int cleanOn(Cleaner cleanerElement)
  {
    return cleanerElement.clean();
  }
}
