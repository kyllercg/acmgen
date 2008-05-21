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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.0
 */
public class ReadFile {

	private I18Printer messages;
	private static final String i18_file = "config/i18";
	
	/**
	 * Class constructor
	 */
	public ReadFile() {
		
    	this.messages = new I18Printer(i18_file);
	}

	/**
	 * Reads a Properties file
	 * @param props_filename - the filename of the properties file
	 * @return a properties object with the read properties
	 */
	public Properties readPropsFile(String props_filename) {
		
	    try {
	    	
	    	Properties props = new Properties();
	    	
	    	props.load(new FileInputStream(props_filename));
	    	
	    	return props;
	    } catch (IOException e) {
	    	
	    	System.err.println(messages.getMessage("io_error_msg")
	    			+ e.getMessage());
	    	return null;
	    }
	}

	/**
	 * Reads a text file
	 * 
	 * @param filename - the filename of the file to be read
	 * @return a vector of strings with the data of the file
	 */
	public Vector<String> readTextFile(String filename) {

		try {
	    	
			int b;
			String saux = "";
			Vector<String> vaux = new Vector<String>();
			FileInputStream is = new FileInputStream(filename);
	    	
			while ((b = is.read()) != -1) {
				
				saux += (char)b;
				if (b == '\n') {
					
					vaux.add(saux);
					saux = "";
				}
			}
	    	
			return vaux;
	    } catch (IOException e) {
	    	
	    	System.err.println(messages.getMessage("io_error_msg")
	    			+ e.getMessage());
	    	return null;
	    }
	}
}
