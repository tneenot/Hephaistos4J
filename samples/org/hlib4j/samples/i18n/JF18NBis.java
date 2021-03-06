/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.samples.i18n;

import org.hlib4j.i18n.I18n;
import org.hlib4j.i18n.I18nContainer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Another child class for i18n test.
 *
 * @author Tioben Neenot
 */
public class JF18NBis extends JFrame implements ActionListener, I18n
{

  /**
   * Serial ID
   */
  private static final long serialVersionUID = -6047130304181848275L;
  private final JLabel lblLang;
  /**
   * I18n manager
   */
  private final I18nContainer i18n;

  /**
   * Create the frame.
   */
  public JF18NBis()
  {
    // I18n manager for other base name
    i18n = new I18nContainer();
    i18n.add(this);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBounds(100, 100, 250, 60);

        /*  Internal component  */
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout());
    setContentPane(contentPane);

    lblLang = new JLabel();
    JComboBox<Object> cbLang = new JComboBox<>(new Object[]
      {
        "Français", "English"
      });

    contentPane.add(lblLang, BorderLayout.WEST);
    contentPane.add(cbLang, BorderLayout.CENTER);

    cbLang.addActionListener(this);
    cbLang.setSelectedIndex(1);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    @SuppressWarnings("unchecked")
    JComboBox<Object> _box = (JComboBox<Object>) e.getSource();
    if (_box.getSelectedItem().equals("English"))
    {
      i18n.change(new Locale("en", "GB"));
    } else
    {
      i18n.change(new Locale("fr", "FR"));
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.tioben.i18n.I18n#change(java.util.Locale)
   */
  @Override
  public void change(Locale l)
  {
    try
    {
      ResourceBundle r = ResourceBundle.getBundle(getBaseName(), l);
      setTitle(r.getString("JF18NBis.title"));
      lblLang.setText(r.getString("JF18NBis.txt"));
    } catch (Exception ignored)
    {
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.tioben.i18n.I18n#getBaseName()
   */
  @Override
  public String getBaseName()
  {
    return "org.hlib4j.samples.i18n.bis";
  }
}
