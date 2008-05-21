/**
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * $Id$
 */

package net.kyllercg.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.0
 */
public class I18Printer {

	private ResourceBundle messages;
	private static String av_locales[] = {"en_US","pt_BR"};
	
	/**
	 * Class constructor
	 * @param basename - the basename of the i18 file
	 */
	public I18Printer(String basename) {
		
		boolean has_locale = false;
		Locale locale = Locale.getDefault();
		
		for (int i = 0; i < av_locales.length; i++) {
		
			if (locale.toString().equals(av_locales[i])) {
				
				has_locale = true;
				break;
			}
		}
		
		if (!has_locale) {
			
			locale = new Locale("en", "US");
		}
		
    	messages = ResourceBundle.getBundle(basename, locale);
	}
	
	public String getMessage(String msg_key) {
		
		return messages.getString(msg_key);
	}
}
