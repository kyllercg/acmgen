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

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import net.kyllercg.util.FileUtils;
import net.kyllercg.util.I18Printer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class ACMgen {
	
	protected boolean a_cpp 		= false;
	protected boolean a_pep 		= false;
	protected boolean a_petrify 	= false;
	protected boolean a_smv	 		= false;
	protected boolean a_smv_pn 		= false;
	protected boolean a_verilog		= false;
	
	protected int ACM_TYPE;
	protected int ACM_SIZE;
	
	protected RRBB rrbb = null;
	protected OWBB owbb = null;
	protected OWRRBB owrrbb = null;
	protected CppCode cppcode = null;
	protected PNCode pncode = null;
	protected SmvCode smvcode = null;
	protected SmvCode smvpncode = null;
	protected VerilogCode verilogcode = null;
	
	private String filebasename = "";
	
	private Options options;
	
	static I18Printer msg;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ACMgen acm = new ACMgen(args);
		
		acm.genACM();
		acm.printACMcode();
	}
	
	/**
	 * Default class constructor. Required by ACMgen_gtk.
	 */
	public ACMgen() {}
	
	/**
	 * Class contructor
	 * @param args - the command line parameters
	 */
	public ACMgen(String[] args) {
		
		ACMgen.msg = new I18Printer(ACMgenOptions.i18BaseName);
		
		if (System.getProperty("acmgen.dir") != null) {
			
			if (System.getProperty("acmgen.dir").endsWith("/")) {

				ACMgenOptions.setPrefix(System.getProperty("acmgen.dir"));
			} else {

				ACMgenOptions.setPrefix(System.getProperty("acmgen.dir") + "/");
			}
		}
		
		FileUtils rf = new FileUtils();
		Properties props = rf.readPropsFile(ACMgenOptions.props_file);
		
		ACMgenOptions.pep2smv_file = props.getProperty(ACMgenOptions.pep2smv_key);
    	ACMgenOptions.output_file = props.getProperty(ACMgenOptions.output_key);
    	ACMgenOptions.tmp_dir = props.getProperty(ACMgenOptions.tmp_dir_key);
    	
		this.createOptions();
		
		try {
			
			CommandLineParser parser = new PosixParser();
			CommandLine line = parser.parse(options, args);
			this.parseCmdLineOp(line, options);
			this.setFileBaseName();
		} catch (ParseException e) {
			
			this.printErrorMsg(msg.getMessage("parse_error_msg"));
	        HelpFormatter f = new HelpFormatter();
			f.printHelp(msg.getMessage("cmdlineoptions"), options);
		} catch (Exception e) {
			
			this.printErrorMsg(msg.getMessage("unknown_error_msg") +
	        		e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates the ACM stuff. e.g.:
	 * 
	 * 		- Petri net models for PEP
	 * 		- SMV models
	 * 		- C++ code
	 */
	protected void genACM() {
		
		switch(this.ACM_TYPE) {
			
			case ACMgenOptions.t_rrbb:
				
				this.genRRBBacm();
				break;
				
			case ACMgenOptions.t_owbb:
				
				this.genOWBBacm();
				break;
		
			case ACMgenOptions.t_owrrbb:

				this.genOWRRBBacm();
				break;
				
			default:
				
				break;
		}
	}

	/**
	 * Generates the RRBB ACM stuff. e.g.:
	 * 
	 * 		- Petri net models for PEP
	 * 		- SMV models
	 * 		- C++ code
	 * 		- Verilog code
	 */
	private void genRRBBacm() {
		
		this.rrbb = new RRBB(this.ACM_SIZE);
		
		if (this.a_cpp) {
			
			FileUtils f = new FileUtils();
			
			this.cppcode = new CppRRBBcode(this.rrbb);
			this.cppcode.genCode(
					f.readTextFile(ACMgenOptions.rrbb_c_rd +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.rrbb_c_rd +
							ACMgenOptions.h_ext),
					f.readTextFile(ACMgenOptions.rrbb_c_wr +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.rrbb_c_wr +
							ACMgenOptions.h_ext));
		}
		
		if (this.a_verilog) {
		
			FileUtils f = new FileUtils();
			
			this.verilogcode = new VerilogRRBBcode(this.rrbb);
			this.verilogcode.genCode(
					f.readTextFile(ACMgenOptions.rrbb_v_rd),
					f.readTextFile(ACMgenOptions.rrbb_v_rde),
					f.readTextFile(ACMgenOptions.rrbb_v_wr),
					f.readTextFile(ACMgenOptions.rrbb_v_wre),
					f.readTextFile(ACMgenOptions.rrbb_v_sm),
					f.readTextFile(ACMgenOptions.rrbb_v),
					f.readTextFile(ACMgenOptions.mux_v));
		}
		
		if (this.a_pep || this.a_smv_pn) {
		
			this.pncode = new PNCode(this.rrbb.getRRBB());
			this.pncode.genPEPcode();
		}
		
		if (this.a_smv) {
			
			this.smvcode = new SmvRRBBcode(this.ACM_SIZE);
			this.smvcode.genCode(ACMgenOptions.rrbb_smv);
		}
		
		if (this.a_smv_pn) {
			
			this.smvpncode = new SmvRRBBcode(this.ACM_SIZE,
				genPEPfromSMV(this.pncode.getPEPcode()));
			this.smvpncode.genCode(ACMgenOptions.rrbb_smvpn);
		}
	}
	
	/**
	 * Generates the OWBB ACM stuff. e.g.:
	 * 
	 * 		- Petri net models for PEP
	 * 		- SMV models
	 * 		- C++ code
	 */
	private void genOWBBacm() {
		
		this.owbb = new OWBB(this.ACM_SIZE);
		
		if (this.a_cpp) {
			
			FileUtils f = new FileUtils();
			
			this.cppcode = new CppOWRRBBcode(this.owbb);
			this.cppcode.genCode(
					f.readTextFile(ACMgenOptions.owrrbb_c_rd +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_rd +
							ACMgenOptions.h_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_wr +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_wr +
							ACMgenOptions.h_ext));
		}
		
		if (this.a_verilog) {
			
			FileUtils f = new FileUtils();
			
			this.verilogcode = new VerilogOWRRBBcode(this.owbb);
			this.verilogcode.genCode(
					f.readTextFile(ACMgenOptions.owrrbb_v_rd),
					f.readTextFile(ACMgenOptions.owrrbb_v_rde),
					f.readTextFile(ACMgenOptions.owrrbb_v_wr),
					f.readTextFile(ACMgenOptions.owrrbb_v_wre),
					f.readTextFile(ACMgenOptions.owrrbb_v_sm),
					f.readTextFile(ACMgenOptions.owrrbb_v),
					f.readTextFile(ACMgenOptions.mux_v));
		}
		
		if (this.a_pep || this.a_smv_pn) {
			
			this.pncode = new PNCode(this.owbb.getOWBB());
			this.pncode.genPEPcode();
		}
		
		if (this.a_smv) {
			
			this.smvcode = new SmvOWRRBBcode(this.ACM_SIZE);
			this.smvcode.genCode(ACMgenOptions.owrrbb_smv);
		}
		
		if (this.a_smv_pn) {
			
			this.smvpncode = new SmvOWRRBBcode(this.ACM_SIZE,
				genPEPfromSMV(this.pncode.getPEPcode()));
			this.smvpncode.genCode(ACMgenOptions.owrrbb_smvpn);
		}
	}
	
	/**
	 * Generates the OWRRBB ACM stuff. e.g.:
	 * 
	 * 		- Petri net models for PEP
	 * 		- SMV models
	 * 		- C++ code
	 */
	private void genOWRRBBacm() {
		
		owrrbb = new OWRRBB(this.ACM_SIZE);
		
		if (this.a_cpp) {
			
			FileUtils f = new FileUtils();
			
			this.cppcode = new CppOWRRBBcode(this.owrrbb);
			this.cppcode.genCode(
					f.readTextFile(ACMgenOptions.owrrbb_c_rd +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_rd +
							ACMgenOptions.h_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_wr +
							ACMgenOptions.cpp_ext),
					f.readTextFile(ACMgenOptions.owrrbb_c_wr +
							ACMgenOptions.h_ext));
		}
		
		if (this.a_verilog) {
			
			FileUtils f = new FileUtils();
			
			this.verilogcode = new VerilogOWRRBBcode(this.owrrbb);
			this.verilogcode.genCode(
					f.readTextFile(ACMgenOptions.owrrbb_v_rd),
					f.readTextFile(ACMgenOptions.owrrbb_v_rde),
					f.readTextFile(ACMgenOptions.owrrbb_v_wr),
					f.readTextFile(ACMgenOptions.owrrbb_v_wre),
					f.readTextFile(ACMgenOptions.owrrbb_v_sm),
					f.readTextFile(ACMgenOptions.owrrbb_v),
					f.readTextFile(ACMgenOptions.mux_v));
		}
		
		if (this.a_pep || this.a_smv_pn) {
			
			this.pncode = new PNCode(this.owrrbb.getOWRRBB());
			this.pncode.genPEPcode();
		}
		
		if (this.a_smv) {
			
			this.smvcode = new SmvOWRRBBcode(this.ACM_SIZE);
			this.smvcode.genCode(ACMgenOptions.owrrbb_smv);
		}
		
		if (this.a_smv_pn) {
			
			this.smvpncode = new SmvOWRRBBcode(this.ACM_SIZE,
				genPEPfromSMV(this.pncode.getPEPcode()));
			this.smvpncode.genCode(ACMgenOptions.owrrbb_smvpn);
		}
	}
	
	/**
	 * Sets the basename of the files (.smv, .llnet, etc.) to be saved.
	 */
	protected void setFileBaseName(String fbs) {
		
		filebasename = fbs;
	}
	
	/**
	 * Sets the basename of the files (.smv, .llnet, etc.) to be saved.
	 */
	protected void setFileBaseName() {
		
		filebasename = this.ACM_SIZE + "x";
		
		if (this.ACM_TYPE == ACMgenOptions.t_rrbb) {
			
			filebasename += "1-" + ACMgenOptions.rrbb;
		} else if (this.ACM_TYPE == ACMgenOptions.t_owbb) {
			
			filebasename += "2-" + ACMgenOptions.owbb;
		} else if (this.ACM_TYPE == ACMgenOptions.t_owrrbb) {
			
			filebasename += "2-" + ACMgenOptions.owrrbb;
		}
	}

	/**
	 * Writes the code of the ACM to the HD.
	 */
	protected void printACMcode() {
		
		FileUtils f = new FileUtils();
		
		if (this.a_cpp && this.cppcode != null) {
			
			f.writeTextFile("Reader" + ACMgenOptions.cpp_ext,
					this.cppcode.getReader_cpp());
			f.writeTextFile("Reader" + ACMgenOptions.h_ext,
					this.cppcode.getReader_h());
			f.writeTextFile("Writer" + ACMgenOptions.cpp_ext,
					this.cppcode.getWriter_cpp());
			f.writeTextFile("Writer" + ACMgenOptions.h_ext,
					this.cppcode.getWriter_h());
		} else if (this.a_cpp && this.cppcode == null) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("cppcode_error_msg"));
		}
		
		if (this.a_verilog && this.verilogcode != null) {
						
			f.writeTextFile("reader" + ACMgenOptions.verilog_ext,
					this.verilogcode.getReader_v());
			f.writeTextFile("reader_engine" + ACMgenOptions.verilog_ext,
					this.verilogcode.getReaderE_v());
			f.writeTextFile("writer" + ACMgenOptions.verilog_ext,
					this.verilogcode.getWriter_v());
			f.writeTextFile("writer_engine" + ACMgenOptions.verilog_ext,
					this.verilogcode.getWriterE_v());
			f.writeTextFile("shmem" + ACMgenOptions.verilog_ext,
					this.verilogcode.getShMem_v());
			f.writeTextFile("ACM" + ACMgenOptions.verilog_ext,
					this.verilogcode.getACM_v());
			f.writeTextFile("mux" + ACMgenOptions.verilog_ext,
					this.verilogcode.getMux_v());
			f.copyFile(ACMgenOptions.flip_flop_v,
					"flip_flop" + ACMgenOptions.verilog_ext);
		} else if (this.a_cpp && this.cppcode == null) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("cppcode_error_msg"));
		}

		if (this.a_pep && this.pncode != null) {
			
			f.writeTextFile(this.filebasename +
					ACMgenOptions.llnet_ext, this.pncode.getPEPcode());
		} else if (a_pep && pncode == null) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("pncode_error_msg"));
		}
			
		if (this.a_petrify) {

//			code.genPETRIFYcode();
//			f.writeTextFile(this.filebasename +
//					ACMgenOptions.llnet_ext, code.getCode());
			
			this.printErrorMsg("Petrify generator not implemented, yet");
		}
		
		if (this.a_smv && this.smvcode != null) {

			f.writeTextFile(this.filebasename +
					ACMgenOptions.smv_ext, this.smvcode.getCode());
		} else if (this.a_smv && this.smvcode == null) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("smvcode_error_msg"));
		}
	
		if (this.a_smv_pn && this.smvpncode != null && this.pncode != null) {

			f.writeTextFile(this.filebasename + "_pn" +
					ACMgenOptions.smv_ext, this.smvpncode.getCode());
		} else if (this.a_smv_pn && 
				(this.smvpncode == null || this.pncode == null)) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("smvpncode_error_msg"));
		}
	}
	
	/**
	 * System call to the pep2smv program to generate the SMV model from the
	 * PEP model.
	 * @param pepcode - the PEP model
	 * @return - a vector with the SMV model
	 */
	protected Vector<String> genPEPfromSMV(Vector<String> pepcode) {
		
		FileUtils f = new FileUtils();
		String pep_file = ACMgenOptions.tmp_dir + "/" +
			ACMgenOptions.output_file + ACMgenOptions.llnet_ext;
		String smv_file = ACMgenOptions.tmp_dir + "/" +
			ACMgenOptions.output_file + ACMgenOptions.smv_ext;
		String cmdline = ACMgenOptions.pep2smv_file + " " + pep_file;
		Vector<String> v = null;
		
		f.writeTextFile(pep_file, pepcode);
		
		try {
			
			Runtime r = Runtime.getRuntime();
			r.exec(cmdline).waitFor();
			
			FileUtils inf = new FileUtils();
			v = inf.readTextFile(smv_file);
			
			File f1 = new File(pep_file);
			File f2 = new File(smv_file);
			
			if (f1.exists()) {
				f1.delete();
			}
			
			if (f2.exists()) {	
				f2.delete();
			}
			
			if (v == null) {
				
				this.printErrorMsg(ACMgen.msg.getMessage("smvcode_error_msg"));
				System.exit(-1);
			}
		} catch (IOException e) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("smvcode_error_msg") +
	        		e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		} catch (InterruptedException e) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("pep2smv_term__error_msg") +
	        		e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		
		return v;
	}
	
	/**
	 * Prints error messages to the stderr.
	 * @param errors - a string with the error message
	 */
	public void printErrorMsg(String errors) {
		
		System.err.println(errors);
	}
	
	/**
	 * Creates the valid command line options
	 */
	private void createOptions() {
		
		Option op_help = new Option("h", "help", false,
				ACMgen.msg.getMessage("help_desc"));
		
		Option op_rrbb = new Option("r", "rrbb", true,
				ACMgen.msg.getMessage("rrbb_desc"));
			op_rrbb.setArgs(1);
			op_rrbb.setArgName("size");
		Option op_owbb = new Option("o", "owbb", true,
				ACMgen.msg.getMessage("owbb_desc"));
			op_owbb.setArgs(1);
			op_owbb.setArgName("size");
		Option op_owrrbb = new Option("b", "owrrbb", true,
				ACMgen.msg.getMessage("owrrbb_desc"));
			op_owrrbb.setArgs(1);
			op_owrrbb.setArgName("size");
		
		Option op_cpp = new Option("C", "cpp", false, 
				ACMgen.msg.getMessage("cpp_desc"));
		Option op_verilog = new Option("v", "verilog", false, 
				ACMgen.msg.getMessage("verilog_desc"));
		Option op_pep = new Option("p", "pep", false, 
				ACMgen.msg.getMessage("pep_desc"));
		Option op_petrify = new Option("P", "petrify", false,
				ACMgen.msg.getMessage("petrify_desc"));
		Option op_smv = new Option("s", "smv", false, 
				ACMgen.msg.getMessage("smv_desc"));
		Option op_smv_pn = new Option("S", "smvpn", false, 
				ACMgen.msg.getMessage("smvpn_desc"));
		
		OptionGroup op_acm_group = new OptionGroup();
		op_acm_group.setRequired(true);
		op_acm_group.addOption(op_help);
		op_acm_group.addOption(op_rrbb);
		op_acm_group.addOption(op_owbb);
		op_acm_group.addOption(op_owrrbb);
		
		this.options = new Options();
		this.options.addOptionGroup(op_acm_group);
		this.options.addOption(op_cpp);
		this.options.addOption(op_verilog);
		this.options.addOption(op_pep);
		this.options.addOption(op_petrify);
		this.options.addOption(op_smv);
		this.options.addOption(op_smv_pn);
	}
	
	/**
	 * Parses the command line parameters
	 * @param line - the command line parameters
	 * @param options - the valid options
	 */
	private void parseCmdLineOp(CommandLine line, Options options) {
		
		if (line.hasOption('h')) {
			HelpFormatter f = new HelpFormatter();
			f.printHelp(ACMgen.msg.getMessage("cmdlineoptions"), options);
		} else if (line.hasOption('r')) {
			this.ACM_TYPE = ACMgenOptions.t_rrbb;
			this.ACM_SIZE = Integer.parseInt(line.getOptionValue('r'));
		} else if (line.hasOption('o')) {
			this.ACM_TYPE = ACMgenOptions.t_owbb;
			this.ACM_SIZE = Integer.parseInt(line.getOptionValue('o'));
		} else if (line.hasOption("b")) {
			this.ACM_TYPE = ACMgenOptions.t_owrrbb;
			this.ACM_SIZE = Integer.parseInt(line.getOptionValue('b'));
		}
		
		if (line.hasOption('C')) {
			this.a_cpp = true;
		}
		
		if (line.hasOption('v')) {
			this.a_verilog = true;
		}
		
		if (line.hasOption('p')) {
			this.a_pep = true;
		} 
		
		if (line.hasOption('P')) {
			this.a_petrify = true;
		} 
		
		if (line.hasOption("s")) {
			this.a_smv = true;
		}
		
		if (line.hasOption("S")) {
			this.a_smv_pn = true;
		}
	}
}
