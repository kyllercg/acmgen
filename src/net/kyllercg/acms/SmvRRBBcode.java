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

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class SmvRRBBcode extends SmvCode {

	protected static final String add_text_lambda	= "" +
			"\t& next(rd_data) = rd_data\n" +
			"\t& next(acmI) = acmI\n" +
			"\t& wrp.running = 1\n";
	protected static final String add_text_mu		= "" +
			"\t& next(rd_data) = rd_data\n" +
			"\t& next(acmI) = acmI\n" +
			"\t& rdp.running = 1\n";
	
	/**
	 * Class constructor
	 * 
	 * @param size - the size of the ACM
	 */
	public SmvRRBBcode(int size) {
		
		super(size);
	}
	
	/**
	 * Class constructor
	 * 
	 * @param size - the size of the ACM
	 * @param pncode - the Petri net SMV code generated by PEP
	 */
	public SmvRRBBcode(int size, Vector<String> pncode) {
	
		super(size, pncode);
	}
	
	/**
	 * Fix the ACM size in the SMV model.
	 *
	 * @param none
	 * @return nothing
	 */
	protected void fixHeader() {
		
		Date d = new Date(System.currentTimeMillis());
		Iterator<String> i = smvcode.iterator();
		String saux = "";
		Vector<String> vaux = new Vector<String>();
		
		vaux.add("-- Generated by ACMgen\n");
		vaux.add("-- " + System.getProperty("os.name") + " " +
				System.getProperty("os.version") + " at " + d.toString() +
				"\n");
		
		while (i.hasNext()) {
			
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
			
				saux = saux.replace(acm_size_text, String.valueOf(size));
			}
			
			vaux.add(saux);
		}
		
		smvcode = vaux;
	}
	
	/**
	 * Insert the Petri Net model into th SMV model.
	 *
	 * @param none
	 * @return nothing
	 */
	protected void insertPNCode() {
		
		Iterator<String> i = smvcode.iterator();
		String saux = "";
		Vector<String> vaux = new Vector<String>();
		
		while (i.hasNext()) {
			
			saux = (String)i.next();
			
			if (saux.contains(pep_text)) {
				
				Iterator<String> i2 = this.pncode.iterator();
				
				while (i2.hasNext()) {
					
					vaux.add((String)i2.next());
				}
				
			} else {
			
				vaux.add(saux);
			}
		}
			
		this.smvcode = vaux;
	}
		
	/**
	 * Fix the Petri Net code into de SMV model
	 *
	 * @param none
	 * @return nothing
	 */
	protected void fixPNcode() {
		
		Iterator<String> i = pncode.iterator();
		String saux = "";
		Vector<String> vaux = new Vector<String>();
		
		while (i.hasNext()) {
			
			saux = (String)i.next();
		
			if (saux.contains(pep_text_main)) {
				
				saux = "-- " + saux;
			} else if (saux.contains(pep_text_init)) {
				
				vaux.add(saux);
				saux = ((String)i.next()).replace("\n", ";\n");
			} else if (saux.startsWith(pep_text_lambda)) { 
				
				vaux.add(saux);
				saux = (String)i.next() + add_text_lambda;
			} else if (saux.startsWith(pep_text_mu)) { 
				
				vaux.add(saux);
				saux = (String)i.next() + add_text_mu;
			} else if (saux.startsWith(pep_text_wr)) { 
				
				vaux.add(saux);
				int x = Integer.parseInt(
						saux.substring(pep_text_wr.length()).replace("\n", ""));
				String aux = "\t& next(rd_data) = rd_data\n";
				for (int c = 0; c < size; c++) {
					
					if (c == x) {
						
						aux += "\t& next(acmI[" + c + "]) = wr_data\n";
					} else {
						
						aux += "\t& next(acmI[" + c + "]) = acmI[" + c + "]\n";
					}
				}
				aux += "\t& wrp.running = 1\n";
				saux = (String)i.next() + aux;
			} else if (saux.startsWith(pep_text_rd)) { 
				
				vaux.add(saux);
				int x = Integer.parseInt(
						saux.substring(pep_text_rd.length()).replace("\n", ""));
				String aux = "\t& next(rd_data) = acmI[" + x + "]\n";
				aux += "\t& next(acmI) = acmI\n";
				aux += "\t& rdp.running = 1\n";
				saux = (String)i.next() + aux;
			} else if (saux.contains(pep_text_loop)) {
				
				vaux.setElementAt("-- " + vaux.elementAt(vaux.size() - 1),
						vaux.size() - 1);
				vaux.add("-- " + (String)i.next());
				saux = ((String)i.next()).replace("\n", ";\n");
			} else if (saux.contains(pep_text_define)) {
				
				saux = "-- " + saux;
				
				do {
					
					vaux.add(saux);
					saux = "DEFINE " + (String)i.next();
				} while (i.hasNext());
			}
			
			vaux.add(saux);
		}
		
		pncode = vaux;
	}

	protected void fixFreshness() {
		
		Iterator<String> i = smvcode.iterator();
		String saux = "";
		Vector<String> vaux = new Vector<String>();
		
		while (i.hasNext()) {
			
			saux = (String)i.next();
		
			if (saux.contains(fresh_text)) {
				
				int c;
				saux = "";
				
				for (c = 3; c <= this.size; c++) {
					
					saux += "\t-- sequencing properties for wr_cont = "
						 	+ c + "\n";
					saux += "\tSPEC AG(wr_cont = " + c + " ->\n";
					saux += "\t\t\tAX((wr_cont >= " + c + " & acm[0.." +
							(c - 1) + "] = acm2[0.." + (c - 1) + "]) ||\n";
					saux += "\t\t\t   (wr_cont = " + (c - 1) + "  & acm[0.." +
							(c - 2) + "] = acm2[1.." + (c - 1) + "])));\n";
				}
			} 
			
			vaux.add(saux);
		}
		
		smvcode = vaux;
	}

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