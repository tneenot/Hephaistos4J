/*
 * Hephaistos 4 Java library: a library with facilities to get more concise code.
 *
 *  Copyright (C) 2017 Tioben Neenot
 *
 * This source is distributed under conditions defined into the LICENSE file.
 */

package org.hlib4j.i18n;

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
