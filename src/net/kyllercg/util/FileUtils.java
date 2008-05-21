/**
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
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
 * "$Id$"
 */

package net.kyllercg.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.0
 * @created 17/02/2008 at 19:55:18
 */
public class FileUtils {
	
	private I18Printer messages;
	private static final String i18_file = "config/i18";
	
	/**
	 * Class constructor
	 */
	public FileUtils() {
		
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
	
	/**
	 * Copy the contents of a file to another file (like UNIX cp command)
	 * 
	 * @param src file name of the source file
	 * @param dest file name of the destination file
	 *
	 * @return a boolean indicating if operation was ok or not
	 */
	public boolean copyFile(String src, String dest) {

		try {
			
			FileInputStream source = new FileInputStream(src);
			FileOutputStream destination = new FileOutputStream(dest);
			
			byte[] buffer = new byte[1024];
			int i = 0;
			
			while ((i = source.read(buffer)) != -1) {
				
				destination.write(buffer, 0, i);
			}
			
			source.close();
			destination.close();
		} catch (IOException e) {
			
			System.err.println(messages.getMessage("io_error_msg")
	    			+ e.getMessage());
			return false;
		}
		
		return true;
    }
}
