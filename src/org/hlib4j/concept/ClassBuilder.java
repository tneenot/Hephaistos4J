/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.concept;

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
 * <p>
 * This XML file is loading with <code>ClassBuilder</code> that allows to create an instance of {@link ClassDefinition}
 * type according to a class name that's corresponding to XML description file. The {@link ClassDefinition} instance is
 * created with {@link #createInstance(java.lang.String)} method. If an invalid name or non existent name is given as
 * parameter, this method will return <code>null</code> value.
 *
 * @author Tioben Neenot
 */
public class ClassBuilder
{

  /**
   * XML class definition
   */
  private final File xmlFileClassDef;

  /**
   * Create an instance of the <code>ClassBuilder</code> class.
   *
   * @param xmlFileClassDef XML file associated with this instance.
   */
  public ClassBuilder(File xmlFileClassDef)
  {
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
  public static File parse(ResourceBundle bundle) throws IOException
  {
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
  private static File parse(ResourceBundle bundle, File dir) throws IOException
  {
    return new ResourceBundleToXMLParser(bundle, dir).invoke();
  }

  /**
   * Gets all classes.
   *
   * @return Classes collection for all existent classes.
   */
  public Collection<ClassDefinition> getAllClasses()
  {
    Collection<ClassDefinition> _classes_list = new ArrayList<>();

    try
    {
      NodeList _classes_node = getClasses();
      for (int i = 0; i < _classes_node.getLength(); ++i)
      {
        Node _class_node = _classes_node.item(i);
        _classes_list.add(getClassDefinitionFor(_class_node, getClassNameFor(_class_node)));
      }
    } catch (IllegalArgumentException | DOMException | InstantiationException | IOException | SAXException | ParserConfigurationException ex)
    {
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
  public ClassDefinition createInstance(String className)
  {
    try
    {
      // Gets all classes
      NodeList _class_nodes = getClasses();
      for (int i = 0; i < _class_nodes.getLength(); ++i)
      {
        // Search the class name corresponding to the given parameter
        Node _class_node = _class_nodes.item(i);
        ClassDefinition _def = getClassDefinitionFor(_class_node, className);
        if (_def != null)
        {
          return _def;
        }
      }
    } catch (InstantiationException | SAXException | IOException | ParserConfigurationException ex)
    {
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
    DOMException, InstantiationException
  {
    PropertyManager _class_definition = null;

    String _class_name = getClassNameFor(classNode);
    if (_class_name.equals(className))
    {
      // Build the awaiting class definition. In fact the class definition consist to register a list of properties for this class
      // with their accessors.
      _class_definition = new PropertyManager(_class_name);
      addPropertiesToClassFromNode(_class_definition, classNode);


    }
    return _class_definition;
  }

  private void addPropertiesToClassFromNode(PropertyManager classDefinition, Node classNode)
  {
    // Gets all properties that will be linked to this class definition
    NodeList _properties = classNode.getChildNodes();
    addAccessorsToClassFromProperties(classDefinition, _properties);
  }

  private void addAccessorsToClassFromProperties(PropertyManager classDefinition, NodeList properties)
  {
    for (int j = 0; j < properties.getLength(); ++j)
    {
      // For each property get all parameters. A parameter is an accessor.
      NodeList _accessor_definition = properties.item(j).getChildNodes();
      setAccessorDefinitionToClass(classDefinition, _accessor_definition);
    }
  }

  private void setAccessorDefinitionToClass(PropertyManager classDefinition, NodeList accessorDefinition)
  {
    for (int k = 0; k < accessorDefinition.getLength(); ++k)
    {
      // Variables for all properties type
      NamedNodeMap _attributes = accessorDefinition.item(k).getAttributes();
      if (null != _attributes)
      {
        String _property_name = _attributes.getNamedItem("name").getNodeValue();
        boolean _read_only = Boolean.parseBoolean(_attributes.getNamedItem("readonly").getNodeValue());
        Object _value = parse(accessorDefinition.item(k).getTextContent().trim());

        // Add the property to the class definition
        classDefinition.Add(new Property(_property_name, _value, _read_only));
      }
    }
  }

  /**
   * Gets the class name for the given classNode
   *
   * @param classNode Class node for which the class name must be returned
   * @return The class name corresponding to the classNode
   * @throws DOMException If XML error.
   */
  private String getClassNameFor(Node classNode) throws DOMException
  {
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
  private NodeList getClasses() throws IOException, SAXException, ParserConfigurationException
  {
    // Build the XML parsing to create the instance corresponding to the given
    // class name
    Document _doc = parseXmlDocument(makeDocumentBuilderFactory());
    return _doc.getElementsByTagName("class");
  }

  private Document parseXmlDocument(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException, SAXException, IOException
  {
    DocumentBuilder _doc_builder = documentBuilderFactory.newDocumentBuilder();
    return _doc_builder.parse(xmlFileClassDef);
  }

  private DocumentBuilderFactory makeDocumentBuilderFactory()
  {
    DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();
    _factory.setIgnoringComments(true);
    _factory.setCoalescing(true);
    return _factory;
  }

  /**
   * Parse the given string value to a valid basis value type
   *
   * @param valueToParse Raw value to parse
   * @return The value parsed or same value if hadn't been parsed
   */
  private Object parse(String valueToParse)
  {
    try
    {
      // Control if string is a type of hex value
      if (valueToParse.startsWith("0x"))
      {
        return Integer.parseInt(valueToParse.substring(2), 16);
      }

      // Control if string is a Long type
      if (valueToParse.endsWith("L"))
      {
        return Long.parseLong(valueToParse.substring(0, valueToParse.length() - 0x1));
      }

      // It's not a Long
      if (valueToParse.endsWith("f"))
      {
        return Float.parseFloat(valueToParse);
      }

      return Integer.parseInt(valueToParse);
    } catch (NumberFormatException e)
    {
      // It's not an Integer
      try
      {
        // Try for a Double
        return Double.parseDouble(valueToParse);

      } catch (NumberFormatException e2)
      {
        // It's not a Double
        // Control if it's a date
        if (valueToParse.startsWith("#") && valueToParse.endsWith("#"))
        {
          try
          {
            return DateFormat.getDateInstance(DateFormat.SHORT).parse(valueToParse.substring(1, valueToParse.length() - 1));
          } catch (ParseException ex)
          {
            // It's not a Date. So pass, the default parsing result will be a String.
          }
        }

      }
    }

    // It's a String
    return valueToParse;
  }

  private static class ResourceBundleToXMLParser
  {
    private ResourceBundle bundle;
    private File xmlFile;

    public ResourceBundleToXMLParser(ResourceBundle bundle, File dir)
    {
      this.bundle = bundle;
      this.xmlFile = new File(dir.getAbsolutePath() + File.separator + "DefXML" + System.nanoTime() + ".tmp");
    }

    private static void writeHeader(FileWriter xmlWriter) throws IOException
    {
      xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      xmlWriter.write("<classes>");
      xmlWriter.write("<class name=\"Bundle\">");
      xmlWriter.write("<properties>");
    }

    private static void writeBody(ResourceBundle bundle, FileWriter xmlWriter) throws IOException
    {
      // Gets all elements from ResourceBundle and write them into XML format.
      Enumeration<String> _keys = bundle.getKeys();
      while (_keys.hasMoreElements())
      {
        String _key = _keys.nextElement();
        xmlWriter.write("<property name=\"" + _key + "\" readonly=\"false\">" + bundle.getString(_key) + "</property>");
      }
    }

    private static void writerFooter(FileWriter xmlWriter) throws IOException
    {
      xmlWriter.write("</properties>");
      xmlWriter.write("</class>");
      xmlWriter.write("</classes>");
    }

    public File invoke() throws IOException
    {
      try (FileWriter _xml_writer = new FileWriter(xmlFile))
      {
        writeHeader(_xml_writer);
        writeBody(bundle, _xml_writer);
        writerFooter(_xml_writer);
      }

      return this.xmlFile;
    }
  }
}

/**
 * Class implementation to get all properties from the XML classes definition
 *
 * @author Tioben Neenot
 */
class PropertyManager extends ClassDefinition
{

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
  public PropertyManager(String name) throws InstantiationException
  {
    try
    {
      this.name = controlInvalidSpaceInName(States.validate(name));
    } catch (AssertionError e)
    {
      throw new InstantiationException("Name can't be null or empty");
    }
  }

  private String controlInvalidSpaceInName(String name)
    throws InstantiationException
  {
    if (name.indexOf(' ') != -1)
    {
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
  public String getName()
  {
    return name;
  }

  /**
   * Add a property to the properties list
   *
   * @param p Property to add
   */
  void Add(Property p)
  {
    properties.put(p.getName(), p);
  }
}
