/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Units tests for the <code>PropertyManager</code> class.
 *
 * @author Tioben Neenot
 * @see PropertyManager
 */
public class ClassImplementationTest
{

  /**
   * Instance class reference
   */
  private PropertyManager instance = null;

  /**
   * Test initialization
   *
   * @throws InstantiationException Exception that must not be ran for the test must be available.
   */
  @Before
  public void setUp() throws InstantiationException
  {
    instance = new PropertyManager("ClassRef");
  }

  /**
   * Test cleaning
   */
  @After
  public void tearDown()
  {
    instance = null;
  }


  /**
   * Test of the PropertyManager constructor
   *
   * @throws InstantiationException Exception that must not be ran for the test must be available.
   */
  @Test(expected = InstantiationException.class)
  public void test_Constructor_NullName_InstantiationException() throws InstantiationException
  {
    new PropertyManager(null);
  }

  /**
   * Test of the PropertyManager constructor
   *
   * @throws InstantiationException Exception that must not be ran for the test must be available.
   */
  @Test(expected = InstantiationException.class)
  public void test_Constructor_EmptyNameWithNaturalForm_InstantiationException() throws InstantiationException
  {
    new PropertyManager("");
  }

  /**
   * Test of the PropertyManager constructor
   *
   * @throws InstantiationException Awaiting exception
   */
  @Test(expected = InstantiationException.class)
  public void test_Constructor_EmptyNameWithVariantForm_InstantiationException() throws InstantiationException
  {
    new PropertyManager("  ");
  }

  /**
   * Test of the PropertyManager constructor
   *
   * @throws InstantiationException Exception that must not be ran for the test must be available.
   */
  @Test(expected = InstantiationException.class)
  public void test_Constructor_WithSpaceInName_InstantiationException() throws InstantiationException
  {
    new PropertyManager("Class Ref");
  }

  /**
   * Test of getName method, of class PropertyManager.
   */
  @Test
  public void test_GetName_ForValidName_ValidDescriptionName()
  {
    String expResult = "ClassRef";
    String result = instance.getName();
    assertEquals(expResult, result);
  }

  /**
   * Test of Add method, of class PropertyManager.
   *
   * @throws java.lang.reflect.InvocationTargetException Exception that must not be ran for the test must be available.
   */
  @Test
  public void test_Add_ValidPropertyValue_AddedProperty() throws IllegalArgumentException, InvocationTargetException
  {
    Property p = new Property("prop", 1, false);
    instance.Add(p);

    assertNotNull(instance.getPropertyValue("prop"));
  }
}
