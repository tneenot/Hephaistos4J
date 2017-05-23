package org.hlib4j.samples.i18n;
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

import org.hlib4j.i18n.I18n;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Child window for i18n test.
 *
 * @author Tioben Neenot
 */
public class JFI18NChild extends JFrame implements I18n
{

  /**
   * Serial ID
   */
  private static final long serialVersionUID = -7010803432954171543L;
  /**
   * Internal components
   */
  private final JLabel lblText;

  /**
   * Create the frame.
   */
  public JFI18NChild()
  {
    // Here adds another window same i18n manager than JFI18nTest window.
    // JFI18NTest.i18n.add(this);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setBounds(100, 100, 200, 100);
        /*
      Internal content pane
	 */
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    lblText = new JLabel();
    contentPane.add(lblText, BorderLayout.CENTER);
  }

  @Override
  public void change(Locale l)
  {
    try
    {
      ResourceBundle r = ResourceBundle.getBundle(getBaseName(), l);
      setTitle(r.getString("JFI18NChild.title"));
      lblText.setText(r.getString("JFI18NChild.lbl.text"));
    } catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public String getBaseName()
  {
    return "org.hlib4j.samples.i18n.messages";
  }
}
