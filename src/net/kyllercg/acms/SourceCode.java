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
 * 
 * "$Id$"
 */

package net.kyllercg.acms;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.0
 * @created 07/02/2008 at 14:40:41
 */
public abstract class SourceCode {
	
	protected static final String acm_size_text		= "%%%ACMgen:ACM_SIZE%%%";
	
	protected static final String send_text			= "%%%ACMgen:Send()%%%";
	protected static final String lambda_text		= "%%%ACMgen:Lambda()%%%";
	protected static final String receive_text		= "%%%ACMgen:Receive()%%%";
	protected static final String mu_text			= "%%%ACMgen:Mu()%%%";
	
	protected static final String lambda_prefix		= "l";
	protected static final String mu_prefix			= "m";
	protected static final String write_prefix		= "wr";
	protected static final String read_prefix		= "rd";
	
//	protected abstract void genWriter();
//	protected abstract void genReader();

	/**
	 * Generates a hash code for a given string.
	 * 
	 * @param s - the string tho generate the hash code
	 * @return a long containing the hash cpde for string s
	 */
	protected long hashString(String s) {
		
		int cont;
		int lsize = s.length();
		char[] lchar = s.toCharArray();
		long hcode = 0;
		
		for (cont = 0; cont < lsize; cont++) {
			
			hcode += lchar[cont] * Math.pow(31, lsize - (cont + 1));
		}
		
		return hcode;
	}
}
