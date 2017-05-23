/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Units tests for Property class
 *
 * @author Tioben Neenot
 * @see Property
 */
public class PropertyTest
{

  /**
   * Read only property
   */
  private Property readOnly = null;
  /**
   * Property read/write
   */
  private Property readWrite = null;

  /**
   * Test initialization
   *
   * @throws Exception If initialization failed
   */
  @Before
  public void setUp() throws Exception
  {
    readOnly = new Property("PropertyRead", true);
    readWrite = new Property("PropertyWrite");
  }

  /**
   * Test cleaning
   *
   * @throws Exception If initialisation failed
   */
  @After
  public void tearDown() throws Exception
  {
    readOnly = readWrite = null;
  }

  /**
   * Unit test <code>getName()</code>.
   * <ul>
   * <li>Description: Control the default name.</li>
   * <li>Result: default name conforms to the model.</li>
   * <li>Comment: none.</li>
   * </ul>
   */
  @Test
  public final void test_GetName_ValidName_ValidAwaitingName()
  {
    Assert.assertEquals("PropertyRead", readOnly.getName());
  }

  /**
   * Unit test <code>getValue()</code>.
   * <ul>
   * <li>Description: control the default value.</li>
   * <li>Result: default value conforms.</li>
   * <li>Comment: value is <code>null</code>.</li>
   * </ul>
   */
  @Test
  public final void test_GetValue_NullValue_AwaitingNullValue()
  {
    Assert.assertNull(readWrite.getValue());
  }

  /**
   * Unit test <code>SetValueReadOnly()</code>.
   * <ul>
   * <li>Description: sets a value for a readonly property.</li>
   * <li>Result: an exception may occur.</li>
   * <li>Comment: none.</li>
   * </ul>
   *
   * @throws UnsupportedOperationException Exception awaiting for the test
   */
  @Test(expected = UnsupportedOperationException.class)
  public final void test_SetValue_ReadOnly_UnsupportedOperationException() throws UnsupportedOperationException
  {
    readOnly.setValue("Toto");
  }

  /**
   * Unit test <code>setValueReadWrite()</code>.
   * <ul>
   * <li>Description: Set a value for a read/write property.</li>
   * <li>Result: value updates.</li>
   * <li>Comment: none.</li>
   * </ul>
   *
   * @throws UnsupportedOperationException If set a readonly value.
   */
  @Test
  public final void test_SetValue_ReadWrite_ValueUpdated() throws UnsupportedOperationException
  {
    readWrite.setValue("Toto");

    Assert.assertEquals("Toto", readWrite.getValue());
  }

  /**
   * Unit test <code>equalsSameObject()</code>.
   * <ul>
   * <li>Description: Compare 2 same properties.</li>
   * <li>Result: properties are equals.</li>
   * <li>Comment: none.</li>
   * </ul>
   */
  @Test
  public final void test_Equals_SameInstanceType_Equals()
  {
    Property read = new Property("PropertyRead", true);

    Assert.assertTrue(readOnly.equals(read));
  }

  /**
   * Unit test <code>isReadOnlyTrue()</code>.
   * <ul>
   * <li>Description: Control that a property as readonly is really readonly.</li>
   * <li>Result: property is readonly.</li>
   * <li>Comment: none.</li>
   * </ul>
   */
  @Test
  public final void test_isReadOnly_ValidReadOnly_ReadOnly()
  {
    Assert.assertTrue(new Property("Toto", true).isReadOnly());
  }

  /**
   * Unit test <code>isReadOnlyReadWrite()</code>.
   * <ul>
   * <li>Description: Control a property as read/write is really read/write.</li>
   * <li>Result: property is read/write.</li>
   * <li>Comment: none.</li>
   * </ul>
   */
  @Test
  public final void test_isReadOnly_InvalidReadOnly_NotReadOnly()
  {
    Assert.assertFalse(new Property("Toto", false).isReadOnly());
  }

  @Test(expected = IllegalArgumentException.class)
  public final void test_Constructor_NullName_IllegalArgumentException()
  {
    new Property(null, false);
  }

  @Test
  public final void test_hashCode_IsValidHashCode_HashCodeValid()
  {
    Assert.assertNotEquals(0, new Property("foo", true).hashCode());
  }

  @Test
  public final void test_equals_WithObject_NotEquals()
  {
    Assert.assertNotEquals(new Object(), new Property("foo", false));
  }

  @Test
  public final void test_toString_ControlDescription_RightDescription()
  {
    // Setup
    Property _property = new Property("foo", 1, true);

    Assert.assertEquals("foo=1 (isReadOnly:true)", _property.toString());
  }
}
