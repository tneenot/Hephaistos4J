/*
 *  Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 *  This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.i18n;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Layout for I18n support. This layout is a convenient class to implement I18n
 * capabilities for graphical user interface. The I18nLayout will take account
 * all components from the target container specified by its
 * constructor.<br><br>
 * <p>
 * I18nLayout will take account only components for which a
 * <code>setName(String )</code> is defined on a valid value. I18nLayout will
 * search the correspondence between the <code>getName()</code> value of the
 * component and a properties file corresponding to {@link I18n#getBaseName()}
 * and the specified locale. If the properties file contains a special value
 * like <code>&lt;component_name&gt;.tooltip</code>, it will be applied to the
 * <code>setToolTipText(String)</code> of the component.
 *
 * @author Tioben Neenot
 * @see I18n
 */
public class I18nLayout implements I18n, LayoutManager
{

  /**
   * Default base name for the I18nLayout
   */
  private final String baseName;
  /**
   * Reference for the target container
   */
  private final Container target;

  /**
   * Builds an instance of I18nLayout with the specified base name and target
   * container.
   *
   * @param baseName Default base name
   * @param target   Target container
   */
  public I18nLayout(String baseName, Container target)
  {
    this.baseName = baseName;
    this.target = target;
  }

  @Override
  public void change(Locale locale)
  {
    ResourceBundle bundle = ResourceBundle.getBundle(getBaseName(), locale);
    synchronized (this.target.getTreeLock())
    {
      updateComponents(bundle, this.target);
    }
  }

  /**
   * Updates all components content according to resource bundle and container
   *
   * @param resourceBundle ResourceBundle for components
   * @param container      Container with component for updating.
   */
  private void updateComponents(ResourceBundle resourceBundle, Container container)
  {
    for (Component _component : container.getComponents())
    {
      // Take account only component with name
      boolean _has_a_name = _component.getName() != null;

      // According to component type, fix text value
      if (_component instanceof AbstractButton && _has_a_name)
      {
        ((AbstractButton) _component).setText(resourceBundle.getString(_component.getName()));
      } else if (_component instanceof JLabel && _has_a_name)
      {
        ((JLabel) _component).setText(resourceBundle.getString(_component.getName()));
      } else if (_component instanceof Container)
      {
        updateComponents(resourceBundle, (Container) _component);
      }

      try
      {
        // Control if component as tooltip text is associated with
        if (_has_a_name && resourceBundle.containsKey(_component.getName() + ".tooltip"))
        {
          ((JComponent) _component).setToolTipText(resourceBundle.getString(_component.getName() + ".tooltip"));
        }
      } catch (ClassCastException e)
      {
        // Do nothing
      }
    }
  }

  @Override
  public String getBaseName()
  {
    return baseName;
  }

  /**
   * If the layout manager uses a per-component string, adds the component comp
   * to the layout, associating it with the string specified by
   * <code>name</code>.
   *
   * @param name      the string to be associated with the component
   * @param component the component to be added
   */
  @Override
  public void addLayoutComponent(String name, Component component)
  {
    target.getLayout().addLayoutComponent(name, component);
  }

  /**
   * Removes the specified component from the layout.
   *
   * @param component the component to be removed
   */
  @Override
  public void removeLayoutComponent(Component component)
  {
    target.getLayout().removeLayoutComponent(component);
  }

  /**
   * Calculates the preferred size dimensions for the specified container, given
   * the components it contains.
   *
   * @param parent the container to be laid out
   * @return The preferred size dimensions
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  @Override
  public Dimension preferredLayoutSize(Container parent)
  {
    return target.getLayout().preferredLayoutSize(parent);
  }

  /**
   * Calculates the minimum size dimensions for the specified container, given
   * the components it contains.
   *
   * @param parent the container to be laid out
   * @return The minimum size dimensions
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  @Override
  public Dimension minimumLayoutSize(Container parent)
  {
    return target.getLayout().minimumLayoutSize(parent);
  }

  /**
   * Lays out the specified container.
   *
   * @param parent the container to be laid out
   */
  @Override
  public void layoutContainer(Container parent)
  {
    target.getLayout().layoutContainer(parent);
  }

  /**
   * Returns a representation of the I18nLayout.
   *
   * @return A string representation of the I18nLayout.
   */
  @Override
  public String toString()
  {
    return "I18nLayout{" + "baseName=" + getBaseName() + ", target=" + target + '}';
  }

}
