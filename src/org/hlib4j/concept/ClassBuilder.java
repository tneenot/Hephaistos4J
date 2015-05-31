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
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This factory create a class according to a name that's corresponding into a XML definition. It's possible to know all
 * available classes by using the <code>getAllClasses()</code> method.
 *
 * <h2>Description</h2>
 * Classes definitions are specifying in a XML file whose format contents is describing here after.
 *
 * <h2>XML file format</h2>
 * The XML file format is defining like following: <br>
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;classes&gt;
 *  &lt;!-- Class definition --&gt;
 *   &lt;class name="ClassRef"&gt;
 *     &lt;!-- Properties definition --&gt;
 *     &lt;properties&gt; &lt;!-- readonly property type with default value --&gt;
 *      &lt;property name="property_name" readonly="true"&gt;
 *       Property value &lt;/property&gt;
 *       &lt;!-- read/write property type with default value --&gt;
 *        &lt;property name="prop_read_write" readonly="false"&gt;
 *         Property value read write
 *        &lt;/property&gt;
 *        &lt;!-- read/write property type with no value --&gt;
 *        &lt;property name="prop_read_write_no_value" readonly ="false"/&gt;
 *     &lt;/properties&gt;
 *  &lt;/class&gt;
 * &lt;/classes&gt;
 * </pre>
 *
 * This XML file is loading with <code>ClassBuilder</code> that allows to create an instance of {@link ClassDefinition}
 * type according to a class name that's corresponding to XML description file. The {@link ClassDefinition} instance is
 * created with {@link #createInstance(java.lang.String)} method. If an invalid name or non existent name is given as
 * parameter, this method will return <code>null</code> value.
 *
 * @author Tioben Neenot
 */
public class ClassBuilder {

    /**
     * XML class definition
     */
    private final File xmlFileClassDef;

    /**
     * Create an instance of the <code>ClassBuilder</code> class.
     *
     * @param xmlFileClassDef XML file associated with this instance.
     */
    public ClassBuilder(File xmlFileClassDef) {
        this.xmlFileClassDef = xmlFileClassDef;
    }

    /**
     * Parse a ResourceBundle content to a XML file according to {@link ClassBuilder} definition. XML file will be stored
     * into the temp directory. The class name given as root XML elements is the name: "Bundle".
     *
     * @param bundle Resource bundle to parse to a XML file.
     * @return XML file parsed from the ResourceBundle.
     * @throws java.io.IOException If error during XML file creating.
     */
    public static File parse(ResourceBundle bundle) throws IOException {
        return parse(bundle, new File(System.getenv("TEMP") == null ? "/tmp" : System.getenv("TEMP")));
    }

    /**
     * Parse a ResourceBundle content to a XML file according to {@link ClassBuilder} definition. File will be stored into
     * the directory path. The class name given as root XML elements is the name: "Bundle".
     *
     * @param bundle Resource bundle to parse to a XML file.
     * @param dir    Target directory into the XML file will be parsed.
     * @return XML file parsed from the ResourceBundle.
     * @throws java.io.IOException If error during XML file creating.
     */
    private static File parse(ResourceBundle bundle, File dir) throws IOException {
        File _xml_file = new File(dir.getAbsolutePath() + File.separator + "DefXML" + System.nanoTime() + ".tmp");
        try (FileWriter _xml_writer = new FileWriter(_xml_file)) {
            _xml_writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            _xml_writer.write("<classes>");
            _xml_writer.write("<class name=\"Bundle\">");
            _xml_writer.write("<properties>");

            // Gets all elements from ResourceBundle and write them into XML format.
            Enumeration<String> _keys = bundle.getKeys();
            while (_keys.hasMoreElements()) {
                String _key = _keys.nextElement();
                _xml_writer.write("<property name=\"" + _key + "\" readonly=\"false\">" + bundle.getString(_key) + "</property>");
            }
            _xml_writer.write("</properties>");
            _xml_writer.write("</class>");
            _xml_writer.write("</classes>");
        }

        return _xml_file;
    }

    /**
     * Gets all classes.
     *
     * @return Classes collection for all existent classes.
     */
    public Collection<ClassDefinition> getAllClasses() {
        Collection<ClassDefinition> _classes_list = new ArrayList<>();

        try {
            NodeList _classes_node = getClasses();
            for (int i = 0; i < _classes_node.getLength(); ++i) {
                Node _class_node = _classes_node.item(i);
                _classes_list.add(getClassDefinitionFor(_class_node, getClassNameFor(_class_node)));
            }
        } catch (IllegalArgumentException | DOMException | InstantiationException | IOException | SAXException | ParserConfigurationException ex) {
            Logger.getLogger(ClassBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return _classes_list;
    }

    /**
     * Create an instance of the <code>ClassDefinition</code> according to the given name.
     *
     * @param className Class name
     * @return the ClassDefinition with all properties for the class name, or <code>null</code> if the class doesn't
     * exist.
     */
    public ClassDefinition createInstance(String className) {
        try {
            // Gets all classes
            NodeList _class_nodes = getClasses();
            for (int i = 0; i < _class_nodes.getLength(); ++i) {
                // Search the class name corresponding to the given parameter
                Node _class_node = _class_nodes.item(i);
                ClassDefinition _def = getClassDefinitionFor(_class_node, className);
                if (_def != null) {
                    return _def;
                }
            }
        } catch (InstantiationException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ClassBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ClassDefinition not found. So return null.
        return null;
    }

    /**
     * Gets the class definition for the given class name.
     *
     * @param classNode Class node list to explore
     * @param className Class to find
     * @return The ClassDefinition corresponding to the className, or <code>null</code> if className not found
     * @throws IllegalArgumentException If invalid argument
     * @throws DOMException             XML error
     * @throws InstantiationException   Class name is not found into XML class definition file.
     */
    private ClassDefinition getClassDefinitionFor(Node classNode, String className) throws IllegalArgumentException,
            DOMException, InstantiationException {
        ClassDefinition _class_definition = null;

        String _class_name = getClassNameFor(classNode);
        if (_class_name.equals(className)) {
            // Build the class definition awaiting
            _class_definition = new PropertyManager(_class_name);

            // Gets all properties
            NodeList _properties = classNode.getChildNodes();
            for (int j = 0; j < _properties.getLength(); ++j) {
                // For each property get all parameters
                NodeList _property_list = _properties.item(j).getChildNodes();
                for (int k = 0; k < _property_list.getLength(); ++k) {
                    // Variables for all properties elements
                    String _property_name = null;
                    Object _value = parse(_property_list.item(k).getTextContent().trim());
                    boolean _read_only = false;
                    NamedNodeMap _attributes = _property_list.item(k).getAttributes();
                    if (null != _attributes) {
                        for (int l = 0; l < _attributes.getLength(); ++l) {
                            Node _property = _attributes.item(l);
                            String _property_raw = _property.getNodeName();
                            if ("name".equals(_property_raw)) {
                                _property_name = _property.getNodeValue();
                            } else {
                                if ("readonly".equals(_property_raw)) {
                                    _read_only = Boolean.parseBoolean(_property.getNodeValue());
                                }
                            }
                        }
                        // Add the property to the class definition
                        ((PropertyManager) _class_definition).Add(new Property(_property_name, _value, _read_only));
                    }
                }
            }
        }
        return _class_definition;
    }

    /**
     * Gets the class name for the given classNode
     *
     * @param classNode Class node for which the class name must be returned
     * @return The class name corresponding to the classNode
     * @throws DOMException If XML error.
     */
    private String getClassNameFor(Node classNode) throws DOMException {
        NamedNodeMap _class_attributes = classNode.getAttributes();
        return _class_attributes.item(0).getNodeValue();
    }

    /**
     * Gets the classes list to explore the XML file
     *
     * @return The classes list to explore the XML file
     * @throws IOException                  If error during XML file access
     * @throws SAXException                 If parsing error
     * @throws ParserConfigurationException Exception for the parser configuration
     */
    private NodeList getClasses() throws IOException, SAXException, ParserConfigurationException {
        // Build the XML parsing to create the instance corresponding to the given
        // class name
        DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();
        _factory.setIgnoringComments(true);
        _factory.setCoalescing(true);
        DocumentBuilder _doc_builder = _factory.newDocumentBuilder();
        Document _doc = _doc_builder.parse(xmlFileClassDef);
        return _doc.getElementsByTagName("class");
    }

    /**
     * Parse the given string value to a valid basis value type
     *
     * @param valueToParse Raw value to parse
     * @return The value parsed or same value if hadn't been parsed
     */
    private Object parse(String valueToParse) {
        try {
            // Control if string is a type of hex value
            if (valueToParse.startsWith("0x")) {
                return Integer.parseInt(valueToParse.substring(2), 16);
            }

            // Control if string is a Long type
            if (valueToParse.endsWith("L")) {
                return Long.parseLong(valueToParse.substring(0, valueToParse.length() - 0x1));
            }

            return Integer.parseInt(valueToParse);
        } catch (NumberFormatException e) {
            // It's not an Integer
            try {
                // It's not a Long
                if (valueToParse.endsWith("f")) {
                    return Float.parseFloat(valueToParse);
                }

                // Try for a Double
                return Double.parseDouble(valueToParse);

            } catch (NumberFormatException e2) {
                // It's not a Float
                try {
                    return Double.parseDouble(valueToParse);
                } catch (NumberFormatException e3) {
                    // It's not a Double
                    // Control if it's a date
                    if (valueToParse.startsWith("#") && valueToParse.endsWith("#")) {
                        try {
                            return DateFormat.getDateInstance(DateFormat.SHORT).parse(valueToParse.substring(1, valueToParse.length() - 1));
                        } catch (ParseException ex) {
                            // It's not a Date. So pass, the default parsing result will be a String.
                        }
                    }
                }
            }
        }

        // It's a String
        return valueToParse;
    }
}

/**
 * Class implementation to get all properties from the XML classes definition
 *
 * @author Tioben Neenot
 */
class PropertyManager extends ClassDefinition {

    /**
     * Class name
     */
    private String name = null;

    /**
     * Create an instance of the PropertyManager class.
     *
     * @param name Name of the PropertyManager class.
     * @throws InstantiationException if class name is <code>null</code>, empty or contains space character in name
     */
    public PropertyManager(String name) throws InstantiationException {
        try {
            this.name = controlInvalidSpaceInName(States.validate(name));
        } catch (AssertionError e) {
            throw new InstantiationException("Name can't be null or empty");
        }
    }

    private String controlInvalidSpaceInName(String name)
            throws InstantiationException {
        if (name.indexOf(' ') != -1) {
            throw new InstantiationException("Space in name forbidden");
        }

        return name;
    }

    /**
     * Returns the class name
     *
     * @return The class name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Add a property to the properties list
     *
     * @param p Property to add
     */
    void Add(Property p) {
        properties.put(p.getName(), p);
    }
}
