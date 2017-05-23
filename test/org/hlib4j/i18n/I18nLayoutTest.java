/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.i18n;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link I18nLayout} class.
 *
 * @author Tioben Neenot
 */
public class I18nLayoutTest
{

  /**
   * Test of change method, of class I18nLayout. Uses an empty component.
   */
  @Test
  public void test_Change_NoError()
  {
    Locale l = new Locale("fr", "FR_EURO");
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    instance.change(l);
  }

  /**
   * Test of getBaseName method, of class I18nLayout.
   */
  @Test
  public void test_GetBaseName_ValidValue()
  {
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    String expResult = "org.hlib4j.i18n.messages";
    String result = instance.getBaseName();
    assertEquals(expResult, result);
  }

  /**
   * Test of addLayoutComponent method, of class I18nLayout.
   */
  @Test
  public void test_AddLayoutComponent_ValidComponent_NoError()
  {
    String name = "btnTest";
    Component comp = new JButton("testing");
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    instance.addLayoutComponent(name, comp);
  }

  /**
   * Test of removeLayoutComponent method, of class I18nLayout.
   */
  @Test
  public void test_RemoveLayoutComponent_ValidComponent_NoError()
  {
    Component comp = new JButton("testing");
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.test", new JPanel());
    instance.addLayoutComponent("btnTest", comp);
    instance.removeLayoutComponent(comp);
  }

  /**
   * Test of preferredLayoutSize method, of class I18nLayout.
   */
  @Test
  public void test_PreferredLayoutSize_ForSpecificDimension_ValidAwaitingDimension()
  {
    Container parent = new JPanel();
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    parent.setLayout(instance);
    Dimension expResult = new Dimension(10, 10);
    Dimension result = instance.preferredLayoutSize(parent);
    assertEquals(expResult, result);
  }

  /**
   * Test of minimumLayoutSize method, of class I18nLayout.
   */
  @Test
  public void test_MinimumLayoutSize_ForSpecificDimension_ValidAwaitingDimension()
  {
    Container parent = new JPanel();
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    parent.setLayout(instance);
    Dimension expResult = new Dimension(10, 10);
    Dimension result = instance.minimumLayoutSize(parent);
    assertEquals(expResult, result);
  }

  /**
   * Test of layoutContainer method, of class I18nLayout.
   */
  @Test
  public void test_LayoutContainer_SetLayoutContainer_NoError()
  {
    Container parent = new Panel();
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", new JPanel());
    instance.layoutContainer(parent);
  }

  /**
   * Test of layoutContainer method, of class I18nLayout. Takes a null target container
   */
  @Test(expected = NullPointerException.class)
  public void test_LayoutContainer_SetNullValue_NullPointerException()
  {
    Container parent = new Panel();
    I18nLayout instance = new I18nLayout("org.hlib4j.i18n.messages", null);
    instance.layoutContainer(parent);
  }
}
