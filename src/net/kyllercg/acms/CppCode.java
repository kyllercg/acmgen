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
 * @version 1.1
 */
public abstract class CppCode extends SourceCode {
	
	protected static final String dec_text			= "%%%ACMgen:DECLARE_VARS%%%";
	protected static final String dt_ext_text		= "%%%ACMgen:DtExternal()%%%";
	protected static final String dt_local_text		= "%%%ACMgen:DtLocal()%%%";
	protected static final String init_ext_text		= "%%%ACMgen:InitExternal()%%%";
	protected static final String init_local_text	= "%%%ACMgen:InitLocal()%%%";
	protected static final String init_reader		= "%%%ACMgen:Reader()%%%";
	protected static final String init_writer		= "%%%ACMgen:Writer()%%%";
	
	protected static final String ctrlv_text		= "CTRLV";
	protected static final String hash_text			= "HASH";
	protected static final String marking_text		= "MARKING";
	protected static final String shmid_text		= "SHMID";
	
	protected static final String shmid_s			= "_shmid";
	protected static final String shmid_t			= "int";
	protected static final String shv_t				= "bool *";
	protected static final String ctrlv_t			= "bool";
	
	protected static final String if_shmget_src	= "" +
	"\tif ((SHMID = shmget(SHMKEY1 + HASH, sizeof(" +  ctrlv_t +
		"), PERMS | IPC_CREAT)) < 0) {\n\n" +
	"\t\tcout << \"Error creating SHMID. Cannot exec shmget()\" << endl;\n" +
	"\t\texit(errno);\n" +
	"\t}\n";
	protected static final String if_shmat_src	= "" +
	"\tif ((CTRLV = (" + shv_t + ") shmat(SHMID, (" + shv_t +
		") 0, 0)) == (" + shv_t + ") -1) {\n\n" +
	"\t\tcout << \"Error creating CTRLV. Cannot exec shmat()\" << endl;\n" +
	"\t\texit(errno);\n" +
	"\t}\n" +
	"\t*CTRLV = MARKING;\n\n";
	protected static final String if_shmdt_src		= "\tif (shmdt(CTRLV)) {\n\n" +
	"\t\tcout << \"Error destroying CTRLV. Cannot exec shmdt()\" << endl;\n" +
	"\t\texit(errno);\n" +
	"\t}\n\n";
	protected static final String if_shmget_dest	= "" +
	"\twhile ((SHMID = shmget((SHMKEY1 + HASH), sizeof(" +  ctrlv_t +
		"), 0)) < 0) {\n\n" +
	"\t\tcout << \"Error creating SHMID. Cannot exec shmget()\" << endl;\n" +
	"\t\tkill(pair_pid, SIGCONT);" +
	"\t\tsleep(1);" +
	"//\t\texit(errno);\n" +
	"\t}\n";
	protected static final String if_shmat_dest	= "" +
	"\twhile ((CTRLV = (" + shv_t + ") shmat(SHMID, (" + shv_t +
		") 0, 0)) == (" + shv_t + ") -1) {\n\n" +
	"\t\tcout << \"Error creating CTRLV. Cannot exec shmat()\" << endl;\n" +
	"\t\tkill(pair_pid, SIGCONT);" +
	"\t\tsleep(1);" +
	"//\t\texit(errno);\n" +
	"\t}\n";
	protected static final String if_shmdt_dest	= if_shmdt_src +
	"\tif (shmctl(SHMID, IPC_RMID, (struct shmid_ds *) 0) < 0) {\n\n" +
	"\t\tcout << \"Error destroying SHMID. Cannot exec shmctl()\" << endl;\n" +
	"\t\texit(errno);\n" +
	"\t}\n\n";

	/**
	 * The C++ code for the reader process.
	 */
	protected Vector<String> reader_cpp = null;
	
	/**
	 * The C++ header for the reader process.
	 */
	protected Vector<String> reader_h = null;
	
	/**
	 * The C++ code for the writer process.
	 */
	protected Vector<String> writer_cpp = null;
	
	/**
	 * The C++ header for the writer process.
	 */
	protected Vector<String> writer_h = null;
	
	/**
	 * Class constructor.
	 */
	public CppCode() {
		
		super();
	}
	
	/**
	 * Generates the C++ code for the ACM.
	 * 
	 * @param reader_cpp - a string with the reader C++ template file
	 * @param reader_h - a string with the reader header template file
	 * @param writer_cpp - a string with the writer c++ template file
	 * @param writer_h - a string with the writer header template file
	 */
	public void genCode(Vector<String> reader_cpp, Vector<String> reader_h, 
			Vector<String> writer_cpp, Vector<String> writer_h) {
		
		this.reader_cpp = reader_cpp;
		this.reader_h = reader_h;
		this.writer_cpp = writer_cpp;
		this.writer_h = writer_h;
		
		this.genReader();
		this.genWriter();
	}
	
	/**
	 * Generates the C++ code for the RRBB reader process.
	 */
	protected void genReader() {
		
		fixReader_h();
		fixReader_cpp();
	}
	
	/**
	 * Generates the C++ code for the RRBB writer process.
	 */
	protected void genWriter() {
		
		fixWriter_h();
		fixWriter_cpp();
	}
	
	protected abstract void fixReader_h();
	protected abstract void fixReader_cpp();
	protected abstract void fixWriter_h();
	protected abstract void fixWriter_cpp();
	
	/**
	 * Gets the C++ code for the RRBB reader process.
	 * 
	 * @return - a vector of strings with the C++ code for the RRBB reader
	 *  process
	 */
	public Vector<String> getReader_cpp() {
		
		return reader_cpp;
	}
	
	/**
	 * Gets the C++ header code for the RRBB reader process.
	 * 
	 * @return - a vector of strings with the C++ header code for the RRBB
	 *  reader process
	 */
	public Vector<String> getReader_h() {
		
		return reader_h;
	}
	
	/**
	 * Gets the C++ code for the RRBB writer process.
	 * 
	 * @return - a vector of strings with the C++ code for the RRBB writer
	 *  process
	 */
	public Vector<String> getWriter_cpp() {
		
		return writer_cpp;
	}
	
	/**
	 * Gets the C++ header code for the RRBB writer process.
	 * 
	 * @return - a vector of strings with the C++ header code for the RRBB
	 *  writer process
	 */
	public Vector<String> getWriter_h() {
		
		return writer_h;
	}
}
