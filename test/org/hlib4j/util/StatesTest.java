/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2016 Tioben Neenot
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
 */

package org.hlib4j.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Unit tests for {@link org.hlib4j.util.StatesTest} class.
 *
 * @author Tioben Neenot
 */
public class StatesTest
{

  @Test
  public void test_IsNullOrEmptyArray_OnNullValue_NullValueConfirmed()
  {
    Assert.assertTrue(States.isNullOrEmptyArray(null));
  }

  @Test
  public void test_IsNullOrEmpty_OnEmptyStringNaturalForm_StringEmpty()
  {
    Assert.assertTrue(States.isNullOrEmpty(""));
  }

  @Test
  public void test_IsNullOrEmpty_OnEmptyStringVariantForm_StringEmpty()
  {
    Assert.assertTrue(States.isNullOrEmpty(" "));
  }

  @Test
  public void test_IsNullOrEmpty_OnNonNullObject_ObjectNotNull()
  {
    Assert.assertFalse(States.isNullOrEmpty(new Object()));
  }

  @Test
  public void test_IsNullOrEmpty_OnEmptyCollection_EmptyCollection()
  {
    Collection<Object> c = new ArrayList<>();
    Assert.assertTrue(States.isNullOrEmpty(c));
  }

  @Test
  public void test_NullOrEmptyArray_OnNullBoolean_NullBooleanConfirmed()
  {
    Boolean[] _boolean_array = null;
    Assert.assertTrue(States.isNullOrEmptyArray(_boolean_array));
  }

  @Test
  public void test_NullOrEmptyArray_OnEmptyBooleanArray_EmptyArrayConfirmed()
  {
    Boolean[] _boolean_array = {};
    Assert.assertTrue(States.isNullOrEmptyArray(_boolean_array));
  }

  @Test
  public void test_Validate_OnSameInstances_SameInstancesConfirmed()
  {
    Date d = Calendar.getInstance().getTime();
    Date r = States.validate(d);

    Assert.assertSame(d, r);
  }

  @Test
  public void test_Validate_OnEqualsInstances_EqualInstancesConfirmed()
  {
    Date d = Calendar.getInstance().getTime();
    Date r = States.validate(d);

    Assert.assertEquals(d, r);
  }

  @Test
  public void test_Validate_PrimaryIntType_ValueEquals()
  {
    int i = Integer.MAX_VALUE;
    int r = States.validate(i);

    Assert.assertEquals(r, i);
  }

  @Test
  public void test_Validate_PrimaryArrayType_ArraysEquals()
  {
    int[] i =
      {
        1, 2, 3, 4
      };
    int[] r = States.validate(i);

    Assert.assertArrayEquals(r, i);
  }

  @Test(expected = AssertionError.class)
  public void test_Validate_NullArray_AssertionError()
  {
    int[] i = null;
    States.validate(i);
  }

  @Test(expected = AssertionError.class)
  public void test_ValidateArray_EmptyArray_AssertionError()
  {
    Integer[] i = {};
    States.validateArray(i);
  }

  @Test(expected = AssertionError.class)
  public void test_Validate_NullValue_AssertionError()
  {
    States.validate(null);
  }

  @Test
  public void test_ValidateNotNullOnly_OnEmptyArray_ArraysValidate() throws Exception
  {
    Object[] o = {};
    Assert.assertArrayEquals(o, States.validateNotNullOnly(o));
  }

  @Test
  public void test_ValidateNotNullOnly_NoNullInteger_ValueValidate() throws Exception
  {

    Integer i = 4;
    Assert.assertEquals(i, States.validateNotNullOnly(i));
  }


  @Test
  public void test_ValidateOrReplace_NullStringElement_ElementReplaced()
  {
    String ref_element = null;
    String replace_element = "Foo";

    Assert.assertEquals(replace_element, States.validateOrReplace(ref_element, replace_element));
  }

  @Test
  public void test_validateArray_NoNullOrEmptyArray_SameArray()
  {
    Integer[] integer_array = {1, 2, 3, 4, 1};
    Assert.assertArrayEquals(integer_array, States.validateArray(integer_array));
  }

  @Test(expected = AssertionError.class)
  public void test_validateNotNullOnly_NullValue_Exception()
  {
    States.validateNotNullOnly(null);
  }
}
