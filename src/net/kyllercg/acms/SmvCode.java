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
 * $Id$
 */

package net.kyllercg.acms;

import java.util.Vector;

import net.kyllercg.util.ReadFile;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public abstract class SmvCode extends SourceCode {
	
	protected static final String wr_enabled_text	= "%%%ACMgen:WR.ENABLED%%%";
	protected static final String l_enabled_text	= "%%%ACMgen:l.ENABLED%%%";
	protected static final String pep_text			= "%%%ACMgen:PEP_OUTPUT%%%";
	protected static final String fresh_text		= "%%%ACMgen:FRESHNESS%%%";
	
	protected static final String pep_text_main		= "MODULE main";
	protected static final String pep_text_init		= "INIT";
	protected static final String pep_text_loop		= "-- selfloop for deadlocks";
	protected static final String pep_text_define	= "DEFINE";
	protected static final String pep_text_lambda	= "--l";
	protected static final String pep_text_mu		= "--m";
	protected static final String pep_text_wr		= "--wr";
	protected static final String pep_text_rd		= "--rd";
	protected static final String add_text_lambda	= "" +
			"\t& next(rd_data) = rd_data\n" +
			"\t& next(acmI) = acmI\n" +
			"\t& wrp.running = 1\n";
	protected static final String add_text_mu			= "" +
			"\t& next(rd_data) = rd_data\n" +
			"\t& next(acmI) = acmI\n" +
			"\t& rdp.running = 1\n";
	
	protected int size;
	protected Vector<String> smvcode = null;
	protected Vector<String> pncode = null;
	
	public SmvCode(int size) {
		
		this.size = size;
		this.pncode = null;
	}
	
	public SmvCode(int size, Vector<String> pncode) {
	
		this.size = size;
		this.pncode = pncode;
	}
	
	public Vector<String> getCode() {
		
		return this.smvcode;
	}
	
	public void genCode(String template_file) {
		
		ReadFile f = new ReadFile();
		
		this.smvcode = f.readTextFile(template_file);
		
		this.fixHeader();
		
		if (this.pncode != null) {
			
			this.fixPNcode();
			this.insertPNCode();
		}
		
		this.fixFreshness();
	}
	
	protected abstract void fixHeader();
	protected abstract void fixPNcode();
	protected abstract void insertPNCode();
	protected abstract void fixFreshness();

//	/**
//	 * Generates the FRESHNESS CTL property for the case when wr_cont = count
//	 * @param count - an integer with the counter of the number of items in the
//	 *                ACM.
//	 * @return An string containing the CTL property.
//	 */
//	private String genOWRRBB_ctl_freshness(int count) {
//		
//		int max = (int)Math.pow((double)2, (double)count);
//		
//		String ctl = "\tSPEC AG(\n";
//		String aux1 = "";
//		String aux2, aux3, aux4 = "";
//		String bin = "";
//		
//		for (int i = 0; i < max; i++) {
//			
//			aux1 = aux1 + "\t\t((wr_cont = " + count;
//			aux2 = "";
//			aux3 = "";
//			aux4 = "";
//			
//			bin = Integer.toBinaryString(i);
//			
//			while (bin.length() < count) {
//				
//				bin = "0" + bin;
//			}
//			
//			for (int i2 = 0; i2 < count; i2++) {
//				
//				aux2 += " & acm[" + i2 + "] = " + bin.charAt(i2);
//				
//				if (i2 < count - 1) {
//				
//					aux3 += " & acm[" + i2 + "] = " + bin.charAt(i2 + 1);
//				}
//				
//				if (i2 < 1) {
//					
//					aux4 += " & acm[" + i2 + "] = " + bin.charAt(i2);
//				} else if (i2 > 1) {
//					
//					aux4 += " & acm[" + (i2 - 1) + "] = " + bin.charAt(i2);
//				}
//			}
//			
//			aux1 += aux2 + ") ->\n";
//			aux1 += "\t\t\tAX((wr_cont >= " + count + aux2 + ") ||\n" +
//					"\t\t\t   (wr_cont = " + (count - 1) + aux3 + ") ||\n" +
//					"\t\t\t   (wr_cont = " + (count - 1) + aux4 + ")))";
//			
//			if (i != max - 1) {
//				
//				aux1 += " &\n";
//			} else {
//				
//				aux1 += "\n";
//			}
//		}
//		
//		ctl = ctl + aux1 + "\t);\n";
//		return ctl;
//	}
}
