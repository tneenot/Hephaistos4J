/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.samples.concept;

import org.hlib4j.concept.ClassBuilder;
import org.hlib4j.concept.ClassDefinition;
import org.hlib4j.concept.Property;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class test to run {@link ClassDefinition} into a real main test program
 * rather than a JUnit test program.
 *
 * @author Tioben Neenot
 */
public class ClassDefinitionMain
{
  public static void main(String[] args)
  {
    ClassBuilder builder = new ClassBuilder(new File("samples/org/hlib4j/samples/concept/CDef.xml"));
    Collection<ClassDefinition> coll = builder.getAllClasses();

    for (ClassDefinition c : coll)
    {
      System.out.println(c);

      for (Property p : c.getProperties())
      {
        System.out.println("\t" + p);
      }
    }

    try
    {
      // Build the XML parsing to create the instance corresponding to the
      // given class name
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setIgnoringComments(true);
      factory.setCoalescing(true);

      DocumentBuilder doc_builder = factory.newDocumentBuilder();

      Document doc = doc_builder.parse(new File(java.util.ResourceBundle.getBundle(
        "org.hlib4j.samples.concept.class_builder").getString("CLASS_DEFINITION")));

      Element root = doc.getDocumentElement();
      System.out.println("root: " + root.getNodeName());
      NodeList class_nodes = root.getElementsByTagName("class");

      for (int i = 0; i < class_nodes.getLength(); ++i)
      {
        Node class_node = class_nodes.item(i);

        System.out.print(class_node.getNodeName());

        NamedNodeMap attributes = class_node.getAttributes();

        System.out.println(" : " + attributes.item(0));

        NodeList properties = class_node.getChildNodes();
        for (int j = 0; j < properties.getLength(); ++j)
        {
          System.out.println("\t" + properties.item(j));

          NodeList property_list = properties.item(j).getChildNodes();
          for (int k = 0; k < property_list.getLength(); ++k)
          {
            Node p = property_list.item(k);
            System.out.print("\t\t" + p + ": ");
            System.out.println(p.getTextContent());

            NamedNodeMap attributes_p = p.getAttributes();
            if (null != attributes_p)
            {
              for (int l = 0; l < attributes_p.getLength(); ++l)
              {
                System.out.println("\t\t\t> " + attributes_p.item(l));
              }
            }
          }
        }
      }
    } catch (SAXException | IOException | ParserConfigurationException ex)
    {
      Logger.getLogger(ClassDefinitionMain.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
}
