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

import org.hlib4j.i18n.I18nLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * Sample frame to use a I18nLayout manager. While this windows is running,
 * defaults labels are showing. User will click on button on the bottom of the
 * window to apply a new local dynamically.
 *
 * @author Tioben Neenot
 */
public class JI18nLayoutSampleContainer extends JFrame implements ActionListener
{

  /**
   * Component serial ID
   */
  private static final long serialVersionUID = 7124737703530573596L;

  /**
   * I18nLayout manager
   */
  private final I18nLayout layout;
  /**
   * Default locale
   */
  private Locale defaultLocal = new Locale("en", "GB");

  /**
   * Builds an instance of the JI18nLayoutSample
   */
  JI18nLayoutSampleContainer()
  {
    setTitle("JI18nLayout sample container");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel _panel1 = new JPanel(new BorderLayout());
    layout = new I18nLayout("org.hlib4j.samples.i18n.layout", _panel1);

    JPanel _panel2 = new JPanel();

		/*
      Components for sample
	 */
    JLabel lblLabel1 = new JLabel("Default label 1");
    lblLabel1.setName("label1.text");

    JLabel lblLabel2 = new JLabel("Default label 2");
    lblLabel2.setName("label2.text");

    JButton btnBtn1 = new JButton("Default button 1");
    btnBtn1.setName("btn1.text");
    btnBtn1.setToolTipText("Default tooltip text");

    JButton btnBtn2 = new JButton("Default button 2");
    btnBtn2.setName("btn2.text");
    btnBtn2.addActionListener(this);

    _panel1.add(lblLabel1, BorderLayout.NORTH);
    _panel1.add(lblLabel2, BorderLayout.WEST);
    _panel1.add(new JLabel("Static text", SwingConstants.CENTER), BorderLayout.CENTER);

    _panel2.add(btnBtn1);
    _panel2.add(btnBtn2);

    _panel1.add(_panel2, BorderLayout.SOUTH);

    add(_panel1);

    // If comment line above, default text value for each component will be
    // displayed
    layout.change(Locale.getDefault());
    pack();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    new JI18nLayoutSampleContainer().setVisible(true);
  }

  /**
   * Applied a new local and change all components.
   *
   * @param e Event source
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    defaultLocal = defaultLocal.getCountry().equals("GB") ? new Locale("fr", "FR") : new Locale("en", "GB");

    layout.change(defaultLocal);
  }
}
