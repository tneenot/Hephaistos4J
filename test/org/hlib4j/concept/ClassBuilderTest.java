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
public class ClassBuilderTest {
    /**
     * Test cleaning
     */
    @After
    public void tearDown() {
        File _tmp = System.getenv("TEMP") == null ? new File("/tmp") : new File(System.getenv("TEMP"));
        File[] _for_deleting;
        _for_deleting = _tmp.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().contains("DefXML");
            }
        });

        for (File f : _for_deleting) {
            if (!f.delete()) {
                f.deleteOnExit();
            }
        }
    }

    /**
     * Test of createInstance method, of class ClassBuilder.
     */
    @Test
    public void test_GetInstance_ValidInstance() {
        File xmlFile = new File("test/org/hlib4j/concept/CDef.xml");
        assertNotNull(new ClassBuilder(xmlFile));
    }

    /**
     * Test of getAllClasses method, of class ClassBuilder.
     */
    @Test
    public void test_GetAllClasses_ValidClasses() {
        ClassBuilder instance = new ClassBuilder(new File("test/org/hlib4j/concept/CDef.xml"));
        Collection<String> expResult;
        expResult = Arrays.asList(new String[]{"ClassRef", "SecondClass", "ThirdClass"});
        Collection<ClassDefinition> result = instance.getAllClasses();
        for (ClassDefinition d : result) {
            assertTrue(expResult.contains(d.getName()));
        }
    }

    /**
     * Test of createInstance method, of class ClassBuilder.
     */
    @Test
    public void test_CreateInstance_InvalidInstance() {
        ClassBuilder instance = new ClassBuilder(new File("test/org/hlib4j/concept/CDef.xml"));
        assertNull(instance.createInstance("foo"));

    }

    /**
     * Test of parse method, of class ClassBuilder.
     *
     * @throws Exception If exception test running.
     */
    @Test
    public void test_Parse_ValidResourceBundle() throws Exception {
        File result = ClassBuilder.parse(ResourceBundle.getBundle(getClass().getPackage().getName() + ".bundle_test", Locale.ENGLISH));
        assertNotNull(result);
    }

    @Test
    public void test_Parse_ResourceBundleFile_1stString() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.str.1") instanceof String);
    }

    @Test
    public void test_Parse_ResourceBundleFile_2dString() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.str.2") instanceof String);
    }

    @Test
    public void test_Parse_ResourceBundleFile_FloatType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.float") instanceof Float);
    }

    @Test
    public void test_Parse_ResourceBundleFile_DoubleType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.double") instanceof Double);
    }

    @Test
    public void test_Parse_ResourceBundleFile_IntegerType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.int") instanceof Integer);
    }

    @Test
    public void test_Parse_ResourceBundleFile_IntegerHexType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.hex") instanceof Integer);
    }

    @Test
    public void test_Parse_ResourceBundleFile_LongType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.long") instanceof Long);
    }

    @Test
    public void test_Parse_ResourceBundleFile_DateType() throws IOException, InvocationTargetException {
        ClassDefinition _ci = getClassDefinition();
        assertTrue(_ci.getPropertyValue("value.date") instanceof Date);
    }

    private ClassDefinition getClassDefinition() throws IOException {
        ClassBuilder _cb = new ClassBuilder(ClassBuilder.parse(ResourceBundle.getBundle(getClass().getPackage().getName() + ".bundle_test", Locale.ENGLISH)));
        return _cb.createInstance("Bundle");
    }

}
