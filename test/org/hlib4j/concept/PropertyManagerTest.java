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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Unit tests for the <code>ClassBuilder</code> class
 *
 * @author Tioben Neenot
 * @see ClassBuilder
 */
public class PropertyManagerTest {

    /**
     * Class reference for test
     */
    private ClassBuilder instance = null;

    /**
     * Reference directory for current tests
     */
    private String refDirectory = "test/org/hlib4j/concept/CDef.xml";

    /**
     * Test initialization
     */
    @Before
    public void setUp() {
        instance = new ClassBuilder(new File(refDirectory));
    }

    /**
     * Test cleaning
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of createInstance method, of class ClassBuilder.
     */
    @Test
    public void test_Constructor_ValidDirectory() {
        assertNotNull(new ClassBuilder(new File(refDirectory)));
    }

    /**
     * Test of createInstance method, of class ClassBuilder.
     */
    @Test
    public void test_CreateInstance_ValidName_NotNull() {
        String name = "ClassRef";
        ClassDefinition result = instance.createInstance(name);
        assertNotNull(result);
    }

    /**
     * Test of createInstance method, of class ClassBuilder.
     */
    @Test
    public void test_CreateInstance_InvalidName_Null() {
        String name = "Toto";
        assertNull(instance.createInstance(name));
    }

    @Test
    public void test_IsReadOnly_ControlPropertiesReadOnly_True() throws IllegalArgumentException, InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ClassRef");

        for (Property p : properties.getProperties()) {
            if ("property_name".equals(p.getName())) {
                assertTrue(p.isReadOnly());
            }
        }
    }

    @Test
    public void test_IsReadOnly_ControlPropertiesNotReadOnly_False() throws IllegalArgumentException, InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ClassRef");

        for (Property p : properties.getProperties()) {
            if ("prop_read_write".equals(p.getName())) {
                assertFalse(p.isReadOnly());
            }
        }
    }


    /**
     * Test of getAllClasses method, of class ClassBuilder.
     */
    @Test
    public void test_GetAllClasses_ValidClasses_True() {
        Collection<ClassDefinition> result = instance.getAllClasses();
        for (ClassDefinition c : result) {
            assertTrue(c.getName().equals("ClassRef") || c.getName().equals("SecondClass") || c.getName().equals("ThirdClass"));
        }
    }

    @Test
    public void test_GetPropertyValue_StringType_NaturalForm_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.str") instanceof String);
    }

    @Test
    public void test_GetPropertyValue_StringType_VariantForm_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.str2") instanceof String);
    }

    @Test
    public void test_GetPropertyValue_FloatType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.float") instanceof Float);
    }

    @Test
    public void test_GetPropertyValue_DoubleType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.double") instanceof Double);
    }

    @Test
    public void test_GetPropertyValue_IntegerType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.int") instanceof Integer);
    }

    @Test
    public void test_GetPropertyValue_IntegerHexType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.hex") instanceof Integer);
    }

    @Test
    public void test_GetPropertyValue_DateType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.date") instanceof Date);
    }

    @Test
    public void test_GetPropertyValue_LongType_True() throws InvocationTargetException {
        ClassDefinition properties = instance.createInstance("ThirdClass");
        assertTrue(properties.getPropertyValue("value.long") instanceof Long);
    }

    /**
     * Test of getName method, of class PropertyManager.
     *
     * @throws InstantiationException If error during test running.
     */
    @Test
    public void test_GetName_ValidName_Equals() throws InstantiationException {
        System.out.println("getName");
        PropertyManager _instance = new PropertyManager("foo");
        assertEquals("foo", _instance.getName());
    }

    /**
     * Test of Add method, of class PropertyManager.
     *
     * @throws InstantiationException If error during test running.
     */
    @Test
    public void test_Add_ValidProperty_NoError() throws InstantiationException {
        System.out.println("Add");
        PropertyManager _instance = new PropertyManager("foo");
        _instance.Add(new Property("bar"));
    }
}
