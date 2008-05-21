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

import java.util.Iterator;
import java.util.Vector;

/**
 * This class receives as input a certain ACM model described as a Petri net and
 * generates a Verilog code that implements such Petri net model.
 * 
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class VerilogOWRRBBcode extends VerilogCode {

	/**
	 * The OWRRBB ACM Petri net model.
	 */
	private ACMsys pn = null;
	
//	private Vector<String> allLabels;
	
	/**
	 * The class constructor.
	 * 
	 * @param pn - an OWRRBB PetriNet object
	 */
	public VerilogOWRRBBcode(OWRRBB pn) {

		this.pn = pn;
//		allLabels = new Vector<String>();
//		
//		Iterator i = this.pn.getReader().getPlaces().iterator();
//		
//		while (i.hasNext()) {
//			
//			Place p = (Place)i.next();
//			allLabels.add(p.getLabel());
//		}
//		
//		i = this.pn.getReader().getTransitions().iterator();
//		
//		while (i.hasNext()) {
//			
//			Transition t = (Transition)i.next();
//			allLabels.add(t.getLabel());
//		}
	}
	
	/**
	 * The class constructor.
	 * 
	 * @param pn - an OWBB PetriNet object
	 */
	public VerilogOWRRBBcode(OWBB pn) {

		this.pn = pn;
	}
	
	/**
	 * Fix the Verilog code for the RRBB reader module.
	 */
	protected void fixReader_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.reader_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, 
						String.valueOf(pn.getSize() - 1));
				vaux.add(saux);
			} else if (saux.contains(acm_size_text2)) {
				
				saux = saux.replace(acm_size_text2, 
						String.valueOf((pn.getSize() * 2) - 1));
				vaux.add(saux);
			} else if (saux.contains(init_local_text)) {
				
				Iterator<String> iaux =  fixReader_InitLocal().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_ext_text)) {
				
				Iterator<String> iaux =  fixReader_InitExternal().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(receive_text)) {
				
				Iterator<String> iaux =  fixReader_Receive().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(mu_text)) {
				
				Iterator<String> iaux =  fixReader_Mu().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(run_text)) {
				
				Iterator<String> iaux =  fixReader_Run().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else {
			
				vaux.add(saux);
			}
		}
		
		this.reader_v = vaux;
	}

	/**
	 * Fix the Verilog local variables init stuff.
	 */
	private Vector<String> fixReader_InitLocal() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux;
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("ri") ||
					p.getLabel().startsWith("pri") ||
					p.getLabel().startsWith("ra")) {
				
				saux = "\t\t" + this.genVName(p.getLabel()) + " = " +
					(p.getMarking() == 1 ? "1" : "0") + ";\n";
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog external variables init stuff.
	 */
	private Vector<String> fixReader_InitExternal() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux;
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("re") ||
					p.getLabel().startsWith("rne")) {
				
				saux = "\t\t" + this.genVName(p.getLabel()) + " = " +
					(p.getMarking() == 1 ? "1" : "0") + ";\n";
				vaux.add(saux);
			} 
		}
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog Reader::Receive() stuff.
	 */
	private Vector<String> fixReader_Receive() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getReader()).getTransitions();
		Vector<Arc> varcs = (pn.getReader()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(read_prefix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "\t\t\trrun = 0;\n";
				int cell = this.getVPos(
						t.getLabel().substring(read_prefix.length()));
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getSrc().getLabel())
								+ " == 1";
							if_body += "\t\t\t" +
								this.genVName(a.getSrc().getLabel()) +
								" = 0;\n";
						} else if (a.getType() == Arc.trans2place) {
						
							if_body += "\t\t\t" +
								this.genVName(a.getDest().getLabel()) +
								" = 1;\n";
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getDest().getLabel()) +
								" == 1";
						}
					}
				}
				
				if_body += "\t\t\tadd = " + cell + ";\n";
				if_body += "\t\t\tsmreq = ~smreq;\n";
				
				if (vaux.isEmpty()) {
					
					vaux.add("\t\tif (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				} else {
					
					vaux.add(" else if (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog Reader::Mu() stuff.
	 */
	private Vector<String> fixReader_Mu() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getReader()).getTransitions();
		Vector<Arc> varcs = (pn.getReader()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(mu_prefix) && 
					!t.getLabel().contains(run_infix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "\t\t\trrun = 0;\n";
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getSrc().getLabel())
								+ " == 1";
							if_body += "\t\t\t" + 
								this.genVName(a.getSrc().getLabel()) +
								" = 0;\n";
						} else if (a.getType() == Arc.trans2place) {
						
							if_body += "\t\t\t" + 
								this.genVName(a.getDest().getLabel()) +
								" = 1;\n";
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getDest().getLabel()) +
								" == 1";
						}
					}
				}
				
				if (vaux.isEmpty()) {
					
					vaux.add("\t\tif (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body + 
							"\t\tend");
				} else {
					
					vaux.add(" else if (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}

	/**
	 * Fix the Verilog Reader::RunBabyRun() stuff.
	 */
	private Vector<String> fixReader_Run() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getReader()).getTransitions();
		Vector<Arc> varcs = (pn.getReader()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(mu_prefix) && 
					t.getLabel().contains(run_infix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "\t\t\trrun = 1;\n";
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getSrc().getLabel())
								+ " == 1";
							if_body += "\t\t\t" + 
								this.genVName(a.getSrc().getLabel()) +
								" = 0;\n";
						} else if (a.getType() == Arc.trans2place) {
						
							if_body += "\t\t\t" + 
								this.genVName(a.getDest().getLabel()) +
								" = 1;\n";
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getDest().getLabel()) +
								" == 1";
						}
					}
				}
				
				if (vaux.isEmpty()) {
					
					vaux.add("\t\tif (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body + 
							"\t\tend");
				} else {
					
					vaux.add(" else if (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog code for the RRBB writer module.
	 */
	protected void fixWriter_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.writer_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, 
						String.valueOf(pn.getSize() - 1));
				vaux.add(saux);
			} else if (saux.contains(acm_size_text2)) {
				
				saux = saux.replace(acm_size_text2, 
						String.valueOf((pn.getSize() * 2) - 1));
				vaux.add(saux);
			} else if (saux.contains(init_local_text)) {
				
				Iterator<String> iaux =  fixWriter_InitLocal().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_ext_text)) {
				
				Iterator<String> iaux =  fixWriter_InitExternal().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(send_text)) {
				
				Iterator<String> iaux =  fixWriter_Send().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(lambda_text)) {
				
				Iterator<String> iaux =  fixWriter_Lambda().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else {
			
				vaux.add(saux);
			}
		}
		
		this.writer_v = vaux;
	}
	
	/**
	 * Fix the Verilog local variables init stuff.
	 */
	private Vector<String> fixWriter_InitLocal() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux;
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("wi") ||
					p.getLabel().startsWith("pwi")) {
				
				saux = "\t\t" + this.genVName(p.getLabel()) + " = " +
					(p.getMarking() == 1 ? "1" : "0") + ";\n";
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog external variables init stuff.
	 */
	private Vector<String> fixWriter_InitExternal() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("we") ||
					p.getLabel().startsWith("l")) {
				
				saux = "\t\t" + this.genVName(p.getLabel()) + " = " +
					(p.getMarking() == 1 ? "1" : "0") + ";\n";
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog Writer::Send() stuff.
	 */
	private Vector<String> fixWriter_Send() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getWriter()).getTransitions();
		Vector<Arc> varcs = (pn.getWriter()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(write_prefix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "";
				int cell = this.getVPos(
						t.getLabel().substring(write_prefix.length()));
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getSrc().getLabel()) +
								" == 1";
							if_body += "\t\t\t" + 
								this.genVName(a.getSrc().getLabel()) +
								" = 0;\n";
						} else if (a.getType() == Arc.trans2place) {
						
							if_body += "\t\t\t" + 
								this.genVName(a.getDest().getLabel()) +
								" = 1;\n";
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getDest().getLabel()) +
								" == 1";
						}
					}
				}
				
				if_body += "\t\t\tadd = " + cell + ";\n";
				if_body += "\t\t\tsmreq = ~smreq;\n";
				
				if (vaux.isEmpty()) {
					
					vaux.add("\t\tif (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				} else {
					
					vaux.add(" else if (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog Writer::Lambda() stuff.
	 */
	private Vector<String> fixWriter_Lambda() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getWriter()).getTransitions();
		Vector<Arc> varcs = (pn.getWriter()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(lambda_prefix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "";
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getSrc().getLabel()) +
								" == 1";
							if_body += "\t\t\t" + 
								this.genVName(a.getSrc().getLabel()) +
								" = 0;\n";
						} else if (a.getType() == Arc.trans2place) {
						
							if_body += "\t\t\t" +
								this.genVName(a.getDest().getLabel()) +
									" = 1;\n";
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += this.genVName(a.getDest().getLabel()) +
								" == 1";
						}
					}
				}
				
				if (vaux.isEmpty()) {
					
					vaux.add("\t\tif (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body + 
							"\t\tend");
				} else {
					
					vaux.add(" else if (" + if_cond + ")\n\t\tbegin\n" + 
							"\t\t\t// " + t.getLabel() + "\n" + if_body +
							"\t\tend");
				}
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the Verilog shmem_mod module.
	 */
	protected void fixShMem_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.shmem_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text2)) {
				
				saux = saux.replace(acm_size_text2, 
						String.valueOf((pn.getSize() * 2) - 1));
			}
			
			vaux.add(saux);
		}
		
		this.shmem_v = vaux;
	}

	/**
	 * Fix the Verilog owrrbb_mod main module.
	 */
	protected void fixACM_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.acm_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, 
						String.valueOf(pn.getSize() - 1));
			} else if (saux.contains(acm_size_text2)) {
				
				saux = saux.replace(acm_size_text2, 
						String.valueOf((pn.getSize() * 2) - 1));
			}
			
			vaux.add(saux);
		}
		
		this.acm_v = vaux;
	}
	
	/**
	 * Fix the Verilog code for the MUX module.
	 */
	public void fixMux_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.mux_v.iterator();
		String saux = "";
		int s = pn.getSize() - 1;
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, String.valueOf(s));
				vaux.add(saux);
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text, 
						String.valueOf(Math.abs(Math.log10(s) / Math.log10(2)) - 1));
				vaux.add(saux);
			} else if (saux.contains(acm_mux_text)) {
				
				Iterator<String> iaux =  fixReader_InitExternal().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else {
			
				vaux.add(saux);
			}
		}
		
		this.reader_v = vaux;
	}

//	/**
//	 * Gets the index of a given string in the vector of all labels.
//	 * 
//	 * @param label - the string with the label to look for
//	 * @return the index of the label, or -1 if it does not exists
//	 */
//	private int getLabelIndex(String label) {
//		
//		int cont = 0;
//		Iterator i = allLabels.iterator();
//		
//		while (i.hasNext()) {
//			
//			String l = (String)i.next();
//			
//			if (l.equals(label)) {
//				
//				return cont;
//			}
//			cont++;
//		}
//		
//		return -1;
//	}
	
	/**
	 * Generates a Verilog array variable name with index reference.
	 * 
	 * @param label - the label of the place to be indexed
	 * @return - a string with the name with array index. e.g. "re" generates
	 *           "re[0]"
	 */
	private String genVName(String label) {
		
		String nlabel = "";
		
		if (label.startsWith("ri")) {
			
			nlabel = "ri[" + this.getVPos(label.substring(2)) + "]";
		} else if (label.startsWith("pri")) {
			
			nlabel = "pri[" + this.getVPos(label.substring(3)) + "]";
		} else if (label.startsWith("re")) {
			
			nlabel = "re[" + this.getVPos(label.substring(2)) + "]";
		} else if (label.startsWith("rne")) {
			
			nlabel = "rne[" + this.getVPos(label.substring(3)) + "]";
		} else if (label.startsWith("ra")) {
			
			nlabel = "ra[" + label.substring(2) + "]";
		} else if (label.startsWith("wi")) {
			
			nlabel = "wi[" + this.getVPos(label.substring(2)) + "]";
		} else if (label.startsWith("pwi")) {
			
			nlabel = "pwi[" + this.getVPos(label.substring(3)) + "]";
		} else if (label.startsWith("we")) {
			
			nlabel = "we[" + label.substring(2) + "]";
		} else if (label.startsWith("l")) {
			
			nlabel = "l[" + this.getVPos(label.substring(1)) + "]";
		}
		
		return nlabel;
	}
	
	/**
	 * Gets a string of cell.slot (the dot means concatenation) and returns an
	 * integer with the correct position in the array. For instance, the input
	 * 21 (101 binary) will generate 5 (5 = 2*2+1 = 101 binary).
	 * 
	 * @param pos - the string to be parsed
	 * @return the string pos after parsing it. i.e. the position in the array
	 */
	private int getVPos(String pos) {
		
		int i;
		
		i = Integer.parseInt(pos.substring(0, pos.length() - 1)) * 2 +
			Integer.parseInt(pos.substring(pos.length() - 1));
		
		return i;
	}
}
