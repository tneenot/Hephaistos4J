/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Tioben Neenot
 */
public class ClassBuilderTest
{
  /**
   * Test cleaning
   */
  @After
  public void tearDown()
  {
    File _tmp = System.getenv("TEMP") == null ? new File("/tmp") : new File(System.getenv("TEMP"));
    File[] _for_deleting;
    _for_deleting = _tmp.listFiles(new FileFilter()
    {

      @Override
      public boolean accept(File pathname)
      {
        return pathname.getAbsolutePath().contains("DefXML");
      }
    });

    for (File f : _for_deleting)
    {
      if (!f.delete())
      {
        f.deleteOnExit();
      }
    }
  }

  /**
   * Test of createInstance method, of class ClassBuilder.
   */
  @Test
  public void test_GetInstance_ValidInstance()
  {
    File xmlFile = new File("test/org/hlib4j/concept/CDef.xml");
    assertNotNull(new ClassBuilder(xmlFile));
  }

  /**
   * Test of getAllClasses method, of class ClassBuilder.
   */
  @Test
  public void test_GetAllClasses_ValidClasses()
  {
    ClassBuilder instance = new ClassBuilder(new File("test/org/hlib4j/concept/CDef.xml"));
    Collection<String> expResult;
    expResult = Arrays.asList("ClassRef", "SecondClass", "ThirdClass");
    Collection<ClassDefinition> result = instance.getAllClasses();
    for (ClassDefinition d : result)
    {
      assertTrue(expResult.contains(d.getName()));
    }
  }

  /**
   * Test of createInstance method, of class ClassBuilder.
   */
  @Test
  public void test_CreateInstance_InvalidInstance_NotCreated()
  {
    ClassBuilder instance = new ClassBuilder(new File("test/org/hlib4j/concept/CDef.xml"));
    assertNull(instance.createInstance("foo"));

  }

  /**
   * Test of parse method, of class ClassBuilder.
   *
   * @throws Exception If exception test running.
   */
  @Test
  public void test_Parse_ValidResourceBundle_ResourceFileCreated() throws Exception
  {
    File result = ClassBuilder.parse(ResourceBundle.getBundle(getClass().getPackage().getName() + ".bundle_test", Locale.ENGLISH));
    assertNotNull(result);
  }

  @Test
  public void test_Parse_ResourceBundleFile_1stParameterString_StringTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.str.1") instanceof String);
  }

  @Test
  public void test_Parse_ResourceBundleFile_2dParameterString_StringTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.str.2") instanceof String);
  }

  @Test
  public void test_Parse_ResourceBundleFile_FloatTypeRef_FloatTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.float") instanceof Float);
  }

  @Test
  public void test_Parse_ResourceBundleFile_DoubleTypeRef_DoubleTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.double") instanceof Double);
  }

  @Test
  public void test_Parse_ResourceBundleFile_IntegerTypeRef_IntegerTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.int") instanceof Integer);
  }

  @Test
  public void test_Parse_ResourceBundleFile_IntegerHexRef_IntegerTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.hex") instanceof Integer);
  }

  @Test
  public void test_Parse_ResourceBundleFile_LongTypeRef_LongTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.long") instanceof Long);
  }

  @Test
  public void test_Parse_ResourceBundleFile_DateTypeRef_DateTypeFound() throws IOException, InvocationTargetException
  {
    ClassDefinition _ci = getClassDefinition();
    assertTrue(_ci.getPropertyValue("value.date") instanceof Date);
  }

  private ClassDefinition getClassDefinition() throws IOException
  {
    ClassBuilder _cb = new ClassBuilder(ClassBuilder.parse(ResourceBundle.getBundle(getClass().getPackage().getName() + ".bundle_test", Locale.ENGLISH)));
    return _cb.createInstance("Bundle");
  }

}
