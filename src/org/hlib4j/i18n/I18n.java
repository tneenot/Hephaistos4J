package org.hlib4j.i18n;
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

import java.util.Locale;

/**
 * Defines the operation to change locale. The user defines its own
 * Initialization operations in the {@link org.hlib4j.i18n.I18n#change(java.util.Locale)} method. This
 * method will call when a locale will changed. All methods defined here can be used for graphical interfaces, text interface or log...
 *
 * @author Tioben Neenot
 */
public interface I18n
{

  /**
   * Change the locale with the new one.
   *
   * @param locale New locale to apply.
   */
  void change(Locale locale);

  /**
   * Gets base name for current I18n implementation. This base name is using by
   * internal ResourceBundle of implementation.
   *
   * @return Base name for current I18n implementation.
   */
  String getBaseName();
}
