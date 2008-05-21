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

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public abstract class VerilogCode extends SourceCode {
	
	protected static final int READER				= 0;
	protected static final int WRITER				= 1;
	
	protected static final String acm_size_text2	= "%%%ACMgen:ACM_SIZE*2%%%";
	protected static final String acm_size_text_log	= "%%%ACMgen:ACM_SIZE_LOG%%%";
	protected static final String acm_max_states	= "%%%ACMgen:MAX_STATES%%%";
	protected static final String acm_max_states_	= "%%%ACMgen:MAX_STATES-1%%%";
	protected static final String acm_mux_text		= "%%%ACMgen:MUX_CASE%%%";
	
	protected static final String acm_cell_parms	= "%%%ACMgen:cell_parms%%%";
	protected static final String acm_cell_decl		= "%%%ACMgen:cell_decl%%%";
	protected static final String acm_cell_io_decl	= "%%%ACMgen:cell_io_decl%%%";
	protected static final String acm_states_def	= "%%%ACMgen:states_def%%%";
	protected static final String init_cells		= "%%%ACMgen:init%%%";
	protected static final String acm_fsm			= "%%%ACMgen:fsm%%%";
	protected static final String acm_wire_decl		= "%%%ACMgen:wire_decl%%%";
	protected static final String acm_flip_flops	= "%%%ACMgen:flip_flops%%%";
	
	protected static final String init_ext_text		= "%%%ACMgen:InitExternal()%%%";
	protected static final String init_local_text	= "%%%ACMgen:InitLocal()%%%";
	
	protected static final String run_text			= "%%%ACMgen:RunBabyRun()%%%";
	
	protected static final String run_infix			= "e";
	
	protected static final String SIZE				= "SIZE";
	protected static final String COUNT				= "COUNT";
	protected static final String RCOUNT			= "RCOUNT";
	protected static final String NEXT				= "NEXT";
	protected static final String NNEXT				= "NNEXT";
	protected static final String PREVIOUS			= "PREVIOUS";
	protected static final String INIT_VALUE		= "INIT_VALUE";
	protected static final String FFNAME			= "FFNAME";
	
	protected static final String st_rd				= "rd";
	protected static final String st_wr				= "wr";
	
	protected static final String st_cell			= "cell";
	protected static final String st_cell_decl		= "reg " + st_cell;
	protected static final String st_output			= "output\t" + st_cell;
	protected static final String st_wire			= "WIRE_" + st_cell + "_";
	protected static final String st_wire_decl		= "wire\t" + st_wire;
	protected static final String IDLE				= "IDLE";
	protected static final String INIT				= "INIT";
	protected static final String END				= "END";
	
	protected static final String mux_if			= 
		"\tif (sel == SIZE) begin\n" +
		"\t\tresult <= data[0];\n" +
		"\tend";
	protected static final String mux_else			= 
		" else if (sel == COUNT) begin\n" +
		"\t\tresult <= data[RCOUNT];\n" +
		"\tend";
	
	/**
	 * The total number of states for each process engine.
	 */
	protected int NUMBER_STATES;
	
	/**
	 * The Verilog code for the reader module.
	 */
	protected Vector<String> reader_v = null;
	
	/**
	 * The Verilog code for the reader engine module.
	 */
	protected Vector<String> reader_engine_v = null;
	
	/**
	 * The Verilog code for the writer module.
	 */
	protected Vector<String> writer_v = null;
	
	/**
	 * The Verilog code for the writer engine module.
	 */
	protected Vector<String> writer_engine_v = null;
	
	/**
	 * The Verilog code for the shared memory module.
	 */
	protected Vector<String> shmem_v = null;
	
	/**
	 * The Verilog code for the main module.
	 */
	protected Vector<String> acm_v = null;
	
	/**
	 * The Verilog code for the MUX module.
	 */
	protected Vector<String> mux_v = null;
	
	/**
	 * Class constructor.
	 */
	public VerilogCode() {
		
		super();
	}
	
	/**
	 * Generates the C++ code for the ACM.
	 * 
	 * @param reader_v - a string with the reader Verilog template file
	 * @param reader_engine_v - a string with the reader engine Verilog
	 *        template file
	 * @param writer_v - a string with the writer Verilog template file
	 * @param writer_engine_v - a string with the writer engine Verilog
	 *        template file
	 * @param shmem_v - a string with the shared memory Verilog template file
	 * @param acm_v - a string with the acm main module Verilog template file
	 * @param mux_v - a string with the MUX module Verilog template file
	 */
	public void genCode(Vector<String> reader_v, Vector<String> reader_engine_v,
			Vector<String> writer_v, Vector<String> writer_engine_v,  
			Vector<String> shmem_v, Vector<String> acm_v,
			Vector<String> mux_v) {
		
		this.reader_v = reader_v;
		this.reader_engine_v = reader_engine_v;
		this.writer_v = writer_v;
		this.writer_engine_v = writer_engine_v;
		this.shmem_v = shmem_v;
		this.acm_v = acm_v;
		this.mux_v = mux_v;
		
		this.genReader();
		this.genWriter();
		this.genShMem();
		this.genACM();
		this.genMux();
	}
	
	/**
	 * Generates the Verilog code for the READER module.
	 */
	protected void genReader() {
		
		fixReader_v();
		fixReaderEngine_v();
	}
	
	/**
	 * Generates the Verilog code for the WRITER module.
	 */
	protected void genWriter() {
		
		fixWriter_v();
		fixWriterEngine_v();
	}
	
	/**
	 * Generates the Verilog code for the SHMEM module.
	 */
	protected void genShMem() {
		
		fixShMem_v();
	}
	
	/**
	 * Generates the Verilog code for the MUX module.
	 */
	protected void genMux() {
		
		fixMux_v();
	}
	
	/**
	 * Generates the Verilog code for the ACM module.
	 */
	protected void genACM() {
		
		fixACM_v();
	}
	
	protected abstract void fixReader_v();
	protected abstract void fixReaderEngine_v();
	protected abstract void fixWriter_v();
	protected abstract void fixWriterEngine_v();
	protected abstract void fixShMem_v();
	protected abstract void fixACM_v();
	protected abstract void fixMux_v();
	
	/**
	 * Gets the Verilog code for the reader module.
	 * 
	 * @return - a vector of strings with the Verilog code for the READER module
	 */
	public Vector<String> getReader_v() {
		
		return reader_v;
	}
	
	/**
	 * Gets the Verilog code for the reader engine module.
	 * 
	 * @return - a vector of strings with the Verilog code for the READER 
	 *           engine module
	 */
	public Vector<String> getReaderE_v() {
		
		return reader_engine_v;
	}
	
	/**
	 * Gets the Verilog code for the writer module.
	 * 
	 * @return - a vector of strings with the Verilog code for the WRITER module
	 */
	public Vector<String> getWriter_v() {
		
		return writer_v;
	}
	
	/**
	 * Gets the Verilog code for the writer engine module.
	 * 
	 * @return - a vector of strings with the Verilog code for the WRITER
	 *           engine module
	 */
	public Vector<String> getWriterE_v() {
		
		return writer_engine_v;
	}
	
	/**
	 * Gets the Verilog code for the shared memory module.
	 * 
	 * @return - a vector of strings with the Verilog code for the SHMEM module
	 */
	public Vector<String> getShMem_v() {
		
		return shmem_v;
	}
	
	/**
	 * Gets the Verilog code for the ACM main module.
	 * 
	 * @return - a vector of strings with the Verilog code for the ACM module
	 */
	public Vector<String> getACM_v() {
		
		return acm_v;
	}
	
	/**
	 * Gets the Verilog code for the MUX module.
	 * 
	 * @return - a vector of strings with the Verilog code for the MUX module
	 */
	public Vector<String> getMux_v() {
		
		return mux_v;
	}
}
