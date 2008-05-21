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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import net.kyllercg.util.I18Printer;
import net.kyllercg.util.ReadFile;

import org.gnu.glade.*;
import org.gnu.gtk.*;

/**
 * @author Kyller Costa Gorgônio
 * @version 1.2
 */
public class ACMgen_gtk extends ACMgen {
	
	private LibGlade glade = null;
	private TextView console = null;
	private Entry acm_size = null;
	private Entry e_filename = null;
	
	/**
	 * The main function
	 * @param args
	 */
	public static void main(String[] args) {

		ACMgen_gtk acm;
		
		try {
			
			Gtk.init(args);
			acm = new ACMgen_gtk();
			acm.doNothing(); // only to remove the warning message
			Gtk.main();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Class constructor
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws GladeXMLException
	 */
	public ACMgen_gtk() throws GladeXMLException, FileNotFoundException,
								IOException {
		
		this.ACM_TYPE = ACMgenOptions.t_rrbb;
		ACMgen.msg = new I18Printer(ACMgenOptions.i18BaseName);
		
		if (System.getProperty("acmgen.dir") != null) {
			
			if (System.getProperty("acmgen.dir").endsWith("/")) {
				
				ACMgenOptions.setPrefix(System.getProperty("acmgen.dir"));
			} else {
				
				ACMgenOptions.setPrefix(System.getProperty("acmgen.dir") + "/");
			}
		}
		
		ReadFile rf = new ReadFile();
		Properties props = rf.readPropsFile(ACMgenOptions.props_file);
		
		ACMgenOptions.pep2smv_file = 
			props.getProperty(ACMgenOptions.pep2smv_key);
    	ACMgenOptions.output_file = 
    		props.getProperty(ACMgenOptions.output_key);
    	ACMgenOptions.tmp_dir = props.getProperty(ACMgenOptions.tmp_dir_key);
		
		// Read & parse the glade-file...
        this.glade = new LibGlade(ACMgenOptions.gladeFile, this);
        
        this.getGtkWidgets();
        this.setI18n();
	}
	
	/**
	 * Prints messages on the GUI console.
	 * @param mesg - the message to be printed
	 */
	public void printErrorMsg(String mesg) {
		
		TextBuffer b = new TextBuffer();
		b = console.getBuffer();
		b.insertText(b.getEndIter(), mesg + "\n");
		console.setBuffer(b);
		console.scrollToIter(b.getEndIter(), 0.0);
	}
	
	/* *********************************************************************
	 * SIGNAL (EVENTS) HANDLING
	 * ********************************************************************* */
	
	public void on_MainWindow_delete_event() {
		
		Gtk.mainQuit();
	}
	
	public void on_radiobutton_rrbb_pressed() {
		
		this.ACM_TYPE = ACMgenOptions.t_rrbb;
		this.printErrorMsg("rrbb = " + this.ACM_TYPE);
	}
	
	public void on_radiobutton_owbb_pressed() {
		
		this.ACM_TYPE = ACMgenOptions.t_owbb;
		this.printErrorMsg("owbb = " + this.ACM_TYPE);
	}
	
	public void on_radiobutton_owrrbb_pressed() {
		
		this.ACM_TYPE = ACMgenOptions.t_owrrbb;
		this.printErrorMsg("owrrbb = " + this.ACM_TYPE);
	}
	
	public void on_checkbutton_cpp_toggled() {
		
		this.a_cpp = !this.a_cpp;
		this.printErrorMsg("c++ = " + a_cpp);
	}
	
	public void on_checkbutton_verilog_toggled() {
		
		this.a_verilog = !this.a_verilog;
		this.printErrorMsg("verilog = " + a_verilog);
	}
	
	public void on_checkbutton_pep_toggled() {
		
		this.a_pep = !this.a_pep;
		this.printErrorMsg("pep = " + a_pep);
	}

	public void on_checkbutton_smv_toggled() {
		
		this.a_smv = !this.a_smv;
		this.printErrorMsg("smv = " + a_smv);
	}

	public void on_checkbutton_smvpn_toggled() {
		
		this.a_smv_pn = !this.a_smv_pn;
		this.printErrorMsg("smv+pn = " + a_smv_pn);
	}
	
	public void on_toolbutton_about_clicked() {
		
	}
	
	public void on_toolbutton_gen_clicked() {
		
		try {
			
			this.ACM_SIZE = Integer.parseInt(this.acm_size.getText());
			
			if (!this.e_filename.getText().equals("")) {
				
				this.setFileBaseName(this.e_filename.getText());
			} else {
				
				this.setFileBaseName();
			}
			
			this.genACM();
			this.printACMcode();
		} catch (NumberFormatException e) {
			
			this.printErrorMsg(ACMgen.msg.getMessage("number_format_error_msg"));
			e.printStackTrace();
		}
	}
	
	public void on_toolbutton_quit_clicked() {
		
		Gtk.mainQuit();
	}

	/* *********************************************************************
	 * GTK WIDGETS
	 * ********************************************************************* */
	
	/**
	 * This method is sets all labels with the correct locale messages.
	 */
	private void setI18n() {
		
		// Label widgets		
		Label l = (Label) glade.getWidget("label_acm_op");
		l.setLabel("<b>" + ACMgen.msg.getMessage("label_acm_op.label") + "</b>");
		
		l = (Label) glade.getWidget("label_code_op");
		l.setLabel("<b>" + ACMgen.msg.getMessage("label_code_op.label") + "</b>");
		
		l = (Label) glade.getWidget("label_filename");
		l.setLabel(ACMgen.msg.getMessage("label_filename.label"));
		
		l = (Label) glade.getWidget("label_size");
		l.setLabel(ACMgen.msg.getMessage("label_size.label"));
		
		// ToolButton widgets
		ToolButton b = (ToolButton) glade.getWidget("toolbutton_about");
		b.setLabel(ACMgen.msg.getMessage("toolbutton_about.label"));
		
		b = (ToolButton) glade.getWidget("toolbutton_gen");
		b.setLabel(ACMgen.msg.getMessage("toolbutton_gen.label"));
		
		b = (ToolButton) glade.getWidget("toolbutton_quit");
		b.setLabel(ACMgen.msg.getMessage("toolbutton_quit.label"));
		
		// Window widgets
		Window w = (Window) glade.getWidget("MainWindow");
		w.setTitle(ACMgen.msg.getMessage("MainWindow.title"));
	}
	
	/**
	 * This method is to connect the GTK widgets to Java objects. Also, it sets
	 * all labels with the correct locale.
	 */
	private void getGtkWidgets() {
		
		// Entry widgets
		this.acm_size = (Entry) this.glade.getWidget("entry_size");
		
		// Entry widgets
		this.e_filename = (Entry) this.glade.getWidget("entry_filename");
		
		// TextView widgets
		this.console = (TextView) this.glade.getWidget("textview_console");
	}
	
	/**
	 * This method does nothing and exists only to remove a warning message in
	 * the main method.
	 * warning --> The local variable acm is never read
	 */
	private void doNothing() {
		
		// do nothing
	}
}
