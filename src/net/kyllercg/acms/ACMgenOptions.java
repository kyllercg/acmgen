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

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class ACMgenOptions {
	
	public static final int t_rrbb 				= 1;
	public static final int t_owbb 				= 2;
	public static final int t_owrrbb 			= 3;
	
	public static final String rrbb 			= "rrbb";
	public static final String owbb 			= "owbb";
	public static final String owrrbb 			= "owrrbb";
	
	public static String output_file			= "";
	public static String pep2smv_file			= "";
	public static String props_file				= "config/ACMgen.conf";
	public static String i18BaseName			= "config/i18";
	public static String gladeFile				= "config/acmgen_gui.glade";
	
	public static String tmp_dir				= "";
	
	public static final String cpp_ext			= ".cpp";
	public static final String h_ext			= ".h";
	public static final String llnet_ext		= ".ll_net";
	public static final String petrify_ext		= ".p";
	public static final String smv_ext			= ".smv";
	public static final String verilog_ext		= ".v";
	
	public static final String output_key		= "output";
	public static final String pep2smv_key		= "pep2smv";
	public static final String tmp_dir_key		= "tmp_dir";
	
	public static String rrbb_c_rd		= "templates/rrbb_C_Reader";
	public static String rrbb_c_wr		= "templates/rrbb_C_Writer";
	public static String owbb_c_rd		= "templates/owbb_C_Reader";
	public static String owbb_c_wr		= "templates/owbb_C_Writer";
	public static String owrrbb_c_rd	= "templates/owrrbb_C_Reader";
	public static String owrrbb_c_wr	= "templates/owrrbb_C_Writer";
	
	public static String rrbb_smv		= "templates/rrbb_SMV.smv";
	public static String owbb_smv		= "templates/owbb_SMV.smv";
	public static String owrrbb_smv		= "templates/owrrbb_SMV.smv";
	
	public static String rrbb_smvpn		= "templates/rrbb_SMV_PN.smv";
	public static String owbb_smvpn		= "templates/owbb_SMV_PN.smv";
	public static String owrrbb_smvpn	= "templates/owrrbb_SMV_PN.smv";
	
	public static String flip_flop_v	= "templates/verilog_flip_flop.v";
	public static String mux_v			= "templates/verilog_mux.v";
	
	public static String rrbb_v			= "templates/verilog_rrbb.v";
	public static String rrbb_v_rd		= "templates/verilog_rrbb_reader.v";
	public static String rrbb_v_rde		= "templates/verilog_rrbb_reader_engine.v";
	public static String rrbb_v_wr		= "templates/verilog_rrbb_writer.v";
	public static String rrbb_v_wre		= "templates/verilog_rrbb_writer_engine.v";
	public static String rrbb_v_sm		= "templates/verilog_rrbb_shmem.v";
	
	public static String owbb_v			= "templates/owrrbb_Verilog.v";
	public static String owbb_v_rd		= "templates/owrrbb_Verilog_Reader.v";
	public static String owbb_v_rde		= "templates/owrrbb_Verilog_Reader.v";
	public static String owbb_v_wr		= "templates/owrrbb_Verilog_Writer.v";
	public static String owbb_v_wre		= "templates/owrrbb_Verilog_Writer.v";
	public static String owbb_v_sm		= "templates/owrrbb_Verilog_ShMem.v";
	
	public static String owrrbb_v		= "templates/owrrbb_Verilog.v";
	public static String owrrbb_v_rd	= "templates/owrrbb_Verilog_Reader.v";
	public static String owrrbb_v_rde	= "templates/owrrbb_Verilog_Reader.v";
	public static String owrrbb_v_wr	= "templates/owrrbb_Verilog_Writer.v";
	public static String owrrbb_v_wre	= "templates/owrrbb_Verilog_Writer.v";
	public static String owrrbb_v_sm	= "templates/owrrbb_Verilog_ShMem.v";
	
	public static void setPrefix(String prefix) {
		
		props_file		= prefix + props_file;
		i18BaseName		= prefix + i18BaseName;
		gladeFile		= prefix + gladeFile;
		
		rrbb_c_rd		= prefix + rrbb_c_rd;
		rrbb_c_wr		= prefix + rrbb_c_wr;
		owbb_c_rd		= prefix + owbb_c_rd;
		owbb_c_wr		= prefix + owbb_c_wr;
		owrrbb_c_rd		= prefix + owrrbb_c_rd;
		owrrbb_c_wr		= prefix + owrrbb_c_wr;
		
		rrbb_smv		= prefix + rrbb_smv;
		owbb_smv		= prefix + owbb_smv;
		owrrbb_smv		= prefix + owrrbb_smv;
		
		rrbb_smvpn		= prefix + rrbb_smvpn;
		owbb_smvpn		= prefix + owbb_smvpn;
		owrrbb_smvpn	= prefix + owrrbb_smvpn;
		
		flip_flop_v		= prefix + flip_flop_v;
		mux_v			= prefix + mux_v;
		
		rrbb_v			= prefix + rrbb_v;
		rrbb_v_rd		= prefix + rrbb_v_rd;
		rrbb_v_rde		= prefix + rrbb_v_rde;
		rrbb_v_wr		= prefix + rrbb_v_wr;
		rrbb_v_wre		= prefix + rrbb_v_wre;
		rrbb_v_sm		= prefix + rrbb_v_sm;
		
		owbb_v			= prefix + owbb_v;
		owbb_v_rd		= prefix + owbb_v_rd;
		owbb_v_rde		= prefix + owbb_v_rde;
		owbb_v_wr		= prefix + owbb_v_wr;
		owbb_v_wre		= prefix + owbb_v_wre;
		owbb_v_sm		= prefix + owbb_v_sm;
		
		owrrbb_v		= prefix + owrrbb_v;
		owrrbb_v_rd		= prefix + owrrbb_v_rd;
		owrrbb_v_rde		= prefix + owrrbb_v_rde;
		owrrbb_v_wr		= prefix + owrrbb_v_wr;
		owrrbb_v_wre	= prefix + owrrbb_v_wre;
		owrrbb_v_sm		= prefix + owrrbb_v_sm;
	}
}
