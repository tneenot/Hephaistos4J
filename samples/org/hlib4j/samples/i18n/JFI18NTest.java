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
import org.hlib4j.i18n.I18nContainer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Swing class for I18N capabilities.
 *
 * @author Tioben Neenot
 */
public class JFI18NTest extends JFrame implements ActionListener, I18n
{

	/**
	 * Serial ID for application
	 */
	private static final long          serialVersionUID = 7966266710914701667L;
	private static final I18nContainer i18n             = new I18nContainer( new Locale( "en", "GB" ) );
	private static               int           currentIndex     = 0;
	private final JLabel              lblText;
	private final JLabel              lblLang;
	private final JComboBox< Object > cbLang;
	private final JButton             btnNewWindow;
	private final JButton             btnChildWindow;
	private final JButton             btnBis;

	/**
	 * Create the frame.
	 */
	public JFI18NTest()
	{
		// Initialise the i18n manager
		i18n.add( this );

		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		setBounds( 100, 100, 200, 120 );
		/*
	  Internal panel
	 */
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(5, 1));
		setContentPane(contentPane);

		// Build all components with no default initialisation
		lblText = new JLabel();
		lblLang = new JLabel();
		btnNewWindow = new JButton();
		btnNewWindow.setName( "btnNewWindow" );

		btnChildWindow = new JButton();
		btnChildWindow.setName( "btnChildWindow" );

		btnBis = new JButton();
		btnBis.setName( "btnBis" );

		btnNewWindow.addActionListener( this );
		btnChildWindow.addActionListener( this );
		btnBis.addActionListener( this );

		cbLang = new JComboBox<>( new Object[]
																{
																	"Français", "English"
																} );
		cbLang.addActionListener( this );

		// Due to fix the current selected index after an addActionListener, the
		// change will be calling
		cbLang.setSelectedIndex( currentIndex );
		/*
	  Internals components for JPanel
	 */
		JTextField txtField = new JTextField(25);

		JPanel _txt_panel = new JPanel( new BorderLayout() );
		_txt_panel.add( lblText, BorderLayout.WEST );
		_txt_panel.add(txtField, BorderLayout.CENTER );

		JPanel _lang_panel = new JPanel( new BorderLayout() );
		_lang_panel.add( lblLang, BorderLayout.WEST );
		_lang_panel.add( cbLang, BorderLayout.CENTER );

		contentPane.add(_txt_panel);
		contentPane.add(_lang_panel);
		contentPane.add(btnNewWindow);
		contentPane.add(btnChildWindow);
		contentPane.add(btnBis);

		pack();
	}

	/**
	 * Launch the application.
	 *
	 * @param args Command line arguments (not used)
	 */
	public static void main( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					JFI18NTest frame = new JFI18NTest();
					frame.setVisible( true );
				}
				catch ( Exception ignored)
				{
				}
			}
		} );
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public void actionPerformed( ActionEvent e )
	{
		JComboBox< Object > _box;

		if ( e.getSource() instanceof JButton )
		{
			JFrame _frame;
			JButton _btn = ( JButton ) e.getSource();
			if ( _btn.getName().equals( "btnNewWindow" ) )
			{
				_frame = new JFI18NTest();
			}
			else if ( _btn.getName().equals( "btnChildWindow" ) )
			{
				_frame = new JFI18NChild();
				i18n.add( ( I18n ) _frame );
			}
			else
			{
				_frame = new JF18NBis();
			}

			_frame.setLocation( this.getX() + this.getWidth(), this.getY() );
			_frame.setVisible( true );

			_box = cbLang;
		}
		else
		{
			_box = ( JComboBox< Object > ) e.getSource();
		}

		currentIndex = _box.getSelectedIndex();
		Object _item = _box.getSelectedItem();
		if ( _item.equals( "English" ) )
		{
			i18n.change(new Locale("en", "GB"));
		}
		else
		{
			i18n.change(new Locale("fr", "FR"));
		}
	}

	@Override
	public void change( Locale l )
	{
		try
		{
			ResourceBundle r = ResourceBundle.getBundle( getBaseName(), l );
			setTitle( r.getString( "JFI18NTest.title.window" ) );
			lblText.setText( r.getString( "JFI18NTest.text.text" ) );
			lblLang.setText( r.getString( "JFI18NTest.text.language" ) );
			btnNewWindow.setText( r.getString( "JFI18NTest.text.button" ) );
			btnChildWindow.setText( r.getString( "JFI18NTest.text.buttonChild" ) );
			btnBis.setText( r.getString( "JFI18NTest.txt.buttonBis" ) );
		}
		catch ( Exception ignored)
		{
		}
	}

	@Override
	public String getBaseName()
	{
		return "org.hlib4j.samples.i18n.messages";
	}
}
