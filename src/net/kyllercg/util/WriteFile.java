/**
 * Copyright (C) 2006, 2007 - Kyller Costa Gorgônio
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.1
 */
public class WriteFile {

	private I18Printer messages;
	private static final String i18_file = "config/i18";
	
	/**
	 * Class constructor
	 */
	public WriteFile() {
		
    	this.messages = new I18Printer(i18_file);
	}

	/**
	 * Writes a text file
	 * @param filename - the filename of the file to be written
	 * @param text - the data of the file
	 * @return - a boolean indicating if operation was ok or not
	 */
	public boolean writeTextFile(String filename, Vector<String> text) {
		
		try {
			
			FileOutputStream f = new FileOutputStream(filename);
			Iterator<String> i = text.iterator();
			while (i.hasNext()) {
				
				f.write(((String)i.next()).getBytes());
			}
			
			return true;
		} catch (FileNotFoundException e) {
			
			System.err.println(messages.getMessage("io_error_msg")
	    			+ e.getMessage());
			return false;
		} catch (IOException e) {
			
			System.err.println(messages.getMessage("io_error_msg")
	    			+ e.getMessage());
			return false;
		}
	}
}
