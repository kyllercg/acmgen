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
public class VerilogRRBBcode extends VerilogCode {

	/**
	 * The RRBB ACM Petri net model.
	 */
	private RRBB pn = null;
	
	protected static final String IDLE_code			=
		"\t\t\tstate[" + IDLE + "COUNT]: begin\n" +
		"\t\t\t\tif (ereq) begin\n" +
		"\t\t\t\t\teack <= 1'b0;\n" +
		"\t\t\t\t\treq <= 1'b1;\n" +
		"\t\t\t\t\tstate[" + IDLE + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[INITCOUNT] <= 1'b1;\n" +
		"\t\t\t\tend\n" +
		"\t\t\tend\n";
	
	protected static final String INIT_rd_code			=
		"\t\t\tstate[" + INIT + "COUNT]: begin\n" +
		"\t\t\t\tif (ack) begin\n" +
		"\t\t\t\t\treq <= 1'b0;\n" +
		"\t\t\t\t\tedata <= data;\n" +
		"\t\t\t\t\tstate[" + INIT + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b1;\n" +
		"\t\t\t\tend\n" +
		"\t\t\tend\n";
		
	protected static final String END_rd_code			=
		"\t\t\tstate[" + END + "COUNT]: begin\n" +
		"\t\t\t\tif (!wr) begin\n" +
		"\t\t\t\t\tcellCOUNT <= 1'b0;\n" +
		"\t\t\t\t\tcellNEXT <= 1'b1;\n" +
		"\t\t\t\t\tsel <= NNEXT;\n" +
		"\t\t\t\t\teack <= 1'b1;\n" +
		"\t\t\t\t\taddr <= NEXT;\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[" + IDLE + "NEXT] <= 1'b1;\n" +
		"\t\t\t\tend else begin\n" +
		"\t\t\t\t\tcellPREVIOUS <= 1'b0;\n" +
		"\t\t\t\t\tcellCOUNT <= 1'b1;\n" +
		"\t\t\t\t\teack <= 1'b1;\n" +
		"\t\t\t\t\taddr <= COUNT;\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[" + IDLE + "COUNT] <= 1'b1;\n" +
		"\t\t\t\tend\n" +
		"\t\t\tend\n";
	
	protected static final String INIT_wr_code			=
		"\t\t\tstate[" + INIT + "COUNT]: begin\n" +
		"\t\t\t\tif (ack) begin\n" +
		"\t\t\t\t\treq <= 1'b0;\n" +
		"\t\t\t\t\tdata <= edata;\n" +
		"\t\t\t\t\tstate[" + INIT + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b1;\n" +
		"\t\t\t\tend\n" +
		"\t\t\tend\n";
	
	protected static final String END_wr_code			=
		"\t\t\tstate[" + END + "COUNT]: begin\n" +
		"\t\t\t\tif (!rd) begin\n" +
		"\t\t\t\t\tcellCOUNT <= 1'b0;\n" +
		"\t\t\t\t\tcellNEXT <= 1'b1;\n" +
		"\t\t\t\t\tsel <= NNEXT;\n" +
		"\t\t\t\t\teack <= 1'b1;\n" +
		"\t\t\t\t\taddr <= NEXT;\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b0;\n" +
		"\t\t\t\t\tstate[" + IDLE + "NEXT] <= 1'b1;\n" +
		"\t\t\t\tend else begin\n" +
		"\t\t\t\t\tstate[" + END + "COUNT] <= 1'b1;\n" +
		"\t\t\t\tend\n" +
		"\t\t\tend\n";
	
	protected static final String flip_flop_decl		=
		"// flip-flop for cell COUNT\n" +
		"flip_flop_reset FFNAMECOUNT(\n" +
		"\t.D(WIRE_cell_COUNT),\n" +
		"\t.Q(FFNAME[RCOUNT]),\n" +
		"\t.clock(clock),\n" +
		"\t.reset(reset));\n" +
		"defparam FFNAMECOUNT.init_value = INIT_VALUE;\n\n";
	
	/**
	 * The class constructor.
	 * 
	 * @param pn - a RRBB PetriNet object
	 */
	public VerilogRRBBcode(RRBB pn) {

		this.pn = pn;
		this.NUMBER_STATES = 3 * pn.getSize();
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
						String.valueOf(pn.getSize() -1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			} else if (saux.contains(acm_cell_parms)) {
				
				saux = this.fix_CellParms();
			} else if (saux.contains(acm_wire_decl)) {
				
				saux = this.fix_WireDecl();
			} else if (saux.contains(acm_flip_flops)) {
				
				saux = this.fix_FlipFlopsDecl(0, VerilogCode.READER);
			}
			
			vaux.add(saux);
		}
		
		this.reader_v = vaux;
	}
	
	/**
	 * Fix the Verilog code for the RRBB reader engine module.
	 */
	protected void fixReaderEngine_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.reader_engine_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text,
						String.valueOf(pn.getSize() -1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			} else if (saux.contains(acm_max_states)) {
				
				saux = saux.replace(acm_max_states, 
						String.valueOf(this.NUMBER_STATES));
			} else if (saux.contains(acm_max_states_)) {
				
				saux = saux.replace(acm_max_states_, 
						String.valueOf(this.NUMBER_STATES - 1));
			} else if (saux.contains(acm_cell_parms)) {
				
				saux = this.fix_CellParmsEngine();
			} else if (saux.contains(acm_cell_io_decl)) {
				
				saux = this.fix_CellIOdecl();
			} else if (saux.contains(acm_cell_decl)) {
				
				saux = this.fix_CellDecl();
			} else if (saux.contains(acm_states_def)) {
				
				saux = this.fix_StatesEnum();
			} else if (saux.contains(init_cells)) {
				
				saux = this.fix_InitCells(0);
			} else if (saux.contains(acm_fsm)) {
				
				saux = this.fixReader_FSMengine();
			}
			
			vaux.add(saux);
		}
		
		this.reader_engine_v = vaux;
	}
	
	/**
	 * Fix the Verilog Finite State Machine stuff on reader engine module.
	 * 
	 * @return a string with the FSM to go on module
	 */
	private String fixReader_FSMengine() {
		
		String saux = "";
		String fsm = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux = VerilogRRBBcode.IDLE_code +
					VerilogRRBBcode.INIT_rd_code +
					VerilogRRBBcode.END_rd_code;
				
			saux = saux.replace(VerilogCode.COUNT, String.valueOf(c));
			saux = saux.replace(VerilogCode.NNEXT,
					String.valueOf((c + 2) % pn.getSize()));
			saux = saux.replace(VerilogCode.NEXT,
					String.valueOf((c + 1) % pn.getSize()));
			saux = saux.replace(VerilogCode.PREVIOUS,
					String.valueOf(c == 0 ? pn.getSize() - 1 : c - 1));
			
			fsm += saux;
		}
		
		return fsm;
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
						String.valueOf(pn.getSize() -1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			} else if (saux.contains(acm_cell_parms)) {
				
				saux = this.fix_CellParms();
			} else if (saux.contains(acm_wire_decl)) {
				
				saux = this.fix_WireDecl();
			} else if (saux.contains(acm_flip_flops)) {
				
				saux = this.fix_FlipFlopsDecl(1, VerilogCode.WRITER);
			}
			
			vaux.add(saux);
		}
		
		this.writer_v = vaux;
	}

	/**
	 * Fix the Verilog code for the RRBB writer engine module.
	 */
	protected void fixWriterEngine_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.writer_engine_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text,
						String.valueOf(pn.getSize() -1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			} else if (saux.contains(acm_max_states)) {
				
				saux = saux.replace(acm_max_states, 
						String.valueOf(this.NUMBER_STATES));
			} else if (saux.contains(acm_max_states_)) {
				
				saux = saux.replace(acm_max_states_, 
						String.valueOf(this.NUMBER_STATES - 1));
			} else if (saux.contains(acm_cell_parms)) {
				
				saux = this.fix_CellParmsEngine();
			} else if (saux.contains(acm_cell_io_decl)) {
				
				saux = this.fix_CellIOdecl();
			} else if (saux.contains(acm_cell_decl)) {
				
				saux = this.fix_CellDecl();
			} else if (saux.contains(acm_states_def)) {
				
				saux = this.fix_StatesEnum();
			} else if (saux.contains(init_cells)) {
				
				saux = this.fix_InitCells(1);
			} else if (saux.contains(acm_fsm)) {
				
				saux = this.fixWriter_FSMengine();
			}
			
			vaux.add(saux);
		}
		
		this.writer_engine_v = vaux;
	}
	
	/**
	 * Fix the Verilog Finite State Machine stuff on writer engine module.
	 * 
	 * @return a string with the FSM to go on module
	 */
	private String fixWriter_FSMengine() {
		
		String saux = "";
		String fsm = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux = VerilogRRBBcode.IDLE_code +
					VerilogRRBBcode.INIT_wr_code +
					VerilogRRBBcode.END_wr_code;
				
			saux = saux.replace(VerilogCode.COUNT, String.valueOf(c));
			saux = saux.replace(VerilogCode.NNEXT,
					String.valueOf((c + 2) % pn.getSize()));
			saux = saux.replace(VerilogCode.NEXT,
					String.valueOf((c + 1) % pn.getSize()));
			saux = saux.replace(VerilogCode.PREVIOUS,
					String.valueOf(c == 0 ? pn.getSize() - 1 : c - 1));
			
			fsm += saux;
		}
		
		return fsm;
	}
	
	/**
	 * Fix the Verilog cell declaration stuff on process engine module.
	 * 
	 * @return a string of cell declarations
	 */
	private String fix_CellDecl() {
		
		String saux = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux += VerilogCode.st_cell_decl + c + ";\n";
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog cell IO declaration stuff on process engine module.
	 * 
	 * @return a string of cell declarations
	 */
	private String fix_CellIOdecl() {
		
		String saux = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux += VerilogCode.st_output + c + ";\n";
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog cell parameters stuff on process module.
	 * 
	 * @return a string of cell parameters to go on module declaration
	 */
	private String fix_CellParms() {
		
		String saux = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux += "\t." + VerilogCode.st_cell + c +
					"(" + VerilogCode.st_wire + c + "),\n";
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog cell parameters stuff on process engine module.
	 * 
	 * @return a string of cell parameters to go on module declaration
	 */
	private String fix_CellParmsEngine() {
		
		String saux = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux += "\t" + VerilogCode.st_cell + c + ",\n";
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog flip-flops declarations of the cells on process module.
	 * 
	 * @param i the index of the flip-flip to initialize with value 1
	 * @param type should be 0 if it's the READER or 1 if it's the WRITER
	 * @return a string with the FSM to go on module
	 */
	private String fix_FlipFlopsDecl(int i, int type) {
		
		String saux = "";
		String flip_flops = "";
		int c, rc;
		
		for (c = 0, rc = pn.getSize() - 1; c < pn.getSize(); c++, rc--) {
			
			saux = VerilogRRBBcode.flip_flop_decl;
			
			if (type == VerilogCode.READER) {
				
				saux = saux.replace(VerilogCode.FFNAME, VerilogCode.st_rd);
			} else if (type == VerilogCode.WRITER) {
				
				saux = saux.replace(VerilogCode.FFNAME, VerilogCode.st_wr);
			}
			saux = saux.replace(VerilogCode.RCOUNT, String.valueOf(rc));
			saux = saux.replace(VerilogCode.COUNT, String.valueOf(c));
			saux = saux.replace(VerilogCode.INIT_VALUE,
					String.valueOf(c == i ? 1 : 0));
			
			flip_flops += saux;
		}
		
		return flip_flops;
	}

	/**
	 * Fix the Verilog cell declaration stuff on process engine module.
	 * 
	 * @param i the index of the cell to initialize with value 1
	 * @return a string of cell declarations
	 */
	private String fix_InitCells(int i) {
		
		String saux = "";
				
		for (int c = 0; c < pn.getSize(); c++) {
			
			saux += c == i ? "\t\t" + VerilogCode.st_cell + c +" <= 1'b1;\n"					
					: "\t\t" + VerilogCode.st_cell + c + " <= 1'b0;\n";
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog cell parameters stuff on process engine module.
	 * 
	 * @return a string of cell parameters to go on module declaration
	 */
	private String fix_StatesEnum() {
		
		String saux = "";
		int c, i;
		
		for (c = 0, i = 0; c < pn.getSize(); c++) {
			
			saux += "\t" + VerilogCode.IDLE + c + " = " + i++ + ",\n" +
					"\t" + VerilogCode.INIT + c + " = " + i++ + ",\n" +
					"\t" + VerilogCode.END + c + " = " + i++ + "";
			
			if (c != pn.getSize() - 1) {
				
				saux += ",\n";
			} else {
				
				saux += ";\n";
			}
		}
		
		return saux;
	}

	/**
	 * Fix the Verilog wires declaration stuff on process module.
	 * 
	 * @return a string of cell declarations
	 */
	private String fix_WireDecl() {
		
		String saux = "";
		int c;
		
		for (c = 0; c < pn.getSize(); c++) {
			
			saux += VerilogCode.st_wire_decl + c + ";\n";
		}
		
		return saux;
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
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, 
						String.valueOf(pn.getSize() - 1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			}
			
			vaux.add(saux);
		}
		
		this.shmem_v = vaux;
	}

	/**
	 * Fix the Verilog RRBB ACM main module.
	 */
	protected void fixACM_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.acm_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			}
			
			vaux.add(saux);
		}
		
		this.acm_v = vaux;
	}
	
	/**
	 * Fix the Verilog code for the MUX module.
	 */
	protected void fixMux_v() {
		
		Vector<String> vaux = new Vector<String>();
		Iterator<String> i = this.mux_v.iterator();
		String saux = "";
		
		while (i.hasNext()) {
		
			saux = (String)i.next();
			
			if (saux.contains(acm_size_text)) {
				
				saux = saux.replace(acm_size_text, String.valueOf(pn.getSize() - 1));
			} else if (saux.contains(acm_size_text_log)) {
				
				saux = saux.replace(acm_size_text_log, 
						String.valueOf((int)(Math.log10(pn.getSize()) / Math.log10(2))));
			} else if (saux.contains(acm_mux_text)) {
				
				saux = fixMux_Case();
			}
			
			vaux.add(saux);
		}
		
		this.mux_v = vaux;
	}
	
	/**
	 * Fix the Verilog if-then-else stuff on the multiplexer.
	 * 
	 * @return a string containing the if-then-else statements
	 */
	private String fixMux_Case() {
		
		String saux = "";
		String saux2 = VerilogCode.mux_if.replace(SIZE,
				String.valueOf(pn.getSize() - 1));;
		int c, rc;
		
		for (c = pn.getSize() - 2, rc = 1; c >= 0; c--, rc++) {
			
			saux = VerilogCode.mux_else.replace(RCOUNT, String.valueOf(rc));
			saux = saux.replaceAll(COUNT, String.valueOf(c));
			saux2 += saux;
		}
		
		return saux2;
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
			
			nlabel = "ri[" + label.substring(2) + "]";
		} else if (label.startsWith("pri")) {
			
			nlabel = "pri[" + label.substring(3) + "]";
		} else if (label.startsWith("re")) {
			
			nlabel = "re[" + label.substring(2) + "]";
		} else if (label.startsWith("rne")) {
			
			nlabel = "rne[" + label.substring(3) + "]";
		} else if (label.startsWith("wi")) {
			
			nlabel = "wi[" + label.substring(2) + "]";
		} else if (label.startsWith("pwi")) {
			
			nlabel = "pwi[" + label.substring(3) + "]";
		} else if (label.startsWith("we")) {
			
			nlabel = "we[" + label.substring(2) + "]";
		} else if (label.startsWith("wne")) {
			
			nlabel = "wne[" + label.substring(3) + "]";
		}
		
		return nlabel;
	}
}
