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
 * generates a C++ code that implements such Petri net model.
 * 
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class CppOWRRBBcode extends CppCode {

	/**
	 * The RRBB ACM Petri net model.
	 */
	private ACMsys pn = null;
	
	private Vector<String> allLabels;
	
	/**
	 * The class constructor.
	 * 
	 * @param pn - an OWRRBB PetriNet object
	 */
	public CppOWRRBBcode(OWRRBB pn) {

		this.pn = pn;
		allLabels = new Vector<String>();
		this.genLabels();
	}
	
	/**
	 * The class constructor.
	 * 
	 * @param pn - an OWBB PetriNet object
	 */
	public CppOWRRBBcode(OWBB pn) {

		this.pn = pn;
		allLabels = new Vector<String>();
		this.genLabels();
	}
	
	/**
	 * Get the labels of all places and transitions of the Petri net and put
	 * them on the vector of all labels.
	 */
	private void genLabels() {
		
		Iterator<Place> ip = this.pn.getReader().getPlaces().iterator();
		Iterator<Transition> it = this.pn.getReader().getTransitions().iterator();
		
		while (ip.hasNext()) {
			
			Place p = (Place)ip.next();
			allLabels.add(p.getLabel());
		}
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			allLabels.add(t.getLabel());
		}
	}
	
	/**
	 * Fix the C++ code for the RRBB reader process.
	 */
	protected void fixReader_cpp() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.reader_cpp.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(init_reader)) {
				
				Iterator<String> iaux =  fixReader_Reader().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_local_text)) {
				
				Iterator<String> iaux =  fixReader_InitLocalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_ext_text)) {
				
				Iterator<String> iaux =  fixReader_InitExternalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(dt_local_text)) {
				
				Iterator<String> iaux =  fixReader_DtLocalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(dt_ext_text)) {
				
				Iterator<String> iaux =  fixReader_DtExternalShv().iterator();
				
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
			} else {
			
				vaux.add(saux);
			}
		}
		
		this.reader_cpp = vaux;
	}
	
	/**
	 * Fix the C++ header file for the RRBB reader process.
	 */
	protected void fixReader_h() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = null;
		Iterator<String> i = this.reader_h.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, String.valueOf(pn.getSize()));
			} else if (saux.contains(dec_text)) {
				
				vplace = (pn.getReader()).getPlaces();
				Iterator<Place> iaux = vplace.iterator();
				saux = "\t\t// reader variables\n";
				
				while (iaux.hasNext()) {
					
					vaux.add(saux);
					Place p = (Place)iaux.next();
					
					if ((pn.getReader()).isTestPlace(p)   ||
							p.getLabel().startsWith("re") ||
							p.getLabel().startsWith("rne")) {
						
						saux = "\t\t" + shv_t + p.getLabel() + ";\n";
						vaux.add(saux);
						saux = "\t\t" + shmid_t + " " + p.getLabel() +
							shmid_s + ";\n";
					} else {
						
						saux = "\t\t" + ctrlv_t + " " + p.getLabel() + ";\n";
					}
				}
			}
			
			vaux.add(saux);
		}
		
		this.reader_h = vaux;
	}
	
	/**
	 * Fix the C++ Reader::Reader() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_Reader() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "\t// reader variables\n";
		vaux.add(saux);
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (!((pn.getReader()).isTestPlace(p)   ||
					p.getLabel().startsWith("re") ||
					p.getLabel().startsWith("rne"))) {
				
				saux = "\t" + p.getLabel() + " = " +
					(p.getMarking() == 1 ? "true" : "false") + ";\n";
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Reader::InitLocalShv() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_InitLocalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("re") ||
					p.getLabel().startsWith("rne")) {
				
				saux = if_shmget_src.replace(shmid_text, 
								p.getLabel() + shmid_s) + 
						if_shmat_src.replace(shmid_text, 
								p.getLabel() + shmid_s);
				saux = saux.replace(hash_text,
						String.valueOf(this.getLabelIndex(p.getLabel())));
				saux = saux.replace(ctrlv_text, p.getLabel());
				saux = saux.replace(marking_text,
						String.valueOf(p.getMarking()));
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Reader::InitExternalShv() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_InitExternalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if ((pn.getReader()).isTestPlace(p)) {
				
				saux = if_shmget_dest.replace(shmid_text, 
								p.getLabel() + shmid_s) + 
						if_shmat_dest.replace(shmid_text, 
								p.getLabel() + shmid_s);
				saux = saux.replace(hash_text,
						String.valueOf(this.getLabelIndex(p.getLabel())));
				saux = saux.replace(ctrlv_text, p.getLabel());
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Reader::DtLocalShv() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_DtLocalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("re") ||
					p.getLabel().startsWith("rne")) {
				
				saux = if_shmdt_src.replace(ctrlv_text, p.getLabel());
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}

	/**
	 * Fix the C++ Reader::DtExternalShv() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_DtExternalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getReader()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if ((pn.getReader()).isTestPlace(p)) {
				
				saux = if_shmdt_dest.replace(ctrlv_text, p.getLabel());
				saux = saux.replace(shmid_text, p.getLabel() + shmid_s);
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Reader::Receive() method for the RRBB reader process.
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
				String if_body = "";
				String cs = t.getLabel().substring(read_prefix.length());
				int slot = Integer.parseInt(cs.substring(cs.length() - 1));
				int cell = Integer.parseInt(cs.substring(0, cs.length() - 1));
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if (a.getSrc().getLabel().startsWith("re") ||
									a.getSrc().getLabel().startsWith("rne")) {
								
								if_cond += "*" + a.getSrc().getLabel() +
									" == true";
								if_body += "\t\t*" + a.getSrc().getLabel() +
									" = false;\n";
							} else {
								
								if_cond += a.getSrc().getLabel() + " == true";
								if_body += "\t\t" + a.getSrc().getLabel() +
									" = false;\n";
							}
						} else if (a.getType() == Arc.trans2place) {
						
							if (a.getDest().getLabel().startsWith("re") ||
									a.getDest().getLabel().startsWith("rne")) {
								
								if_body += "\t\t*" + a.getDest().getLabel() +
									" = true;\n";
							} else {
								
								if_body += "\t\t" + a.getDest().getLabel() +
									" = true;\n";
							}
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += "*" + a.getDest().getLabel() + " == true";
						}
					}
				}
				
				if_body += "\t\tval = *(shm_data + " + (2 * cell + slot) + ");\n";
				
				if (vaux.isEmpty()) {
					
					vaux.add("\tif (" + if_cond + ") {\n" + 
							"\t\t// " + t.getLabel() + "\n" + if_body + "\t}");
				} else {
					
					vaux.add(" else if (" + if_cond + ") {\n" + 
							"\t\t// " + t.getLabel() + "\n" + if_body + "\t}");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Reader::Mu() method for the RRBB reader process.
	 */
	private Vector<String> fixReader_Mu() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Transition> vtrans = (pn.getReader()).getTransitions();
		Vector<Arc> varcs = (pn.getReader()).getArcs();
		
		Iterator<Transition> it = vtrans.iterator();
		
		while (it.hasNext()) {
			
			Transition t = (Transition)it.next();
			
			if (t.getLabel().startsWith(mu_prefix)) {
				
				Iterator<Arc> ia = varcs.iterator();
				String if_cond = "";
				String if_body = "";
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if (a.getSrc().getLabel().startsWith("re") ||
									a.getSrc().getLabel().startsWith("rne")) {
								
								if_cond += "*" + a.getSrc().getLabel() +
									" == true";
								if_body += "\t\t\t*" + a.getSrc().getLabel() +
									" = false;\n";
							} else {
								
								if_cond += a.getSrc().getLabel() + " == true";
								if_body += "\t\t\t" + a.getSrc().getLabel() +
									" = false;\n";
							}
						} else if (a.getType() == Arc.trans2place) {
						
							if (a.getDest().getLabel().startsWith("re") ||
									a.getDest().getLabel().startsWith("rne")) {
								
								if_body += "\t\t\t*" + a.getDest().getLabel() +
									" = true;\n";
							} else {
								
								if_body += "\t\t\t" + a.getDest().getLabel() +
									" = true;\n";
							}
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += "*" + a.getDest().getLabel() + " == true";
						}
					}
				}
				
				if (t.getLabel().endsWith("e1") || t.getLabel().endsWith("e2")) {
					
					vaux.add("\t\t// " + t.getLabel() + "\n" +
							"\t\tif (" + if_cond + ") {\n\n" + if_body + "\t\t}\n");
				} else {
					
					vaux.add("\t\t// " + t.getLabel() + "\n" +
							"\t\tif (" + if_cond + ") {\n\n" + if_body + 
							"\t\t\tbreak;\n" + "\t\t}\n");
				}
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ code for the RRBB writer process.
	 */
	protected void fixWriter_cpp() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.writer_cpp.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(init_writer)) {
				
				Iterator<String> iaux =  fixWriter_Writer().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_local_text)) {
				
				Iterator<String> iaux =  fixWriter_InitLocalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(init_ext_text)) {
				
				Iterator<String> iaux =  fixWriter_InitExternalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(dt_local_text)) {
				
				Iterator<String> iaux =  fixWriter_DtLocalShv().iterator();
				
				while (iaux.hasNext()) {
					
					vaux.add((String)iaux.next());
				}
			} else if (saux.contains(dt_ext_text)) {
				
				Iterator<String> iaux =  fixWriter_DtExternalShv().iterator();
				
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
		
		this.writer_cpp = vaux;
	}
	
	/**
	 * Fix the C++ header file for the RRBB writer process.
	 */
	protected void fixWriter_h() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = null;
		Iterator<String> i = this.writer_h.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, String.valueOf(pn.getSize()));
			} else if (saux.contains(dec_text)) {
				
				vplace = (pn.getWriter()).getPlaces();
				Iterator<Place> iaux = vplace.iterator();
				saux = "\t\t// writer variables\n";
				
				while (iaux.hasNext()) {
					
					vaux.add(saux);
					Place p = (Place)iaux.next();
					
					if ((pn.getWriter()).isTestPlace(p)   ||
							p.getLabel().startsWith("we") ||
							p.getLabel().startsWith("l")) {
						
						saux = "\t\t" + shv_t + p.getLabel() + ";\n";
						vaux.add(saux);
						saux = "\t\t" + shmid_t + " " + p.getLabel() +
							shmid_s + ";\n";
					} else {
						
						saux = "\t\t" + ctrlv_t + " " + p.getLabel() + ";\n";
					}
				}
			}
			
			vaux.add(saux);
		}
		
		this.writer_h = vaux;
	}
	
	/**
	 * Fix the C++ Writer::Writer() method for the RRBB writer process.
	 */
	private Vector<String> fixWriter_Writer() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "\t// writer variables\n";
		vaux.add(saux);
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (!((pn.getWriter()).isTestPlace(p)   ||
					p.getLabel().startsWith("we") ||
					p.getLabel().startsWith("l"))) {
				
				saux = "\t" + p.getLabel() + " = " +
					(p.getMarking() == 1 ? "true" : "false") + ";\n";
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Writer::InitLocalShv() method for the RRBB writer process.
	 */
	private Vector<String> fixWriter_InitLocalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("we") ||
					p.getLabel().startsWith("l")) {
				
				saux = if_shmget_src.replace(shmid_text, 
								p.getLabel() + shmid_s) + 
						if_shmat_src.replace(shmid_text, 
								p.getLabel() + shmid_s);
				saux = saux.replace(hash_text,
						String.valueOf(this.getLabelIndex(p.getLabel())));
				saux = saux.replace(ctrlv_text, p.getLabel());
				saux = saux.replace(marking_text,
						String.valueOf(p.getMarking()));
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Writer::InitExternalShv() method for the RRBB writer process.
	 */
	private Vector<String> fixWriter_InitExternalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if ((pn.getWriter()).isTestPlace(p)) {
				
				saux = if_shmget_dest.replace(shmid_text, 
								p.getLabel() + shmid_s) + 
						if_shmat_dest.replace(shmid_text, 
								p.getLabel() + shmid_s);
				saux = saux.replace(hash_text,
						String.valueOf(this.getLabelIndex(p.getLabel())));
				saux = saux.replace(ctrlv_text, p.getLabel());
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Writer::DtLocalShv() method for the RRBB writer process.
	 */
	private Vector<String> fixWriter_DtLocalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if (p.getLabel().startsWith("we") ||
					p.getLabel().startsWith("l")) {
				
				saux = if_shmdt_src.replace(ctrlv_text, p.getLabel());
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}

	/**
	 * Fix the C++ Writer::DtExternalShv() method for the RRBB writer process.
	 */
	private Vector<String> fixWriter_DtExternalShv() {
		
		Vector<String> vaux = new Vector<String>();
		Vector<Place> vplace = (pn.getWriter()).getPlaces();
		Iterator<Place> iaux = vplace.iterator();
		String saux = "";
		
		while (iaux.hasNext()) {
			
			Place p = (Place)iaux.next();
			
			if ((pn.getWriter()).isTestPlace(p)) {
				
				saux = if_shmdt_dest.replace(ctrlv_text, p.getLabel());
				saux = saux.replace(shmid_text, p.getLabel() + shmid_s);
				
				vaux.add(saux);
			}
		}
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Writer::Send() method for the RRBB writer process.
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
				String cs = t.getLabel().substring(write_prefix.length());
				int slot = Integer.parseInt(cs.substring(cs.length() - 1));
				int cell = Integer.parseInt(cs.substring(0, cs.length() - 1));
				
				while (ia.hasNext()) {
					
					Arc a = (Arc)ia.next();
					
					if (a.getSrc().getUUID().equals(t.getUUID()) ||
							a.getDest().getUUID().equals(t.getUUID())) {
						
						if (a.getType() == Arc.place2trans) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if (a.getSrc().getLabel().startsWith("we") ||
									a.getSrc().getLabel().startsWith("wne")) {
								
								if_cond += "*" + a.getSrc().getLabel() +
									" == true";
								if_body += "\t\t*" + a.getSrc().getLabel() +
									" = false;\n";
							} else {
								
								if_cond += a.getSrc().getLabel() + " == true";
								if_body += "\t\t" + a.getSrc().getLabel() +
									" = false;\n";
							}
						} else if (a.getType() == Arc.trans2place) {
						
							if (a.getDest().getLabel().startsWith("we") ||
									a.getDest().getLabel().startsWith("wne")) {
								
								if_body += "\t\t*" + a.getDest().getLabel() +
									" = true;\n";
							} else {
								
								if_body += "\t\t" + a.getDest().getLabel() +
									" = true;\n";
							}
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += "*" + a.getDest().getLabel() + " == true";
						}
					}
				}
				
				if_body += "\t\t*(shm_data + " + (2 * cell + slot) + ") = val;\n";
				
				if (vaux.isEmpty()) {
					
					vaux.add("\tif (" + if_cond + ") {\n" + 
							"\t\t// " + t.getLabel() + "\n" + if_body + "\t}");
				} else {
					
					vaux.add(" else if (" + if_cond + ") {\n" + 
							"\t\t// " + t.getLabel() + "\n" + if_body + "\t}");
				}
			}
		}
		
		vaux.add("\n");
		
		return vaux;
	}
	
	/**
	 * Fix the C++ Writer::Lambda() method for the RRBB writer process.
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
							
							if (a.getSrc().getLabel().startsWith("we") ||
									a.getSrc().getLabel().startsWith("l")) {
								
								if_cond += "*" + a.getSrc().getLabel() +
									" == true";
								if_body += "\t\t*" + a.getSrc().getLabel() +
									" = false;\n";
							} else {
								
								if_cond += a.getSrc().getLabel() + " == true";
								if_body += "\t\t" + a.getSrc().getLabel() +
									" = false;\n";
							}
						} else if (a.getType() == Arc.trans2place) {
						
							if (a.getDest().getLabel().startsWith("we") ||
									a.getDest().getLabel().startsWith("l")) {
								
								if_body += "\t\t*" + a.getDest().getLabel() +
									" = true;\n";
							} else {
								
								if_body += "\t\t" + a.getDest().getLabel() +
									" = true;\n";
							}
						} else if (a.getType() == Arc.test) {
						
							if_cond += if_cond.equals("") ? "" : " && ";
							
							if_cond += "*" + a.getDest().getLabel() + " == true";
						}
					}
				}
				
				vaux.add("\t// " + t.getLabel() + "\n" +
						"\tif (" + if_cond + ") {\n\n" + if_body + "\t}\n");
			}
		}
		
		return vaux;
	}
	
	/**
	 * Gets the index of a given string in the vector of all labels.
	 * 
	 * @param label - the string with the label to look for
	 * @return the index of the label, or -1 if it does not exists
	 */
	private int getLabelIndex(String label) {
		
		int cont = 0;
		Iterator<String> i = allLabels.iterator();
		
		while (i.hasNext()) {
			
			String l = (String)i.next();
			
			if (l.equals(label)) {
				
				return cont;
			}
			cont++;
		}
		
		return -1;
	}
}
