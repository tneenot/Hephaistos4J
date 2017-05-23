package org.hlib4j.concept;
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

import org.hlib4j.util.States;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a class with its properties. The class has a name and a list of properties. If the developer gets an invalid
 * property by its name an exception is occurring.
 * <p>
 * <h2>Concept</h2>
 * This class allows to define any kind of property type to centralize them under the same concept.
 * Rather than to defines several sub-classes with specific properties, if yours sub-classes can be resume to a single
 * concept where only properties are variations, so that means at the final, only one class is necessary.
 * <p>
 * In this case the  * <code>ClassDefinition</code> allows to avoid several sub-classes to group them under the same concepts, where
 * properties can be used from the <code>getProperty(String)</code>. The developer can known all available properties with the
 * <code>getProperties()</code>, but is not possible to add or remove all available properties from this method.
 * <p>
 * <h2>The list of user properties</h2>
 * The list of properties are defining in a Map of properties. The field <code>properties</code> is protected to allow the developer
 * to define himself the policy to set for all available properties.
 * <p>
 * This class offers the same exception as if the developer uses the reflection capability to find a valid property. To
 * set a value to a property, the developer uses the <code>setProperty(String, Object)</code>. But an exception can be occurred
 * if the property is read only while a value is setting to. See the {@link Property} documentation to know if a property is
 * read only or not.
 * <p>
 * <h2>Dynamic value type</h2>
 * Any kind of value can be set to a property since the value is an <code>Object</code> type. That means it's possible to
 * change dynamically the original value type for the property. It's another aspect for this class: you can have a less value
 * type control for your property. In some situations that can be more interesting to have a property that can accept any
 * kind of value rather than a specific value like a classical class definition. This is the case for an application that
 * manages a script variable definition for example. The other advantage for this <code>Object</code> type is to use a
 * general concept for your property value, if the value type has no sense for your implementation.
 *
 * @author Tioben Neenot
 * @see Property
 */
public abstract class ClassDefinition
{

  /**
   * Properties list for user properties.
   */
  protected final Map<String, Property> properties = new HashMap<>();

  /**
   * Sets a property value. If a value can't be set or if the property name doesn't exist, an exception is occurs.
   *
   * @param name  Property name
   * @param value Value of the property
   * @throws IllegalArgumentException      If the property name is null or empty
   * @throws InvocationTargetException     If the property name doesn't exist.
   * @throws UnsupportedOperationException If a value sets a read only property
   */
  public void setPropertyValue(String name, Object value) throws IllegalArgumentException, InvocationTargetException,
    UnsupportedOperationException
  {
    // Control the argument validity
    properties.get(ControlArgumentValidity(name)).setValue(value);
  }


  /**
   * Gets a property value. If the property name doesn't exist, an exception is occurs.
   *
   * @param name Name of the property
   * @return Value of the property
   * @throws IllegalArgumentException  If the property name is null or empty
   * @throws InvocationTargetException If the property name doesn't exist
   */
  public Object getPropertyValue(String name) throws IllegalArgumentException, InvocationTargetException
  {
    return properties.get(ControlArgumentValidity(name)).getValue();
  }

  private String ControlArgumentValidity(String name) throws InvocationTargetException
  {
    // Control the argument validity
    try
    {
      isPropertyExists(States.validate(name));
    } catch (AssertionError e)
    {
      throw new IllegalArgumentException("Property name can't be null or empty");
    }

    return name;
  }


  private void isPropertyExists(String name) throws InvocationTargetException
  {
    if (properties.containsKey(name) == false)
    {
      throw new InvocationTargetException(new Throwable("Unknown property" + name));
    }
  }

  /**
   * Gets all properties. The collection doesn't allow to add or remove the properties, but each property can be
   * modified according to if it's read only or not.
   *
   * @return The list of all properties
   */
  public Collection<Property> getProperties()
  {
    return Collections.unmodifiableCollection(properties.values());
  }

  /**
   * Returns the class name
   *
   * @return The class name
   */
  public abstract String getName();

  /**
   * Gets the class definition description
   *
   * @return Class definition description
   */
  @Override
  public String toString()
  {
    return getName();
  }

}
